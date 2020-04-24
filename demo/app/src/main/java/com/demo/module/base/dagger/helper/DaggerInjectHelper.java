package com.demo.module.base.dagger.helper;

import com.demo.module.base.BaseFragment;
import com.demo.module.base.BaseFragmentActivity;
import com.demo.module.base.dagger.component.DaggerFragmentComponent;
import com.demo.module.main.MainFragment;
import com.demo.module.main.ProfileFragment;

public class DaggerInjectHelper {

    public static void inject(BaseFragmentActivity activity) {

    }

    public static void inject(BaseFragment fragment) {
        if (fragment instanceof MainFragment) {
            DaggerFragmentComponent.create().inject((MainFragment) fragment);
        } else if (fragment instanceof ProfileFragment) {
            DaggerFragmentComponent.create().inject((ProfileFragment) fragment);
        }
    }
}
