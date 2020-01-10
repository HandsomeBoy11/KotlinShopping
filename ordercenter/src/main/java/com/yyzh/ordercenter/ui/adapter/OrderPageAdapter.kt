package com.yyzh.ordercenter.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yyzh.ordercenter.common.OrderConstant
import com.yyzh.ordercenter.ui.fragment.OrderFragment

/**
 * 订单pageradapter
 */
class OrderPageAdapter(manager: FragmentManager):FragmentPagerAdapter(manager) {
    private var tabs= arrayListOf("全部","待付款","待收货","已完成","已取消")
    override fun getItem(position: Int): Fragment {
        var fragment=OrderFragment()
        var bundle=Bundle()
        bundle.putInt(OrderConstant.KEY_ORDER_STATUS,position)
        fragment.arguments=bundle
        return fragment
    }

    override fun getCount(): Int {
        return tabs.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabs[position]
    }
}