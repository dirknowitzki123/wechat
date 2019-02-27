package com.by.bussiness.wechat.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.by.bussiness.wechat.service.IWeChatPasswordService;
import com.by.core.util.Page;

/**
 * Created by yiqr on 2017/6/8.
 */
@Controller
@RequestMapping(value = "/wechat/password")
public class WeChatPasswordController {

    @Autowired
    private IWeChatPasswordService passwordService;

    @RequestMapping(value = "first",method = RequestMethod.GET)
    public String first(HttpServletRequest request, HttpServletResponse response) {
        return  "/wechat/password/first.index";
    }

    @ResponseBody
    @RequestMapping(value = "/first/save",method = RequestMethod.POST)
    public Page<?> firstSave(Page<Object> page) {
        return  passwordService.firstSave(page);
    }

    @RequestMapping(value = "/second",method = RequestMethod.GET)
    public ModelAndView second(HttpServletRequest request, HttpServletResponse response, String phoneNo) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("phoneNo",phoneNo);
        mv.setViewName("/wechat/password/second.index");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/second/save",method = RequestMethod.POST)
    public Page<?> secondSave(Page<Object> page) {
        return  passwordService.secondSave(page);
    }

    @RequestMapping(value = "/third",method = RequestMethod.GET)
    public ModelAndView third(HttpServletRequest request, HttpServletResponse response, String phoneNo) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("phoneNo",phoneNo);
        mv.setViewName("/wechat/password/third.index");
        return mv;
    }
    
    //暂时停止和满得利的信息同步
    /*@ResponseBody
    @RequestMapping(value = "/mdlSave",method = RequestMethod.POST)
    public Page<?> mdlSave(@RequestBody Map<String, Object> map) {
        return passwordService.mdlSave(map);
    }*/

}
