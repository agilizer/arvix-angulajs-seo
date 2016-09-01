package cn.arvix.angularjs.seo.controller;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.arvix.angularjs.seo.service.SeoService;

@Controller()
@RequestMapping("/api/v1/seo")
public class SeoController {
    
	@Autowired
	SeoService seoService;
	
	@ApiOperation(value = "send")
	@ResponseBody
	@RequestMapping(value = "/fetch", method = RequestMethod.POST)
	public String fetch(@RequestParam String sourceUrl, HttpServletRequest request) {
		return seoService.genHtml(sourceUrl);
	}

}
