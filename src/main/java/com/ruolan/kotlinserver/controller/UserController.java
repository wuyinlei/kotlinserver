package com.ruolan.kotlinserver.controller;


import com.ruolan.kotlinserver.common.Constants;
import com.ruolan.kotlinserver.domain.ModifyPwdRequest;
import com.ruolan.kotlinserver.domain.UpdateRequest;
import com.ruolan.kotlinserver.domain.base.BaseResponse;
import com.ruolan.kotlinserver.domain.LoginRequest;
import com.ruolan.kotlinserver.domain.RegisterRequest;
import com.ruolan.kotlinserver.model.UserInfo;
import com.ruolan.kotlinserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
@RequestMapping(produces = {"application/json;charset=UTF-8"}, value = {"/user"})
public class UserController extends BaseController  {

    @Autowired
    private UserService userService;

    /**
     * 注册接口
     *
     * @param request request
     * @return BaseResponse
     */
    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<UserInfo> register(@RequestBody RegisterRequest request) {
        BaseResponse resp = new BaseResponse();
        String mobile = request.getMobile();
        String verifyCode = request.getVerifyCode();
        UserInfo userInfo = this.userService.getUserByMobile(mobile);
        //判断数据库中是否存在了用户
        if (userInfo != null) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.ACCOUNT_HAS_BEEN_REGISTERED);
            return resp;
        }
        //验证验证码  这个地方写死了先  todo
        if (!"123456".equals(verifyCode)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.VERIFY_CODE_ERROR);
            return resp;
        }

        //注册逻辑
        userInfo = new UserInfo();
        userInfo.setUserMobile(mobile);
        userInfo.setUserName(mobile);
        userInfo.setUserPwd(request.getPwd());
        userInfo.setUserIcon("http://image.xinliji.me/Fo057Cf3KCXPcTWb6WPKzaUztXvB");
        //写入数据库中
        int updateElement = this.userService.addUser(userInfo);
        userInfo.setUserPwd(null);
        if (updateElement == 1) {
            return new BaseResponse<>(userInfo);
        } else {
            return new BaseResponse(Constants.CODE.ERROR_CODE, Constants.MESSAGE.ACCOUNT_REGISTER_ERROR);
        }
    }

    /**
     * 登录接口
     *
     * @param loginRequest LoginRequest
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<UserInfo> login(HttpServletRequest request,@RequestBody LoginRequest loginRequest) {
        BaseResponse resp = new BaseResponse();
        String mobile = loginRequest.getMobile();
        String password = loginRequest.getPassword();

//        request.getSession()

        if (StringUtils.isEmpty(mobile)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.PHONE_NUMBER_IS_EMPTY);
            return resp;
        }

        if (StringUtils.isEmpty(password)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.PASSWORD_IS_EMPTY);
            return resp;
        }

        UserInfo userInfo = userService.getUserByMobile(mobile);

        if (userInfo == null) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.USER_IS_EMPTY);
            return resp;
        }

        if (!password.equals(userInfo.getUserPwd())) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.USER_PASSWORD_ERROR);
            return resp;
        }

        if (!StringUtils.isEmpty(loginRequest.getPushId())) {
            userInfo.setPushId(loginRequest.getPushId());
        }

        //每次在登录的时候执行这个
        request.getSession().setAttribute(Constants.MESSAGE.CURRENT_USER, userInfo);

        userInfo.setUserPwd(password);

        this.userService.modifyUser(userInfo);

        sendMessage(userInfo.getId(), loginRequest.getPushId());
        //置密码为空
        userInfo.setUserPwd(null);
        resp.setStatus(Constants.CODE.SUCCESS_CODE);
        resp.setMessage(Constants.MESSAGE.LOGIN_SUCCESS);
        resp.setData(userInfo);

        return resp;
    }

    /**
     * 发送消息
     *
     * @param userId 用户id
     * @param pushId 推送id
     */
    private void sendMessage(Integer userId, String pushId) {
        // TODO: 2018/1/16 发送消息  集成jpush


    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<UserInfo> resetPassword(@RequestBody ModifyPwdRequest modifyPwdRequest) {
        BaseResponse resp = new BaseResponse();

        String mobile = modifyPwdRequest.getMobile();
        String oldPassword = modifyPwdRequest.getOldPassword();
        String newPassword = modifyPwdRequest.getNewPassword();

        if (StringUtils.isEmpty(mobile)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.PHONE_NUMBER_IS_EMPTY);
            return resp;
        }

        if (StringUtils.isEmpty(oldPassword)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.PASSWORD_IS_EMPTY);
            return resp;
        }

        if (StringUtils.isEmpty(newPassword)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.NEW_PASSWORD_IS_EMPTY);
            return resp;
        }

        UserInfo userInfo = userService.getUserByMobile(mobile);
        if (userInfo == null) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.USER_IS_EMPTY);
            return resp;
        }

        if (!userInfo.getUserPwd().equals(oldPassword)) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.USER_PASSWORD_ERROR);
            return resp;
        }

        userInfo.setUserPwd(newPassword);

        int result = this.userService.modifyUser(userInfo);

        if (result > 0) {
            resp.setStatus(Constants.CODE.SUCCESS_CODE);
            resp.setMessage(Constants.MESSAGE.MODIFY_PASSWORD_SUCCESS);
            return resp;
        } else {
            resp.setStatus(-1);
            resp.setMessage(Constants.MESSAGE.MODIFY_PASSWORD_ERROR);
            return resp;
        }
    }

    /**
     * 更新用户信息
     *
     * @param updateRequest UpdateRequest
     * @return BaseResponse
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<UserInfo> update(@RequestBody UpdateRequest updateRequest) {
        BaseResponse resp = new BaseResponse();

        if (updateRequest.getId() < 0) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.USER_IS_EMPTY);
            return resp;
        }

        UserInfo userInfo = this.userService.getUserById(updateRequest.getId());
        if (userInfo == null) {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.USER_IS_EMPTY);
            return resp;
        }

        userInfo.setUserName(updateRequest.getUserName());
        userInfo.setUserIcon(updateRequest.getUserIcon());
        userInfo.setUserGender(updateRequest.getGender());
        userInfo.setUserSign(updateRequest.getSign());
        userInfo.setUserAddress(updateRequest.getAddress());
        userInfo.setUserNickName(updateRequest.getNickname());
        userInfo.setUserRealName(updateRequest.getUserName());

        //更新用户信息
        int updateElement = userService.modifyUser(userInfo);
        if (updateElement == 1) {

            userInfo.setUserPwd(null);
            resp.setStatus(0);
            resp.setData(userInfo);
            return resp;
        } else {
            resp.setStatus(Constants.CODE.ERROR_CODE);
            resp.setMessage(Constants.MESSAGE.UPDATE_USER_INFO_ERROR);
            return resp;
        }

    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse logout(HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse(null);
        request.getSession().setAttribute(Constants.MESSAGE.CURRENT_USER, null);
        return baseResponse;
    }

}
