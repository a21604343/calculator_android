package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class OperationUI (
    var expression : String,
    var result : String,
    var timestamp : Long
) : Parcelable
