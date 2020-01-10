package com.yyzh.goodscenter.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yyzh.goodscenter.ui.fragment.GoodsDetailTabOneFragment
import com.yyzh.goodscenter.ui.fragment.GoodsDetailTabTwoFragment

class GoodsDetailpagerAdapter(manager: FragmentManager):FragmentPagerAdapter(manager) {
    override fun getItem(position: Int): Fragment {
        return if(position==0){
            GoodsDetailTabOneFragment()
        }else{
            GoodsDetailTabTwoFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(position==0) "商品" else "详情"

    }
}