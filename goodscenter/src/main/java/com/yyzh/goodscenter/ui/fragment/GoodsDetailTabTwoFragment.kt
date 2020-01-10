package com.yyzh.goodscenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.baselibrary.ui.fragment.BaseFragment
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.event.GoodsDetailEvent
import kotlinx.android.synthetic.main.fragment_goods_detail2.*

class GoodsDetailTabTwoFragment:BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_goods_detail2,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        Bus.observe<GoodsDetailEvent>().subscribe {
            it.run {
                mGoodsDetailOneIv.loadUrl(url1)
                mGoodsDetailTwoIv.loadUrl(url2)
            }
        }.registerInBus(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Bus.unregister(this)
    }
}