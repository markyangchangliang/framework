package com.markyang.framework.auth.social.controller;

import com.markyang.framework.pojo.web.ResultVo;
import lombok.AllArgsConstructor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 社区控制器
 * @author yangchangliang
 */
@Controller
@AllArgsConstructor
@RequestMapping("/social-register")
public class SocialController {

    private final ProviderSignInUtils providerSignInUtils;

    @GetMapping
    public String socialRegister(HttpServletRequest request, Model model) {
        Connection<?> connection = this.providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        if (connection != null) {
            model.addAttribute("bind", true);
            model.addAttribute("providerId", connection.getKey().getProviderId());
            model.addAttribute("providerUserId", connection.getKey().getProviderUserId());
            model.addAttribute("displayName", connection.getDisplayName());
            model.addAttribute("imageUrl", connection.getImageUrl());
        } else {
            model.addAttribute("bind", false);
        }
        return "social-register";
    }

    @PostMapping("/do")
    @ResponseBody
    public ResultVo<Object> doRegister(HttpServletRequest request) {
        this.providerSignInUtils.doPostSignUp("1", new ServletWebRequest(request));
        return ResultVo.success("绑定成功");
    }
}
