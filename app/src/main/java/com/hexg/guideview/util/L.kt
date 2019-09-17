package com.hexg.guideview.util

import android.content.res.Resources
import com.hexg.guideview.App

//localization class
object L {
    //Application resources
    var RESOURCES: Resources? = null

    fun GetResources(): Resources {
        if (RESOURCES == null) {
            RESOURCES = App.INSTANCE.CONTEXT.resources
        }

        return RESOURCES!!
    }
}
