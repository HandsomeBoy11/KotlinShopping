package com.yyzh.ordercenter.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.order.data.protocol.Order
import com.kotlin.user.injection.component.DaggerOrderComponent
import com.kotlin.user.injection.module.OrderModule
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ext.setVisable
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.ordercenter.R
import com.yyzh.ordercenter.event.UpdateAddressEvent
import com.yyzh.ordercenter.persenter.ConfirmOrderPersener
import com.yyzh.ordercenter.persenter.view.ConfirmOrderView
import com.yyzh.ordercenter.ui.adapter.OrderConfirmAdapter
import com.yyzh.providerlibrary.common.ProviderConstant
import com.yyzh.providerlibrary.router.RouterPath
import kotlinx.android.synthetic.main.actvitiy_order_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<ConfirmOrderPersener>(), ConfirmOrderView, View.OnClickListener {

    private val mConfirmAdapter by lazy { OrderConfirmAdapter(this) }
    private lateinit var mCurrentOrder: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvitiy_order_confirm)
        ARouter.getInstance().inject(this)
        initView()
        initData()
        initObserver()
    }

    private fun initObserver() {
        Bus.observe<UpdateAddressEvent>().subscribe {
            mCurrentOrder.run {
                mCurrentOrder.shipAddress = it.address
            }
            updateAddressInfo()
        }.registerInBus(this)
    }

    private fun initData() {
        val orderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        if (orderId != -1) {
            mPersenter.getOrderById(orderId)
        }

    }

    private fun initView() {
        mSelectShipTv.onClick(this)
        mShipView.onClick(this)
        mSubmitOrderBtn.onClick(this)

        var manager = LinearLayoutManager(this)
        mOrderGoodsRv.layoutManager = manager
        mOrderGoodsRv.adapter = mConfirmAdapter


    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mSelectShipTv, R.id.mShipView -> {
                startActivity<OrderAddressActivity>()
            }
            R.id.mSubmitOrderBtn -> {
                if (checkAddress()) {
                    mPersenter.submitOrder(mCurrentOrder)
                }else{
                    toast("请选择地址")
                }
            }
        }
    }

    /**
     * 检测地址
     */
    private fun checkAddress(): Boolean {
        return mCurrentOrder.shipAddress != null
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build()
            .inject(this)
        mPersenter.mView = this
    }


    /**
     * 通过订单id获取订单裂变
     */
    override fun onGetOrderByIdResult(result: Order) {
        mCurrentOrder = result
        mConfirmAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"
        updateAddressInfo()
    }

    /**
     * 更新地址信息
     */
    private fun updateAddressInfo() {
        if (mCurrentOrder.shipAddress == null) {
            mSelectShipTv.setVisable(true)
            mShipView.setVisable(false)
        } else {
            mSelectShipTv.setVisable(false)
            mShipView.setVisable(true)
            mShipNameTv.text =
                mCurrentOrder.shipAddress?.shipUserName + "   " + mCurrentOrder.shipAddress?.shipUserMobile
            mShipAddressTv.text = mCurrentOrder.shipAddress?.shipAddress
        }

    }

    override fun onSubmitOrderResult(result: Boolean) {
        if (result) {
            ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                .withInt(ProviderConstant.KEY_ORDER_ID,mCurrentOrder.id)
                .withLong(ProviderConstant.KEY_ORDER_PRICE,mCurrentOrder.totalPrice)
                .navigation()
            finish()
        } else {
            toast("提交失败")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}