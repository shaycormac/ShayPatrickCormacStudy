package com.assassin.rxjava.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.assassin.rxjava.R;
import com.assassin.rxjava.RetrofitProvider;
import com.assassin.rxjava.entity.LoginRequest;
import com.assassin.rxjava.entity.LoginResponse;
import com.assassin.rxjava.entity.RegisterRequest;
import com.assassin.rxjava.entity.RegisterResponse;
import com.assassin.rxjava.entity.UserBaseInfoRequest;
import com.assassin.rxjava.entity.UserBaseInfoResponse;
import com.assassin.rxjava.entity.UserExtraInfoRequest;
import com.assassin.rxjava.entity.UserExtraInfoResponse;
import com.assassin.rxjava.entity.UserInfo;
import com.assassin.rxjava.interf.Api;
import com.assassin.rxjava.util.VolleyLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class Rxjava2Activity extends AppCompatActivity {
    //发射器在当前activity销毁，干掉下游接受
    private Disposable disposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //第一格列兹
        //  firstRxjava();
        //连接起来链式调用
        // chainRxjava();
        //dispose的学习使用
        // disposeStudy();
        //RxJava线程控制的学习(子线程发射，UI线程接受)
        // threadRxjava();
        //模拟发一个网络请求
        // retrofitDemo();
        //学习map操作符
       // mapStudy();
        //学习flatMap的使用
       // flatMapStudy();
        //嵌套请求，使用flatMap(注册完后登录)
      //  nestedRequest();
        //zip的学习，适用于一个页面多个接口，同时有数据了才展示
        zipStudy();
        //zip的具体业务场景模拟
        zipRequestStudy();
    }

    private void zipRequestStudy()
    {
        Api api = RetrofitProvider.get().create(Api.class);
        //两个请求
        Observable<UserBaseInfoResponse> baseInfoObservable = api.getUserBaseInfo(new UserBaseInfoRequest())
                .subscribeOn(Schedulers.io());
        Observable<UserExtraInfoResponse> extraInfoResponseObservable = api.getUserExtraInfo(new UserExtraInfoRequest())
                .subscribeOn(Schedulers.io());
        Observable.zip(baseInfoObservable, extraInfoResponseObservable, new BiFunction<UserBaseInfoResponse, UserExtraInfoResponse, UserInfo>() {
            @Override
            public UserInfo apply(UserBaseInfoResponse userBaseInfoResponse, UserExtraInfoResponse userExtraInfoResponse) throws Exception {
                //返回去什么呢？？？？
                return new UserInfo(userBaseInfoResponse, userExtraInfoResponse);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfo>() 
                {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception 
                    {
                        //获取了数据，要展示
                        
                    }
                });
    }

    private void zipStudy() 
    {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception 
            {
                VolleyLog.d("水管1发射：1");
                e.onNext(1);
                VolleyLog.d("水管1发射：2");
                e.onNext(2);
                VolleyLog.d("水管1发射：3");
                e.onNext(3);
                VolleyLog.d("水管1发射：4");
                e.onNext(4);
                VolleyLog.d("水管1发射完毕");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                VolleyLog.d("水管2发射：a");
                e.onNext("a");
                VolleyLog.d("水管2发射：b");
                e.onNext("b");
                VolleyLog.d("水2发射：c");
                e.onNext("c");
                VolleyLog.d("水管2发射完毕");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        //zip合并
        Observable.zip(observable, stringObservable, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                //合并这两个类发出去
                return integer + "  " + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
        
    }

    private void nestedRequest()
    {
        //得到api对象
        final Api api = RetrofitProvider.get().create(Api.class);
        api.register(new RegisterRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception {
                        //根据注册的相应结果去做一些操作
                    }
                })
                .observeOn(Schedulers.io())  //回到IO线程
                .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
                    @Override
                    public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse) throws Exception {
                        return api.login(new LoginRequest());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception 
                    {
                        Toast.makeText(Rxjava2Activity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void flatMapStudy() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<String>();
                        for (int i = 0; i < 3; i++) {
                            list.add("ShayCormac===" + integer);
                        }
                        return Observable.fromIterable(list).delay(2, TimeUnit.SECONDS);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception
            {
                VolleyLog.d("得到过来的数据：%s",s);
            }
        });
    }

    private void mapStudy() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer) + "ShayCormac";
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                VolleyLog.d("得到的结果为：%s", s);
            }
        });
    }

    private void retrofitDemo() {
        //得到api的实现类
        Api api = RetrofitProvider.get().create(Api.class);
        //api.get250Top(0,250)这是一个发射器，大哥
        api.get250Top(0, 250)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> value) {
                        try {
                            VolleyLog.d("得到的值为：%s", value.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        VolleyLog.d("错误的云因：%s", throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void threadRxjava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                VolleyLog.d("发射器所在的线程为：%s", Thread.currentThread().getName());
                e.onNext(1);

            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        VolleyLog.d("接受器所在的线程为：%s", Thread.currentThread().getName());
                        VolleyLog.d("的道德之为：%d", integer);
                    }
                });
    }


    private void firstRxjava() {
        //创建一个上游 observable
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //发射
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });
        //创建一个下游observe
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                VolleyLog.d("onSubscribe执行");
            }

            @Override
            public void onNext(Integer value) {
                VolleyLog.d("onNext的得到的值：%d", value);
            }

            @Override
            public void onError(Throwable e) {
                VolleyLog.d("onError执行");
            }

            @Override
            public void onComplete() {
                VolleyLog.d("onComplete执行");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }

    private void chainRxjava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //发射
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                VolleyLog.d("onSubscribe执行");
            }

            @Override
            public void onNext(Integer value) {
                VolleyLog.d("onNext的得到的值：%d", value);
            }

            @Override
            public void onError(Throwable e) {
                VolleyLog.d("onError执行");
            }

            @Override
            public void onComplete() {
                VolleyLog.d("onComplete执行");
            }
        });
    }


    private void disposeStudy() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //发射
                VolleyLog.d("要发射的数字为1");
                e.onNext(1);
                VolleyLog.d("要发射的数字为2");
                e.onNext(2);
                VolleyLog.d("要发射的数字为3");
                e.onNext(3);
                VolleyLog.d("发射完成标志，上游可以继续发射，但是下游接到这个就不能再接受后续事件了");
                e.onComplete();
                VolleyLog.d("要发射的数字为4");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            //把dispose提出来
            private Disposable disposable;
            //第几个数
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                VolleyLog.d("onSubscribe执行");
                disposable = d;
            }

            @Override
            public void onNext(Integer value) {
                VolleyLog.d("onNext的得到的值：%d", value);
                i++;
                if (i == 2) {
                    VolleyLog.d("开始执行Dispose");
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                VolleyLog.d("onError执行");
            }

            @Override
            public void onComplete() {
                VolleyLog.d("onComplete执行");
            }
        });
    }


    @Override
    protected void onDestroy() {
        //销毁请求(干掉容器里面所有的dispose)
        compositeDisposable.clear();
        disposable.dispose();
        super.onDestroy();

    }
}
