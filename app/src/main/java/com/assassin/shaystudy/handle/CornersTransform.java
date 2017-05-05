package com.assassin.shaystudy.handle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @author 作者：Shay-Patrick-Cormac
 * @datetime 创建时间：2017/4/21 23:02
 * @email 邮箱： 574583006@qq.com
 * @content 说明：之前已经讲解过如何简单的显示图片,但是有时候项目中会有很多特殊的需求,比如说圆角处理,
 * 圆形图片,高斯模糊等,Glide提供了方法可以很好的进行处理,接下来我们就介绍一下。
 * 创建一个类继承BitmapTransformation
 */
public class CornersTransform  extends BitmapTransformation{
    //圆角大小
    private float radius;
    public CornersTransform(Context context) {
        super(context);
        //default value
        radius = 20;
    }
    public CornersTransform(Context context,float radius) {
        super(context);
        this.radius = radius;
    }
    

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return cornersCrop(pool,toTransform);
    }

    //圆角预处理
    private Bitmap cornersCrop(BitmapPool pool, Bitmap source) 
    {
        if (null==source)
            return null;
        //从缓存池取bitmap
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (null==result)
            //缓存池中没有的话，就创建一个bitmap
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        //画布
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        //设置音影
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF,radius,radius,paint);
        return result;
    }

    @Override
    public String getId() {
        //返回当前类的名字 
        return getClass().getName();
    }
}
