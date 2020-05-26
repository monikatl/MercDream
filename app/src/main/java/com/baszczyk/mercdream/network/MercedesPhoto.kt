package com.baszczyk.mercdream.network

import com.squareup.moshi.Json

data class MercedesPhoto(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double)
