package com.example.ifkakao.presentation.main_activity

import com.example.ifkakao.domain.model.Session

interface MainActivityListener {
    enum class Code {
        GO_TO_HOME,
        GO_TO_SESSION_LIST,
        GO_TO_DETAIL_SESSION,
    }

    fun callBack(code: Code, session: Session = Session())
}