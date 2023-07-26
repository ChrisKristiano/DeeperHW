package com.example.deeperhw.domain.usecase

import com.example.deeperhw.common.RepoResult
import com.example.deeperhw.di.IoDispatcher
import com.example.deeperhw.domain.repository.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class PerformLoginUseCase @Inject constructor(
    private val repository: DataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(email: String, password: String): Flow<RepoResult<Nothing>> = flow {
        try {
            emit(RepoResult.Loading())
            repository.login(email, password)
            emit(RepoResult.Success())
        } catch (e: Exception) {
            emit(RepoResult.Error())
        }
    }.flowOn(dispatcher)
}