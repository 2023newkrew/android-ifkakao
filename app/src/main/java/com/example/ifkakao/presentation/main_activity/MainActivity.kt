package com.example.ifkakao.presentation.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.ifkakao.R
import com.example.ifkakao.di.MyApplication
import com.example.ifkakao.di.component.MainComponent
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.presentation.detail_session.DetailSessionFragment
import com.example.ifkakao.presentation.home.fragment.HomeFragment
import com.example.ifkakao.presentation.session_list.fragment.SessionListFragment
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
            add<HomeFragment>(R.id.main_fragment_container_view)
        }
//        viewModel.load()
    }

    override fun callBack(code: MainActivityListener.Code, session: Session) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            when(code){
                MainActivityListener.Code.GO_TO_HOME -> replace<HomeFragment>(R.id.main_fragment_container_view)
                MainActivityListener.Code.GO_TO_SESSION_LIST -> replace<SessionListFragment>(R.id.main_fragment_container_view)
                MainActivityListener.Code.GO_TO_DETAIL_SESSION -> {
                    val bundle = Bundle()
                    bundle.putSerializable("test", session)
                    val detailSessionFragment = DetailSessionFragment()
                    detailSessionFragment.arguments = bundle
                    replace(R.id.main_fragment_container_view, detailSessionFragment)
                }
            }
        }
    }
}