package com.xiaoge.org.activity.rxjava_activitys;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiaoge.org.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class RxJavaActivity extends AppCompatActivity {
    public static final String TAG = RxJavaActivity.class.getSimpleName();

    private Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            Log.i(TAG, "Observable subscribe " + emitter.serialize());
        }
    });

    private Observable observable_1 = Observable.just("hello", "world");

    String[] arr = new String[]{"C++", "Java"};

    private Observable observable_2 = Observable.fromArray(arr);

    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.i(TAG, "Observer onSubscribe");
        }

        @Override
        public void onNext(String s) {
            Log.i(TAG, "Observer onNext");
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "Observer onError");
        }

        @Override
        public void onComplete() {
            Log.i(TAG, "Observer onComplete");
        }
    };

    private Subscriber<String> subscriber = new Subscriber<String>() {

        @Override
        public void onSubscribe(Subscription s) {
            Log.i(TAG, "Subscriber onSubscribe");
        }

        @Override
        public void onNext(String s) {
            Log.i(TAG, "Subscriber onNext");
        }

        @Override
        public void onError(Throwable t) {
            Log.i(TAG, "Subscriber onError");
        }

        @Override
        public void onComplete() {
            Log.i(TAG, "Subscriber onComplete");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable_1.subscribe(observer);
                observable.just("A", "B", "C");
            }
        });

        findViewById(R.id.btn_start1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1, 2, 3)
                        .subscribeOn(Schedulers.io())// 指定subscribe发生在IO线程
                        .observeOn(AndroidSchedulers.mainThread())// 指定回调运行在主线程
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.i(TAG, "onSubscribe");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.i(TAG, "onNext: " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError");
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG, "onComplete");
                            }
                        });
            }
        });

        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Log.i(TAG, "subscribe");
                        emitter.onNext("hello");
                        emitter.onNext("world");
                    }
                }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });

        findViewById(R.id.btn_consumer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Log.i(TAG, "subscribe");
                        emitter.onNext("hello");
                        emitter.onNext("world");
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "accept: " + s);
                    }
                });
            }
        });

        findViewById(R.id.btn_flatMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Log.i(TAG, "subscribe");
                        emitter.onNext("hello");
                        emitter.onNext("world");
                    }
                }).flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {// 相当于拦截回调 重新发射
                        Log.i(TAG, "flatMap apply: " + s);

                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("I am value " + s);
                        }
                        //随机生成一个时间
                        int delayTime = (int) (1 + Math.random() * 10);
                        return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.i(TAG, "accept: " + s);
                            }
                        });
            }
        });
    }
}
