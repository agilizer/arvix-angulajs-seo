package cn.arvix.angularjs.seo.bean;

import cn.arvix.angularjs.seo.service.SeoService.NgVersion;

public class CacheAndNgVersionBean {
	private boolean isCache;
	private NgVersion ngVersion;
	public CacheAndNgVersionBean(boolean isCache,NgVersion ngVersion){
		this.isCache = isCache;
		this.ngVersion = ngVersion;
	}
	public boolean isCache() {
		return isCache;
	}
	public void setCache(boolean isCache) {
		this.isCache = isCache;
	}
	public NgVersion getNgVersion() {
		return ngVersion;
	}
	public void setNgVersion(NgVersion ngVersion) {
		this.ngVersion = ngVersion;
	}
	
	
}
