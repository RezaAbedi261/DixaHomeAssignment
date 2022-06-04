package com.dixa.dixagitpreview.extension

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type
import kotlin.reflect.KClass

//val Any.json: String get() = Injectable().moshi.adapter(this.javaClass).toJson(this)

val gson = Gson()
val Any.json: String get() = gson.toJson(this)

fun <T> String.fromJson(cls: Type): T? {

    try {
        return  gson.fromJson<T>(this, cls)
    }catch (e:Exception){}
    return null
}

@Suppress("unused")
inline fun <reified T> String.fromJson(): T? {
    return fromJson(T::class.java)
}
