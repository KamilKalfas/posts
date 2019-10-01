package com.kkalfas.sample.core

interface UseCase<in Params, out Type> where Type : Any {
    suspend operator fun invoke(params: Params): Either<Failure, Type>
}