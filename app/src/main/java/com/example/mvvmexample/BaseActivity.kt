package com.example.mvvmexample

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.yanghao277dev.mvvmbase.base.AbsLifecycleActivity
import com.yanghao277dev.mvvmbase.base.AbsViewModel

abstract class BaseActivity<VM : BaseViewModel> : AbsLifecycleActivity<VM>(){

    override fun initStatusBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.colorPrimary)
            .flymeOSStatusBarFontColor(R.color.colorPrimary).init()
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