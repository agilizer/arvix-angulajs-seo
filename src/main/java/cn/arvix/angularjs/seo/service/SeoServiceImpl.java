package cn.arvix.angularjs.seo.service;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.paulhammant.ngwebdriver.NgWebDriver;

@Service
public class SeoServiceImpl implements SeoService{
	private final Logger logger = LoggerFactory.getLogger(SeoServiceImpl.class);
	private HtmlUnitDriver driver = new HtmlUnitDriver(true);
	@Override
	public String genHtml(String sourceUrl) {
		String result = "ERROR:sourceUrl:"+sourceUrl;
		try{
			if(sourceUrl!=null&&sourceUrl.trim().length()>0){
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);
				driver.get(sourceUrl);
				NgWebDriver ngWebDriver = new NgWebDriver(driver);
				ngWebDriver.waitForAngularRequestsToFinish();
				result = driver.getPageSource();
			}
		}catch(Exception e){
			result = "ERROR:"+e.getMessage();
			logger.error("fetch url:",sourceUrl,e);
		}
		return result;
	}

}
