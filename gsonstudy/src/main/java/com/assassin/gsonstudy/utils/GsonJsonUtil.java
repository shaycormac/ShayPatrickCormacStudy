package com.assassin.gsonstudy.utils;

import com.assassin.gsonstudy.entity.LzyResponse;
import com.assassin.gsonstudy.entity.SimpleResponse;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/27 15:15
 * @Version:
 * @Description:
 */

public abstract class  GsonJsonUtil<T>
{
    
    public  <T> T getJson(Response response) throws NoSuchMethodException {
     /*   Method applyMethod = GsonJsonUtil.class.getMethod("getJson", Response.class);
        Type[] types=applyMethod.getGenericParameterTypes();
        ParameterizedType pType = (ParameterizedType) types[0];
        Type type2=pType.getActualTypeArguments()[0];
        Type type3 = pType.getRawType();*/
        
        Type genType = getClass().getGenericSuperclass();
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
        //com.lzy.demo.model.LzyResponse<com.lzy.demo.model.ServerModel>
        Type type = params[0];

        // 这里这么写的原因是，我们需要保证上面我解析到的type泛型，仍然还具有一层参数化的泛型，也就是两层泛型
        // 如果你不喜欢这么写，不喜欢传递两层泛型，那么以下两行代码不用写，并且javabean按照
        // https://github.com/jeasonlzy/okhttp-OkGo/blob/master/README_JSONCALLBACK.md 这里的第一种方式定义就可以实现
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        //如果确实还有泛型，那么我们需要取出真实的泛型，得到如下结果
        //class com.lzy.demo.model.LzyResponse
        //此时，rawType的类型实际上是 class，但 Class 实现了 Type 接口，所以我们用 Type 接收没有问题
        Type rawType = ((ParameterizedType) type).getRawType();
        //这里获取最终内部泛型的类型 com.lzy.demo.model.ServerModel
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        if (typeArgument == Void.class) {
            //无数据类型,表示没有data数据的情况（以  new DialogCallback<LzyResponse<Void>>(this)  以这种形式传递的泛型)
            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
            response.close();
            //noinspection unchecked
            return (T) simpleResponse.toJsonResponse();
        } else if (rawType == LzyResponse.class)
        {
            //有数据类型，表示有data
            LzyResponse lzyResponse = Convert.fromJson(jsonReader, type);
            response.close();
            int code = lzyResponse.code;
            //这里的0是以下意思
            //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
            if (code == 1)
            {
                //noinspection unchecked
              //  ToastUtils.ToastBottomMsg(StudyApp.getInstance(),lzyResponse.msg);
                return (T) lzyResponse;
            } else if (code == 104) {
                //比如：用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户授权信息无效");
            } else if (code == 105) {
                //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户收取信息已过期");
            } else if (code == 106) {
                //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户账户被禁用");
            } else if (code == 300) {
                //比如：其他乱七八糟的等，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("其他乱七八糟的等");
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + lzyResponse.msg);
            }
        } else
        {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }
}
