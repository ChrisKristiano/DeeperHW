package com.example.deeperhw.domain.usecase

import com.example.deeperhw.common.RepoResult
import com.example.deeperhw.data.entities.ScanEntity
import com.example.deeperhw.di.IoDispatcher
import com.example.deeperhw.domain.extensions.toLocalDateTime
import com.example.deeperhw.domain.model.Scan
import com.example.deeperhw.domain.repository.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class GetScannedLocationsUseCase @Inject constructor(
    private val repository: DataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<RepoResult<List<Scan>>> = flow {
        try {
            emit(RepoResult.Loading())
            val result = repository.getScannedLocations().map { it.toScan() }
            emit(RepoResult.Success(result))
        } catch (e: Exception) {
            emit(RepoResult.Error())
        }
    }.flowOn(dispatcher)

    private fun ScanEntity.toScan(): Scan = Scan(
        id = this.id,
        name = this.name,
        date = this.ddate?.toLocalDateTime()
    )
}