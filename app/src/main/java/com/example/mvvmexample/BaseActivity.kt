package com.example.mvvmexample

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.yanghao277dev.mvvmbase.base.AbsLifecycleActivity
import com.yanghao277dev.mvvmbase.base.AbsViewModel

abstract class BaseActivity<VM : BaseViewModel> : AbsLifecycleActivity<VM>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        ImmersionBar.with(this)
            .statusBarColor(getStatusBarColor())
            .flymeOSStatusBarFontColor(R.color.colorPrimary).init()
    }

    protected open fun getStatusBarColor(): Int{
        return R.color.colorPrimary
    }

    protected fun initProgressDialog(){

    }

    override fun showProgressDialog(){

    }

    override fun hideProgressDialog(){

    }

    override fun dismissProgressDialog() {

    }
}