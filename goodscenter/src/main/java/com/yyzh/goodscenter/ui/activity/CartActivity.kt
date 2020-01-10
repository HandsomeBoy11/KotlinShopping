package com.yyzh.goodscenter.ui.activity

import android.os.Bundle
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.ui.fragment.CartFragment

class CartActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initView()
    }

    private fun initView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flContainer,CartFragment())
        transaction.commit()
    }
}