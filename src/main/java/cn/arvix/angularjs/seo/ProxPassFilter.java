package cn.arvix.angularjs.seo;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.arvix.angularjs.seo.bean.CacheAndNgVersionBean;
import cn.arvix.angularjs.seo.service.CacheAndNgVersionConfigService;
import cn.arvix.angularjs.seo.service.SeoService;

/**
 * @date: 2016/1/30 19:22
 */
@Component
public class ProxPassFilter implements Filter {
	private static final Logger log = LoggerFactory
			.getLogger(ProxPassFilter.class);
//	public ProxPassFilter(SeoService seoService){
//		this.seoService  = seoService;
//	}
	@Value("${spider.user.agent}")
	private String spiderUserAgent = "";
	private List<String> spiderList = new ArrayList<String>();
	@Autowired
	SeoService seoService;
	@Autowired
	CacheAndNgVersionConfigService cacheAndNgVersionConfigService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		spiderUserAgent = spiderUserAgent.toLowerCase();
		String[] temp = spiderUserAgent.split("\\|");
		for(String s:temp){
			if(!"".equals(s.trim())){
				spiderList.add(s);
			}
		}
		log.info("spiderUserAgent:{}",spiderUserAgent);
		log.info("spiderList:{}",spiderList);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		if (request.getHeaderNames().hasMoreElements()) {
			Enumeration<String> headers = request.getHeaderNames();
			String s = "";
			while (headers.hasMoreElements()) {
				s = headers.nextElement();
				log.info("headers.nextElement()--->{} : {}", s,
						request.getHeader(s));
			}
		}		
		String URI = request.getRequestURI();
		String queryStr = request.getQueryString();
		log.info("URI:{},queryStr:{}", URI,request.getQueryString());
		boolean isSeo = false;
		if(!URI.startsWith("/api/v1") && !"/".equals(URI) && URI.indexOf(".js")<0 && URI.indexOf(".css")<0
				&& URI.indexOf("png")<0 && URI.indexOf("swagger")<0	&& URI.indexOf("api-docs")<0){
			isSeo= true;
		}	
		if("/".equals(URI)&&"_escaped_fragment_=".equals(queryStr)){
			isSeo= true;
		}
		if(isSeo==false){
			String userAgent = request.getHeader("User-Agent");
			if(userAgent!=null){
				for(String spider:spiderList){
					if(userAgent.toLowerCase().contains(spider)){
						isSeo= true;
						log.info("isSeo true,userAgent:{},spider:{}",userAgent,spider);
						break;
					}
				}
			}
		}
		if(isSeo){
			String sourceUrl = genSourceUrl(request);
			log.info("sourceUrl:"+sourceUrl);
			if(request.getQueryString()!=null){
				sourceUrl = sourceUrl+"?"+request.getQueryString();
			}
			CacheAndNgVersionBean cacheAndNgVersion = cacheAndNgVersionConfigService.genCacheAndNgVersion(sourceUrl);
			String result = seoService.genHtml(sourceUrl,cacheAndNgVersion.isCache(),cacheAndNgVersion.getNgVersion());
			writeReponse(response,result);
			return;
		}		
		chain.doFilter(req, res);
	}
	private String genSourceUrl(HttpServletRequest request ){
		String portStr = "";
		if(request.getServerPort()!=80){
			portStr =  ":" + request.getServerPort(); //端口号 
		}
		String queryStr = "";
		if(request.getQueryString()!=null){
			queryStr = "?" + request.getQueryString(); 
		}
		String requestUrl = request.getScheme() //当前链接使用的协议
			    + "://" + request.getServerName()//服务器地址 
			    + portStr
			    + request.getContextPath() //应用名称，如果应用名称为
			    + request.getServletPath() //请求的相对url 
			    +queryStr; //请求参数
		return requestUrl;
	}
	private void writeReponse(HttpServletResponse response,String result) throws IOException{
		
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");		
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = result.getBytes();
			bos.write(buff, 0, buff.length);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	

	@Override
	public void destroy() {

	}
}
