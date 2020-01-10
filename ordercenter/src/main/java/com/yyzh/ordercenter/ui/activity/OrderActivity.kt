package com.yyzh.ordercenter.ui.activity

import android.os.Bundle
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.ordercenter.R
import com.yyzh.ordercenter.common.OrderConstant
import com.yyzh.ordercenter.common.OrderStatus
import com.yyzh.ordercenter.ui.adapter.OrderPageAdapter
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity:BaseActivity() {
    private val mAdapter by lazy { OrderPageAdapter(supportFragmentManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        initView()
    }

    private fun initView() {
        mOrderVp.adapter=mAdapter
        mOrderTab.setupWithViewPager(mOrderVp)
        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS,OrderStatus.ORDER_ALL)
    }
}