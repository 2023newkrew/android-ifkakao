package com.example.ifkakao.domain.use_case

import com.example.ifkakao.domain.repository.WindowRepository

class GetWidthUseCase(private val repository: WindowRepository) {
    operator fun invoke() = repository.getWidth()
}