package uz.eldor.contactsapp.utils.types

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes resId:Int) = LayoutInflater.from( context).inflate(resId, this, false)