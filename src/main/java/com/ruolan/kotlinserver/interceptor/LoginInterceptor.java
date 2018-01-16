package com.ruolan.kotlinserver.interceptor;

import com.google.gson.Gson;
import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        Object currentUser = request.getSession().getAttribute("currentUser");
        if (currentUser != null) {
            return true;
        } else {
            //设置response的字符集
            response.setCharacterEncoding("UTF-8");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(Constants.CODE.ERROR_CODE);
            baseResponse.setMessage("请登录");
            Gson gson = new Gson();
            String json = gson.toJson(baseResponse);
            response.getWriter().write(json);
            return false;
        }
    }

}
