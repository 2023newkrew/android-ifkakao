package com.example.ifkakao.domain.use_case

import com.example.ifkakao.domain.repository.WindowRepository

class GetIsDualPaneUseCase(private val repository: WindowRepository) {
    operator fun invoke(): Boolean = repository.getIsDualPane()
}