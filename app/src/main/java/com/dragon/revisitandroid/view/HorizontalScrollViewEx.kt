package com.dragon.revisitandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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
    private lateinit var scroller: Scroller
    private lateinit var tracker: VelocityTracker


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }


    private fun init() {
        scroller = Scroller(context)
        tracker = VelocityTracker.obtain()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth = 0
        var measuredHeight = 0
        val childCount = childCount
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            measuredHeight = childView.measuredHeight
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredHeight = childView.measuredHeight
            setMeasuredDimension(widthSpecSize, measuredHeight)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            setMeasuredDimension(measuredWidth, heightSpecSize)
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childCount = childCount
        mChildrenSize = childCount

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != View.GONE) {
                val childWidth = childView.measuredWidth
                mChildWidth = childWidth
                childView.layout(childLeft, 0, childLeft + childWidth, childView.measuredHeight)
                childLeft += childWidth
            }
        }

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted = false
        val x = ev.x.toInt()
        val y = ev.y.toInt()

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                intercepted = false
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                    intercepted = true
                }

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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        tracker.addMovement(event)
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastX
                val deltaY = y - mLastY
                scrollBy(-deltaX, 0)
            }
            MotionEvent.ACTION_UP -> {
                val scrollX = scrollX
                tracker.computeCurrentVelocity(1000)
                val xVelocity = tracker.getXVelocity()
                if (abs(xVelocity) >= 50) {
                    mChildIndex = if (xVelocity > 0) (mChildIndex - 1) else mChildIndex + 1
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth
                }

                mChildIndex = max(0, min(mChildIndex, mChildrenSize - 1))
                val dx = mChildIndex * mChildWidth - scrollX
                smoothScrollBy(dx, 0)
            }
        }

        mLastX = x
        mLastY = y
        return true
    }

    private fun smoothScrollBy(dx: Int, dy: Int) {
        scroller.startScroll(scrollX, 0, dx, 0, 500)
        invalidate()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }


    override fun onDetachedFromWindow() {
        tracker.recycle()
        super.onDetachedFromWindow()
    }
}