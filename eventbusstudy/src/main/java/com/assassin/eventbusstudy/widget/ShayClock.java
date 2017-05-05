package com.assassin.eventbusstudy.widget;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

import com.assassin.eventbusstudy.R;


/**
 * @author 作者：Shay-Patrick-Cormac
 * @datetime 创建时间：2017/5/1 23:16
 * @email 邮箱： 574583006@qq.com
 * @content 说明：自定义时钟，学习自定义View时再深入一点的一些方法。
 */
public class ShayClock extends View {
    //记录当前时间
    private Time time;
    //存放三张图片资源
    private Drawable mHourHand;
    private Drawable mMinuteHand;
    private Drawable mDial;
    //用来记录表盘图片的宽和高，
    //以便帮助我们在onMeasure中确定View的大
    //小，毕竟，我们的View中最大的一个Drawable就是它了。
    private int mDialWidth;
    private int mDialHeight;
    //用来记录View是否被加入到了Window中，我们在View attached到
    //Window时注册监听器，监听时间的变更，并根据时间的变更，改变自己
//的绘制，在View从Window中剥离时，解除注册，因为我们不需要再监听
//时间变更了，没人能看得到我们的View了。
    private boolean mAttach;
    //分，时
    private float mMinutes;
    private float mHour;
    //用来跟踪我们的View 的尺寸的变化，
//当发生尺寸变化时，我们在绘制自己
//时要进行适当的缩放。
    private boolean mChanged;


    public ShayClock(Context context) {
        this(context, null);
    }

    public ShayClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShayClock(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShayClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Resources r = context.getResources();
        if (mDial == null)
            // mDial = context.getDrawable(R.drawable.clock_dial);
            mDial = ContextCompat.getDrawable(context, R.drawable.clock_dial);
        if (mHourHand == null)
            mHourHand = ContextCompat.getDrawable(context, R.drawable.clock_hand_hour);
        if (mMinuteHand == null)
            mMinuteHand = ContextCompat.getDrawable(context, R.drawable.clock_hand_minute);

        time = new Time();

        //得到时钟的参数
        mDialWidth = mDial.getIntrinsicWidth();
        mDialHeight = mDial.getIntrinsicHeight();
    }

    //重新测量大小
    //在该方法中，我们的View想要的尺寸当然就是与表盘一样大的尺寸，这样可以保证我们的View有最佳的展示，
    // 可是如果ViewGroup给的尺寸比较小，我们就根据表盘图片的尺寸，进行适当的按比例缩放。
    // 注意，这里我们没有直接使用ViewGroup给我们的较小的尺寸，而是对我们的表盘图片的宽高进行相同比例的缩放后
    // ，设置的尺寸，这样的好处是，可以防止表盘图片绘制时的拉伸或者挤压变形。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到父布局传过来的参数
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //可能的缩放,默认不缩放
        float hScale = 1.0f;
        float vScale = 1.0f;
        //如果得到父布局给的尺寸小于getIntrinsicHeight()的尺寸，需要缩放
        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDialWidth)
            hScale = (float) widthSize / (float) mDialWidth;
        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDialHeight)
            vScale = (float) heightSize / (float) mDialHeight;
        //保持比例，取最小的值相乘进行缩放
        float scale = Math.min(hScale, vScale);
        //重新测量
        setMeasuredDimension(resolveSizeAndState((int) (mDialWidth * scale), widthMeasureSpec, 0),
                resolveSizeAndState((int) (mDialHeight * scale), heightMeasureSpec, 0));

    }

    //确定了大小，是不是就可以绘制了，先不着急，我们先要处理两件事，一件就是让我们的自定义View能够感知自己尺寸的变化，
