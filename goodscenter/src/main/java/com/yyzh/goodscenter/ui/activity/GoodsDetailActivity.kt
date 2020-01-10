package com.yyzh.goodscenter.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.android.material.tabs.TabLayout
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.event.AddCartEvent
import com.yyzh.goodscenter.event.UpdateCartSizeEvent
import com.yyzh.goodscenter.ui.adapter.GoodsDetailpagerAdapter
import com.yyzh.providerlibrary.common.afterLogin
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import q.rorbin.badgeview.QBadgeView

class GoodsDetailActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mQBadgeView: QBadgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)
        initView()
        initObserver()
        initData()
    }

    private fun initData() {
        //初始购物车数量
        setCartCount()
    }

    private fun initObserver() {
        Bus.observe<UpdateCartSizeEvent>().subscribe {
            setCartCount()
        }.registerInBus(this)
    }

    /**
     * 设置购物车数量
     */
    private fun setCartCount() {
        mQBadgeView.badgeGravity = Gravity.END or Gravity.TOP
        mQBadgeView.setGravityOffset(22f, -2f, true)
        mQBadgeView.setBadgeTextSize(6f, true)
        mQBadgeView.bindTarget(mInCartTv).badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)
    }

    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        var adapter = GoodsDetailpagerAdapter(supportFragmentManager)
        mGoodsDetailVp.adapter = adapter
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)
        mLeftIv.onClick(this)
        mShareTv.onClick(this)
        mInCartTv.onClick(View.OnClickListener {
            afterLogin {
                startActivity<CartActivity>()
            }
        })
        mAddCartBtn.onClick(this)
        mQBadgeView = QBadgeView(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mLeftIv -> {
                finish()
            }
            R.id.mShareTv -> {//分享
                toast("分享")

            }
            R.id.mAddCartBtn -> {//添加购物车
                afterLogin {
                    Bus.send(AddCartEvent())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}