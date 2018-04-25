package com.svgouwu.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

import com.svgouwu.client.MyApplication;
import com.svgouwu.client.utils.CommonUtils;

/**
 * 流式布局的RadioGroup
 */
public class FlowRadioGroup extends RadioGroup {
    private static int DEFAULT_HORIZONTAL_SPACING = CommonUtils.dip2px(MyApplication.getInstance(), 10);
    private static int DEFAULT_VERTICAL_SPACING = CommonUtils.dip2px(MyApplication.getInstance(), 10);

    private int mVerticalSpacing = DEFAULT_VERTICAL_SPACING;
    private int mHorizontalSpacing = DEFAULT_HORIZONTAL_SPACING;

    public FlowRadioGroup(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (isInEditMode()) {
        }
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int myWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        int childCount = getChildCount();
        // Measure each child and put the child to the right of previous child
        // if there's enough room for it, otherwise, wrap the line and put the child to next line.
        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            } else {
                continue;
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);
            int tempLineWidth = childLeft + childWidth + paddingRight;
            if (tempLineWidth > myWidth) {
//                childLeft = paddingLeft;
                childLeft = paddingLeft + childWidth + mHorizontalSpacing;//MELON
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }
        }

        int wantedHeight = childTop + lineHeight + paddingBottom;
        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }

            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }
}