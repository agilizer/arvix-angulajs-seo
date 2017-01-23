package cn.arvix.angularjs.seo.service;

public interface SeoService {
	String NG2_REDIS_MAP_KEY = "ng2";
    String genHtml(String sourceUrl);

    void removeCache(String sourceUrl);

    void cleanCache();

    String getCacheDomain();

    String push(String sourceUrl, String document);

    String get(String sourceUrl);

}
