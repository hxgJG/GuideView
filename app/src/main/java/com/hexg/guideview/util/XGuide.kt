package com.hexg.guideview.util

import android.graphics.RectF
import android.util.Log
import android.view.View

object XGuide {

    /**
     * 确定高亮视图的位置
     * @param view 需要引导的视图
     * @return
     */
    fun getRectOnScreen(view: View?): RectF {
        if (view == null) {
            return RectF()
        }
        val result = RectF()
        val pos = IntArray(2)
        view.getLocationOnScreen(pos)
        result.left = pos[0].toFloat()
        result.top = pos[1].toFloat()
        result.right = result.left + view.measuredWidth
        result.bottom = result.top + view.measuredHeight
        Log.i("hxg", "left = ${result.left}, top = ${result.top}, right = ${result.right}, bottom = ${result.bottom}, ")
        return result
    }

}
