package com.demo.appmvp.di.module;

import com.demo.appmvp.data.UserRepository;
import com.demo.appmvp.mvp.contract.LoginContract;
import com.demo.appmvp.mvp.presenter.LoginPresenter;
import dagger.Module;
import dagger.Provides;

@Module(includes = {AccountModule.class})
public class PresenterProviderModule {

    @Provides
    public LoginContract.Presenter provideLoginPresenter(UserRepository userRepository) {
        return new LoginPresenter(userRepository);
    }


}
