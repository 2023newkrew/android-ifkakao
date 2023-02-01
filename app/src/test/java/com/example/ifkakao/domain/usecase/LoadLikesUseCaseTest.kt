package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.repository.LocalRepository
import org.junit.Assert.*

import org.junit.Test

object MockLocalRepository: LocalRepository {
    override fun loadLikes(): MutableSet<String>? {
        return setOf("1","2","3").toMutableSet()
    }

    override fun saveLike(set: Set<String>): Boolean {
        return false
    }

}


class LoadLikesUseCaseTest {

    @Test
    operator fun invoke() {
        val localRepository = MockLocalRepository

        assertEquals(localRepository.loadLikes(), setOf("1","2","3"))
    }
}