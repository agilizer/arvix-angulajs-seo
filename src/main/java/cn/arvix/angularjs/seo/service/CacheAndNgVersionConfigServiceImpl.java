package cn.arvix.angularjs.seo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.arvix.angularjs.seo.bean.CacheAndNgVersionBean;
import cn.arvix.angularjs.seo.service.SeoService.NgVersion;

@Service
public class CacheAndNgVersionConfigServiceImpl implements CacheAndNgVersionConfigService{
	private final Logger logger = LoggerFactory.getLogger(CacheAndNgVersionConfigServiceImpl.class);
	@Value("#{'${cache.domain}'.split(',')}") 
	private List<String> cacheDomainList;
	@Value("#{'${cache.domain.ng.version}'.split(',')}") 
	private List<String> ngVersionList;
	
	@PostConstruct
	public void init(){
		logger.info("cache.domain:{}",cacheDomainList);
		logger.info("cache.domain.ng.version:{}",ngVersionList);
	}
	
	@Override
	public boolean isCacheDomain(String sourceUrl) {
		boolean result = false;
		for(String configDomain:cacheDomainList){
			if(sourceUrl.startsWith(configDomain)){
				result = true;
				break;
			}
		}
		return result;
	}

	@Override
	public CacheAndNgVersionBean genCacheAndNgVersion(String sourceUrl) {
		CacheAndNgVersionBean cacheAndNgVersion = new CacheAndNgVersionBean(false,NgVersion.NG_VERSION_1);
		for(int i=0;i<cacheDomainList.size();i++){
			if(sourceUrl.startsWith(cacheDomainList.get(i))){
				cacheAndNgVersion = new CacheAndNgVersionBean(true,NgVersion.valueOf(ngVersionList.get(i)));
				break;
			}
		}
		return cacheAndNgVersion;
	}


}
