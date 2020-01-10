package com.yyzh.ordercenter.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.order.data.protocol.Order
import com.kotlin.user.injection.component.DaggerOrderComponent
import com.kotlin.user.injection.module.OrderModule
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.ordercenter.R
import com.yyzh.ordercenter.persenter.OrderDetailPresenter
import com.yyzh.ordercenter.persenter.view.OrderDetailView
import com.yyzh.ordercenter.ui.adapter.OrderGoodsAdapter
import com.yyzh.providerlibrary.common.ProviderConstant
import com.yyzh.providerlibrary.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_detail.*


/*
    订单详情
 */
@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_ORDER)
class OrderDetailActivity : BaseMvpActivity<OrderDetailPresenter>(), OrderDetailView {
    private lateinit var mAdapter: OrderGoodsAdapter

    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
        mPersenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        initView()
        loadData()
    }

    /*
        初始化视图
     */
    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    /*
        加载数据
     */
    private fun loadData() {
        mPersenter.getOrderById(intent.getIntExtra(ProviderConstant.KEY_ORDER_ID,0))
    }

    /*
        获取订单回调
     */
    override fun onGetOrderByIdResult(result: Order) {
        mShipNameTv.setContentText(result.shipAddress!!.shipUserName)
        mShipMobileTv.setContentText(result.shipAddress!!.shipUserMobile)
        mShipAddressTv.setContentText(result.shipAddress!!.shipAddress)
        mTotalPriceTv.setContentText(YuanFenConverter.changeF2YWithUnit(result.totalPrice))

        mAdapter.setData(result.orderGoodsList)
    }

}
