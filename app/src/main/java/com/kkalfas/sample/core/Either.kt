package com.kkalfas.sample.core

sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun either(onFailure: (L) -> Any, onSuccess: (R) -> Any): Any =
        when (this) {
            is Left -> onFailure(a)
            is Right -> onSuccess(b)
        }
}

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.right(fn((this as Either.Right).b))

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(a)
        is Either.Right -> fn(b)
    }


sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    data class Exception(val error: Throwable) : Failure()
}