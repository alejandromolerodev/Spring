package com.example.foodhub.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class NutriScore(val value: String) {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun fromString(value: String?): NutriScore? {
            return entries.find {
                it.value.equals(value?.trim().orEmpty(), ignoreCase = true)
            } ?: UNKNOWN
        }
    }
}