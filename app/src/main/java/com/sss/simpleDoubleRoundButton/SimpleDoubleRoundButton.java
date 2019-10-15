package com.sss.simpleDoubleRoundButton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自绘的双头按钮，扩展了边框、按压变色、按钮大小比例动态调整、圆角等一系列实用功能
 */
@SuppressWarnings("ALL")
public class SimpleDoubleRoundButton extends View {
    /**************************以下可以作为自定义参数*******************************/
    //左上角圆角
    private int leftTopCornersRadius = dp2px(5);
    //右上角圆角
    private int rightTopCornersRadius = dp2px(5);
    //左下角圆角
    private int leftBottomCornersRadius = dp2px(5);
    //右下角圆角
    private int rightBottomCornersRadius = dp2px(5);
    //左半部分背景颜色
    private int leftRectColor = Color.BLUE;
    //左半部分触摸背景颜色
    private int leftTouchRectColor = Color.DKGRAY;
    //右半部分背景颜色
    private int rightRectColor = Color.RED;
    //右半部分触摸背景颜色
    private int rightTouchRectColor = Color.GRAY;
    //边框宽度
    private int strokeWidth = dp2px(0);
    //边框颜色
    private int strokeRectColor = Color.TRANSPARENT;
    //左右按钮绘制区域大小百分比
    private float buttonRectPercent = 0.5f;
    //左边按钮文字
    private String leftText = "Left Button";
    //左边按钮文字颜色
    private int leftTextColor = Color.WHITE;
    //左边按钮文字字体大小
    private float leftTextSize = sp2px(15);
    //右边按钮文字
    private String rightText = "Right Button";
    //右边按钮文字字体大小
    private float rightTextSize = sp2px(15);
    //右边按钮文字颜色
    private int rightTextColor = Color.WHITE;
    /**************************以上可以作为自定义参数*******************************/
    //宽度
    private int width = dp2px(100);
    //高度
    private int height = dp2px(35);
    //左半部分按钮绘制区域
    private Rect leftRect = new Rect();
    //右半部分按钮绘制区域
    private Rect rightRect = new Rect();
    //左半部分区域路径
    private Path leftPath = new Path();
    //右半部分区域路径
    private Path rightPath = new Path();
    //左半部分画笔
    private Paint leftPaint = new Paint();
    //右半部分画笔
    private Paint rightPaint = new Paint();
    //边框画笔
    private Paint strokePaint = new Paint();
    //边框绘制区域
    private Rect strokeRect = new Rect();
    //边框路径
    private Path strokePath = new Path();
    //左边按钮画笔
    private Paint leftTextPaint = new Paint();
    //右边按钮画笔
    private Paint rightTextPaint = new Paint();
    //边框启用
    private StrokeEnalbe strokeEnalbe = StrokeEnalbe.all;

    private int leftOrRightClick;

    private OnSimpleDoubleRoundButtonCallBack onSimpleDoubleRoundButtonCallBack;

    public void setOnSimpleDoubleRoundButtonCallBack(OnSimpleDoubleRoundButtonCallBack onSimpleDoubleRoundButtonCallBack) {
        this.onSimpleDoubleRoundButtonCallBack = onSimpleDoubleRoundButtonCallBack;
    }

    public SimpleDoubleRoundButton(Context context) {
        super(context);
        init();
    }

