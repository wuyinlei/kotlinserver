package com.ruolan.kotlinserver.common;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Constants {

    public class  CODE {

        public static final int ERROR_CODE = -1;

        public static final int SUCCESS_CODE = 0;

    }


    public class MESSAGE{

        //注册一些常量
        public static final String ACCOUNT_HAS_BEEN_REGISTERED = "账号已被注册";

        public static final String REQUEST_SUCCESS = "请求成功";

        public static final String VERIFY_CODE_ERROR = "验证码错误";

        public static final String ACCOUNT_OR_PASSWORD_ERROR = "账户或者密码错误";

        public static final String ACCOUNT_REGISTER_ERROR = "账户注册失败";

        //登录的一些常量
        public static final String PHONE_NUMBER_IS_EMPTY = "手机号不能为空";

        public static final String PASSWORD_IS_EMPTY = "密码不能为空";

        public static final String NEW_PASSWORD_IS_EMPTY = "密码不能为空";


        public static final String USER_IS_EMPTY = "该用户不存在";

        public static final String USER_PASSWORD_ERROR = "输入的密码不对";

        public static final String LOGIN_SUCCESS = "登录成功";

        public static final String UPDATE_USER_INFO_ERROR = "更新用户信息失败";


        //修改密码的一些常量
        public static final String MODIFY_PASSWORD_SUCCESS = "密码修改成功";
        public static final String MODIFY_PASSWORD_ERROR = "密码修改失败";
    }

//    public static final String

}
