# arvix-angulajs-seo
项目主要用于生成angularjs渲染和ajax请求完成后的页面源码，以便于缓存和直接返回给seo。
主要使用selenium的htmlunitdriver

#springboot
详细可以访问:    http://start.spring.io/


#开发工具
需要集成 gradle.<br>
可以使用eclipse 或者 sts 或者idea.

#spring loaded
spring loaded作用为,更改代码后更新虚拟机字节码,即热部署.<br>
具体配置参考: http://wiselyman.iteye.com/blog/2146475<br>
spring loaded地址:https://github.com/spring-projects/spring-loaded <br>
jar包已经下载到devLib目录<br>
虚拟机参数示例:<br>
-javaagent:/home/asdtiang/workspace-sts-3.6.3.SR1/study/devLib/springloaded-1.2.3.RELEASE.jar -noverify
‪E:\study\devLib\springloaded-1.2.3.RELEASE.jar

#项目启动
直接运行cn.arvix.angularjs.seo.SeoApplication
可以 run as Java Application

#war包生成 
 gradle clean war   -Dfile.encoding=UTF-8  
 
#项目配置
都在application.properties下面。




 
