package android.afebrerp.com.movies.domain.usecases.wrappers

import android.afebrerp.com.movies.domain.model.entity.base.BaseEntity
import android.afebrerp.com.movies.domain.model.params.base.BaseParams
import android.afebrerp.com.movies.domain.usecases.base.BaseUseCase
import kotlin.reflect.KClass

abstract class BaseUseCaseWrapper {

    companion object {
        private const val TAG = "BaseUseCaseWrapper"
    }

    private val map: HashMap<KClass<*>, BaseUseCase<BaseEntity, BaseParams>> = HashMap()

    protected fun <T : BaseUseCase<*, *>> addUseCases(vararg useCase: T) =
            useCase.forEach { useCaseIterator ->
                try {
                    map[useCaseIterator::class] = useCaseIterator as BaseUseCase<BaseEntity, BaseParams>
                } catch (e: Exception) {
                    print(TAG + "UseCase does not extend from BaseCoRoutineUseCase<BaseEntity, BaseParams>, please check Usecases used")
                }
            }

    fun <T : BaseUseCase<*, *>> getUseCase(classType: KClass<T>): BaseUseCase<BaseEntity, BaseParams>? = map[classType]

    fun cancelUseCases() = map.forEach { it.value.cancel() }

}