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
        try {
            useCaseWrapper.getUseCase(classType)!!.executeAsync(params, {
                if (it.result) onResultOk(it)
                else onResultError(ExceptionManager.manageError(BackendException()))
            }, {
                Log.e("BaseViewModel", "Error", it)
                onErrorReceived.value = (it?.customMessage)
                onResultError(ExceptionManager.manageError(it ?: BackendException()))
            })
        } catch (exception: KotlinNullPointerException) {
            Log.e("BaseViewModel", "Error probably missing the useCase requested on your wrapper.", exception)
        }
    }

    /**
     * Implementation of cancel to show that has been evaluated, at this moment
     * cancelling a Job, doesn't cancel the call from retrofit at the start of this project,
     * it's being evaluated and will be added in a few weeks or a month.
     * this can crash when returning data if the app is on background
     * https://github.com/square/retrofit/pull/2886
     * */
    fun <T : BaseUseCase<*, *>> cancelJob(classType: KClass<T>) {
        try {
            useCaseWrapper.getUseCase(classType)!!.cancel()
        } catch (exception: KotlinNullPointerException) {
            Log.e("BaseViewModel", "Error probably missing the useCase requested on your wrapper.", exception)
        }
    }
}