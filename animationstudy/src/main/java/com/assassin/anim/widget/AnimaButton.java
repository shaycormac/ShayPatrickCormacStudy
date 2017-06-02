package com.assassin.anim.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/20 14:59
 * @Version: 1.0
 * @Description: 自定义带动画的提交按钮
 */

public class AnimaButton extends View 
{
    /**
     * view的宽度
     */
    private int width;
    /**
     * view的高度
     */
    private int height;

    /**
     * 点击事件及动画事件2完成回调
     */
    private AnimationButtonListener animationButtonListener;
    /**
     * 背景颜色
     */
    private static final int BG_COLOR = 0xffbc7d53;
    /**
     * 矩形圆角变成内切圆后，矩形变成圆形的平移距离
     */
    private int default_two_circle_distance;
    private final static int DURATION = 500;
    //圆角半径，逐渐变大的
    private int circleAngle;
    //两个内切圆的圆心距离，不断的改变的
    private int two_circle_distance;
    //上移的位移
    private static final float MOVE_DISTANCE =400;
    /**
     * 是否开始绘制对勾
     */
    private boolean startDrawOk;
    
    //todo 看不懂
    /**
     * 取路径的长度
     */
    private PathMeasure pathMeasure;
    /**
     * 对路径处理实现绘制动画效果
     */
    private PathEffect effect;
    private static final String BUTTON_STRING ="确认完成";

    public void setAnimationButtonListener(AnimationButtonListener listener) {
        animationButtonListener = listener;
    }

    /**
     * 动画集
     *
     * @param context
     */
    private AnimatorSet animatorSet = new AnimatorSet();

    /**
     * 圆角矩形画笔
     */
    private Paint paint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 对勾（√）画笔
     */
    private Paint okPaint;

    /**
     * 矩形到圆角矩形过度的动画（获取动画属性的改变值，目的来给圆角的半径赋值）
     */
    private ValueAnimator animator_rect_to_angle;
    /**
     * 圆角矩形到圆形过度的动画（获取动画属性的改变值，目的来给textView的透明度赋值）
     */
    private ValueAnimator animator_rect_to_square;
    /**
     * view上移的动画
     */
    private ObjectAnimator animator_move_to_up;
    /**
     * 绘制对勾（√）的动画
     */
    private ValueAnimator animator_draw_ok;


    /**
     * 根据view的大小设置成矩形
     */
    private RectF rectf = new RectF();

    /**
     * 文字绘制所在矩形
     */
    private Rect textRect = new Rect();

    /**
     * 路径--用来获取对勾的路径
     */
    private Path path = new Path();
    
    public AnimaButton(Context context) {
       this(context,null);
    }

    public AnimaButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnimaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        //初始化画笔
        initPaint();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (animationButtonListener!=null)
                    animationButtonListener.onClickListener();
                
            }
        });
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) 
            {
                //只里面只关心动画结束情况
                if (animationButtonListener != null) {
                    animationButtonListener.animationFinish();
                }
                
            }
        });
    }

    private void initPaint() 
    {
        //整个图形绘制
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(BG_COLOR);
       //绘制文字
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
       //绘制path的对号
        okPaint = new Paint();
        okPaint.setStrokeWidth(10);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.WHITE);
        
    }

    //这尼玛开始回调？？
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) 
    {
        super.onSizeChanged(w, h, oldw, oldh);
        //给View传值
        width = w;
        height = h;
        default_two_circle_distance = (w - h) / 2;

        initOk();
        //初始化动画！！！！
        initAnimation();
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) 
    {
        super.onDraw(canvas);
        draw_oval_to_circle(canvas);
        drawText(canvas);

        //绘制对号，暂时看不懂
        if (startDrawOk) {
            canvas.drawPath(path, okPaint);
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) 
    {
        textRect.left = 0;
        textRect.top = 0;
        textRect.right = width;
        textRect.bottom = height;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(BUTTON_STRING, textRect.centerX(), baseline, textPaint);
        
    }

    /**
     * 绘制长方形变成圆形(不停的调用)
     *
     * @param canvas 画布
     */
    private void draw_oval_to_circle(Canvas canvas) 
    {
        //从0开始
        rectf.left = two_circle_distance;
        rectf.top = 0;
        rectf.right = width - two_circle_distance;
        rectf.bottom = height;
        //画圆角矩形(圆形慢慢变大)
        canvas.drawRoundRect(rectf,circleAngle,circleAngle,paint);
        
    }

    /**
     * 初始化动画
     */
    private void initAnimation() 
    {
        set_rect_to_angle_animation();
        set_rect_to_circle_animation();
        set_move_to_up_animation();
        set_draw_ok_animation();
        
        //动画列表一起执行
        animatorSet.play(animator_move_to_up)
                .before(animator_draw_ok)
                .after(animator_rect_to_square)
                .after(animator_rect_to_angle);
    }

    /**
     * 绘制对勾的动画（当大圆到最上面开始绘制）
     */
    private void set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(DURATION);
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk = true;
                //得到值
                float value = (Float) animation.getAnimatedValue();

                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();
            }
        });
    }

    /**
     * 设置view上移的动画（已经变成一个大圆啦）
     */
    private void set_move_to_up_animation()
    {
        //得到当前的Y方向位移
        float curTranslationY =getTranslationY();
        //上移，所以减
        animator_move_to_up = ObjectAnimator.ofFloat(this, "translationY", curTranslationY, curTranslationY - MOVE_DISTANCE);
        animator_move_to_up.setDuration(DURATION);
        //设置差值器
        animator_move_to_up.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    /**
     * 设置圆角(变成内切圆后)矩形过度到圆的动画
     */
    private void set_rect_to_circle_animation() 
    {
        //从平移的距离
        animator_rect_to_square = ValueAnimator.ofInt(0, default_two_circle_distance);
        animator_rect_to_square.setDuration(DURATION);
        animator_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) 
            {
                two_circle_distance = (int) animation.getAnimatedValue();
                //目的是改变透明度
                int alpha = 255 - (two_circle_distance * 255) / default_two_circle_distance;
                textPaint.setAlpha(alpha);
                //不停地重新绘制
                invalidate();
            }
        });
    }

    /**
     * 设置矩形过度到圆角矩形的动画
     */
    private void set_rect_to_angle_animation() 
    {
        //属性动画么？（对圆角半径这个值属性进行动画改变？？碉堡了）
        //从0逐渐到内切圆
        animator_rect_to_angle = ValueAnimator.ofInt(0, height / 2);
        animator_rect_to_angle.setDuration(DURATION);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) 
            {
                //每一帧的执行，都会调用这个方法
                circleAngle = (int) animation.getAnimatedValue();
                //改变圆角半径，然后重绘
                invalidate();
            }
        });
    }

    /**
     * 绘制对号（看不懂，回头研究）
     */
    private void initOk() {

        //对勾的路径
        path.moveTo(default_two_circle_distance + height / 8 * 3, height / 2);
        path.lineTo(default_two_circle_distance + height / 2, height / 5 * 3);
        path.lineTo(default_two_circle_distance + height / 3 * 2, height / 5 * 2);

        pathMeasure = new PathMeasure(path, true);
    }

    /**
     * 回调接口
     */
    public  interface AnimationButtonListener {
        /**
         * 按钮点击事件
         */
        void onClickListener();

        /**
         * 动画完成回调
         */
        void animationFinish();
    }


    /**
     * 启动动画
     */
    public void start() {
        animatorSet.start();
    }
}
