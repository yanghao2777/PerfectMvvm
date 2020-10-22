package com.example.mvvmexample

import com.yanghao277dev.mvvmbase.base.AbsViewModel

open class BaseViewModel : AbsViewModel<BaseRepository>(){
    init {
        mModel = BaseRepository.INSTANCE
    }
}