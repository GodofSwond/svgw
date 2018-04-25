package com.svgouwu.client.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.OrderConfirmActivity;
import com.svgouwu.client.bean.GoodsSpec;
import com.svgouwu.client.bean.GoodsSpecChange;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 商品规格 弹窗
 * Created by melon on 2017/7/27.
 * Email 530274554@qq.com
 */

public class GoodsSpecDialog extends Dialog {
    private BaseActivity act;
    private TextView tvGoodsPrice, tv_goods_detail_dialog_splemoney, tv_good_dialog_rebate;
    private GoodsSpec mGoodsSpec; //商品规格
    private GoodsSpecChange mGoodsSpecChange; //选择了规格
    private int mGoodsNum = 1;
    private String mGoodsId, mstRebate;
    private Map<String, String> selectedSpecs;
    private TextView tvGoodsNum;

    public GoodsSpecDialog(BaseActivity act, GoodsSpec mGoodsSpec, String goodsId, String stRebate) {
        super(act, R.style.Down2UpDialogStyle);
        this.act = act;
        this.mGoodsSpec = mGoodsSpec;
        this.mGoodsId = goodsId;
        this.mstRebate = stRebate;
        init();
    }

    public GoodsSpecDialog(BaseActivity act, GoodsSpec mGoodsSpec, String goodsId) {
        super(act, R.style.Down2UpDialogStyle);
        this.act = act;
        this.mGoodsSpec = mGoodsSpec;
        this.mGoodsId = goodsId;
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_goods_details_buy);

        // 动画弹出
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.dimAmount = 0.8f;
        lp.width = CommonUtils.getScreenWidth(act);
        window.setWindowAnimations(R.style.Down2UpDialogAnimStyle);

