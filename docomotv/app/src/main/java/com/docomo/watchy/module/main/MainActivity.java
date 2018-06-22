package com.docomo.watchy.module.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.docomo.watchy.module.base.BaseActivity;
import com.docomo.watchy.module.main.fragment.MainFragment;
import com.docomo.watchy.module.main.fragment.ProfileFragment;
import com.docomo.watchy.widget.CommonFragmentTabHost;
import com.docomo.watchy.R;

import butterknife.BindArray;
import butterknife.BindView;

public class MainActivity extends BaseActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQ_CODE_LOGIN = 1;

    @BindView(android.R.id.tabhost)
    CommonFragmentTabHost mTabHost;

    @BindArray(R.array.tab_ids)
    String mTabIds[];

    TextView tvShoppingCartIndicator;

    private View[] mTabBtnList;
    private String[] mTabTitles;//每个tab下的文言
    private TabWidget mTabWidget = null;
    // 每个tab的selector
    private final int tabSelectors[] = {
            R.drawable.selector_tab_bar_img_main,
            R.drawable.selector_tab_bar_img_category,
            R.drawable.selector_tab_bar_img_shopping_cart,
            R.drawable.selector_tab_bar_img_profile};

    private final Class[] contentFragments = {
            MainFragment.class,
            CategoryFragment.class,
            ShoppingCartFragment.class,
            ProfileFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getContentView() {
        return R.layout.main_act;
    }

    private void initView() {

        mTabTitles = this.getResources().getStringArray(R.array.tab_titles);

        this.mTabHost.setup(this, getSupportFragmentManager(), R.id.tabContent);

        final int count = this.contentFragments.length;
        mTabBtnList = new View[count];

        for (int i = 0; i < count; i++) {
            Log.d(TAG, "initView: "+count);
            Bundle bundle = new Bundle();
            bundle.putString("tabId", mTabIds[i]);
            mTabBtnList[i] = getTabItemView(i);
            TabHost.TabSpec tabSpec = this.mTabHost.newTabSpec(this.mTabIds[i]).setIndicator(mTabBtnList[i]);
            this.mTabHost.addTab(tabSpec, this.contentFragments[i], bundle);
        }

        this.mTabWidget = this.mTabHost.getTabWidget();
        this.mTabWidget.setDividerDrawable(null);

        this.mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            private String frontSelection = "main";
            @Override
            public void onTabChanged(final String tabId) {
                Log.d(TAG, "onTabChanged: " + tabId);
                switch (tabId) {
                    case "main":
                        // 主页
                        frontSelection = tabId;
                        break;
                    case "category":
                        // 分类
                        frontSelection = tabId;
                        break;
                    case "shoppingcart":
                        // 购物车
                        frontSelection = tabId;
                        break;
                    case "profile":
                        // 我的
                        frontSelection = tabId;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initData() {
        setReceiver(new LocalReceiver());
    }

    /**
     * 得到index位置上的tab的view
     */
    private View getTabItemView(final int index) {
        View view = mInflater.inflate(R.layout.common_main_tab_item, null);
        ImageView imgViewTabIcon = (ImageView) view.findViewById(R.id.iv_icon);
        imgViewTabIcon.setImageResource(this.tabSelectors[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        textView.setText(this.mTabTitles[index]);
        // 购物车上的红点提示，购物车上的红点提示需要单独的 action 来指定
        if (index == 2) {
            tvShoppingCartIndicator = view.findViewById(R.id.tv_count_badge);
        }
        return view;
    }

    private class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: in = ");
        }
    }

}
