package com.dragon.revisitandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import kotlin.math.abs

/**
 * Description:
 * @author: guoyongping
 * @date:  2019-10-24 11:34
 */
class HorizontalScrollViewEx : ViewGroup {

    private var mChildrenSize: Int = 0
    private var mChildWidth: Int = 0
    private var mChildIndex: Int = 0
    // 记录上次滑动的坐标
    private var mLastX: Int = 0
    private var mLastY: Int = 0
    private var mLastXIntercept: Int = 0
    private var mLastYIntercept: Int = 0


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {


    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted = false
        val x = ev.x.toInt()
        val y = ev.y.toInt()

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                intercepted = false

            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x.minus(mLastXIntercept)
                val deltaY = y.minus(mLastYIntercept)
                intercepted = abs(deltaX) > abs(deltaY)
            }

            MotionEvent.ACTION_UP -> {
                intercepted = false
            }
        }

        mLastX = x
        mLastY = y
        mLastXIntercept = x
        mLastYIntercept = y

        return intercepted
    }


}