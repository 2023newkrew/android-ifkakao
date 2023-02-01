package com.example.ifkakao.di.component

import com.example.ifkakao.presenter.activity.MainActivity
import dagger.Subcomponent

@Subcomponent
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
}