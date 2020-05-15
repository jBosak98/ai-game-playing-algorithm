package lib.Gson

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import lib.game.GameWithMove

fun <T> Gson.isValid(json: String, c: Class<T>): Boolean =
    try {
        this.fromJson(json, c)
        true
    } catch (jsonSyntaxException: JsonSyntaxException) {
        false
    }
