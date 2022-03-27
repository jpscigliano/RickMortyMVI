package com.testme.feedframework.remote.rest.mapper


import com.testme.feeddomain.model.PagedData
import com.testme.feeddomain.model.Pagination
import com.testme.feedframework.remote.rest.dto.PagedDataResponseDto
import com.testme.domain.mapper.Mapper

class PagedResponseDtoToPagedDataMapper<PagedDataResponse, DataType>(
  private val dataResponseMapper: Mapper<PagedDataResponse, DataType>
) : Mapper<PagedDataResponseDto<PagedDataResponse>, PagedData<DataType>> {

  override fun map(input: PagedDataResponseDto<PagedDataResponse>?): PagedData<DataType> =
    PagedData(
      pagination = Pagination(
        count = input?.info?.count ?: 0,
        pages = input?.info?.pages ?: 0,
        next = input?.info?.next,
        prev = input?.info?.prev,
      ),
      data = dataResponseMapper.map(input?.results)
    )
}