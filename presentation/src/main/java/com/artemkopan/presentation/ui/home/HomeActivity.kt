package com.artemkopan.presentation.ui.home

import android.os.Bundle
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseActivity


class HomeActivity : BaseActivity<HomeViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getContentView(): Int = R.layout.activity_home

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

}