package com.example.ifkakao.presentation.main_activity

import com.example.ifkakao.domain.model.Session

interface MainActivityListener {
    enum class Code {
        HOME,
        SESSION_LIST,
        DETAIL_SESSION,
    }

    fun goToFragment(code: Code, session: Session = Session())
}