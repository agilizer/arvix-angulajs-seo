package cn.arvix.angularjs.seo.service;

import com.paulhammant.ngwebdriver.NgWebDriver;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SeoServiceImpl implements SeoService {
    private final Logger logger = LoggerFactory.getLogger(SeoServiceImpl.class);
    @Autowired
    private StringRedisTemplate template;
    @Value("${cache.timeout}")
    private long timeout = 1;
    @Value("${cache.timeout.timeunit}")
    private String timeoutUnitStr = "DAYS";
    private HtmlUnitDriver driver = new HtmlUnitDriver(true);
    @Value("${cache.domain}")
    private String cacheDomain;
    private TimeUnit timeoutUnit = null;
    
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式

    @PostConstruct
    public void init() {
        timeoutUnit = TimeUnit.valueOf(timeoutUnitStr);
    }

    @Override
    public String genHtml(String sourceUrl) {

        String result = "ERROR:sourceUrl:" + sourceUrl;
        if (null == sourceUrl) {
            return result;
        }
        if (sourceUrl.indexOf("_escaped_fragment_=") > 0) {
            sourceUrl = sourceUrl.replace("_escaped_fragment_=", "");
            if (sourceUrl.endsWith("?")) {
                sourceUrl = sourceUrl.substring(0, sourceUrl.length() - 1);
            }
        }
        if (sourceUrl.startsWith(cacheDomain)) {
            String tempResult = template.opsForValue().get(sourceUrl);
            if (tempResult != null) {
                logger.info("cache get ,sourceUrl :{}", sourceUrl);
                return tempResult;
            }
        }
        try {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().timeouts()
                    .setScriptTimeout(40, TimeUnit.SECONDS);
            driver.get(sourceUrl);
            NgWebDriver ngWebDriver = new NgWebDriver(driver);
            ngWebDriver.waitForAngularRequestsToFinish();
            // 过滤script标签
            result = driver.getPageSource();
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(result);
            result = m_script.replaceAll("");
            if (sourceUrl.startsWith(cacheDomain)) {
                template.opsForValue().set(sourceUrl, result, timeout, timeoutUnit);
                logger.info("cache set,sourceUrl :{}", sourceUrl);
            }
        } catch (Exception e) {
            result = "ERROR:" + e.getMessage();
            logger.error("fetch url:", sourceUrl, e);
        }
        return result;
    }

    @Override
    public void removeCache(String sourceUrl) {
        if (sourceUrl != null) {
            template.delete(sourceUrl);
        }
    }

    @Override
    public void cleanCache() {
        template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return "success";
            }
        });
    }

    @Override
    public String getCacheDomain() {
        return this.cacheDomain;
    }

    @Override
    public String push(String sourceUrl, String document) {
        template.opsForHash().put(NG2_REDIS_MAP_KEY, sourceUrl, document);
        return "success";
    }

    @Override
    public String get(String sourceUrl) {
    	Object obj =  template.opsForHash().get(NG2_REDIS_MAP_KEY, sourceUrl);
    	if(obj!=null){
    		return (String) obj;
    	}
        return null;
    }

}
