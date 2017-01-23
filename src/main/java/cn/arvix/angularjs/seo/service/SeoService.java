package cn.arvix.angularjs.seo.service;

public interface SeoService {
	public static enum NgVersion{
		NG_VERSION_1("1"),
		NG_VERSION_2("2");
		private String version;
		NgVersion(String version){
			this.version = version;
		}
		public String getVersion(){
			return this.version;
		}
	}
	String genHtml(String sourceUrl,boolean cache,NgVersion ngVersion);
	String genHtml(String sourceUrl,NgVersion ngVersion);
	void removeCache(String sourceUrl);
	void cleanCache();
}