        initView();
    }

    private void initView() {
        ImageView ivGoodsPic = (ImageView) findViewById(R.id.iv_goods_detail_dialog_pic);
        TextView tvGoodsName = (TextView) findViewById(R.id.tv_goods_detail_dialog_name);
        TextView tv_goods_detail_dialog_sel_tip = (TextView) findViewById(R.id.tv_goods_detail_dialog_sel_tip);
        tvGoodsPrice = (TextView) findViewById(R.id.tv_goods_detail_dialog_money);//原价
        tv_goods_detail_dialog_splemoney = (TextView) findViewById(R.id.tv_goods_detail_dialog_splemoney);//活动价格
        tvGoodsNum = (TextView) findViewById(R.id.tv_goods_detail_dialog_num);
        LinearLayout specsContainer = (LinearLayout) findViewById(R.id.ll_goods_details_specs);

        Glide.with(getContext()).load(mGoodsSpec.defalut_pic).into(ivGoodsPic);
        tvGoodsName.setText(mGoodsSpec.goodsname);

        List<GoodsSpec.Spec> specs = mGoodsSpec.standard;
        tv_good_dialog_rebate = (TextView) findViewById(R.id.tv_good_dialog_rebate);//购买返利

        if (specs != null && specs.size() != 0) {
            //spec1=黄色 spec2=73
            selectedSpecs = new HashMap<>();
            for (int j = 0; j < specs.size(); j++) {
                final int specIndex = j;
                GoodsSpec.Spec spec = specs.get(j);
                String name = spec.specName;
                final List<String> values = spec.specValues;
                View specView = LayoutInflater.from(getContext()).inflate(R.layout.item_goods_details_spec, specsContainer, false);
                TextView tvName = (TextView) specView.findViewById(R.id.tv_goods_details_spec_name);
                FlowRadioGroup flValues = (FlowRadioGroup) specView.findViewById(R.id.flow_goods_details_spec_value);

                tvName.setText(name);
                if (values != null) {
                    for (int i = 0; i < values.size(); i++) {
                        final String value = values.get(i);
                        RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_goods_details_spec_value, flValues, false);
                        radioButton.setText(value);
                        radioButton.setId(i);
                        if (i == 0) {
                            radioButton.setChecked(true);
                            selectedSpecs.put("spec" + (specIndex + 1), value);
                            getSpecGoodsInfo(selectedSpecs);
                        }
                        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    //获取规格对应商品信息
                                    selectedSpecs.put("spec" + (specIndex + 1), value);
                                    getSpecGoodsInfo(selectedSpecs);
                                }
                            }
                        });
                        flValues.addView(radioButton);
                    }
                }

                specsContainer.addView(specView);
            }
        } else {
            //没有规格
            tv_goods_detail_dialog_sel_tip.setVisibility(View.GONE);
        }


        showPrice(mGoodsSpec.price, mstRebate);

        findViewById(R.id.iv_goods_detail_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tv_goods_detail_dialog_add_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
        findViewById(R.id.tv_goods_detail_dialog_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNow();
            }
        });
        findViewById(R.id.tv_goods_detail_dialog_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsNum == 1) return;
                tvGoodsNum.setText(--mGoodsNum + "");
            }
        });
        findViewById(R.id.tv_goods_detail_dialog_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGoodsNum.setText(++mGoodsNum + "");
            }
        });

        show();
    }

    //立即购买
    private void buyNow() {
        if (!CommonUtils.checkLogin()) {
            return;
        }
        if (mGoodsSpec == null) {
            ToastUtil.toast("规格异常，请稍后再试");
            return;
        }

        String specId = "";
        if (mGoodsSpec.standard.size() != 0) {
            if (mGoodsSpecChange == null) {
                ToastUtil.toast("您未选择规格");
                return;
            } else {
                //全部规格都选择才能提交
                if (selectedSpecs != null && (selectedSpecs.size() != mGoodsSpec.standard.size())) {
                    ToastUtil.toast("请选择规格");
                    return;
                }
                specId = mGoodsSpecChange.specId;
            }
        } else {
            specId = mGoodsSpec.specId;
        }
        if (mGoodsSpecChange != null && mGoodsSpecChange.sku == null) {
            ToastUtil.toast("该类商品已无库存");
            return;
        }

        MobclickAgent.onEvent(act, UmengStat.BUYNOWBUTTON);

        String url = Api.URL_GOODS_DETAILS_BUY_NOW;
        HashMap<String, String> params = new HashMap<>();
        if (!CommonUtils.isEmpty(specId))
            params.put("spid", specId);
        params.put("quantity", mGoodsNum + "");
        params.put("token", MyApplication.getInstance().getLoginKey());
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {
            Double intentRebate;

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                act.weixinDialogInit(act.getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                act.cancelWeiXinDialog();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<Object> response) {
                try {
                    if (response.isSuccess()) {
                        GoodsSpecDialog.this.dismiss();

                        JSONObject jObj = new JSONObject(response.data.toString());
                        int recid = jObj.optInt("recid");
                        if (!CommonUtils.isEmpty(mstRebate)) {
                            intentRebate = (Double.parseDouble(mstRebate) * mGoodsNum);
                        }
                        Intent intent = new Intent(getContext(), OrderConfirmActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("recid", recid + "");
                        intent.putExtra("intentRebate", intentRebate + "");
                        act.startActivity(intent);
                        HashMap<String, String> idmap = new HashMap<String, String>();
                        idmap.put("GoodsID", recid + "");
                        MobclickAgent.onEvent(act, UmengStat.COMMODITYPRICEOFIDFORDETAILS, idmap);
                    } else {
                        ToastUtil.toast(response.msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    private void addToCart() {
        if (!CommonUtils.checkLogin()) {
            return;
        }

        if (mGoodsSpec == null) {
            ToastUtil.toast("规格异常，请稍后再试");
            return;
        }

        String specId = "";
        if (mGoodsSpec.standard.size() != 0) {
            if (mGoodsSpecChange == null) {
                ToastUtil.toast("您未选择规格");
                return;
            } else {
                //全部规格都选择才能提交
                if (selectedSpecs != null && (selectedSpecs.size() != mGoodsSpec.standard.size())) {
                    ToastUtil.toast("请选择规格");
                    return;
                }

                specId = mGoodsSpecChange.specId;
            }
        } else {
            specId = mGoodsSpec.specId;
        }

        if (mGoodsSpecChange != null && mGoodsSpecChange.sku == null) {
            ToastUtil.toast("该类商品已无库存");
            return;
        }

        MobclickAgent.onEvent(act, UmengStat.ADDTOCARTBUTTON);

        String url = Api.URL_GOODS_SPEC_ADD_TO_CART;
        HashMap<String, String> params = new HashMap<>();
        if (!CommonUtils.isEmpty(specId))
            params.put("spid", specId);
        params.put("quantity", mGoodsNum + "");
        params.put("token", MyApplication.getInstance().getLoginKey());
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<GoodsSpecChange>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                act.weixinDialogInit(act.getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                act.cancelWeiXinDialog();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<GoodsSpecChange> response) {
                try {
                    if (!CommonUtils.isEmpty(response.msg)) {
                        ToastUtil.toast(response.msg);
                    }

                    if (response.isSuccess()) {
                        GoodsSpecDialog.this.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    private void showPrice(GoodsSpec.Price price, String shareRebate) {
        //   String priceStr = "";
        if (price != null) {
            /*if (price.isSpel == 1) {
                priceStr = price.spelPrice;
            } else {
                priceStr = price.price;
            }*/
            if (!CommonUtils.isEmpty(price.activePrice)) {
                tvGoodsPrice.setText(CommonUtils.getCurrencySign() + price.activePrice);
                tv_goods_detail_dialog_splemoney.setText(CommonUtils.getCurrencySign() + price.price);
                tv_goods_detail_dialog_splemoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tvGoodsPrice.setText(CommonUtils.getCurrencySign() + price.price);
            }
        }
        if (!CommonUtils.isEmpty(shareRebate) && !"0".equals(shareRebate) && !"0.0".equals(shareRebate) && !"0.00".equals(shareRebate)) {
            tv_good_dialog_rebate.setVisibility(View.VISIBLE);
            tv_good_dialog_rebate.setText("购买后返四维币" + shareRebate + "元");
        } else {
            tv_good_dialog_rebate.setVisibility(View.GONE);
        }


    }


    //每个规格对应的商品信息
    private void getSpecGoodsInfo(Map<String, String> selectedSpecs) {
        if (mGoodsSpec == null) {
            ToastUtil.toast("商品规格异常，请稍后再试");
            return;
        }
        if (selectedSpecs == null) return;

        String url = Api.URL_GOODS_SPEC_GOODS_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("id", mGoodsId);

        for (Map.Entry<String, String> entry : selectedSpecs.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            params.put(key, value);
        }

        OkHttpUtils.get().url(url).params(params).build().execute(new CommonCallback<GoodsSpecChange>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                //    act.weixinDialogInit(act.getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                //    act.cancelWeiXinDialog();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<GoodsSpecChange> response) {
                try {
                    if (response.isSuccess()) {
                        //显示新商品信息
                        mGoodsSpecChange = response.data;
                        showPrice(mGoodsSpecChange.price, mGoodsSpecChange.shareRebate);
                        if (mGoodsSpecChange != null && mGoodsSpecChange.sku == null) {
                            ToastUtil.toast("该类商品已无库存");
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
