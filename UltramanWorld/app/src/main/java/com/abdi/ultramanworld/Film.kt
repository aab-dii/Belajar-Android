package com.abdi.ultramanworld

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Film(
    val name: String,
    val description: String,
    val rating: String,
    val year: String,
    val episode : String,
    val type: String,
    val photo: Int
): Parcelable
