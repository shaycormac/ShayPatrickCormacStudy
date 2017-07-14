package com.assassin.gsonstudy.newNet;

import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/14 11:23
 * @Version:
 * @Description:
 */

public abstract class MyCallback<T> extends AbsCallback<T>
{
    private Type type;
    private Class<T> clazz;

    public MyCallback() {
    }

    public MyCallback(Type type) {
        this.type = type;
    }

    public MyCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

   /* @Override
    public void onB(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        request.headers("header1", "HeaderValue1")//
                .params("params1", "ParamsValue1")//
                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }*/

    @Override
    public T convertSuccess(Response response) throws Exception
    {
        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertSuccess(response);
            }
        }

        JsonConvert<T> convert = new JsonConvert<>(type);
        return convert.convertSuccess(response);
    }
}
