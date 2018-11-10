package android.afebrerp.com.movies.domain.exception

abstract class BaseException(override val cause: Throwable = Throwable(), val exceptionCode: Int = 0, val customMessage: String = "")
    : RuntimeException(cause)