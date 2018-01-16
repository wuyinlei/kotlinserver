package com.ruolan.kotlinserver.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        Object currentUser = request.getSession().getAttribute("currentUser");
        if (currentUser != null) {
            return true;
        } else {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            //在这个地方要去登录设置路径
            out.println("window.open ('" + request.getContextPath()
                    + "/user/login','_self')");
            out.println("</script>");
            out.println("</html>");
            return false;
        }
    }

}
