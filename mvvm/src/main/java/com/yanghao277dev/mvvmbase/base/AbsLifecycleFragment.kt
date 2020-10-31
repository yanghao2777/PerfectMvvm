package com.yanghao277dev.mvvmbase.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class AbsLifecycleFragment<T : AbsViewModel<*>?> : Fragment() {
    protected var mViewModel: T? = null

    private fun initViewModel() {
        mViewModel = vmProviders(this, TUtil.getInstance(this,0)!!)
    }

    private fun <T : ViewModel?> vmProviders(fragment: Fragment?, modelClass: Class<T>): T {
        return ViewModelProvider(fragment!!)[modelClass]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initViewModel()
        MyLog.lifeStateF("CreateView",this::class.java)
        return initViewBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyLog.lifeStateF("ViewCreate",this::class.java)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyLog.lifeStateF("ActivityCreate",this::class.java)
    }

    abstract fun initView()

    protected abstract fun dataObserver()

    protected abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?) : View

    override fun onStart() {
        super.onStart()
        MyLog.lifeStateF("Start",this::class.java)
        dataObserver()
    }

    override fun onResume() {
        super.onResume()
        MyLog.lifeStateF("Resume",this::class.java)
    }

    override fun onPause() {
        super.onPause()
        MyLog.lifeStateF("Pause",this::class.java)
    }

    override fun onStop() {
        super.onStop()
        MyLog.lifeStateF("Stop",this::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyLog.lifeStateF("Destroy",this::class.java)
    }
}