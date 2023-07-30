package com.example.deeperhw.domain.usecase

import com.example.deeperhw.common.RepoResult
import com.example.deeperhw.di.IoDispatcher
import com.example.deeperhw.domain.repository.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

private const val BATHYMETRY_OBJECT_KEY = "bathymetry"

class GetScanDetailsUseCase @Inject constructor(
    private val repository: DataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(scanId: Int): Flow<RepoResult<JSONObject>> = flow {
        try {
            emit(RepoResult.Loading<JSONObject>())
            val repoResult = repository.getScanDetails(scanId).getJSONObject(BATHYMETRY_OBJECT_KEY)
            emit(RepoResult.Success<JSONObject>(repoResult))
        } catch (e: Exception) {
            emit(RepoResult.Error<JSONObject>())
        }
    }.flowOn(dispatcher)
}