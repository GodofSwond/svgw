package com.svgouwu.client.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseTopFragment;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.AddressListActivity;
import com.svgouwu.client.activity.MainActivity;
import com.svgouwu.client.activity.OrderConfirmActivity;
import com.svgouwu.client.adapter.CartAdapter;
import com.svgouwu.client.bean.CartResult;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.StoreInfo;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

import static com.svgouwu.client.MyApplication.getIPAddress;
import static com.svgouwu.client.activity.MainActivity.FRAGMENT_CART;
import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

/**
 * Created by topwolf on 2017/6/6.
 * 购物车页面
 */

public class CartFragment extends BaseTopFragment implements View.OnClickListener, CartAdapter.CheckInterface, CartAdapter.ModifyCountInterface, CartAdapter.GroupEditorListener {


    @BindView(R.id.iv_left)
    ImageView ivLeft;//顶部返回
    @BindView(R.id.tv_title)
    TextView tvTitle;//标题
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_topbar_title)
    RelativeLayout rlTopbarTitle;
    @BindView(R.id.ll_topbar)
    LinearLayout llTopbar;
    @BindView(R.id.listView)
    ExpandableListView listView;//页面listview
    @BindView(R.id.all_checkBox)
    CheckBox allCheckBox;//选中checkbox
    @BindView(R.id.total_price)
    TextView totalPrice;//合计价格
    @BindView(R.id.go_pay)
    TextView goPay;//去支付
    @BindView(R.id.order_info)
    LinearLayout orderInfo;
    @BindView(R.id.share_goods)
    TextView shareGoods;
    @BindView(R.id.collect_goods)
    TextView collectGoods;
    @BindView(R.id.del_goods)
    TextView delGoods;
    @BindView(R.id.share_info)
    LinearLayout shareInfo;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;//底部ll
    @BindView(R.id.mPtrframe)
    PtrFrameLayout mPtrframe;
    Unbinder unbinder;
    LinearLayout empty_shopcart;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;
    @BindView(R.id.v_divider)
    View v_divider;
    @BindView(R.id.ll_calc)
    LinearLayout ll_calc;
    @BindView(R.id.emptyView)
    LinearLayout emptyView;
    @BindView(R.id.tv_car_all_rebate)
    TextView tv_car_all_rebate;//返利总金额

    private Context mcontext;
    private double mtotalPrice = 0.00;
    private int mtotalCount = 0;
    //false就是编辑，ture就是完成
    private boolean flag = false;
    private CartAdapter adapter;
    private List<StoreInfo> groups; //组元素的列表
    private Map<String, List<GoodsInfo>> childs; //子元素的列表
    private String intentRebate;

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initViews() {
        mStatusBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        tvTitle.setText("购物车");
        ivLeft.setVisibility(View.GONE);
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                initData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
//        empty_shopcart = (LinearLayout) findView(R.id.layout_empty_shopcart);
//        mPtrframe =findView(R.id.mPtrframe);
        initPtrFrame();
    }

    private void initPtrFrame() {
//        final StoreHouseHeader header=new StoreHouseHeader(this);
//        header.setPadding(dp2px(20), dp2px(20), 0, 0);
//        header.initWithString("xiaoma is good");
        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getContext());
        header.setPadding(dp2px(20), dp2px(20), 0, 0);
        mPtrframe.setHeaderView(header);
        mPtrframe.addPtrUIHandler(header);
        mPtrframe.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrframe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mPtrframe.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }


    private void initEvents() {
//        allCheckBox.setChecked(false);
        tvRight.setOnClickListener(this);
        adapter = new CartAdapter(groups, childs, mcontext);
        adapter.setCheckInterface(this);//关键步骤1：设置复选框的接口
        adapter.setModifyCountInterface(this); //关键步骤2:设置增删减的接口
        adapter.setGroupEditorListener(this);//关键步骤3:监听组列表的编辑状态
        listView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
        listView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        }
        calulate();
        setCartNum();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int firstVisiablePostion = view.getFirstVisiblePosition();
                int top = -1;
                View firstView = view.getChildAt(firstVisibleItem);
                LogUtils.i("childCount=" + view.getChildCount());//返回的是显示层面上的所包含的子view的个数
                if (firstView != null) {
                    top = firstView.getTop();
                }
                LogUtils.i("firstVisiableItem=" + firstVisibleItem + ",fistVisiablePosition=" + firstVisiablePostion + ",firstView=" + firstView + ",top=" + top);
                if (firstVisibleItem == 0 && top == 0) {
                    mPtrframe.setEnabled(true);
                } else {
                    mPtrframe.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("onHiddenChanged:  onResume");
//        initViews();
//        initListener();
//        initData();
        if (((MainActivity) getActivity()).current_page == FRAGMENT_CART) {
            initData();
        }
        MobclickAgent.onPageStart(UmengStat.SHOPPINGCARTPAGE);
        CommonUtils.umTongJi(getContext(), getIPAddress(), "7", "0");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.SHOPPINGCARTPAGE);
    }

    @Override //Fragment之间切换时调用。
    public void onHiddenChanged(boolean hidden) {
        LogUtils.e("onHiddenChanged: " + hidden + "---isVisible: " + isVisible());
        super.onHiddenChanged(hidden);

        if (!hidden) {
            initData();
//            setCartNum();
        }

    }

    /**
     * 设置购物车的数量
     */
    private void setCartNum() {
        if (groups == null) return;
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            group.isChoosed = (allCheckBox.isChecked());
            List<GoodsInfo> Childs = childs.get(group.storeId);
            for (GoodsInfo childs : Childs) {
                count++;
            }
        }

        //购物车已经清空
        if (count == 0) {
            clearCart();
        } else {
            tvTitle.setText("购物车(" + count + ")");
        }

    }

    private void clearCart() {
        tvTitle.setText("购物车(0)");
        tvRight.setVisibility(View.GONE);
        llCart.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);//这里发生过错误
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        String url = Api.URL_CART;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<CartResult>() {
            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(HttpResult<CartResult> response) {
                try {
                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null && response.data.cart_goods != null && response.data.cart_goods.storelist != null && response.data.cart_goods.storelist.size() > 0) {
                            emptyView.setVisibility(View.GONE);
                            llCart.setVisibility(View.VISIBLE);
                            mcontext = getContext();
                            groups = new ArrayList<StoreInfo>();
                            childs = new HashMap<String, List<GoodsInfo>>();
                            for (CartResult.StoreList storeList : response.data.cart_goods.storelist) {
                                boolean isChoosed = true;
                                for (GoodsInfo goodsInfo : storeList.goods) {
                                    if (goodsInfo.selected != 1) {
                                        isChoosed = false;
                                    }
                                }
                                groups.add(new StoreInfo(storeList.storeId, storeList.storeName, isChoosed));
                                childs.put(storeList.storeId, storeList.goods);
                            }
                            if (isCheckAll()) {
                                allCheckBox.setChecked(true);//全选
                            } else {
                                allCheckBox.setChecked(false);//反选
                            }
                            initEvents();
                        } else {
                            llCart.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                            tvTitle.setText("购物车(" + 0 + ")");
//                            listView.setEmptyView(layout_empty_shopcart);
                        }
                    }
                } catch (Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 删除操作
     * 1.不要边遍历边删除,容易出现数组越界的情况
     * 2.把将要删除的对象放进相应的容器中，待遍历完，用removeAll的方式进行删除
     */
    private void doDelete() {
        List<StoreInfo> toBeDeleteGroups = new ArrayList<StoreInfo>(); //待删除的组元素
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isChoosed) {
                toBeDeleteGroups.add(group);
            }
            List<GoodsInfo> toBeDeleteChilds = new ArrayList<GoodsInfo>();//待删除的子元素
            List<GoodsInfo> child = childs.get(group.storeId);
            for (int j = 0; j < child.size(); j++) {
                if (child.get(j).selected == 1) {
                    toBeDeleteChilds.add(child.get(j));
                }
            }
            child.removeAll(toBeDeleteChilds);
        }
        groups.removeAll(toBeDeleteGroups);
        //重新设置购物车
        setCartNum();
        adapter.notifyDataSetChanged();

    }

    /**
     * @param groupPosition 组元素的位置
     * @param isChecked     组元素的选中与否
     *                      思路:组元素被选中了，那么下辖全部的子元素也被选中
     */
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> child = childs.get(group.storeId);
        for (int i = 0; i < child.size(); i++) {
            child.get(i).selected = isChecked ? 1 : 0;
        }
        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (StoreInfo group : groups) {
            if (!group.isChoosed) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param groupPosition 组元素的位置
     * @param childPosition 子元素的位置
     * @param isChecked     子元素的选中与否
     */
    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> child = childs.get(group.storeId);
        for (int i = 0; i < child.size(); i++) {
            //不选全中
            if ((child.get(i).selected == 1 ? true : false) != isChecked) {
                allChildSameState = false;
                break;
            }
        }

        if (allChildSameState) {
            group.isChoosed = isChecked;//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
        } else {
            group.isChoosed = false;//否则一律视为未选中
        }

        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }

        adapter.notifyDataSetChanged();
        calulate();

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
//       updateQuantity(groupPosition,childPosition,showCountView,1);
//        GoodsInfo good = (GoodsInfo) adapter.getChild(groupPosition, childPosition);
//        int count = good.quantity;
//        count++;
//        good.quantity = count;
//        ((TextView) showCountView).setText(String.valueOf(count));
//        adapter.notifyDataSetChanged();
//        calulate();
    }

    /**
     * @param groupPosition
     * @param childPosition
     * @param showCountView
     * @param isChecked
     */
    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        //       updateQuantity(groupPosition,childPosition,showCountView,-1);
