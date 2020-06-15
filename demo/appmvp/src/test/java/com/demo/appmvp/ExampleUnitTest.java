package com.demo.appmvp;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test
    public void unitTest() {


        System.out.println("Completable ---------");

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                System.out.println("Hello world!! this is completable.");
            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError");
            }
        });

        System.out.println("AsyncSubject ---------");

        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext("emmit 1");
        asyncSubject.onNext("emmit 2");
        asyncSubject.onNext("emmit 3");

        asyncSubject.onComplete();

        asyncSubject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("onNext" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        System.out.println("BehaviorSubject ---------");

        BehaviorSubject<Integer> subject = BehaviorSubject.create();

        BehaviorSubject<Integer> subjectDefault = BehaviorSubject.createDefault(-1);

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("onNext " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        //subjectDefault.subscribe(observer);

        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        //subject.subscribe(observer);
        subject.onNext(4);
        subject.onError(new NullPointerException());

        subject.subscribe(observer);

        System.out.println("PublishSubject --------------------------------");
        // 释放订阅后接收到正常发射的数据，有error将不会发射任何数据
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        // 观察者对象
        Observer<Integer> pobserver = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("--------------------------------");
                System.out.println("--> onSubscribe");
            }

            @Override
            public void onNext(Integer t) {
                System.out.println("--> onNext: " + t);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("--> onError: " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("--> onComplete");
            }
        };

        // 1. 此时订阅将释放后续正常发射的数据： 1，2, 3, 4, error
        // subject.subscribe(observer);
        publishSubject.onNext(1);
        publishSubject.onNext(2);

        // 2. 此时订阅，发射后续正常发射的数据：3, 4, error
        // subject.subscribe(observer);
        publishSubject.onNext(3);
        publishSubject.onNext(4);

        // 此时将不会发送任何数据，直接发送error
        publishSubject.onError(new NullPointerException());
        publishSubject.onNext(5);
        publishSubject.onComplete();

        // 3. 此时订阅如果有error，仅发送error，否则无数据发射
        publishSubject.subscribe(pobserver);


        //Observable.create(new ObservableOnSubscribe<Object>() {
        //    @Override
        //    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
        //        emitter.onNext();
        //        emitter.onNext();
        //        emitter.onNext();
        //    }
        //});

        //Observable.just()


    }

    @Test
    public void addition_isCorrect() {

        Flowable.just("Hello world!!").subscribe(System.out::println);


        // Subscribing
        Observable observable = Observable.create(emitter -> {
            emitter.onNext("hello world!!!");
        });

        Disposable disposable = observable.subscribe(o -> {
            System.out.println(o.toString());
        });

        disposable.isDisposed();

        disposable.dispose();


        assertEquals(4, 2 + 2);
    }


}
