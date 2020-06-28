package com.demo.corelib.util.image;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.demo.corelib.R;
import com.demo.corelib.util.ImageUtils;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * 单张图片显示Fragment
 * 目前仅支持两种图片路径：
 * 1. 本地文件路径
 * 2. 网络图片路径
 */
public class ImageDetailFragment extends Fragment {

    // TODO 考虑是否将其移到 widgets 库中

    private static final String TAG = ImageDetailFragment.class.getSimpleName();

    private static final String FILE_URI_PREFIX = "file://";
    private String mImagePath;
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;

    public static ImageDetailFragment newInstance(String imagePath) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imagePath);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePath = getArguments() != null ? getArguments().getString("url") : null;
        if (mImagePath != null) {

            if (URLUtil.isHttpsUrl(mImagePath) || URLUtil.isHttpUrl(mImagePath)) {
                Log.d(TAG, "mImagePath = " + mImagePath);
                return;
            }

            mImagePath = FILE_URI_PREFIX + mImagePath;
        }

        Log.d(TAG, "mImagePath = " + mImagePath);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);

        mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

            @Override
            public void onPhotoTap(ImageView arg0, float arg1, float arg2) {
                if (((ImagePagerActivity) getActivity()).getCustomActionBar().isShown()) {
                    ((ImagePagerActivity) getActivity()).getCustomActionBar().setVisibility(View.INVISIBLE);
                } else {
                    ((ImagePagerActivity) getActivity()).getCustomActionBar().setVisibility(View.VISIBLE);
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageUtils.showImageByGlide(mImageView, mImagePath, getContext());

    }

}
