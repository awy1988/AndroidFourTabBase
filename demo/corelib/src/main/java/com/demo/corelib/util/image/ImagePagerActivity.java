package com.demo.corelib.util.image;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.demo.corelib.R;
import java.util.ArrayList;

/**
 * 图片查看器
 */
public class ImagePagerActivity extends FragmentActivity {

    private static final String TAG = ImagePagerActivity.class.getSimpleName();

    private static final String STATE_POSITION = "STATE_POSITION";
    /**
     * 初始显示哪张图片
     */
    public static final String EXTRA_IMAGE_INDEX = "image_index";

    /**
     * 图片集合
     */
    public static final String EXTRA_IMAGE_PATHS = "image_paths";

    /**
     * 是否为仅展示图片，仅展示图片时，不显示删除图片按钮
     */
    public static final String EXTRA_IMAGE_SHOW_DELETE_BUTTON = "image_show_delete_button";
    public static final String EXTRA_RESULT_IMAGE_DELETE_HISTORY = "result_image_delete_history";

    private HackyViewPager mPager;
    private ImagePagerAdapter mAdapter;
    private int pagerPosition;
    private TextView indicator;
    private boolean isShowDeleteButton = false;

    private RelativeLayout mActionBar; // 标题栏
    private LinearLayout llBackBtn; // 返回按钮
    private LinearLayout llDeleteBtn; // 删除按钮

    private ArrayList<String> paths; // 要显示的图片路径集合
    private ArrayList<Integer> deletedImagePositionsHistory; // 删除条目位置的历史记录

    private int pathsSizeInitialValue; // 传入的图片路径集合初值

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);

        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        paths = getIntent().getStringArrayListExtra(EXTRA_IMAGE_PATHS);
        isShowDeleteButton = getIntent().getBooleanExtra(EXTRA_IMAGE_SHOW_DELETE_BUTTON, false);
        pathsSizeInitialValue = paths.size();
        deletedImagePositionsHistory = new ArrayList<>();

        mActionBar = (RelativeLayout) findViewById(R.id.ab_actionbar);
        llBackBtn = (LinearLayout) findViewById(R.id.ll_common_ab_back_btn);
        llDeleteBtn = (LinearLayout) findViewById(R.id.ll_delete_circle_img_btn);

        llBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paths.size() == pathsSizeInitialValue) { // 没有删除图片，则直接返回
                    ImagePagerActivity.this.finish();
                } else {
                    Intent data = new Intent();
                    data.putIntegerArrayListExtra(EXTRA_RESULT_IMAGE_DELETE_HISTORY,
                        deletedImagePositionsHistory); // 删除历史（只传回删除历史即可）

                    Log.d(TAG, "paths = " + paths.toString());
                    for (String s : paths) {
                        Log.d(TAG, "paths path = >>>>>> " + s);
                    }

                    Log.d(TAG, "deletedImagePositionsHistory = " + deletedImagePositionsHistory.toString());
                    for (Integer s : deletedImagePositionsHistory) {
                        Log.d(TAG, "deletedImagePositionsHistory history = >>>>>> " + s);
                    }

                    setResult(RESULT_OK, data);
                    ImagePagerActivity.this.finish();
                }
            }
        });

        initDeleteButton();

        mPager = (HackyViewPager) findViewById(R.id.pager);

        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), paths);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                pagerPosition = arg0;
                Log.d(TAG, "pagerPosition in onPageSelected = " + pagerPosition);
                updatePageIndicator();
            }
        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);
    }

    /**
     * 初始化删除按钮
     */
    private void initDeleteButton() {

        if (!isShowDeleteButton) {
            llDeleteBtn.setVisibility(View.GONE);
            return;
        }

        llDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "pagerPosition = " + pagerPosition);

                paths.remove(pagerPosition);
                deletedImagePositionsHistory.add(pagerPosition); // 记录删除历史

                if (pagerPosition == paths.size()) {
                    // 删除的是最后一个条目,则需要将pagerPosition向前移动一个位置，如果不是删除最后一个条目，则pagerPosition保持原位不动
                    pagerPosition = paths.size() - 1;
                }

                mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), paths);
                mPager.setAdapter(mAdapter);
                mPager.setCurrentItem(pagerPosition, false);
                updatePageIndicator();

                if (paths.size() == 0) {
                    setResult(RESULT_OK);
                    ImagePagerActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;
        private ArrayList<Fragment> mItems;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
            mItems = new ArrayList<>();

            for (int i = 0; i < fileList.size(); i++) {
                mItems.add(null);
            }
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String path = fileList.get(position);
            mItems.set(position, ImageDetailFragment.newInstance(path));
            return mItems.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    /**
     * 获取本页面的标题栏总体布局
     */
    public View getCustomActionBar() {
        return this.mActionBar;
    }

    /**
     * 更新页码指示器
     */
    private void updatePageIndicator() {
        CharSequence text = getString(R.string.viewpager_indicator, pagerPosition + 1, mPager.getAdapter().getCount());
        indicator.setText(text);
    }
}
