package com.atguigu.gmall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.passport.config.JwtUtil;
import com.atguigu.gmall.service.UserInfoService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PassportController {

    @Reference
    private UserInfoService userInfoService;

    @Value("${token.key}")
    String signKey;

    @RequestMapping("index")
    public String index(HttpServletRequest request){
        String originUrl = request.getParameter("originUrl ");
        request.setAttribute("originUrl",originUrl);
        System.out.println("originUrl ====>>>>> " + originUrl);
        return "index";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest request, UserInfo userInfo){
        // 取得ip地址
        String remoteAddr  = request.getHeader("X-forwarded-for");
        if (userInfo!=null) {
            UserInfo loginUser = userInfoService.login(userInfo);
            if (loginUser == null) {
                return "fail";
            } else {
                // 生成token
                Map map = new HashMap();
                map.put("userId", loginUser.getId());
                map.put("nickName", loginUser.getNickName());
                String token = JwtUtil.encode(signKey, map, remoteAddr);
                System.out.println("token===>>> " + token);
                return token;
            }
        }
        return "fail";
    }


    @RequestMapping("verify")
    @ResponseBody
    public String verify(HttpServletRequest request){
        String token = request.getParameter("token");
        String currentIp = request.getParameter("currentIp");
        // 检查token
        // Map<String, Object> map = JwtUtil.decode(token, signKey, currentIp);
        Map<String, Object> map = JwtUtil.decode(token, signKey, currentIp);
        if (map!=null){
            // 检查redis信息
            String userId = (String) map.get("userId");
            UserInfo userInfo = userInfoService.verify(userId);
            if (userInfo!=null){
                return "success";
            }
        }
        return "fail";
    }





}
