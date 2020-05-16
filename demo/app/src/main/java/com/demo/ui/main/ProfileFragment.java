package com.demo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.demo.R;
import com.demo.databinding.ProfileFragBinding;
import com.demo.ui.base.BaseFragment;
import com.demo.ui.main.viewmodel.ProfileViewModel;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private ProfileFragBinding mBinding;

    private ProfileViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View view = inflater.inflate(R.layout.profile_frag, container, false);
        mBinding = (ProfileFragBinding) DataBindingUtil.bind(view);
        mBinding.setFragment(this);
        mBinding.setViewModel(mViewModel);

        mViewModel.getUserInfo();

        mViewModel.getUser().observe(this, user -> {

        });
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    public void logout() {

        Toast.makeText(getActivity(), "logout", Toast.LENGTH_SHORT).show();
    }
}
