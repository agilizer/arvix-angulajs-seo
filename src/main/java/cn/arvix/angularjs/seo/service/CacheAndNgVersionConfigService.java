package cn.arvix.angularjs.seo.service;

import cn.arvix.angularjs.seo.bean.CacheAndNgVersionBean;

/**
 * 缓存和ngVersion配置，支持多域名多版本。
 * @author abel
 *
 */
public interface CacheAndNgVersionConfigService {

	boolean isCacheDomain(String sourceUrl);
	CacheAndNgVersionBean genCacheAndNgVersion(String sourceUrl);
}

