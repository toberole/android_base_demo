package com.xiaoge.org.activity.rxjava_activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.xiaoge.org.R;
import com.xiaoge.org.util.AppUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


public class RxJavaActivity_X extends AppCompatActivity {
    public static final String TAG = RxJavaActivity_X.class.getSimpleName();

    @BindView(R.id.btn_1)
    Button btn_1;

    @OnClick(R.id.btn_1)
    void btn1_clicked() {
        // test1();
        // test2();
        // test3(); // 切断的操作，让Observer观察者不再接收上游事件
        // test4(); // subscribe 抛出异常
        // test5(); // map
        // test6(); // zip
        // test7(); // Concat
        test7_x();
        // test8(); // FlatMap
        // test9(); // concatMap
        // test10(); // distinct
        // test11(); // Filter
        // test12(); // buffer
        // test13(); // timer
        // test14(); // interval
        // test15(); // doOnNext && doOnAfter
        // test16(); // skip
        // test17(); // take
        // test18(); // just
        // test19(); // single
        // test20(); // debounce
        // test21(); // defer
        // test22(); // last
        // test23(); // merge
        // test24(); // reduce
        // test25(); // scan
        // test26(); // window
    }

    /**
     * window
     * 按照实际划分窗口，将数据发送给不同的 Observable
     */
    private void test26() {
        Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
                .take(15) // 最多接收15个
                .window(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(@NonNull Observable<Long> longObservable) throws Exception {
                        Log.e(TAG, "Sub Divide begin...\n");

                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(@NonNull Long aLong) throws Exception {
                                        Log.e(TAG, "Next:" + aLong + "\n");
                                    }
                                });
                    }
                });
    }

    /**
     * scan 操作符作用和上面的 reduce 一致，
     * 唯一区别是 scan 会始终如一地把每一个步骤都输出。
     */
    private void test25() {
        Observable.just(1, 2, 3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        Log.e(TAG, "reduce apply i1: " + integer + " i2: " + integer2);
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: scan " + integer + "\n");
            }
        });
    }

    /**
     * reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值
     */
    private void test24() {
        Observable.just(1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        Log.e(TAG, "reduce apply i1: " + integer + " i2: " + integer2);
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: reduce : " + integer + "\n");
            }
        });
    }

    /**
     * merge 的作用是把多个 Observable 结合起来，接受可变参数，
     * 也支持迭代器集合。注意它和 concat 的区别在于，
     * 不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
     */
    private void test23() {
        Observable.merge(Observable.just(1, 2, 3, 4, 5), Observable.just(6, 7, 8, 9, 10))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "accept: merge :" + integer + "\n");
                    }
                });
    }

    /**
     * last仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
     */
    private void test22() {
        // 输出3
        Observable.just(1, 2, 3)
                .last(4)
                .subscribeOn(Schedulers.from(AppUtil.getInstance().getExecutor()))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "last : " + integer);
                    }
                });

        Log.e(TAG, "***************************");

        // 输出5
        Observable.just(1, 2, 3, 4, 5)
                .last(3)
                .subscribeOn(Schedulers.from(AppUtil.getInstance().getExecutor()))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "last : " + integer);
                    }
                });
    }

    /**
     * defer
     * 简单地时候就是每次订阅都会创建一个新的 Observable
     * 并且如果没有被订阅，就不会产生新的 Observable。
     */
    private void test21() {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                Log.i(TAG, "--------- time: " + System.currentTimeMillis());
                return Observable.just(1, 2, 3);
            }
        });

        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "accept: " + integer);
            }
        });
        Log.i(TAG, "++++++++++++++++++++ ");
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "accept: " + integer);
            }
        });
    }

    /**
     * debounce
     * 去除发送频率过快的项
     * 两个相邻数据发射的时间间隔决定了前一个数据是否会被丢弃
     */
    private void test20() {
        // 输出1,3,4
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(200);
                emitter.onNext(2);
                emitter.onNext(3);
                Thread.sleep(200);
                emitter.onNext(4);
            }
        }).debounce(100, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "debounce accept: " + integer);
                    }
                });
    }

    /**
     * Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()
     */
    private void test19() {
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                emitter.onSuccess("hello single");
            }
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }
        });

        Single.just(666).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                Log.i(TAG, "onSuccess: " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /**
     * 发射器依次调用 onNext()方法
     */
    private void test18() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "just accept: " + integer);
                    }
                });
    }

    /**
     * 最多接收多少个数据
     */
    private void test17() {
        Observable.just(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "take accept: " + integer);
                    }
                });
    }

    /**
     * skip
     * 跳过 count 个数目开始接收。
     */
    private void test16() {
        // 跳过2个 输出3，4，5
        Observable.just(1, 2, 3, 4, 5).skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "skip accept: " + integer);
                    }
                });
    }

    /**
     * 让订阅者在接收到数据之前做一些操作
     */
    private void test15() {
        Observable.just(1, 2, 3, 4, 5).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "doOnNext accept: " + integer);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "subscribe accept: " + integer);
            }
        });
    }

    /**
     * interval
     * 间隔时间执行一个任务,默认在新线程
     */
    private void test14() {
        // disposable 取消接收
        Disposable disposable = Observable.interval(2, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "timer :" + aLong + " at " + (new Date()) + "\n");
                    }
                });
    }

    /**
     * timer
     * 相当于一个定时任务,默认在新线程
     */
    private void test13() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "timer :" + aLong + " at " + (new Date()) + "\n");
                    }
                });
    }

    /**
     * buffer 操作符接受两个参数，buffer(count,skip)
     * 作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer
     * 然后生成一个Observable 。
     */
    private void test12() {
        // 按照步长为2 最大长度不超过3来切割
        Observable.just(1, 2, 3, 4, 5).buffer(3, 2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                for (Integer i : integers) {
                    Log.i(TAG, "i: " + i);
                }

                Log.i(TAG, " ---------------------- ");
            }
        });
    }

    /**
     * Filter
     * 过滤
     */
    private void test11() {
        Observable.just(1, 2, 3, -1, -2, -3)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 0;// 过滤掉 <0 的
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "filter i: " + integer);
            }
        });
    }

    /**
     * distinct
     * 去除重复的
     * 可以自定义去重规则
     */
    private void test10() {
        Observable.just(1, 2, 2, 1, 3, 4, 5, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "distinct accept i:" + integer);
                    }
                });
    }

    /**
     * concatMap
     */
    private void test9() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e(TAG, "flatMap : accept : " + s + "\n");
                    }
                });
    }

    /**
     * FlatMap
     * 它可以把一个发射器 Observable 通过某种方法转换为多个 Observables
     * 然后再把这些分散的 Observables装进一个单一的发射器 Observable。
     * 但有个需要注意的是，flatMap 并不能保证事件的顺序
     * 如果需要保证，需要用到我们下面要讲的 ConcatMap
     */
    private void test8() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e(TAG, "flatMap : accept : " + s + "\n");
                    }
                });
    }

    /**
     * Concat
     * 对于单一的把两个发射器连接成一个发射器
     * eg: 依次输出1，2，3，4，5，6，7
     */
    private void test7() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6, 7))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "concat accept i:" + integer);
                    }
                });
    }

    private void test7_x() {
        Observable<String> cache = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "cache subscribe");
                String[] datas = new String[]{"1", "2", "3"};
                for (String s : datas) {
                    if (s.equalsIgnoreCase("2")) {
                        emitter.onComplete();
                        break;
                    } else {
                        emitter.onNext(s);
                    }
                }
            }
        });

        Observable<String> net = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "net subscribe");
                emitter.onNext("net");
            }
        });

        Observable.concat(cache, net)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    private Disposable d;

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                        this.d = d;
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                        if (s.equalsIgnoreCase("1")) {
                            d.dispose();// 取消 会导致导致后面net Observable不执行
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete ");
                    }
                });
    }

    /**
     * zip
     * 用于合并事件，该合并不是连接，而是两两配对
     * 也就意味着最终配对出的 Observable 发射事件数目只和少的那个相同
     * <p>
     * zip 组合事件的过程就是分别从发射器 A 和发射器 B 各取出一个事件来组合
     * 并且一个事件只能被使用一次，组合的顺序是严格按照事件发送的顺序来进行的
     * 最终接收器收到的事件数量是和发送器发送事件最少的那个发送器的发送事件数目相同
     */
    private void test6() {
        Observable.zip(getIntegerObserver(), getStringObserver(), new BiFunction<Integer, String, Object>() {
            @Override
            public Object apply(Integer integer, String s) throws Exception {
                Log.i(TAG, "BiFunction apply i: " + integer + " s: " + s);

                return s + integer.toString();
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG, "subscribe accept o: " + o);
            }
        });
    }

    private Observable<Integer> getIntegerObserver() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
            }
        });
    }

    private Observable<String> getStringObserver() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("java");
                emitter.onNext("php");
                emitter.onNext("C++");
            }
        });
    }

    /**
     * map
     * 对发射时间发送的每一个事件应用一个函数
     * <p>
     * 事件传递路径：
     * Observable--->map--->subscribe
     */
    private void test5() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello map");
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                Log.i(TAG, "map apply s: " + s);
                return 666;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer i) throws Exception {
                Log.i(TAG, "subscribe accept i: " + i);
            }
        });
    }

    /**
     * subscribe 抛出异常 会导致Observer的onError方法回调
     */
    private void test4() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String res = "res";
                if (res.equalsIgnoreCase("res")) {
                    throw new RuntimeException("-------- 测试 --------");
                }
            }
        }).subscribeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    // 切断的操作，让Observer观察者不再接收上游事件
    private void test3() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello");
                emitter.onNext("java");
                emitter.onNext("C++");
                emitter.onComplete();// 会触发接受者的onComplete，后续的发射事件还是会被发射
                emitter.onNext("PHP");
            }
        }).subscribe(new Observer<String>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "s: " + s);
                if ("java".equalsIgnoreCase(s)) {
                    //d.dispose();// 不在接受后续的回调
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
    }

    private void test2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello test2");
                emitter.onComplete();// 触发收受者的onComplete
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext");
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

    /**
     * ObservableOnSubscribe.subscribe
     * 默认是在执行subscribe方法的线程里面执行的
     */
    private void test1() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello Rxjava");
                Log.i(TAG, "Thread name: " + Thread.currentThread().getName());
            }
        }).observeOn(/*回调的执行线程*/AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(RxJavaActivity_X.this, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java__x);
        ButterKnife.bind(this);
    }
}
