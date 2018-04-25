package com.svgouwu.client.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.kili.okhttp.OkHttpUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.imagepicker.view.SystemBarTintManager;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.AddressListActivity;
import com.svgouwu.client.activity.CouponListActivity;
import com.svgouwu.client.activity.LoginActivity;
import com.svgouwu.client.activity.MyWalletActivity;
import com.svgouwu.client.activity.WealthActivity;
import com.svgouwu.client.activity.OrderListActivity;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.UserCenter;
import com.svgouwu.client.utils.CacheDataManager;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.svgouwu.client.utils.CommonUtils.isLogin;

/**
 * 个人中心
 * Created by topwolf on 2017/6/6.
 */
public class PersonalFragment extends BaseFragment {
    public static final int UPDATE_AVATAR = 2008;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtil.toast("清理完成");
                    try {
                        tvCacheSize.setText(CacheDataManager.getTotalCacheSize(getContext()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    @BindView(R.id.rl_personal_rlClear)
    RelativeLayout rlClear;
    @BindView(R.id.rl_personal_tvCacheSize)
    TextView tvCacheSize;
    @BindView(R.id.iv_personal_avatar)
    ImageView ivPersonalAvatar;
    @BindView(R.id.tv_personal_name)
    TextView tvPersonalName;
    @BindView(R.id.tv_personal_order_num_1)
    TextView tv_personal_order_num_1;
    @BindView(R.id.tv_personal_order_num_2)
    TextView tv_personal_order_num_2;
    @BindView(R.id.tv_personal_order_num_3)
    TextView tv_personal_order_num_3;
    @BindView(R.id.tv_personal_order_num_4)
    TextView tv_personal_order_num_4;
    @BindView(R.id.tv_personal_order_num_5)
    TextView tv_personal_order_num_5;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;

    public PersonalFragment() {
    }

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initViews() {
//        findView(R.id.iv_personal_settings).setOnClickListener(this);
        initImagePicker();
        try {
            tvCacheSize.setText(CacheDataManager.getTotalCacheSize(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清理缓存
     */
    class clearCache implements Runnable {

        @Override
        public void run() {
            CacheDataManager.clearAllCache(getContext());
            try {
                Thread.sleep(1000);
                if (CacheDataManager.getTotalCacheSize(getContext()).startsWith("0")) {
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
                Glide.with(activity).load(path).into(imageView);
            }

            @Override
            public void clearMemoryCache() {

            }
        });
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状

        //预览宽度为屏幕的2/3
        int width = CommonUtils.getScreenWidth(getContext()) * 2 / 3;
        if (width < 300) width = 300;

        imagePicker.setFocusWidth(width);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(width);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(200);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(200);                         //保存文件的高度。单位像素
    }

    private void enterImgSelector() {
        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        startActivityForResult(intent, UPDATE_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                if (data != null && requestCode == UPDATE_AVATAR) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    ImageItem imageItem = images.get(0);
                    LogUtils.e("imageItem: " + imageItem.width + "---" + imageItem.path);
                    uploadPortrait(imageItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //上传头像
    private void uploadPortrait(final ImageItem imageItem) {
        String url = Api.URL_UPLOAD_AVATAR;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        File file = new File(imageItem.path);
        if (!file.exists()) {
            ToastUtil.toast("头像文件不存在");
            return;
        }
        OkHttpUtils.post().addFile("headImg", System.currentTimeMillis() + ".jpg", file).url(url).params(params).build().execute(new CommonCallback<Object>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                weixinDialogInit(getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult response) {

                try {
                    if (!CommonUtils.isEmpty(response.msg))
                        ToastUtil.toast(response.msg);

                    if (response.isSuccess()) {
                        //Glide.with(getContext()).asBitmap().load(imageItem.path).into(new MyGlideRoundImageTarget(ivPersonalAvatar));

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getMineInfo();
                            }
                        }, 500);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.rl_personal_MyMoney, R.id.rl_personal_myMessage, R.id.rl_personal_rlClear, R.id.rl_Discounts, R.id.iv_personal_settings, R.id.rl_personal_center_fav, R.id.ll_personal_integral, R.id.rl_personal_all_order, R.id.ll_personal_login, R.id.rl_address, R.id.rl_personal_order_5, R.id.rl_personal_order_4, R.id.rl_personal_order_3, R.id.rl_personal_order_2, R.id.rl_personal_order_1})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_personal_settings:
                enterSettings();
                break;
            case R.id.rl_personal_center_fav:
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), CommonFragmentActivity.FRAGMENT_FAVORITE_GOODS);
                break;
            case R.id.ll_personal_integral:
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), CommonFragmentActivity.FRAGMENT_INTEGRAL);
                break;
            case R.id.rl_personal_all_order:
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), OrderListActivity.class);
                break;
            case R.id.rl_address:
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), AddressListActivity.class);
                break;
            case R.id.ll_personal_login:
                if (isLogin()) {
                    enterImgSelector();
                    return;
                }
                CommonUtils.startAct(getContext(), LoginActivity.class);
                break;
            case R.id.rl_personal_myMessage:
               /* if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), MessageActivity.class);*/
                break;
            case R.id.rl_Discounts://优惠券
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), CouponListActivity.class);
                break;
            case R.id.rl_personal_rlClear://清理缓存
                new Thread(new clearCache()).start();
                break;
            case R.id.rl_personal_MyMoney://我的钱包
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), WealthActivity.class);
               /* if (CommonUtils.checkLogin()){
                    startActivity(new Intent(getContext(), MyWalletActivity.class));
                }*/
                break;

            case R.id.rl_personal_order_1:
                if (CommonUtils.checkLogin()) {
                    //待付款
                    enterOrder(2);
                }
                break;
            case R.id.rl_personal_order_2:
                if (CommonUtils.checkLogin()) {
                    //待发货
                    enterOrder(3);
                }
                break;
            case R.id.rl_personal_order_3:
                if (CommonUtils.checkLogin()) {
                    //待收货
                    enterOrder(4);
                }
                break;
            case R.id.rl_personal_order_4:
                if (CommonUtils.checkLogin()) {
                    //待评价
                    enterOrder(5);
                }
                break;
            case R.id.rl_personal_order_5:
                if (CommonUtils.checkLogin()) {
                    enterOrder(6);
                }
                break;
        }
    }

    private void enterOrder(int i) {
        //1：所有订单2：待付款3：待发货4：待收货5：待评价
        Intent intent = new Intent(getContext(), OrderListActivity.class);
        intent.putExtra("type", i + "");
        startActivity(intent);
    }

    private void enterSettings() {
        Intent intent = new Intent(getContext(), CommonFragmentActivity.class);
        intent.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.PERSONALCENTERPAGE); //统计页面

        getMineInfo();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.PERSONALCENTERPAGE);
    }

    private void getMineInfo() {
        String loginKey = MyApplication.getInstance().getLoginKey();
        if (CommonUtils.isEmpty(loginKey)) {
            tvPersonalName.setText("点击登录");
            ivPersonalAvatar.setImageResource(R.drawable.ic_mine_avatar1);
            tv_personal_order_num_5.setVisibility(View.GONE);
            tv_personal_order_num_4.setVisibility(View.GONE);
            tv_personal_order_num_3.setVisibility(View.GONE);
            tv_personal_order_num_2.setVisibility(View.GONE);
            tv_personal_order_num_1.setVisibility(View.GONE);
            return;
        }

        String url = Api.URL_USER_CENTER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", loginKey);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<UserCenter>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<UserCenter> response) {

                try {
                    if (response.isSuccess()) {
                        UserCenter userCenter = response.data;
                        if (userCenter != null) {
                            tvPersonalName.setText(userCenter.userName);
                            MyApplication.getInstance().setPhone(userCenter.userName);
                            if (userCenter.waitForPayNum != 0) {
                                //    tv_personal_order_num_1.setText(userCenter.waitForPayNum + "");
                                tv_personal_order_num_1.setVisibility(View.VISIBLE);
                            } else {
                                tv_personal_order_num_1.setVisibility(View.GONE);
                            }
                            if (userCenter.waitForDeliverGoodsNum != 0) {
                                //    tv_personal_order_num_2.setText(userCenter.waitForDeliverGoodsNum + "");
                                tv_personal_order_num_2.setVisibility(View.VISIBLE);
                            } else {
                                tv_personal_order_num_2.setVisibility(View.GONE);
                            }
                            if (userCenter.waitForConfirmNum != 0) {
                                //    tv_personal_order_num_3.setText(userCenter.waitForConfirmNum + "");
                                tv_personal_order_num_3.setVisibility(View.VISIBLE);
                            } else {
                                tv_personal_order_num_3.setVisibility(View.GONE);
                            }
                            if (userCenter.waitForCommentNum != 0) {
                                //    tv_personal_order_num_4.setText(userCenter.waitForCommentNum + "");
                                tv_personal_order_num_4.setVisibility(View.VISIBLE);
                            } else {
                                tv_personal_order_num_4.setVisibility(View.GONE);
                            }
                            if (userCenter.returnGoodsNum != 0) {
                                tv_personal_order_num_5.setText(userCenter.returnGoodsNum + "");
                                tv_personal_order_num_5.setVisibility(View.VISIBLE);
                            } else {
                                tv_personal_order_num_5.setVisibility(View.GONE);
                            }

                            if (!CommonUtils.isEmpty(userCenter.portrait)) {
                                Glide.with(getContext()).asBitmap().load(userCenter.portrait).apply(new RequestOptions().error(R.drawable.ic_mine_avatar1).placeholder(R.drawable.ic_mine_avatar1)).into(new BitmapImageViewTarget(ivPersonalAvatar) {
                                    @Override
                                    protected void setResource(Bitmap resource) {
                                        if(resource != null){
                                            ivPersonalAvatar.setImageDrawable(CommonUtils.createRoundImageWithBorder(resource,getContext()));
                                        }
                                       /* RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                                        circularBitmapDrawable.setCircular(true);
                                        ivPersonalAvatar.setImageDrawable(circularBitmapDrawable);*/
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }
}
