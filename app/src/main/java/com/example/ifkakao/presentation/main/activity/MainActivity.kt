package com.example.ifkakao.presentation.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.ifkakao.R
import com.example.ifkakao.di.MyApplication
import com.example.ifkakao.di.component.MainComponent
import com.example.ifkakao.presentation.main.fragment.*
import com.example.ifkakao.presentation.main.listener.MainActivityListener
import com.example.ifkakao.presentation.main.viewmodel.MainActivityViewModel
import com.example.ifkakao.presentation.session_list.SessionListFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityListener {

    @Inject
    lateinit var mainComponent: MainComponent

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as MyApplication).appComponent.mainComponent().create()
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MainFragment>(R.id.main_fragment_container_view)
        }

//        viewModel.load()
    }
    override fun fragmentCallBack(code: MainActivityListener.Code) {
        when (code){
            MainActivityListener.Code.GO_TO_SESSION_LIST -> supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SessionListFragment>(R.id.main_fragment_container_view)
            }
        }
    }
}