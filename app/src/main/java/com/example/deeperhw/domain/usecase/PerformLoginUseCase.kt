package com.example.deeperhw.domain.usecase

import com.example.deeperhw.common.RepoResult
import com.example.deeperhw.di.IoDispatcher
import com.example.deeperhw.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class PerformLoginUseCase @Inject constructor(
    private val repository: UserRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(email: String, password: String): Flow<RepoResult<Nothing>> = flow {
        try {
            emit(RepoResult.Loading<Nothing>())
            repository.login(email, password)
            emit(RepoResult.Success<Nothing>())
        } catch (e: Exception) {
            emit(RepoResult.Error<Nothing>())
        }
    }.flowOn(dispatcher)
}