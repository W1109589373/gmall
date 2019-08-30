package com.atguigu.gmall.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.config.LoginRequire;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PaymentController {

        //@Reference
        //private OrderService orderService;

        @RequestMapping("index")
        @LoginRequire
        public String index(HttpServletRequest request, Model model){
            // 获取订单的id
//            String orderId = request.getParameter("orderId");
//            OrderInfo orderInfo = orderService.getOrderInfo(orderId);
//            model.addAttribute("orderId",orderId);
//            model.addAttribute("totalAmount",orderInfo.getTotalAmount());
           return "index";
        }

}
