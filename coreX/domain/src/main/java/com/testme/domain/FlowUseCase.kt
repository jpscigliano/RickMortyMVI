package com.testme.domain

import kotlinx.coroutines.flow.Flow

/**
 * This abstraction represents an execution unit, any use case in the application should implement this contract.
 */
interface FlowUseCase<Request, Response> {
  suspend operator fun invoke(request: Request): Flow<DataSourceResult<Response>>
}
