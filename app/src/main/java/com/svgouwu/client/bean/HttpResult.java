package com.svgouwu.client.bean;

/**
 * Created by topwolf on 2016/5/5.
 */
public class HttpResult<T> {

    //0000：成功；0001：失败；0002：参数错误，0003：未登录，0004：权限不足，0005：服务器请求异常
    public static String SUCCESS = "0000";
    public static String FAIL = "0001";
    public static String PARAM_ERROR = "0002";//显示Toast
    public static String UN_LOGIN = "0003";//显示Toast
    public static String NO_PERMISSION = "0004";//显示Toast
    public static String SERVER_ERROR = "0005";//显示Toast
    public static String TOKEN_INVALID = "0021";//用户token验证失败
    public static String UN_BIND_PHONE = "0020";//未绑定手机号

    /** 状态码:200 | 304 | 404 | 500 */
    public String code;
    /** 是否有下一页 */
    public boolean hasmore;
    /** JSON格式的字符串 */
    public T data;
    /** 字符串结果 */
    public String result;
    public String msg;
    /** 总记录数 */
    public long count;

    public int login = -1;

    public String error;
    public int page_total;

    public boolean isSuccess() {
        return SUCCESS.equals(code);
    }
    public boolean isTokenInvalid() {
        return TOKEN_INVALID.equals(code);
    }

    public boolean isNotLogin() {
        return UN_LOGIN.equals(code);
    }
    public boolean isNotBindPone() {
        return UN_BIND_PHONE.equals(code);
    }

    public boolean isNotPermission() {
        return NO_PERMISSION.equals(code);
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", hasMore=" + hasmore +
                ", json='" + data + '\'' +
                ", result='" + result + '\'' +
                ", count=" + count +
                ", login=" + login +
                ", msg=" + msg +
                ", page_total=" + page_total +
                '}';
    }
}
