package com.hedatou.wedding.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hedatou.wedding.service.UserService;

@ControllerAdvice
public class AuthHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/user/")) {
            for (Cookie cookie : request.getCookies())
                if (cookie.getName().equals("l") && userService.getUser(cookie.getValue()) != null)
                    return true;
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

}