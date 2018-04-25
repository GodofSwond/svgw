package com.svgouwu.client;

/**
 * Created by topwolf on 2017/4/25.
 * 所有api请求接口地址
 */

public class Api {
        public static final String BASE_URL="http://10.10.1.11/";
//    public static final String BASE_URL = "http://api.svgouwu.com/";
    public static final String BASE_WWW = "https://www.svgouwu.com/";
    public static final String URL_CHECK_VERSION = BASE_URL + "app.php/version/versionupdate"; //版本升级接口
    public static final String URL_SEND_CODE = BASE_URL + "app.php/send"; //发验证码
    public static final String URL_REGISTER = BASE_URL + "app.php/reg"; //注册
    public static final String URL_LOGIN = BASE_URL + "app.php/login"; //登录
    public static final String URL_USER_CENTER = BASE_URL + "app.php/user/useraccount"; //用户中心
    public static final String URL_USER_BASE_INFO = BASE_URL + "app.php/user/userinfo";//用户基本信息
    public static final String URL_USER_BASE_INFO_EDIT = BASE_URL + "app.php/user/edituserinfo"; //编辑用户基本信息
    public static final String URL_MODIFY_PWD = BASE_URL + "app.php/user/edituserpassword"; //修改密码
    public static final String URL_MODIFY_PHONE = BASE_URL + "app.php/user/editusermobile"; //修改手机号
    public static final String URL_BIND_PHONE = BASE_URL + "app.php/bindregister"; //三方登录绑定手机号
    public static final String URL_MODIFY_EMAIL = BASE_URL + "app.php/user/edituseremail";//修改邮箱
    public static final String URL_CHECK_CODE = BASE_URL + "app.php/checkcode";//校验验证码
    public static final String URL_FORGOT_PWD_SET = BASE_URL + "app.php/findpwd"; //设置新密码
    public static final String URL_THIRD_LOGIN = BASE_URL + "app.php/bindlogin"; //三方登录
    //    public static final String URL_UPLOAD_AVATAR = BASE_URL+"app.php/uploadforapp"; //上传头像
    public static final String URL_CLASSIFY = BASE_URL + "app.php/classify/getclassify"; //分类
    public static final String URL_GOODS_LIST = BASE_URL + "app.php/goods/goodslist"; //商品列表
    public static final String URL_GOODS_FILTER = BASE_URL + "app.php/goods/filter"; //商品筛选
    public static final String URL_AREA_LIST = BASE_URL + "app.php/area/list"; //商品筛选
    public static final String URL_ADDRESS_LIST = BASE_URL + "app.php/user/address/list"; //个人中心收货地址列表
    public static final String URL_ADDRESS_PROCESS = BASE_URL + "app.php/user/address/operation"; //个人中心收货地址操作（添加，更新）
    public static final String URL_ADDRESS_DELETE = BASE_URL + "app.php/user/address/delete"; //个人中心收货地址操作（添加，更新）
    public static final String URL_ADDRESS_SET_DEFAULT = BASE_URL + "app.php/user/address/setdefault"; //个人中心设置默认收货地址
    public static final String URL_CART = BASE_URL + "app.php/cart"; //购物车首页
    public static final String URL_CART_UPDATE = BASE_URL + "app.php/update"; //更改购物车某款商品数量
    public static final String URL_CART_REMOVE = BASE_URL + "app.php/drop"; //删除某款商品（可批量）
    public static final String URL_ORDER_CONFIRM = BASE_URL + "app.php/fillorder"; //购物车结算（填写订单页面）
    public static final String URL_SUBMIT_ORDER = BASE_URL + "app.php/placeorder"; //提交订单
    public static final String URL_ALIPAY = BASE_URL + "app.php/alipaydata"; //支付宝支付接口
    public static final String URL_ALIPAY_CALLBACK = BASE_URL + "app.php/alipaynotify"; //支付宝支付同步回调接口
    public static final String URL_WX = BASE_URL + "app.php/wxpaydata"; //支付宝支付接口
    public static final String URL_WX_CALLBACK = BASE_URL + "app.php/wxpaynotify"; //支付宝支付同步回调接口
    public static final String URL_ORDER_LIST = BASE_URL + "app.php/user/bolist"; //我的订单
    public static final String URL_ORDER_CANCEL = BASE_URL + "app.php/user/docancel"; //接口名称：个人中心确认取消订单
    public static final String URL_ORDER_RECEIVE = BASE_URL + "app.php/user/confirmreceipt"; //
    public static final String URL_HOT_SEARCH = BASE_URL + "app.php/goods/hotsearch"; //热词搜索
    public static final String URL_GOODS_SPEC = BASE_URL + "app.php/goods/getspec"; //商品规格
    public static final String URL_GOODS_SPEC_GOODS_INFO = BASE_URL + "app.php/goods/changegoods"; //商品规格对应的商品信息
    public static final String URL_GOODS_SPEC_ADD_TO_CART = BASE_URL + "app.php/addcart";//添加购物车
    public static final String URL_GOODS_FAV = BASE_URL + "app.php/user/addcollection";//商品收藏
    public static final String URL_GOODS_FAV_CANCEL = BASE_URL + "app.php/user/deletecollection";//取消商品收藏
    public static final String URL_GOODS_FAV_LIST = BASE_URL + "app.php/user/collection";//收藏列表
    public static final String URL_GOODS_FAV_STATE = BASE_URL + "app.php/user/iscollected";//商品收藏状态
    public static final String URL_GOODS_DETAILS_BUY_NOW = BASE_URL + "app.php/buynow";//立即购买
    public static final String URL_ORDER_DETAILS = BASE_URL + "app.php/user/bodetail";//订单详情
    public static final String URL_ORDER_EXPRESS_LIST = BASE_URL + "app.php/express";//个人中心订单物流信息
    public static final String URL_CHECK_INVOICE = BASE_URL + "app.php/checkinvoce";//验证增值税发票接口
    public static final String URL_GET_INVOICE = BASE_URL + "app.php/getinvo";//获取增值税发票接口
    public static final String URL_ORDER_COMMENT = BASE_URL + "app.php/user/evaluateorder";//订单评价
    public static final String URL_GOODS_COMMENT = BASE_URL + "app.php/user/evaluate";//商品评价
    public static final String URL_SPREED_DATA = BASE_URL + "app.php/index/validdate";//大礼包红包是否过期
    public static final String URL_SPREED_RECEIVE = BASE_URL + "app.php/index/getpackage";//大礼包红包领取接口
    public static final String URL_GOODS_DETAILS = BASE_URL + "app.php/goods/goodsinfo?id=";//商品详情接口
    public static final String URL_DIS_NUM = BASE_URL + "app.php/user/coupon/num"; //优惠券数量
    public static final String URL_DISCOUNTS_LIST = BASE_URL + "app.php/user/coupon/list";//优惠券
    public static final String URL_COUPONS_CENTER = BASE_URL + "app.php/index/coupon/center"; //领券中心
    public static final String URL_COUPONS_GETCOUPON = BASE_URL + "app.php/index/coupon/getcoupon"; //领取优惠券
    public static final String URL_PAY_ORDERLIST = BASE_URL + "app.php/getPayOrderList"; //支付订单页信息
    public static final String URL_PAY_SUCCESS_GETCOUPON = BASE_URL + "app.php/user/coupon/trigcoupon"; //支付成功获取优惠券
    public static final String URL_TONGJI = BASE_URL + "app.php/index/ipcount"; //后台统计访问数据
    public static final String URL_SCAN = BASE_URL + "app.php/userscan"; //扫码登陆接口
    public static final String URL_SCAN_LOGIN = BASE_URL + "app.php/scanconfirm"; //扫码确认接口
    public static final String URL_GOODSHARE = BASE_URL + "app.php/index/goodshare"; //商品详情页分享接口
    public static final String URL_BINDAGENCY = BASE_URL + "app.php/agency/bindagency"; //商品详情绑定关系接口
    public static final String URL_COUPONS_REDEEM = BASE_URL + "app.php/user/coupon/getcoupon"; //兑换优惠券
    public static final String URL_ORDER_REBATE = BASE_URL + "app.php/order/rebate"; //订单返利
    //    public static final String URL_UPLOAD_AVATAR = BASE_URL + "app.php/uploadforapp.php"; //上传头像
    public static final String URL_UPLOAD_AVATAR = BASE_WWW + "uploadforapp.php"; //上传头像
    public static final String URL_WEALTH_REBATE = BASE_URL + "app.php/user/wealth/rebate"; //我的返利
    public static final String URL_WEALTH_RAKEBACK = BASE_URL + "app.php/user/wealth/rakeback"; //我的佣金
    public static final String URL_WEALTH_CLEARMONEY = BASE_URL + "app.php/user/wealth/clearmoney"; //获取提现金额
    public static final String URL_WEALTH_CLEAR = BASE_URL + "app.php/user/wealth/clear"; //提现记录
    public static final String URL_WEALTH_APPLYCLEAR = BASE_URL + "app.php/user/wealth/applyclear"; //申请提现

    //H5 地址
    public static final String H5_GOODS_DETAILS = "https://www.svgouwu.com/mobile/index.php?app=goods&act=get_goodinfo&id=";


//    /**
//     * 默认初始化需要给定回调和rx周期类
//     * 可以额外设置请求设置加载框显示，回调等（可扩展）
//     * @param listener
//     * @param rxAppCompatActivity
//     */
//    public Api(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
//        super(listener,rxAppCompatActivity);
//        setShowProgress(true);
//        setCancel(true);
//        setCache(false);
////        setMothed("web/user/useraccount");
////        setMothed("AppFiftyToneGraph/videoLink");
//        setCookieNetWorkTime(60);
//        setCookieNoNetWorkTime(24*60*60);
//    }
//
//    @Override
//    public Observable getObservable(Retrofit retrofit) {
//        ApiService service = retrofit.create(ApiService.class);
//        Map<String,String> map = new HashMap<>();
//        map.put("user","18321152272");
//        map.put("pwd","123456");
//        return service.login(map);
////        return service.useraccount(map);
//    }

}
