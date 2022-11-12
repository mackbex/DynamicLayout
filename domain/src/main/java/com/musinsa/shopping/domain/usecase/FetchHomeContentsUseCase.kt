package com.musinsa.shopping.domain.usecase

import com.musinsa.shopping.domain.repository.HomeRepository
import javax.inject.Inject

class FetchHomeContentsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = homeRepository.fetchHomeContents()
}