package com.svgouwu.client;

import android.os.Environment;

import com.svgouwu.client.utils.CommonUtils;

/**
 * 常量类
 */
public final class Constant {
    public static final long SEND_CODE_TIME = 60 * 1000; //再次发送验证码时间间隔
    public static final int PAGE_LIMIT = 15;

	/*----------发布版本需要更改的设置 start-------------*/

    //调试模式
    public static boolean DEBUG = false;
    //渠道设置
    public static String CHANNEL = CommonUtils.getChannel();//qq 360

    /*--------------setting end---------*/

    public static final String HOST = "baidu.com";
    public static final String H5_BASE_URL = "https://sv.svgouwu.com/mobile/";

    /**
     * 本地缓存目录
     */
    public static final String CACHE_DIR;

    /**
     * 分页显示个数
     */
    public static final int PAGESIZE = 10;

    /**
     * 表情缓存目录
     */
    public static final String CACHE_DIR_SMILEY;

    /**
     * 图片缓存目录
     */
    public static final String CACHE_DIR_IMG;

    /**
     * 待上传图片缓存目录
     */
    public static final String CACHE_DIR_IMG_UPLOADING;
    /**
     * patch补丁目录
     */
    public static final String CACHE_DIR_PATCH;

    /**
     * Google 消息推送 senderID
     */
    public static final String GOOGLE_SENDERID_ID = "237103122055";

    /**
     * 微信APPID
     */
    public static final String APP_ID_WX = "wxae8e90dc4d7fc100";
    /**
     * 微信SECRET
     */
    public static final String SECRET_WX = "d9f4225d484be961bd53160835c9fb3c";//d9f4225d484be961bd53160835c9fb3c  d081f50c00f8e168388f3ab7b36b2776
    /**
     * qq APPID
     */
    public static final String APP_ID_QQ = "1105839416";
    /**
     * qq SECRET
     */
    public static final String SECRET_QQ = "riKrB50LLLUnHMAc";//

    /**
     * 新浪key
     */
    public static final String XINLANG_APP_KEY = "2045436852";

    /**
     * 新浪URL
     */
    public static final String XINLANG_APP_URL = "http://www.sina.com";

    /**
     * 登录成功广播返回标识
     */
    public static final String LOGIN_SUCCESS_URL = "80002";

    /**
     * 商品跳转购物车广播返回标识
     */
    public static final String SHOW_CART_URL = "80003";

    /**
     * 点击去逛逛跳转首页广播返回标识
     */
    public static final String SHOW_HOME_URL = "80009";

    /**
     * 选中发票后返回标识
     */
    public static final int SELECT_INVOICE = 80004;

    /**
     * 新增收货地址返回标识
     */
    public static final int ADD_ADDRESS_SUCC = 80005;

    /**
     * 选中收货地址返回标识
     */
    public static final int SELECT_ADDRESS = 80006;

    /**
     * 支付成功返回标识
     */
    public static final String PAYMENT_SUCCESS = "80007";

    /**
     * 虚拟订单支付成功返回标识
     */
    public static final String VPAYMENT_SUCCESS = "80008";

    /**
     * IM新消息刷新页面返回标识
     */
    public static final String IM_UPDATA_UI = "80010";

    /**
     * IM好友列表状态刷新页面返回标识
     */
    public static final String IM_FRIENDS_LIST_UPDATA_UI = "80011";
    //搜索商品动作确定
    public static final String ON_SEARCH_DONE = "80012";
    public static final String GOOGLE_PUSH = "80013";

    static {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/svgw/";
        } else {
            CACHE_DIR = Environment.getRootDirectory().getAbsolutePath() + "/svgouwu/";
        }

        CACHE_DIR_SMILEY = CACHE_DIR + "/smiley";
        CACHE_DIR_IMG = CACHE_DIR + "/pic";
        CACHE_DIR_IMG_UPLOADING = CACHE_DIR + "/uploading_img";
        CACHE_DIR_PATCH = CACHE_DIR + "/patch/";
    }

    private Constant() {
    }

    /**
     * 与服务器端连接的协议名
     */
    public static final String PROTOCOL = "http://";

    /**
     * 服务器端口号
     */
    public static final String PORT = "80";

    /**
     * 应用上下文名
     */
    public static final String APP = ""; /* /mobile */

    /**
     * 应用上下文完整路径
     */
    public static final String URL_CONTEXTPATH = PROTOCOL + HOST + APP + "/index.php?";
//    public static final String URL_CONTEXTPATH = PROTOCOL + "www.kili.com.viphk.ngrok.org/mobile/index.php?";




    //-------H5 活动地址------//
    public static final String H5_DELIVERY_DETAIL = "https://m.kuaidi100.com/index_all.html?";
    //-------H5 活动地址------//
}