//        GoodsInfo good = (GoodsInfo) adapter.getChild(groupPosition, childPosition);
//        int count = good.quantity;
//        if (count == 1) {
//            return;
//        }
//        count--;
//        good.quantity = (count);
//        ((TextView) showCountView).setText("" + count);
//        adapter.notifyDataSetChanged();
//        calulate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View Delected, View showCountView, boolean isChecked) {
        updateQuantity(groupPosition, childPosition, Delected, showCountView, -1);
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View Addnumber, View showCountView, boolean isChecked) {
        updateQuantity(groupPosition, childPosition, Addnumber, showCountView, 1);
    }

    /**
     * @param groupPosition
     * @param childPosition 思路:当子元素=0，那么组元素也要删除
     */
    @Override
    public void childDelete(int groupPosition, int childPosition) {
        delGoods(groupPosition, childPosition);
    }

    public void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
//        GoodsInfo good = (GoodsInfo) adapter.getChild(groupPosition, childPosition);
//        int count = good.quantity;
//        LogUtils.i("进行更新数据，数量" + count + "");
//        ((TextView) showCountView).setText(String.valueOf(count));
//        adapter.notifyDataSetChanged();
//        calulate();


    }

    @Override
    public void groupEditor(int groupPosition) {

    }

    @OnClick({R.id.all_checkBox, R.id.go_pay, R.id.share_goods, R.id.collect_goods, R.id.del_goods, R.id.emptyView})
    public void onClick(View view) {
        AlertDialog dialog;
        switch (view.getId()) {
            case R.id.all_checkBox:
                doCheckAll();
                break;
            case R.id.go_pay:
                if (mtotalCount == 0) {
                    ToastUtil.toast(mcontext, "请选择要支付的商品");
                    return;
                }

                StringBuilder sb = new StringBuilder();//要购买的商品记录ID
                for (int i = 0; i < groups.size(); i++) {
                    StoreInfo group = groups.get(i);
                    List<GoodsInfo> child = childs.get(group.storeId);
                    for (int j = 0; j < child.size(); j++) {
                        GoodsInfo good = child.get(j);
                        if (good.selected == 1) {
                            sb.append(good.recId + ",");
                        }
                    }
                }
                String recid = sb.substring(0, sb.lastIndexOf(","));
                checkAddress(recid);
//                dialog = new AlertDialog.Builder(mcontext).create();
//                dialog.setMessage("总计:" + mtotalCount + "种商品，" + mtotalPrice + "元");
//                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "支付", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        StringBuilder sb = new StringBuilder();//要购买的商品记录ID
//                        for (int i = 0; i < groups.size(); i++) {
//                            StoreInfo group = groups.get(i);
//                            List<GoodsInfo> child = childs.get(group.storeId);
//                            for (int j = 0; j < child.size(); j++) {
//                                GoodsInfo good = child.get(j);
//                                if (good.selected ==1) {
//                                    sb.append(good.recId +",");
//                                }
//                            }
//                        }
//                        String recid = sb.substring(0,sb.lastIndexOf(","));
//                        Intent intent = new Intent(getContext(), OrderConfirmActivity.class);
//                        intent.putExtra("recid",recid);
//                        getActivity().startActivity(intent);
//                    }
//                });
//                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        return;
//                    }
//                });
//                dialog.show();
                break;
            case R.id.share_goods:
                if (mtotalCount == 0) {
                    ToastUtil.toast(mcontext, "请选择要分享的商品");
                    return;
                }
                ToastUtil.toast(mcontext, "分享成功");
                break;
            case R.id.collect_goods:
                if (mtotalCount == 0) {
                    ToastUtil.toast(mcontext, "请选择要收藏的商品");
                    return;
                }
                ToastUtil.toast(mcontext, "收藏成功");
                break;
            case R.id.del_goods:
                if (mtotalCount == 0) {
                    ToastUtil.toast(mcontext, "请选择要删除的商品");
                    return;
                }
                dialog = new AlertDialog.Builder(mcontext).create();
                dialog.setMessage("确认要删除该商品吗?");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doDelete();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
                break;
            case R.id.actionBar_edit:
                flag = !flag;
                setActionBarEditor();
                setVisiable();
                break;
            case R.id.emptyView:
                ((MainActivity) getActivity()).showFragment(MainActivity.FRAGMENT_HOME);
                break;
        }
    }

    /**
     * ActionBar标题上点编辑的时候，只显示每一个店铺的商品修改界面
     * ActionBar标题上点完成的时候，只显示每一个店铺的商品信息界面
     */
    private void setActionBarEditor() {
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.ActionBarEditor) {
                group.ActionBarEditor = (false);
            } else {
                group.ActionBarEditor = (true);
            }
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            group.isChoosed = (allCheckBox.isChecked());
            List<GoodsInfo> child = childs.get(group.storeId);
            for (int j = 0; j < child.size(); j++) {
                child.get(j).selected = (allCheckBox.isChecked()) ? 1 : 0;//这里出现过错误
            }
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */

    private void calulate() {
        mtotalPrice = 0.00;
        mtotalCount = 0;
        String allPrice = "0";
        String rebate1 = "0";
        double all_rebate = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            List<GoodsInfo> child = childs.get(group.storeId);
            for (int j = 0; j < child.size(); j++) {
                GoodsInfo good = child.get(j);
                if (good.selected == 1) {
                    mtotalCount++;
                    mtotalPrice += good.price * good.quantity;
                    all_rebate += (Double.parseDouble(good.shareRebate) * good.quantity);
                    rebate1 = CommonUtils.add("0",Double.toString(all_rebate),2 );//返利总金额
                    allPrice = CommonUtils.add("0",mtotalPrice+"",2);//总金额
                }
            }
        }
        totalPrice.setText("￥" + allPrice + "");
        goPay.setText("去支付(" + mtotalCount + ")");
        if (mtotalCount == 0) {
            setCartNum();
        } else {
            tvTitle.setText("购物车(" + mtotalCount + ")");
        }
        if(!"0".equals(rebate1)&& !"0.0".equals(rebate1) &&!"0.00".equals(rebate1)){
            tv_car_all_rebate.setVisibility(View.VISIBLE);
            tv_car_all_rebate.setText(Html.fromHtml("返利总金额:  " + "<font color=#df3031>" + " ￥" + rebate1 + "元" + "</font>"));
            intentRebate = rebate1;
        }else{
            tv_car_all_rebate.setVisibility(View.GONE);
            intentRebate = "";
        }

    }

    private void setVisiable() {
        if (flag) {
            orderInfo.setVisibility(View.GONE);
            shareInfo.setVisibility(View.VISIBLE);
            tvRight.setText("完成");
        } else {
            orderInfo.setVisibility(View.VISIBLE);
            shareInfo.setVisibility(View.GONE);
            tvRight.setText("编辑");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        childs.clear();
        groups.clear();
        mtotalPrice = 0.00;
        mtotalCount = 0;
    }

    @Override
    public void processClick(View view) {

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
////        unbinder = ButterKnife.bind(this, rootView);
//        initViews();
////        initListener();
//        initData();
//        return rootView;
//    }

    public void delGoods(final int groupPosition, final int childPosition) {
        StoreInfo group = groups.get(groupPosition);
        final List<GoodsInfo> child = childs.get(group.storeId);

        String recIds = child.get(childPosition).recId + "";

        String url = Api.URL_CART_REMOVE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("recIds", recIds);//记录ID（多条，分隔）

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<CartResult>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(HttpResult<CartResult> response) {
                try {
                    if (response.isSuccess()) {
                        child.remove(childPosition);
                        if (child.size() == 0) {
                            groups.remove(groupPosition);
                        }
                        adapter.notifyDataSetChanged();
                        calulate();
                    } else {
                        ToastUtil.toast(response.msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateQuantity(final int groupPosition, final int childPosition, final View AddandDeleted, final View showCountView, final int operation) {
        GoodsInfo good = (GoodsInfo) adapter.getChild(groupPosition, childPosition);
        int quantity = good.quantity;
        String url = Api.URL_CART_UPDATE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("spid", good.specId + "");//规格ID

        if (operation == 1) {
            params.put("quantity", quantity + 1 + "");//quantity
        } else if (operation == -1) {
            if (quantity <= 1) {
                return;
            }
            params.put("quantity", quantity - 1 + "");//quantity
        }

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<CartResult>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(HttpResult<CartResult> response) {
                try {
                    if (response.isSuccess()) {
                        GoodsInfo good = (GoodsInfo) adapter.getChild(groupPosition, childPosition);
                        int count = good.quantity;
                        if (operation == 1) {
                            count++;
                        } else if (operation == -1) {
                            count--;
                        }

                        good.quantity = count;
                        ((TextView) showCountView).setText(String.valueOf(count));
                        AddandDeleted.setClickable(true);
                        adapter.notifyDataSetChanged();
                        calulate();

                    } else {
                        ToastUtil.toast(response.msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkAddress(final String recid) {
        weixinDialogInit("订单生成中...");
        String url = Api.URL_ORDER_CONFIRM;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("recid", recid);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
            }

            @Override
            public void onResponse(HttpResult<Object> response) {
                try {
                    if ("0028".equals(response.code)) {//没有地址
                        ToastUtil.toast("当前账号没有绑定地址，即将跳转添加地址页面");
                        MyApplication.getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getContext(), AddressListActivity.class);
                                getActivity().startActivity(intent);
                            }
                        }, 1500);

                    } else {
                        Intent intent = new Intent(getContext(), OrderConfirmActivity.class);
                        intent.putExtra("recid", recid);
                        intent.putExtra("intentRebate",intentRebate);
                        getActivity().startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
}
