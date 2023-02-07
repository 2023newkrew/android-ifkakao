package com.example.ifkakao.presentation.presentation_session_list.fragment

import com.example.ifkakao.domain.model.Session

interface SessionListFragmentListener {
    fun callBack(session: Session)
}