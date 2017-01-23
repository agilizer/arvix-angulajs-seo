package cn.arvix.angularjs.seo.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.arvix.angularjs.seo.SeoConstants;
import cn.arvix.angularjs.seo.service.SeoService;
import cn.arvix.angularjs.seo.util.CachePage;

@Controller()
@RequestMapping("/api/v1/seo")
public class SeoController {

    @Autowired
    SeoService seoService;

    @ApiOperation(value = "fetch")
    @ResponseBody
    @RequestMapping(value = "/fetch", method = {RequestMethod.POST, RequestMethod.GET})
    public String fetch(@RequestParam String sourceUrl, HttpServletRequest request) {
        return seoService.genHtml(sourceUrl);
    }

    @ApiOperation(value = "remove")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam String sourceUrl, HttpServletRequest request) {
        seoService.removeCache(sourceUrl);
        return SeoConstants.SUCCESS;
    }


    @ApiOperation(value = "clean")
    @ResponseBody
    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public String clean(HttpServletRequest request) {
        seoService.cleanCache();
        return SeoConstants.SUCCESS;
    }

    @ApiOperation(value = "添加缓存")
    @ResponseBody
    @RequestMapping(value = "/ng2/put", method = RequestMethod.POST)
    public String push(@RequestBody CachePage cachePage) {
        return seoService.push(cachePage.getUrl(), cachePage.getDocument());
    }

    @ApiOperation(value = "获取缓存页面")
    @ResponseBody
    @RequestMapping(value = "/ng2/get", method = RequestMethod.GET)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "资源路径", name = "sourceUrl", dataType = "String", paramType = "query")
    })
    public String get(String sourceUrl) {
        return seoService.get(sourceUrl);
    }

}

