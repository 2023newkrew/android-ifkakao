package com.example.ifkakao.presentation.main.listener

interface MainActivityListener {
    enum class Code {
        GO_TO_SESSION_LIST
    }
    fun fragmentCallBack(code: Code)
}