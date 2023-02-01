package com.example.ifkakao.presentation.main_session.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.ifkakao.R
import com.example.ifkakao.di.MyApplication
import com.example.ifkakao.di.component.MainComponent
import com.example.ifkakao.presentation.main_session.fragment.ChoiceSessionTrackFragment
import com.example.ifkakao.presentation.main_session.fragment.EndingBannerFragment
import com.example.ifkakao.presentation.main_session.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainComponent: MainComponent

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as MyApplication).appComponent.mainComponent().create()
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
//                add<MainVideoFragment>(R.id.main_video_fragment_container_view)
//                add<HelloFragment>(R.id.hello_fragment_container_view)
//                add<ChoiceSessionTypeFragment>(R.id.choice_session_type_fragment_container_view)
                add<ChoiceSessionTrackFragment>(R.id.choice_session_track_fragment_container_view)
                add<EndingBannerFragment>(R.id.ending_banner_fragment_container_view)
            }
        }

//        viewModel.load()
    }
}