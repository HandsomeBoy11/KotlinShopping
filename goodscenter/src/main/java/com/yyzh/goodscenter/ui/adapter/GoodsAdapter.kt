package com.yyzh.goodscenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.service.bean.Goods
import kotlinx.android.synthetic.main.goods_item.view.*

class GoodsAdapter(mContext:Context):BaseRecyclerViewAdapter<Goods,GoodsAdapter.ViewHolder>(mContext) {
    private val inflater by lazy { LayoutInflater.from(mContext) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view= inflater.inflate(R.layout.goods_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //商品图标
        holder.itemView.mGoodsIconIv.loadUrl(model.goodsDefaultIcon)
        //商品描述
        holder.itemView.mGoodsDescTv.text = model.goodsDesc
        //商品价格
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.goodsDefaultPrice)
        //商品销量及库存
        holder.itemView.mGoodsSalesStockTv.text = "销量${model.goodsSalesCount}件     库存${model.goodsStockCount}"
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}