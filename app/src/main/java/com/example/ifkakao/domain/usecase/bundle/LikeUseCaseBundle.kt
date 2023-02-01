package com.example.ifkakao.domain.usecase.bundle

import com.example.ifkakao.domain.usecase.DeleteLikeUseCase
import com.example.ifkakao.domain.usecase.GetLikeListUseCase
import com.example.ifkakao.domain.usecase.InsertLikeUseCase

data class LikeUseCaseBundle(
    val getLikeListUseCase: GetLikeListUseCase,
    val insertLikeUseCase: InsertLikeUseCase,
    val deleteLikeUseCase: DeleteLikeUseCase
)