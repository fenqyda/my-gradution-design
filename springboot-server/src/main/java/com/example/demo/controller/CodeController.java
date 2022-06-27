package com.example.demo.controller;

import cn.hutool.json.JSONObject;
import com.example.demo.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
public class CodeController {
    @Autowired
    RedisUtils redisUtils;
    //短信平台相关参数
    //这个不用改

    @ResponseBody
    @GetMapping("/getcode")
    public String getCode(@RequestParam("Phone") String Phone, HttpSession httpSession){
        try {
            JSONObject json = null;
            //随机生成验证码
            String code = String.valueOf(new Random().nextInt(999999));

            //将验证码存到redis中,同时存入创建时间（也可以传入session）
            //以json存放，这里使用的是阿里的fastjson
            json = new JSONObject();
            json.set("Phone",Phone);
            json.set("code",code);
            json.set("createTime",System.currentTimeMillis());
            System.out.println(json);
            // 将认证码存入redis，有效时长5分钟
            redisUtils.set("verifyCode"+Phone,json,5L, TimeUnit.MINUTES);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @ResponseBody
    @PostMapping("/testCode")
    public boolean getCode(@RequestParam(value = "Phone") String Phone,
                           @RequestParam(value = "code") String code){
        JSONObject Vcode = (JSONObject)redisUtils.get("verifyCode"+Phone);
        System.out.println("Phone:"+Phone);
        System.out.println(Vcode);
        JSONObject verifyCode = (JSONObject)redisUtils.get("verifyCode"+Phone);
        System.out.println("传入的验证码是："+code);
        if(verifyCode.get("code").equals(code)){
            System.out.println("验证码正确");
            return true;
        }
        return false;
    }
}
