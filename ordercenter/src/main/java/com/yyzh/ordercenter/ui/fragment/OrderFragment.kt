package com.yyzh.ordercenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.order.data.protocol.Order
import com.kotlin.user.injection.component.DaggerOrderComponent
import com.kotlin.user.injection.module.OrderModule
import com.yyzh.baselibrary.ui.fragment.BaseMvpFragment
import com.yyzh.ordercenter.R
import com.yyzh.ordercenter.common.OrderConstant
import com.yyzh.ordercenter.persenter.OrderPersener
import com.yyzh.ordercenter.persenter.view.OrderView
import com.yyzh.ordercenter.ui.activity.OrderDetailActivity
import com.yyzh.ordercenter.ui.adapter.OrderAdapter
import com.yyzh.providerlibrary.common.ProviderConstant
import com.yyzh.providerlibrary.router.RouterPath
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class OrderFragment : BaseMvpFragment<OrderPersener>(), OrderView {

    private val mOrderAdapter by lazy { OrderAdapter(activity!!) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity!!)
        mOrderRv.adapter = mOrderAdapter
        mOrderAdapter.setBtnCallBack(object : OrderAdapter.BtnCallBack {
            override fun onPayBun(order: Order) {
                toast("购买")
                ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY).withInt(ProviderConstant.KEY_ORDER_ID, order.id)
                    .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice).navigation()
            }

            override fun ononfirmBtn(order: Order) {
                mPresenter.confirmOrder(order.id)
            }

            override fun onCancelBtn(order: Order) {
                showCancelDialog(order)
            }

        })
        mOrderAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<Order>{
            override fun onItemClick(item: Order, position: Int) {
                startActivity<OrderDetailActivity>(ProviderConstant.KEY_ORDER_ID to item.id)
            }

        })
    }

    private fun showCancelDialog(order: Order) {
        AlertView(
            "取消订单",
            "确定取消该订单？",
            "取消",
            null,
            arrayOf("确定"),
            activity,
            AlertView.Style.Alert,
            OnItemClickListener { o, position ->
                if (position == 0) {
                    mPresenter.cancelOrder(order.id)
                }
            }

        ).show()
    }

    private fun initData() {
        var orderStatus = arguments?.getInt(OrderConstant.KEY_ORDER_STATUS)
        orderStatus?.let {
            mPresenter.getOrderList(it)
        }
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build()
            .inject(this)
        mPresenter.mView = this
    }

    /**
     * 获取订单列表
     */
    override fun onGetOrderListResult(result: MutableList<Order>?) {
        if (result != null && result.size > 0) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            mOrderAdapter.setData(result)
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /**
     * 确认订单
     */
    override fun onConfirmOrderResult(result: Boolean) {
        toast("确认订单成功")
        initData()
    }

    /**
     * 取消订单
     */
    override fun onCancelOrderResult(result: Boolean) {
        toast("取消订单成功")
        initData()

    }
}