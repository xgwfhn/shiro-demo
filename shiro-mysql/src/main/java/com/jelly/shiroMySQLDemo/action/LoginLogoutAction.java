package com.jelly.shiroMySQLDemo.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginLogoutAction {
    @RequestMapping("/login")
    public ModelAndView admin(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        String msg = null;

        try {
            currentUser.login(token);
        } catch ( UnknownAccountException uae ) {
            msg = "UnknownAccount";
        } catch ( IncorrectCredentialsException ice ) {
            msg = "IncorrectCredentials";
        } catch ( LockedAccountException lae ) {
            msg = "LockedAccount";
        } catch ( ExcessiveAttemptsException eae ) {
            msg = "ExcessiveAttempts";
        }catch ( AuthenticationException ae ) {
            msg = "Unexpected Reason";
        }

        ModelAndView modelAndView = new ModelAndView();
        if(msg!=null){
            request.setAttribute("msg", msg);
            modelAndView.setViewName("login");
        }else{
            modelAndView.setViewName("admin");
        }

        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView adminList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        request.setAttribute("msg", "user click logout");
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }
}