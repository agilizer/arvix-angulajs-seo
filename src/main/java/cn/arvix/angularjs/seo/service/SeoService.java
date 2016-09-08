package cn.arvix.angularjs.seo.service;

public interface SeoService {
	String genHtml(String sourceUrl);
	void removeCache(String sourceUrl);
	void cleanCache();
	String getCacheDomain();
}
