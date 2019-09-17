package com.hexg.guideview.activity

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.hexg.guideview.R
import com.hexg.guideview.GuideView
import com.hexg.guideview.util.L
import com.hexg.guideview.util.XGuide

class ActivityMain : XActivity() {
    var addClock: TextView? = null
    var clearList: TextView? = null

    override fun GetLayoutId() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        addClock = null
    }

    override fun OnBindView() {
        addClock = findViewById<TextView>(R.id.add_alert_clock).apply {
            setOnClickListener {
                RemoveGuideView()
                AddGuideView(getGuideView(clearList, "clear List"))
            }
        }

        clearList = findViewById<TextView>(R.id.clear_list).apply {
            setOnClickListener {
                RemoveGuideView(true)
            }
        }

        addClock?.post {
            AddGuideView(getGuideView(addClock))
        }
    }

    private fun getGuideView(view: View?, content: String = "guide content -------guide content -------"): GuideView {
        view?.measure(0, 0)
        val rect = XGuide.getRectOnScreen(view)
        val prompt = TextView(this).apply {
            text = content
            setTextColor(L.GetResources().getColor(R.color.withe))
        }
        prompt.measure(0, 0)
        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            .apply {
                setMargins(
                    rect.left.toInt(),
                    rect.top.toInt() * 2 - rect.bottom.toInt() - prompt.measuredHeight - 30,
                    20,
                    20
                )
            }
        val guideView = GuideView(this, rect).apply {
            setPadding(0, 60, 0, 60)
        }
        guideView.addView(prompt, lp)
        return guideView
    }
}