    public SimpleDoubleRoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleDoubleRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadParameter(context, attrs);
        init();
    }

    /**
     * 载入参数
     */
    private void loadParameter(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleDoubleRoundButton);
        leftTopCornersRadius = (int) typedArray.getDimension(R.styleable.SimpleDoubleRoundButton_left_top_corners_radius, leftTopCornersRadius);
        rightTopCornersRadius = (int) typedArray.getDimension(R.styleable.SimpleDoubleRoundButton_right_top_corners_radius, rightTopCornersRadius);
        leftBottomCornersRadius = (int) typedArray.getDimension(R.styleable.SimpleDoubleRoundButton_left_bottom_corners_radius, leftBottomCornersRadius);
        rightBottomCornersRadius = (int) typedArray.getDimension(R.styleable.SimpleDoubleRoundButton_right_bottom_corners_radius, rightBottomCornersRadius);
        leftRectColor = typedArray.getColor(R.styleable.SimpleDoubleRoundButton_left_background_color, leftRectColor);
        leftTouchRectColor = typedArray.getColor(R.styleable.SimpleDoubleRoundButton_left_touch_background_color, leftTouchRectColor);
        rightRectColor = typedArray.getColor(R.styleable.SimpleDoubleRoundButton_right_background_color, rightRectColor);
        rightTouchRectColor = typedArray.getColor(R.styleable.SimpleDoubleRoundButton_right_touch_background_color, rightTouchRectColor);
        strokeWidth = (int) typedArray.getDimension(R.styleable.SimpleDoubleRoundButton_stroke_width, strokeWidth);
        strokeRectColor = typedArray.getColor(R.styleable.SimpleDoubleRoundButton_stroke_rect_color, strokeRectColor);
        buttonRectPercent = typedArray.getFloat(R.styleable.SimpleDoubleRoundButton_button_rect_percent, buttonRectPercent);
        String l = typedArray.getString(R.styleable.SimpleDoubleRoundButton_left_text);
        leftText = l == null ? leftText : l;
        leftTextColor = typedArray.getColor(R.styleable.SimpleDoubleRoundButton_left_text_color, leftTextColor);
        leftTextSize = (int) typedArray.getDimension(R.styleable.SimpleDoubleRoundButton_left_text_size, leftTextSize);
        String r = typedArray.getString(R.styleable.SimpleDoubleRoundButton_right_text);
        rightText = r == null ? rightText : r;
        rightTextSize = (int) typedArray.getDimension(R.styleable.SimpleDoubleRoundButton_right_text_size, rightTextSize);
        rightTextColor = typedArray.getColor(R.styleable.SimpleDoubleRoundButton_right_text_color, rightTextColor);
        typedArray.recycle();
    }

    /**
     * 初始化各种画笔
     */
    private void init() {

        leftPaint.setAntiAlias(true);
        leftPaint.setStyle(Paint.Style.FILL);
        leftPaint.setColor(leftRectColor);

        rightPaint.setAntiAlias(true);
        rightPaint.setStyle(Paint.Style.FILL);
        rightPaint.setColor(rightRectColor);

        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.FILL);
        strokePaint.setColor(strokeRectColor);

        leftTextPaint.setAntiAlias(true);
        leftTextPaint.setTextSize(leftTextSize);
        leftTextPaint.setColor(leftTextColor);

        rightTextPaint.setAntiAlias(true);
        rightTextPaint.setTextSize(rightTextSize);
        rightTextPaint.setColor(rightTextColor);
        rightTextPaint.setColor(rightTextColor);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                width = width;
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = widthSize;
                break;
        }

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                height = height;
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = heightSize;
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        computeSize();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > leftRect.left && event.getX() < leftRect.right) {
                    leftPaint.setColor(leftTouchRectColor);
                }
                if (event.getX() > rightRect.left && event.getX() < rightRect.right) {
                    rightPaint.setColor(rightTouchRectColor);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > strokeRect.right || event.getX() < strokeRect.left || event.getY() > strokeRect.bottom || event.getY() < strokeRect.top) {
                    //TODO 超出控件范围
                    leftPaint.setColor(leftRectColor);
                    rightPaint.setColor(rightRectColor);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() > leftRect.left && event.getX() < leftRect.right) {
                    leftPaint.setColor(leftRectColor);
                    leftOrRightClick = Gravity.LEFT;
                    if (onSimpleDoubleRoundButtonCallBack != null) {
                        onSimpleDoubleRoundButtonCallBack.onLeftButtonClick(SimpleDoubleRoundButton.this);
                    }
                }
                if (event.getX() > rightRect.left && event.getX() < rightRect.right) {
                    rightPaint.setColor(rightRectColor);
                    leftOrRightClick = Gravity.RIGHT;
                    if (onSimpleDoubleRoundButtonCallBack != null) {
                        onSimpleDoubleRoundButtonCallBack.onRightButtonClick(SimpleDoubleRoundButton.this);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(strokePath, strokePaint);
        canvas.drawPath(leftPath, leftPaint);
        canvas.drawPath(rightPath, rightPaint);
        if (leftRect.width() > 0) {
            canvas.drawText(leftText, leftRect.left + leftRect.width() / 2 - getTextSize(true, leftText, leftTextPaint) / 2 - getStroke(leftOrRightClick) / 2, leftRect.bottom / 2 + getTextSize(false, leftText, leftTextPaint) / 2 + getStroke(leftOrRightClick) / 2, leftTextPaint);
        }
        if (rightRect.width() > 0) {
            canvas.drawText(rightText, rightRect.left + rightRect.width() / 2 - getTextSize(true, rightText, rightTextPaint) / 2 + getStroke(leftOrRightClick) / 2, rightRect.bottom / 2 + getTextSize(false, rightText, rightTextPaint) / 2 + getStroke(leftOrRightClick) / 2, rightTextPaint);
        }
    }

    private int getStroke(int direction) {

        if (direction == Gravity.LEFT && (strokeEnalbe == StrokeEnalbe.left || strokeEnalbe == StrokeEnalbe.all)) {
            return strokeWidth;
        }
        if (direction == Gravity.RIGHT && (strokeEnalbe == StrokeEnalbe.right || strokeEnalbe == StrokeEnalbe.all)) {
            return strokeWidth;
        }
        return 0;
    }

    /**
     * 计算各绘制区域尺寸
     */
    private void computeSize() {
        //左半部分按钮绘制区域
        leftRect.left = 0 + getStroke(Gravity.LEFT);
        leftRect.top = 0 + getStroke(Gravity.LEFT);
        leftRect.right = (int) (getWidth() * buttonRectPercent);
        leftRect.bottom = getHeight() - getStroke(Gravity.LEFT);
        //右半部分按钮绘制区域
        rightRect.left = leftRect.right;
        rightRect.top = 0 + getStroke(Gravity.RIGHT);
        rightRect.right = getWidth() - getStroke(Gravity.RIGHT);
        rightRect.bottom = getHeight() - getStroke(Gravity.RIGHT);
        //边框绘制区域
        strokeRect.left = 0;
        strokeRect.top = 0;
        strokeRect.right = getWidth();
        strokeRect.bottom = getHeight();
        //左半部分按钮路径
        leftPath.reset();
        leftPath.moveTo(leftRect.left + leftTopCornersRadius, leftRect.top);
        leftPath.lineTo(leftRect.right, leftRect.top);
        leftPath.lineTo(leftRect.right, leftRect.bottom);
        leftPath.lineTo(leftRect.left + leftBottomCornersRadius, leftRect.bottom);
        leftPath.cubicTo(leftRect.left + leftBottomCornersRadius, leftRect.bottom, leftRect.left, leftRect.bottom, leftRect.left, leftRect.bottom - leftBottomCornersRadius);
        leftPath.lineTo(leftRect.left, leftRect.left + leftTopCornersRadius);
        leftPath.cubicTo(leftRect.left, leftRect.left + leftTopCornersRadius, leftRect.left, leftRect.top, leftRect.left + leftTopCornersRadius, leftRect.top);
        leftPath.close();
        //右半部分按钮路径
        rightPath.reset();
        rightPath.moveTo(rightRect.left, rightRect.top);
        rightPath.lineTo(rightRect.right - rightTopCornersRadius, rightRect.top);
        rightPath.cubicTo(rightRect.right - rightTopCornersRadius, rightRect.top, rightRect.right, rightRect.top, rightRect.right, rightRect.top + rightTopCornersRadius);
        rightPath.lineTo(rightRect.right, rightRect.bottom - rightBottomCornersRadius);
        rightPath.cubicTo(rightRect.right, rightRect.bottom - rightBottomCornersRadius, rightRect.right, rightRect.bottom, rightRect.right - rightBottomCornersRadius, rightRect.bottom);
        rightPath.lineTo(rightRect.left, rightRect.bottom);
        rightPath.lineTo(rightRect.left, rightRect.top);
        rightPath.close();
        //边框路径
        strokePath.reset();
        strokePath.moveTo(leftTopCornersRadius, strokeRect.top);
        strokePath.lineTo(strokeRect.right - rightTopCornersRadius, strokeRect.top);
        strokePath.cubicTo(strokeRect.right - rightTopCornersRadius, strokeRect.top, strokeRect.right, strokeRect.top, strokeRect.right, strokeRect.top + rightTopCornersRadius);
        strokePath.lineTo(strokeRect.right, strokeRect.bottom - rightBottomCornersRadius);
        strokePath.cubicTo(strokeRect.right, strokeRect.bottom - rightBottomCornersRadius, strokeRect.right, strokeRect.bottom, strokeRect.right - rightBottomCornersRadius, strokeRect.bottom);
        strokePath.lineTo(strokeRect.left + leftBottomCornersRadius, strokeRect.bottom);
        strokePath.cubicTo(strokeRect.left + leftBottomCornersRadius, strokeRect.bottom, strokeRect.left, strokeRect.bottom, strokeRect.left, strokeRect.bottom - leftBottomCornersRadius);
        strokePath.lineTo(strokeRect.left, strokeRect.left + leftTopCornersRadius);
        strokePath.cubicTo(strokeRect.left, strokeRect.left + leftTopCornersRadius, strokeRect.left, strokeRect.top, strokeRect.left + leftTopCornersRadius, strokeRect.top);
        strokePath.close();
    }

    public void setStrokeEnalbe(StrokeEnalbe strokeEnalbe) {
        this.strokeEnalbe = strokeEnalbe;
        computeSize();
        invalidate();
    }

    /**
     * 设置圆角
     */
    public void setRadius(int radius) {
        this.leftTopCornersRadius = dp2px(radius);
        this.rightTopCornersRadius = dp2px(radius);
        this.leftBottomCornersRadius = dp2px(radius);
        this.rightBottomCornersRadius = dp2px(radius);
        computeSize();
        invalidate();
    }

    /**
     * 设置左上圆角
     */
    public void setLeftTopCornersRadius(int leftTopCornersRadius) {
        this.leftTopCornersRadius = dp2px(leftTopCornersRadius);
        computeSize();
        invalidate();
    }

    /**
     * 设置右上圆角
     */
    public void setRightTopCornersRadius(int rightTopCornersRadius) {
        this.rightTopCornersRadius = dp2px(rightTopCornersRadius);
        computeSize();
        invalidate();
    }

    /**
     * 设置左下圆角
     */
    public void setLeftBottomCornersRadius(int leftBottomCornersRadius) {
        this.leftBottomCornersRadius = dp2px(leftBottomCornersRadius);
        computeSize();
        invalidate();
    }

    /**
     * 设置右下圆角
     */
    public void setRightBottomCornersRadius(int rightBottomCornersRadius) {
        this.rightBottomCornersRadius = dp2px(rightBottomCornersRadius);
        computeSize();
        invalidate();
    }

    /**
     * 设置左侧按钮通常状态下背景色
     */
    public void setLeftRectColor(int leftRectColor) {
        this.leftRectColor = leftRectColor;
        init();
        invalidate();
    }

    /**
     * 设置左侧按钮触摸状态下背景色
     */
    public void setLeftTouchRectColor(int leftTouchRectColor) {
        this.leftTouchRectColor = leftTouchRectColor;
        init();
        invalidate();
    }

    /**
     * 设置右侧按钮通常状态下背景色
     */
    public void setRightRectColor(int rightRectColor) {
        this.rightRectColor = rightRectColor;
        init();
        invalidate();
    }

    /**
     * 设置右侧按钮触摸状态下背景色
     */
    public void setRightTouchRectColor(int rightTouchRectColor) {
        this.rightTouchRectColor = rightTouchRectColor;
        init();
        invalidate();
    }

    /**
     * 设置边框宽度
     */
    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = dp2px(strokeWidth);
        computeSize();
        invalidate();
    }

    /**
     * 设置边框颜色
     */
    public void setStrokeRectColor(int strokeRectColor) {
        this.strokeRectColor = strokeRectColor;
        init();
        invalidate();
    }

    /**
     * 设置左右按钮各占的百分比
     */
    public void setButtonRectPercent(float buttonRectPercent) {
        this.buttonRectPercent = buttonRectPercent;
        computeSize();
        invalidate();
    }

    /**
     * 设置左边按钮的文字
     */
    public void setLeftText(String leftText) {
        this.leftText = leftText;
        invalidate();
    }

    /**
     * 设置左边按钮文字颜色
     */
    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        init();
        invalidate();
    }

    /**
     * 设置左边按钮文字大小
     */
    public void setLeftTextSize(float leftTextSize) {
        this.leftTextSize = sp2px(leftTextSize);
        init();
        invalidate();
    }

    /**
     * 设置右边按钮的文字
     */
    public void setRightText(String rightText) {
        this.rightText = rightText;
        invalidate();
    }

    /**
     * 设置右边按钮文字颜色
     */
    public void setRightTextSize(float rightTextSize) {
        this.rightTextSize = sp2px(rightTextSize);
        init();
        invalidate();
    }

    /**
     * 设置右边按钮文字大小
     */
    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        init();
        invalidate();
    }

    /**
     * dp转px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, Resources.getSystem().getDisplayMetrics());
    }

    public interface OnSimpleDoubleRoundButtonCallBack {
        /**
         * 左边按钮点击事件
         */
        void onLeftButtonClick(SimpleDoubleRoundButton view);

        /**
         * 右边按钮点击事件
         */
        void onRightButtonClick(SimpleDoubleRoundButton view);
    }

    /**
     * 获取字符串宽高
     */
    private int getTextSize(boolean isWidth, String str, Paint p) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return isWidth ? (int) p.measureText(str) : (int) (p.getFontMetrics().descent - p.getFontMetrics().ascent) / 2;
    }

    public enum StrokeEnalbe {
        left, right, all, none
    }

}
