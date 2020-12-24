package com.markyang.framework.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 根路径控制器
 * @author yangchangliang
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "home";
    }
}
