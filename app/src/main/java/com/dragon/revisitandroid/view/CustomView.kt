package com.dragon.revisitandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Scroller

/**
 * Description:
 * @author: guoyongping
 * @date:  2019-09-16 11:43
 */
class CustomView : View {
    private val mScroller: Scroller

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mScroller = Scroller(context)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            (parent as View).scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }
}