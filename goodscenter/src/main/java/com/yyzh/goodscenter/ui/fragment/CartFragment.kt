package com.yyzh.goodscenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.kotlin.user.injection.component.DaggerCartComponent
import com.kotlin.user.injection.module.CartModule
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ext.setVisable
import com.yyzh.baselibrary.ui.fragment.BaseMvpFragment
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.event.CartAllCheckedEvent
import com.yyzh.goodscenter.event.UpdateCartSizeEvent
import com.yyzh.goodscenter.event.UpdateTotalPriceEvent
import com.yyzh.goodscenter.persenter.CartPersenter
import com.yyzh.goodscenter.persenter.view.CartView
import com.yyzh.goodscenter.service.bean.CartGoods
import com.yyzh.goodscenter.ui.adapter.CartAdapter
import com.yyzh.providerlibrary.common.ProviderConstant
import com.yyzh.providerlibrary.router.RouterPath
import kotlinx.android.synthetic.main.cart_item.*
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.support.v4.toast

/**
 * 购物车
 */
class CartFragment : BaseMvpFragment<CartPersenter>(), CartView, View.OnClickListener {
    private var totalPrice: Long = 0

    private val goddsCartAdapter by lazy { CartAdapter(activity!!) }
    override fun injectComponent() {
        DaggerCartComponent.builder().activityComponent(mActivityComponent).cartModule(CartModule()).build()
            .inject(this)
        mPresenter.mView = this
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        loadData()

    }

    private fun initObserver() {
        Bus.observe<CartAllCheckedEvent>().subscribe {
            mAllCheckedCb?.isSelected = it.isAllSelect
            updataAllPrice()
        }.registerInBus(this)
        Bus.observe<UpdateTotalPriceEvent>().subscribe {
            updataAllPrice()
        }.registerInBus(this)
    }

    private fun loadData() {
        mPresenter.getCartList()
    }

    private fun initView() {
        mSettleAccountsBtn.onClick(this)
        mDeleteBtn.onClick(this)
        mHeaderBar.getRightView().onClick(this)

        var manager = LinearLayoutManager(activity)
        mCartGoodsRv.layoutManager = manager
        mCartGoodsRv.adapter = goddsCartAdapter

        mAllCheckedCb.onClick {
            mAllCheckedCb.isSelected = !mAllCheckedCb.isSelected
            for (item in goddsCartAdapter.dataList) {
                item.isSelected = mAllCheckedCb.isSelected
            }
            goddsCartAdapter.notifyDataSetChanged()
            updataAllPrice()
        }

    }

    private fun updataAllPrice() {
        totalPrice = goddsCartAdapter.dataList
            .filter { it.isSelected }
            .map { it.goodsCount * it.goodsPrice }
            .sum()
        mTotalPriceTv?.text = "合计:${YuanFenConverter.changeF2YWithUnit(totalPrice)}"
    }

    /**
     * 获取订单列表结果
     */
    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            mAllCheckedCb.isSelected = false
            goddsCartAdapter.setData(result)
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
        //本地存储并发送事件刷新UI
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, result?.size ?: 0)
        Bus.send(UpdateCartSizeEvent())
        updataAllPrice()
    }

    /**
     * 删除订单结果
     */
    override fun onDeleteCartResult(result: Boolean) {
        toast("删除成功")
        switchEditStata()
        loadData()

    }

    /**
     * 提交订单结果
     */
    override fun onComitCartResult(result: Int) {
        //跳到确认订单页面
        ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
            .withInt(ProviderConstant.KEY_ORDER_ID, result).navigation()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mRightTv -> switchEditStata()
            R.id.mSettleAccountsBtn -> submitGoodsCart()
            R.id.mDeleteBtn -> deleteGoods()
        }
    }

    /**
     * 删除购物车中商品
     */
    private fun deleteGoods() {
        val goodsIds: MutableList<Int> = arrayListOf()
        goddsCartAdapter.dataList.filter {
            it.isSelected
        }.mapTo(goodsIds) { it.id }
        if (goodsIds.size == 0) {
            toast("请勾选所要删除的商品")
        } else {
            mPresenter.deleteCartList(goodsIds)
        }
    }

    /**
     * 切换编辑状态
     */
    private fun switchEditStata() {
        if (mHeaderBar.getRightText() == "编辑") {
            mHeaderBar.getRightView().text = "完成"
            mTotalPriceTv.setVisable(false)
            mSettleAccountsBtn.setVisable(false)
            mDeleteBtn.setVisable(true)
        } else {
            mHeaderBar.getRightView().text = "编辑"
            mTotalPriceTv.setVisable(true)
            mSettleAccountsBtn.setVisable(true)
            mDeleteBtn.setVisable(false)
        }
    }

    /**
     * 提交购物车
     */
    private fun submitGoodsCart() {
        val cartGoodsList: MutableList<CartGoods> = arrayListOf()
        goddsCartAdapter.dataList.filter {
            it.isSelected
        }.mapTo(cartGoodsList) { it }
        if (cartGoodsList.size == 0) {
            toast("请勾选购物商品")
        } else {
            mPresenter.submitCart(cartGoodsList, totalPrice)
        }
    }
}