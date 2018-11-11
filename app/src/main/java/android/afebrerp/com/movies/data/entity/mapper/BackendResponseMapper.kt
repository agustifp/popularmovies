package android.afebrerp.com.movies.data.entity.mapper

import android.afebrerp.com.movies.data.entity.BasicError
import android.util.Log
import com.google.gson.Gson
import retrofit2.HttpException


object BackendResponseMapper {


    fun parseHttpException(httpException: HttpException): String {
        var errorMessage = ""
        try {
            val errorJsonString = httpException.response().errorBody()?.string()
            val basicError = Gson().fromJson(errorJsonString, BasicError::class.java)
            errorMessage = basicError.errors[0]
        } catch (e: Exception) {
            Log.e("BackendResponseMapper", e.toString())
        } finally {
            return errorMessage
        }
    }

}