package com.svgouwu.client.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by whq on 2017/11/24.
 */

public class CodeView extends View {
    private static final char[] CHARS = {
            '1','2', '3', '4', '5', '6', '7', '8', '9', '0'};
    public String code;
    private Random random = new Random();
    private int codeLength;//个数
    private float fontSize;//字体大小
    private Paint mTextPaint;//字体的画笔
    private int paddingLeft;//左内边距
    private int paddingTop;//上内边距

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public String getCode() {
        return code;
    }

    public CodeView(Context context) {
        this(context, null);
    }

    public CodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        codeLength = 4;
        paddingLeft = dp2px(8);
        paddingTop = dp2px(5);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(40);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        fontSize = mTextPaint.getTextSize();//字体大小
        mTextPaint.setTextAlign(Paint.Align.CENTER);//字体居中
        code = createCode();//初始化的时候先生存code值,否则调用getCode()方法返回将是null
        //设置点击监听
        //  setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //左右边距+字体的字间距(这里字间距用fontSize代替)
        int width = (int) (2 * paddingLeft + (codeLength - 1) * fontSize);
        //上下边距+字体的高度
        int height = (int) (3 * paddingTop + fontSize);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#D0D3D9"));
        char[] codeChar = code.toCharArray();
        int fontX = 0; //每个字的X坐标
        for (int i = 0; i < codeChar.length; i++) {
            fontX = (int) (paddingLeft + i * fontSize);
            String text = codeChar[i] + "";
            canvas.drawText(text, fontX, paddingTop + fontSize, mTextPaint);
        }
    }

    public String createCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    private int dp2px(float dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

}
