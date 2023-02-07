package com.example.ifkakao.presentation.main_activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class MainActivity
    : AppCompatActivity(), MainActivityListener {

    @Inject
    lateinit var mainComponent: MainComponent

    @Inject
    lateinit var viewModel: MainActivityViewModel

    var nowCode = MainActivityListener.Code.HOME
    var nowSession = Session()

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as MyApplication).appComponent.mainComponent().create()
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                nowCode = (savedInstanceState.getSerializable("code", MainActivityListener.Code::class.java)) ?: MainActivityListener.Code.HOME
                nowSession = savedInstanceState.getSerializable("session", Session::class.java) ?: Session()
            } else {
                nowCode = (savedInstanceState.getSerializable("code") as? MainActivityListener.Code) ?: MainActivityListener.Code.HOME
                nowSession = (savedInstanceState.getSerializable("session") as? Session) ?: Session()
            }
        }
        goToFragment(nowCode, nowSession)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("code", nowCode)
        outState.putSerializable("session", nowSession)
        super.onSaveInstanceState(outState)
    }

    override fun goToFragment(code: MainActivityListener.Code, session: Session) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            nowCode = code
            when (code) {
                MainActivityListener.Code.HOME -> {
                    replace<HomeFragment>(R.id.main_fragment_container_view)
                }
                MainActivityListener.Code.SESSION_LIST -> {
                    replace<SessionListFragment>(R.id.main_fragment_container_view)
                }
                MainActivityListener.Code.DETAIL_SESSION -> {
                    nowSession = session
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