package com.example.carserviceapp.utils

import android.content.Context
import androidx.annotation.DrawableRes

fun getCarImageRes(context: Context, brand: String, model: String): Int {
    val brandPrefix = when (brand.lowercase()) {
        "volkswagen" -> "vw"
        "audi"       -> "audi"
        "skoda"      -> "skoda"
        "seat"       -> "seat"
        "cupra"      -> "cupra"
        "porsche"    -> "porsche"
        "lamborghini" -> "lamborghini"
        "bentley"    -> "bentley"
        else         -> "car"
    }

    val modelSuffix = model.lowercase()
        .replace(" ", "_")
        .replace("-", "_")

    val resourceName = "${brandPrefix}_${modelSuffix}"

    val resId = context.resources.getIdentifier(
        resourceName, "drawable", context.packageName
    )

    return if (resId != 0) resId
    else context.resources.getIdentifier(
        "car_placeholder", "drawable", context.packageName
    )
}