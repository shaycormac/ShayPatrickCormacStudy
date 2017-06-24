package com.assassin.rxjava.interf;

import com.assassin.rxjava.entity.LoginRequest;
import com.assassin.rxjava.entity.LoginResponse;
import com.assassin.rxjava.entity.RegisterRequest;
import com.assassin.rxjava.entity.RegisterResponse;
import com.assassin.rxjava.entity.UserBaseInfoRequest;
import com.assassin.rxjava.entity.UserBaseInfoResponse;
import com.assassin.rxjava.entity.UserExtraInfoRequest;
import com.assassin.rxjava.entity.UserExtraInfoResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/31 16:44
 * @Version: 1.0
 * @Description: rx网络请求
 */

public interface Api 
{
    //这两个接口用于练习嵌套请求，使用flatMap(contacMap按顺序)的学习
    @GET
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @GET("v2/movie/top250")
    Observable<Response<ResponseBody>> get250Top(@Query("start") int start, @Query("count") int count);
    //这两个接口用于zip的学习（业务场景：一个页面需要两个接口，请求完之后才能一块显示）

    @GET
    Observable<UserBaseInfoResponse> getUserBaseInfo(@Body UserBaseInfoRequest request);

    @GET
    Observable<UserExtraInfoResponse> getUserExtraInfo(@Body UserExtraInfoRequest request);
}
