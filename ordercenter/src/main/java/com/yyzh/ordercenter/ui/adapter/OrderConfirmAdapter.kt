package com.yyzh.ordercenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.order.data.protocol.OrderGoods
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.ordercenter.R
import kotlinx.android.synthetic.main.order_confirm_item.view.*

class OrderConfirmAdapter(mContext: Context):BaseRecyclerViewAdapter<OrderGoods, OrderConfirmAdapter.ViewHolder> (mContext){
    val inflater by lazy { LayoutInflater.from(mContext) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=inflater.inflate(R.layout.order_confirm_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mGoodsIconIv.loadUrl(model.goodsIcon)
        holder.itemView.mGoodsDescTv.text = model.goodsDesc
        holder.itemView.mGoodsSkuTv.text = model.goodsSku
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.goodsPrice)
        holder.itemView.mGoodsCountTv.text = "x${model.goodsCount}"
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view)
}