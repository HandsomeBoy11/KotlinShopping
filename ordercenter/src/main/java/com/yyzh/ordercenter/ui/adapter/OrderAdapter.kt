package com.yyzh.ordercenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.order.data.protocol.Order
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ext.setVisable
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.ordercenter.R
import com.yyzh.ordercenter.common.OrderStatus
import kotlinx.android.synthetic.main.order_item.view.*
import org.jetbrains.anko.dip

class OrderAdapter(mContext: Context) : BaseRecyclerViewAdapter<Order, OrderAdapter.ViewHolder>(mContext) {
    val inflater by lazy { LayoutInflater.from(mContext) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = inflater.inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val order = dataList[position]
        when (order.orderStatus) {
            OrderStatus.ORDER_WAIT_PAY -> {
                holder.itemView.mOrderStatusNameTv.text = "待付款"
                holder.itemView.mOrderStatusNameTv.setTextColor(mContext.resources.getColor(R.color.common_red))
                setOptVisable(false, true, true, holder)
            }
            OrderStatus.ORDER_WAIT_CONFIRM -> {
                holder.itemView.mOrderStatusNameTv.text = "待收货"
                holder.itemView.mOrderStatusNameTv.setTextColor(mContext.resources.getColor(R.color.common_blue))
                setOptVisable(true, false, true, holder)
            }
            OrderStatus.ORDER_COMPLETED -> {
                holder.itemView.mOrderStatusNameTv.text = "已完成"
                holder.itemView.mOrderStatusNameTv.setTextColor(mContext.resources.getColor(R.color.common_yellow))
                setOptVisable(false, false, true, holder)
            }
            OrderStatus.ORDER_CANCELED -> {
                holder.itemView.mOrderStatusNameTv.text = "已取消"
                holder.itemView.mOrderStatusNameTv.setTextColor(mContext.resources.getColor(R.color.common_gray))
                setOptVisable(false, false, false, holder)
            }
        }
        var mTotalCount: Int = 0
        when (order.orderGoodsList.size) {
            1 -> {
                holder.itemView.mSingleGoodsView.setVisable(true)
                holder.itemView.mMultiGoodsView.setVisable(false)
                var orderGoods = order.orderGoodsList[0]
                holder.itemView.mGoodsIconIv.loadUrl(orderGoods.goodsIcon)
                holder.itemView.mGoodsDescTv.text = orderGoods.goodsDesc//商品描述
                holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(orderGoods.goodsPrice)//商品价格
                holder.itemView.mGoodsCountTv.text = "x${orderGoods.goodsCount}"//商品数量
                mTotalCount = orderGoods.goodsCount
            }
            else -> {
                holder.itemView.mSingleGoodsView.setVisable(false)
                holder.itemView.mMultiGoodsView.setVisable(true)

                holder.itemView.mMultiGoodsView.removeAllViews()
                for (subOrder in order.orderGoodsList) {
                    var imageview = ImageView(mContext)
                    var lp = ViewGroup.MarginLayoutParams(mContext.dip(60f), mContext.dip(60f))
                    lp.rightMargin = mContext.dip(15f)
                    imageview.layoutParams = lp
                    imageview.loadUrl(subOrder.goodsIcon)
                    holder.itemView.mMultiGoodsView.addView(imageview)
                    mTotalCount += subOrder.goodsCount
                }
            }
        }
        holder.itemView.mOrderInfoTv.text =
            "合计${mTotalCount}件商品，总价${YuanFenConverter.changeF2YWithUnit(order.totalPrice)}"
        holder.itemView.mPayBtn.onClick(View.OnClickListener {
            mBtnCallBack?.let {
                it.onPayBun(order)
            }
        })
        holder.itemView.mConfirmBtn.onClick(View.OnClickListener {
            mBtnCallBack?.let {
                it.ononfirmBtn(order)
            }
        })
        holder.itemView.mCancelBtn.onClick(View.OnClickListener {
            mBtnCallBack?.let {
                it.onCancelBtn(order)
            }
        })
    }

    /**
     * 设置不同状态下按钮显示与隐藏
     */
    private fun setOptVisable(b: Boolean, b1: Boolean, b2: Boolean, holder: ViewHolder) {
        holder.itemView.mConfirmBtn.setVisable(b)
        holder.itemView.mPayBtn.setVisable(b1)
        holder.itemView.mCancelBtn.setVisable(b2)
    }

    private var mBtnCallBack: BtnCallBack? = null
    fun setBtnCallBack(callBack: BtnCallBack) {
        mBtnCallBack = callBack
    }

    interface BtnCallBack {
        fun onPayBun(order: Order)
        fun ononfirmBtn(order: Order)
        fun onCancelBtn(order: Order)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}