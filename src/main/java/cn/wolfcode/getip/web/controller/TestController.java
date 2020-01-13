package cn.wolfcode.getip.web.controller;

import cn.wolfcode.getip.domain.User;
import cn.wolfcode.getip.service.IIPService;
import cn.wolfcode.getip.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    @Autowired
    private IIPService ipService;

    @RequestMapping("/getip")
    @ResponseBody
    public Result getip(User user, HttpServletRequest request){
        String ip = request.getRemoteAddr();


        Boolean flag = ipService.register(ip,user);
        System.err.println(ip);
        if (flag){
            return new Result(true,"注册成功");
        }else {
            return new Result(false,"IP注册太频繁,请24小时候再注册");
        }

    }
}
