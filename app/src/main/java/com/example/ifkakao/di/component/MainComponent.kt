package com.example.ifkakao.di.component

import com.example.ifkakao.di.module.NetworkModule
import com.example.ifkakao.di.module.SessionListModule
import com.example.ifkakao.di.scope.ActivityScope
import com.example.ifkakao.presentation.main_activity.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [SessionListModule::class,NetworkModule::class])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun sessionListFragmentComponent(): SessionListComponent.Factory
}