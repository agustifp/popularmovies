package android.afebrerp.com.movies.presentation.view.base

import android.afebrerp.com.movies.data.exception.ExceptionManager
import android.afebrerp.com.movies.domain.exception.BackendException
import android.afebrerp.com.movies.domain.model.entity.base.BaseEntity
import android.afebrerp.com.movies.domain.model.params.base.BaseParams
import android.afebrerp.com.movies.domain.usecases.base.BaseUseCase
import android.afebrerp.com.movies.domain.usecases.wrappers.BaseUseCaseWrapper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


import kotlin.reflect.KClass

abstract class BaseViewModel(private val useCaseWrapper: BaseUseCaseWrapper) : ViewModel() {

    val onErrorReceived = MutableLiveData<String>()//can have only one observer at a time.

    override fun onCleared() {
        super.onCleared()
        useCaseWrapper.cancelUseCases()
    }

    fun <T : BaseUseCase<*, *>> execute(classType: KClass<T>, params: BaseParams, onResultOk: (BaseEntity) -> Unit, onResultError: (String) -> Unit) {

        useCaseWrapper.getUseCase(classType)?.let { useCase ->
            useCase.executeAsync(params, {
                if (it.result) onResultOk(it)
                else onResultError(ExceptionManager.manageError(BackendException()))
            }, {
                Log.e("BaseViewModel", "Error", it)
               onErrorReceived.value =(it?.customMessage)
                onResultError(ExceptionManager.manageError(it ?: BackendException()))
            })
        }
    }

}