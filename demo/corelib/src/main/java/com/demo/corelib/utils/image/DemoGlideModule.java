package com.demo.corelib.utils.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Glide
 */
@GlideModule
public class DemoGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }


    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

    }
}
