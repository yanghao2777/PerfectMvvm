package com.example.mvvmexample

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mvvmexample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel>() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun dataObserver() {
        mViewModel?.imageLiveUrl?.observe(this, Observer{
            Glide.with(this).load(it).into(viewBinding.bingImage)
        })
    }

    override fun initView() {
        mViewModel?.getBingImage()
    }

    override fun initClickListener() {
        viewBinding.next.setOnClickListener {
            mViewModel?.next()
        }
        viewBinding.pre.setOnClickListener {
            mViewModel?.pre()
        }
    }

    override fun initViewBinding(): View {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        view.setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent))
        return view
    }

}