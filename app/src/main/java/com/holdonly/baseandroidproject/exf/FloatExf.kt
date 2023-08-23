package com.holdonly.baseandroidproject.exf

import android.content.res.Resources
import android.util.TypedValue

val Float.dp2px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )