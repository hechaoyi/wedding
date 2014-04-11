package com.hedatou.wedding.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hedatou.wedding.domain.User;
import com.hedatou.wedding.service.UserService;

@ControllerAdvice
public class AuthHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/user/")) {
            if (getUser(request) != null)
                return true;
            response.sendRedirect("/");
            return false;
        }
        if (request.getRequestURI().startsWith("/admin/")) {
            User user = getUser(request);
            if (user != null && user.isAdmin())
                return true;
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

    private User getUser(HttpServletRequest request) {
        User user = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("l")) {
                    user = userService.getUser(cookie.getValue());
                    break;
                }
            }
        }
        return user;
    }

}