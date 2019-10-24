package com.dragon.revisitandroid.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.Button

/**
 * Description:
 * @author: guoyongping
 * @date:  2019-09-17 10:01
 */
class ObjectAnimatorTest {

    fun test(context: Context) {
        val view: View? = null
        val objectAnimator = ObjectAnimator.ofFloat(view, "translationX", 200f)
        objectAnimator.duration = 300
        objectAnimator.start()

        val myView: MyView = MyView(Button(context))
        var objectAnimator1 = ObjectAnimator.ofInt(myView, "width", 500)
        objectAnimator1.duration = 500
        objectAnimator1.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
            }
        })
        objectAnimator1.start()


    }
}

class MyView {
    val mTarget: View

    constructor(view: View) {
        this.mTarget = view
    }

    fun getWidth(): Int {
        return mTarget.layoutParams.width
    }

    fun setWidth(width: Int) {
        mTarget.layoutParams.width = width
        mTarget.requestLayout()
    }
}