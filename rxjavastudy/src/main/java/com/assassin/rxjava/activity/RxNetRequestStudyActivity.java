package com.assassin.rxjava.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.assassin.rxjava.R;
import com.assassin.rxjava.entity.MobileAdress;
import com.assassin.rxjava.util.VolleyLog;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RxNetRequestStudyActivity extends AppCompatActivity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_net_request_study);

        simpleOkHttpRequest();
        contactRequest();
    }

    /**
     * 2、先读取缓存，如果缓存没数据再通过网络请求获取数据后更新UI

     想必在实际应用中，很多时候（对数据操作不敏感时）都需要我们先读取缓存的数据，如果缓存没有数据，
     再通过网络请求获取，随后在主线程更新我们的UI。

     concat 操作符简直就是为我们这种需求量身定做。

     concat 可以做到不交错的发射两个甚至多个 Observable 的发射事件，并且只有前一个 Observable 
     终止(onComplete) 后才会定义下一个 Observable。

     利用这个特性，我们就可以先读取缓存数据，倘若获取到的缓存数据不是我们想要的，再调用 onComplete() 
     以执行获取网络数据的Observable，如果缓存数据能应我们所需，则直接调用 onNext() ，
     防止过度的网络请求，浪费用户的流量。
     */
    private void contactRequest()
    {
        
        
    }

    /**
     * 1、简单的网络请求
        想必大家都知道，很多时候我们在使用 RxJava 的时候总是和 Retrofit 进行结合使用，而为了方便演示，
     这里我们就暂且采用 OkHttp3 进行演示，配合 map，doOnNext ，线程切换进行简单的网络请求：
     1）通过 Observable.create() 方法，调用 OkHttp 网络请求；
     2）通过 map 操作符集合 gson，将 Response 转换为 bean 类；
     3）通过 doOnNext() 方法，解析 bean 中的数据，并进行数据库存储等操作；
     4）调度线程，在子线程中进行耗时操作任务，在主线程中更新 UI ；
     5）通过 subscribe()，根据请求成功或者失败来更新 UI 。
     */
    private void simpleOkHttpRequest() 
    {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .build();

                Request request = new Request.Builder()
                        .url("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                        .get().build();
                Call call = client.newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).map(new Function<Response, MobileAdress>() {
            @Override
            public MobileAdress apply(Response response) throws Exception {
                VolleyLog.d("map的线程：%s", Thread.currentThread().getName() + "\n");
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        VolleyLog.d("转换前的map：%s", response.body() + "\n");
                        return new Gson().fromJson(body.string(), MobileAdress.class);

                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<MobileAdress>() {
                    @Override
                    public void accept(MobileAdress mobileAdress) throws Exception {

                        VolleyLog.d("ui之前的保存数据~~");
                    }
                }).subscribe(new Consumer<MobileAdress>() {
            @Override
            public void accept(MobileAdress mobileAdress) throws Exception {
                VolleyLog.d("获取的数据~~");
                VolleyLog.d("获取的数据~~%d", mobileAdress.result.mobilenumber);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                VolleyLog.d("错误的原因~%s", throwable.getLocalizedMessage());
            }
        });
        
    }
}
