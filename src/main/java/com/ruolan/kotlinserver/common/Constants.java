package com.ruolan.kotlinserver.common;


public class Constants {

    public class CODE {

        public static final int NO_PERMISSION_ERROR_CODE = -2;

        public static final int ERROR_CODE = -1;

        public static final int SUCCESS_CODE = 0;

    }


    public class MESSAGE {

        public static final String CURRENT_USER = "CURRENT_USER";
        public static final String USER_TOKEN = "token";

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


        public static final String USER_IS_EMPTY = "请登录";

        public static final String USER_PASSWORD_ERROR = "输入的密码不对";

        public static final String LOGIN_SUCCESS = "登录成功";
        public static final String LOGIN_SUCCESS_CONTENT = "恭喜您登录成功";


        public static final String UPDATE_USER_INFO_ERROR = "更新用户信息失败";


        //修改密码的一些常量
        public static final String MODIFY_PASSWORD_SUCCESS = "密码修改成功";
        public static final String MODIFY_PASSWORD_ERROR = "密码修改失败";

        //商品分类相关的常量
        public static final String CATEGORY_LIST_SUCCESS = "商品列表获取成功";
        public static final String CATEGORY_LIST_ERROR = "商品列表获取失败";
        public static final String CATEGORY_LIST_EMPTY = "商品列表为空";
        public static final String CATEGORY_DETAIL_SUCCESS = "列表为空";

        //地址相关的常量
        public static final String NO_PERMISSION = "没有权限操作,等登录之后再进行";
        public static final String ADDRESS_LIST_EMPTY = "地址列表为空";
        public static final String ADDRESS_LIST_SUCCESS = "地址列表获取成功";
        public static final String ADD_ADDRESS_SUCCESS = "添加地址成功";
        public static final String MODIFY_ADDRESS_SUCCESS = "修改地址成功";
        public static final String DELETE_ADDRESS_SUCCESS = "刪除地址成功";

        //购物车相关的常量
        public static final String ADD_CART_SUSSECC = "添加购物车成功";
        public static final String ADD_CART_ERROR = "添加购物车失败";
        public static final String DELETE_CART_ERROR = "删除商品失败";
        public static final String DELETE_CART_SUCCESS = "删除商品成功";


        //消息相关常量
        public static final String GET_MESSAGE_LIST_ERROR = "消息列表为空";
        public static final String GET_MESSAGE_LIST_SUCCESS = "消息列表获取成功";


    }

//    public static final String

}
