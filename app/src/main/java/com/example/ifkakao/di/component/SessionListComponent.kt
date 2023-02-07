package com.example.ifkakao.di.component

import com.example.ifkakao.di.scope.FragmentScope
import com.example.ifkakao.presentation.session_list.fragment.SessionListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface SessionListComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SessionListComponent
    }

    fun inject(fragment: SessionListFragment)
}