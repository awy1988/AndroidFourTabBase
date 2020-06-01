//package com.demo.appko.data.remote.api
//
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.demo.appko.constant.AppModuleApiConstant
//import com.demo.corelib.model.common.LinksModel
//import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener
//import com.demo.corelib.network.base.HttpApiHelper
//import com.nhaarman.mockitokotlin2.mock
//import okhttp3.Headers
//import org.junit.After
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.util.concurrent.CountDownLatch
//import com.nhaarman.mockitokotlin2.verify
//
//@RunWith(AndroidJUnit4::class)
//class AuthServiceTest {
//
//    private lateinit var authService: AuthService
//
//    private lateinit var callbackListener: HandleResponseHeaderRequestCallbackListener<Any>
//
//    @Before
//    fun setUp() {
//        val iAuthService = HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
//            .create(IAuthService::class.java)
//        authService = AuthService(iAuthService)
//        callbackListener = mock()
//    }
//
//    @After
//    fun tearDown() {
//    }
//
//    @Test
//    fun test() {
//        Assert.assertEquals(true,false)
//    }
//
//    @Test
//    fun test_authorizations_by_mockito() {
//        authService.authorizations("super", "super", "", "", callbackListener);
//        verify(callbackListener).onStarted()
//    }
//
//    @Test
//    fun test_authorizations_success() {
//        var onStartCalled = false
//        var onHandleResponseHeadersCalled = false
//        var onEndWithErrorCalled = false
//        var onCompletedCalled = false
//        var token = ""
//        val signal = CountDownLatch(1)
//
//        authService.authorizations("super","super","","", object : HandleResponseHeaderRequestCallbackListener<Any> {
//            override fun onStarted() {
//                onStartCalled = true
//            }
//
//            override fun onEndedWithError(errorInfo: String?) {
//                onEndWithErrorCalled = true
//                signal.countDown()
//            }
//
//            override fun onHandleResponseHeaders(headers: Headers?) {
//                onHandleResponseHeadersCalled = true
//                token = headers!!["x-access-token"].toString()
//            }
//
//            override fun onCompleted(
//                data: Any?,
//                links: LinksModel?
//            ) {
//                onCompletedCalled = true
//                signal.countDown()
//            }
//
//        })
//
//        signal.await()
//
//        Assert.assertEquals(true, onStartCalled)
//        Assert.assertEquals(true, onCompletedCalled)
//        Assert.assertEquals(true, onHandleResponseHeadersCalled)
//        Assert.assertEquals(false, onEndWithErrorCalled)
//        Assert.assertNotEquals("", token)
//    }
//
//    @Test
//    fun test_authorizations_failed() {
//        var onStartCalled = false
//        var onHandleResponseHeadersCalled = false
//        var onEndWithErrorCalled = false
//        var onCompletedCalled = false
//        val signal = CountDownLatch(1)
//
//        authService.authorizations("super","1234","","", object : HandleResponseHeaderRequestCallbackListener<Any> {
//            override fun onStarted() {
//                onStartCalled = true
//            }
//
//            override fun onEndedWithError(errorInfo: String?) {
//                onEndWithErrorCalled = true
//                signal.countDown()
//            }
//
//            override fun onHandleResponseHeaders(headers: Headers?) {
//                onHandleResponseHeadersCalled = true
//            }
//
//            override fun onCompleted(
//                data: Any?,
//                links: LinksModel?
//            ) {
//                onCompletedCalled = true
//                signal.countDown()
//            }
//
//        })
//
//        signal.await()
//
//        Assert.assertEquals(true, onStartCalled)
//        Assert.assertEquals(false, onCompletedCalled)
//        Assert.assertEquals(true, onHandleResponseHeadersCalled)
//        Assert.assertEquals(true, onEndWithErrorCalled)
//    }
//}
