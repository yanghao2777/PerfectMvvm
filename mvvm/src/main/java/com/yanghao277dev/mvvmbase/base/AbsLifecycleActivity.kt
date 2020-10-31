package com.yanghao277dev.mvvmbase.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe

abstract class AbsLifecycleActivity<T : AbsViewModel<*>> : AppCompatActivity() {

    protected var mViewModel: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initViewBinding())

        initStatusBar()

        initViewModel()
        initView()
        dataObserver()
        initClickListener()

    }

     private fun initViewModel() {
        mViewModel = vmProviders(this, TUtil.getInstance(this,0)!!)

        mViewModel?.loadState?.observe(this, Observer {
            when (it) {
                is LoadState.Success -> onSuccessState(it.msg)
                is LoadState.Fail    -> onFailState(it.msg)
                is LoadState.Loading -> onLoadingState(it.msg)
            }
        })
    }

    private fun <T : ViewModel?> vmProviders(activity: AppCompatActivity?, modelClass: Class<T>): T {
        return ViewModelProvider(activity!!).get(modelClass)
    }

    protected abstract fun initStatusBar()

    protected abstract fun dataObserver()

    protected abstract fun showProgressDialog()

    protected abstract fun hideProgressDialog()

    protected abstract fun dismissProgressDialog()

    protected abstract fun initView()

    protected abstract fun initClickListener()

    protected abstract fun initViewBinding() : View

    protected open fun onLoadingState(msg : String){
        MyLog.runningLog("loading : $msg",this::class.java)
    }

    protected open fun onSuccessState(msg : String){
        MyLog.runningLog("success : $msg",this::class.java)
    }

    protected open fun onFailState(msg : String){
        MyLog.runningLog("fail : $msg",this::class.java)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        MyLog.lifeStateA("New Intent" , this::class.java)
    }

    override fun onStart() {
        super.onStart()
        MyLog.lifeStateA("Start" , this::class.java)
    }

    override fun onResume() {
        super.onResume()
        MyLog.lifeStateA("Resume" , this::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        MyLog.lifeStateA("ActivityResult" , this::class.java)
    }

    override fun onPause() {
        super.onPause()
        MyLog.lifeStateA("Pause" , this::class.java)
    }

    override fun onStop() {
        super.onStop()
        MyLog.lifeStateA("Stop" , this::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyLog.lifeStateA("Destroy" , this::class.java)
        dismissProgressDialog()
    }

}