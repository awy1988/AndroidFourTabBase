package com.demo.appmvp.network.payload;

import com.demo.corelib.model.common.ResponseModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;

public class ResponseTransformer {

    public static <T> ObservableTransformer<ResponseModel<T>, T> handleResult() {
        return upstream -> {
            return upstream.onErrorResumeNext(new ErrorResumeFunction<>())
                        .flatMap(new ResponseFunction<>());
        };
    }

    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends ResponseModel<T>>> {

        @Override
        public ObservableSource<? extends ResponseModel<T>> apply(Throwable throwable) throws Throwable {
            return Observable.error(CustomException.handleException(throwable));
        }
    }


    private static class ResponseFunction<T> implements Function<ResponseModel<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(ResponseModel<T> tResponse) throws Throwable {
            boolean isSuccess = tResponse.getSuccess();
            if (isSuccess) {
                return Observable.just(tResponse.getData());
            } else {
                return Observable.error(new ApiException(800, tResponse.getError().getMessage()));
            }
        }
    }



}
