package com.ruolan.kotlinserver.interceptor;

import com.google.gson.Gson;
import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.dao.UserDao;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {


        Object currentUser = request.getSession().getAttribute(Constants.MESSAGE.CURRENT_USER);
        if (currentUser != null) {
            return true;
        } else {

            String userId = request.getHeader("token");
            UserInfo userInfo = userDao.selectByPrimaryKey(Integer.valueOf(userId));

            if (userInfo != null) {
                request.getSession().setAttribute(Constants.MESSAGE.CURRENT_USER, userInfo);
                return true;
            }

            //设置response的字符集
            response.setCharacterEncoding("UTF-8");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(Constants.CODE.NO_PERMISSION_ERROR_CODE);
            baseResponse.setMessage(Constants.MESSAGE.NO_PERMISSION);
            Gson gson = new Gson();
            String json = gson.toJson(baseResponse);
            response.getWriter().write(json);
            return false;
        }
    }

}
