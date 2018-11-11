package com.futureworkshops.codetest.android.data.model.mapper

import android.util.Log
import com.futureworkshops.codetest.android.data.exceptions.BackendException
import com.futureworkshops.codetest.android.data.model.BackendError
import com.futureworkshops.codetest.android.data.model.BackendResponse
import com.futureworkshops.codetest.android.data.model.BasicError
import com.futureworkshops.codetest.android.data.model.dto.BreedDto
import com.futureworkshops.codetest.android.data.model.dto.BreedsListDto
import com.futureworkshops.codetest.android.domain.model.entity.BreedEntity
import com.futureworkshops.codetest.android.domain.model.entity.BreedsListEntity
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.json.JSONException
import retrofit2.HttpException
import java.lang.Exception


object BackendResponseMapper {

    @Throws(JSONException::class)
    fun <T : BackendResponse> handleResponse(jsonElement: JsonElement, mClass: Class<T>): BackendResponse {
        return if (jsonElement.asJsonObject.get("success").asBoolean) {
            Gson().fromJson(jsonElement.toString(), mClass)
        } else {
            val exceptionType = createBackendError(jsonElement)
            throw BackendException(Throwable(),
                    exceptionType.responseData?.code ?: 0,
                    exceptionType.responseData?.message ?: "")
        }
    }

    private fun createBackendError(jsonElement: JsonElement): BackendError =
            Gson().fromJson(jsonElement.toString(), BackendError::class.java)

    fun convertListBreedDTO(breedsListDto: BreedsListDto): BreedsListEntity {
        val listBreed: ArrayList<BreedEntity> = arrayListOf()
        breedsListDto.breeds.forEach { breedDTO -> listBreed.add(convertToBreed(breedDTO)) }
        return BreedsListEntity(listBreed.size > 0, listBreed)
    }

    private fun convertToBreed(breedDto: BreedDto): BreedEntity =
            BreedEntity(true,
                    breedDto.id,
                    breedDto.name ?: "",
                    breedDto.description ?: "",
                    breedDto.photo ?: "",
                    breedDto.origin ?: "")


    fun parseHttpException(httpException: HttpException): String {
        var errorMessage = ""
        try {
            val errorJsonString = httpException.response().errorBody()?.string()
            val basicError = Gson().fromJson(errorJsonString, BasicError::class.java)
            errorMessage = basicError.error
        } catch (e: Exception) {
            Log.e("BackendResponseMapper", e.toString())
        } finally {
            return errorMessage
        }
    }

}