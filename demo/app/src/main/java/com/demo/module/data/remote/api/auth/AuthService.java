package com.demo.module.data.remote.api.auth;

import com.demo.corelib.model.item.Item;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.module.data.remote.api.auth.model.AuthenticateRequestBodyModel;
import java.util.List;

public class AuthService {

    /**
     * TODO 优化逻辑，这个类的使用最好使用依赖注入的方式。
     * 获取商品列表
     * @param username 用户名
     * @param password 密码
     * @param listener
     */
    public void authorizations(String username, String password, final RequestCallbackListener<List<Item>> listener) {
        AuthenticateRequestBodyModel authenticateRequestBodyModel = new AuthenticateRequestBodyModel();
        authenticateRequestBodyModel.setUsername(username);
        authenticateRequestBodyModel.setPassword(password);
        HttpApiHelper.executeRequest(getAuthService().authorizations(authenticateRequestBodyModel), listener);
    }

    private IAuthService getAuthService() {
        return HttpApiHelper.getRetrofitInstance().create(IAuthService.class);
    }

}
