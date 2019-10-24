package com.dragon.revisitandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import kotlin.math.abs

/**
 * Description:水平容器
 *
 * @author guoyongping
 * @date   2019-10-17 21:12
 */
class HorizontalView : ViewGroup {

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

    private var childWidth = 0

    private var lastInterceptX = 0
    private var lastInterceptY = 0
    private var lastX = 0
    private var lastY = 0
    // 当前子元素
    private var currentIndex = 0
    private var scroller: Scroller? = null
    // 检测滑动速度
    private lateinit var tracker: VelocityTracker


    private fun init() {
        scroller = Scroller(context)
        tracker = VelocityTracker.obtain()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        // 如果没有子元素 则将宽高都设置为0
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {// 宽和高都是AT_MOST 则宽度设为所有子元素的宽度的和  高度为第一个子元素的高
            // 第一个子元素
            val childOne = getChildAt(0)
            val childWith = childOne.measuredWidth
            val childHeight = childOne.measuredHeight
            setMeasuredDimension(childWith * childCount, childHeight)

        } else if (widthMode == MeasureSpec.AT_MOST) {// 如果宽度是AT_MOST 则宽为所有子元素的和
            val childWith = getChildAt(0).measuredWidth
            setMeasuredDimension(childWith * childCount, heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) { //如果高度是AT_MOST 则高为第一个子元素的高度
            val childHeight = getChildAt(0).measuredHeight
            setMeasuredDimension(widthSize, childHeight)
        }


    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        var left = 0
        var child: View

        for (i in 0 until childCount) {
            child = getChildAt(i)

            if (child.visibility != View.GONE) {
                val width = child.measuredWidth
                childWidth = width
                child.layout(left, 0, left + width, child.measuredHeight)
                left += width
            }
        }

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var interceptor = false
        val x = ev.x
        val y = ev.y
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                interceptor = false
                if (!scroller?.isFinished!!) {
                    scroller?.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - lastInterceptX
                val deltaY = y - lastInterceptY
                // 如果是水平滑动 则拦截,将事件交给onTouchEvent处理
                interceptor = abs(deltaX) - abs(deltaY) > 0
            }
            MotionEvent.ACTION_UP -> {
                interceptor = false
            }
        }

        lastX = x.toInt()
        lastY = y.toInt()
        lastInterceptX = x.toInt()
        lastInterceptY = y.toInt()

        return interceptor
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        tracker.addMovement(event)
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                if (!scroller?.isFinished!!) {
                    scroller?.abortAnimation()
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - lastY
                scrollBy((-deltaX).toInt(), 0)
            }

            MotionEvent.ACTION_UP -> {
                val distance = scrollX - currentIndex * childWidth
                if (abs(distance) > childWidth / 2) { // 滑动距离是否大于宽度的一半
                    if (distance > 0) {
                        currentIndex++
                    } else {
                        currentIndex--
                    }
                } else { // 快速滑动
                    tracker.computeCurrentVelocity(1000)
                    val xV = tracker.xVelocity
                    if (abs(xV) > 100) {
                        if (xV > 0) {
                            currentIndex--
                        } else {
                            currentIndex++
                        }
                    }
                }

                currentIndex =
                    if (currentIndex < 0) 0 else (if (currentIndex > childCount - 1) childCount - 1 else currentIndex)

                smoothScrollTo(currentIndex * childWidth, 0)

                tracker.clear()
            }
        }

        lastX = x.toInt()
        lastY = y.toInt()

        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller?.computeScrollOffset()!!) {
            scrollTo(scroller!!.currX, scroller!!.currY)
            postInvalidate()
        }
    }

    private fun smoothScrollTo(destX: Int, destY: Int) {
        scroller?.startScroll(scrollX, scrollY, destX - scrollX, destY - scrollY, 1000)
        invalidate()
    }

}