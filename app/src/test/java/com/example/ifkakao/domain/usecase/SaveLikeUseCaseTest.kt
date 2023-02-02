package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.repository.LocalRepository
import org.junit.Assert.*

import org.junit.Test


class SaveLikeUseCaseTest {

    @Test
    operator fun invoke() {
        val localRepository = MockLocalRepository

        assertEquals(localRepository.saveLike(emptySet()),false)
    }
}