package com.hexg.guideview.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.hexg.guideview.GrantedPermissionCallback
import com.hexg.guideview.Safe

abstract class XActivity : AppCompatActivity() {
    private var inflater: LayoutInflater? = null
    private var guideView: View? = null
    private var decorView: View? = null

    var PermissionCallbacks: SparseArray<GrantedPermissionCallback>? = null

    //every activity need to return a layout id...
    protected abstract fun GetLayoutId(): Int

    protected abstract fun OnBindView()

    //inflate helper
    fun Inflate(@LayoutRes layoutResId: Int, parent: ViewGroup? = null): View {
        if (inflater == null) {
            inflater = layoutInflater
        }

        return inflater!!.inflate(layoutResId, parent, false)
    }

    //inflate helper
    fun InflateAndAddView(@LayoutRes layoutResId: Int, parent: ViewGroup): View {
        if (inflater == null) {
            inflater = layoutInflater
        }

        return inflater!!.inflate(layoutResId, parent, true)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GetLayoutId())
        decorView = window?.decorView
        OnBindView()
    }

    override fun onDestroy() {
        super.onDestroy()
        inflater = null
        decorView = null
    }

    override fun onStop() {
        super.onStop()
        RemoveGuideView()
    }

    fun RemoveGuideView(needRefresh: Boolean = false) {
        (decorView as? ViewGroup)?.run {
            removeViewInLayout(guideView)
            if (needRefresh) {
                invalidate()
            }
        }
        this.guideView = null
    }

    fun AddGuideView(guideView: View) {
        decorView?.let {
            this.guideView = guideView
            (it as? ViewGroup)?.addView(
                guideView,
                FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        Safe {
            super.onActivityResult(requestCode, resultCode, intent)
            SafeOnActivityResult(requestCode, resultCode, intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestId: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Safe {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.isEmpty()) {
                //only accept 1 permission result
                //do nothing is the operation is cancelled.
                return@Safe
            }

            val grantedAll = grantResults.none { it != PackageManager.PERMISSION_GRANTED }

            PermissionCallbacks?.get(requestId)?.run {
                //remove callback
                PermissionCallbacks?.remove(requestId)
                invoke(grantedAll, permissions)
            }
        }
    }

    protected open fun SafeOnActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        //empty...
    }
}