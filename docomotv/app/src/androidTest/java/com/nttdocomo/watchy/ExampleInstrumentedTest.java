package com.nttdocomo.watchy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.nttdocomo.watchy", appContext.getPackageName());
    }

    @Test
    public void HttpApiServiceGetTest() {
//        ItemService.getItems(new HashMap<String, String>(), new RequestCallbackListener<List<Item>>() {
//            @Override
//            public void onStarted() {
//                System.out.println("onStarted: ");
//            }
//
//            @Override
//            public void onCompleted(List<Item> data, LinksModel links) {
//                System.out.println("onCompleted: ");
//                System.out.println("onCompleted: data[0] = " + data.get(0).getName() );
//            }
//
//            @Override
//            public void onEndedWithError(String errorInfo) {
//                System.out.println("onEndedWithError: " + errorInfo);
//            }
//        });
    }

    @Test
    public void HttpApiServicePostTest() {
//        AuthorizationRequestBodyModel requestBody = new AuthorizationRequestBodyModel();
//        requestBody.setUsername("15840891377");
//        requestBody.setPassword("rd123456");
//        AccountService.authorization(requestBody, new RequestCallbackListener<UserInfoModel>() {
//            @Override
//            public void onStarted() {
//                System.out.println("onStarted: ");
//            }
//
//            @Override
//            public void onCompleted(UserInfoModel data, LinksModel links) {
//                System.out.println("onCompleted: ");
//                System.out.println("onCompleted: data = " + data.getName());
//            }
//
//            @Override
//            public void onEndedWithError(String errorInfo) {
//                System.out.println("onEndedWithError: ");
//            }
//        });
    }
}