// 这样每次绘制时，可以先判断下尺寸是否发生了变化，如果有变化，就及时调整我们的绘制策略。代码如下：
    //我们会在onDraw使用mChanged变量的。
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mChanged = true;
    }

    //然后我们还要实现一个广播接收器，接收系统发出的时间变化广播，然后更新该View的time，如下：
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这个if判断主要是用来在时区发生变化时，更新mCalendar的时区的，这
            //样，我们的自定义View在全球都可以使用了。
            /*if (Intent.ACTION_TIME_CHANGED.equals(intent.getAction()))
            {
                String tz = intent.getStringExtra("time-zone");
                time = new Time(TimeZone.getTimeZone(tz).getID());
            }*/
            //更新时间
            onTimeChaged();
            //重新绘制
            invalidate();
        }


    };

    private void onTimeChaged() {
        time.setToNow();
        int hour = time.hour;
        int minute = time.minute;
        int second = time.second;
        /*这里我们为什么不直接把minute设置给mMinutes，而是要加上
            second /60.0f呢，这个值不是应该一直为0吗？
            这里又涉及到Calendar的 一个知识点，
            也就是它可以是Linient模式，
            此模式下，second和minute是可能超过60和24的，具体这里就不展开了，
            如果不是很清楚，建议看看Google的官方文档中讲Calendar的部分*/
        mMinutes = minute + second / 60.0f;
        mHour = hour + mMinutes / 60.0f;
        mChanged = true;

    }
    //现在，我们要给我们的View动态地注册广播接收器，没错，我们就是要在
    // onAttachedToWindow和onDetachedFromWindow中完成这一功能。代码如下


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttach) {
            mAttach = true;
            //监听的三种广播
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            //得到上下文
            getContext().registerReceiver(mReceiver, filter);
        }
        time = new Time();
        onTimeChaged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttach) {
            getContext().unregisterReceiver(mReceiver);
            mAttach = false;
        }
    }
    //万事具备，只欠东风，开始绘制我们的View吧。代码如下：

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //View尺寸变化后，我们用changed变量记录下来，
        //同时，恢复mChanged为false，以便继续监听View的尺寸变化。
        boolean changed = mChanged;
        if (changed)
            mChanged = false;
        /* 请注意，这里的availableWidth和availableHeight，
           每次绘制时是可能变化的，
           我们可以从mChanged变量的值判断它是否发生了变化，
           如果变化了，说明View的尺寸发生了变化，
           那么就需要重新为时针、分针设置Bounds，
           因为我们需要时针，分针始终在View的中心。*/
        //得到自定义View的宽高大小
        int availableWidth = super.getRight() - super.getLeft();
        int availableHeight = super.getBottom() - super.getTop();
         /* 这里的x和y就是View的中心点的坐标，
          注意这个坐标是以View的左上角为0点，向右x，向下y的坐标系来计算的。
          这个坐标系主要是用来为View中的每一个Drawable确定位置。
          就像View的坐标是用parent的左上角为0点的坐标系计算得来的一样。
          简单来讲，就是ViewGroup用自己左上角为0点的坐标系为
          各个子View安排位置，
          View同样用自己左上角为0点的坐标系
          为它里面的Drawable安排位置。
          注意不要搞混了。*/
        int x = availableWidth / 2;
        int y = availableHeight / 2;
        final Drawable dial = mDial;
        //得到宽高
        int w = dial.getIntrinsicWidth();
        int h = dial.getIntrinsicHeight();
        boolean scaled = false;
        /*如果可用的宽高小于表盘图片的宽高，
           就要进行缩放，不过这里，我们是通过坐标系的缩放来实现的。
          而且，这个缩放效果影响是全局的，
          也就是下面绘制的表盘、时针、分针都会受到缩放的影响。*/
        if (availableWidth < w || availableHeight < h) 
        {
            scaled = true;
            float scale = Math.min((float) availableWidth / (float) w,
                    (float) availableHeight / (float) h);
            //保存画布到一个栈中（调用restore方法时，取出栈顶的画布）
            canvas.save();
            //以xy两点坐标缩放画布
            canvas.scale(scale, scale, x, y);
        }
        /*如果尺寸发生变化，我们要重新为表盘设置Bounds。
           这里的Bounds就相当于是为Drawable在View中确定位置，
           只是确定的方式更直接，直接在View中框出一个与Drawable大小
           相同的矩形，
           Drawable就在这个矩形里绘制自己。
           这里框出的矩形，是以(x,y)为中心的，宽高等于表盘图片的宽高的一个矩形，
           不用担心表盘图片太大绘制不完整，
            因为我们已经提前进行了缩放了。*/
        //在View上重新绘制Drawable,
        //1.设置边界，相当于绘制一个矩形
        if (changed)
            dial.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
        //2.重新绘制
        dial.draw(canvas);
        //保存画布。
        canvas.save();
         /*根据小时数，以点(x,y)为中心旋转坐标系。
            如果你对来回旋转的坐标系感到头晕，摸不着头脑，
            建议你看一下**徐宜生**《安卓群英传》中讲解2D绘图部分中的Canvas一节。*/
        canvas.rotate(mHour / 12.0f * 360.0f, x, y);
        final Drawable hourHand = mHourHand;
        //同样，根据变化重新设置时针的Bounds
        if (changed) {
            w = hourHand.getIntrinsicWidth();
            h = hourHand.getIntrinsicHeight();      

            /* 仔细体会这里设置的Bounds，我们所画出的矩形，
                同样是以(x,y)为中心的
                矩形，时针图片放入该矩形后，时针的根部刚好在点(x,y)处，
                因为我们之前做时针图片时，
                已经让图片中的时针根部在图片的中心位置了，
                虽然，看起来浪费了一部分图片空间（就是时针下半部分是空白的），
                但却换来了建模的简单性，还是很值的。*/
            hourHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
        }
        hourHand.draw(canvas);
        //恢复之前的画布，即绘制时钟的画布
        canvas.restore();
        //保存这个画布。
        canvas.save();

        //根据分针旋转坐标系
        canvas.rotate(mMinutes / 60.0f * 360.0f, x, y);
        final Drawable minuteHand = mMinuteHand;

        if (changed) {
            w = minuteHand.getIntrinsicWidth();
            h = minuteHand.getIntrinsicHeight();
            minuteHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
        }
        //在新的画布上绘制分。
        minuteHand.draw(canvas);
        //返回上个画布
        canvas.restore();

        //最后，我们把缩放的坐标系复原。
        if (scaled) {
            canvas.restore();
        }


    }
}
