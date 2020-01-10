package com.yyzh.goodscenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eightbitlab.rxbus.Bus
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.baselibrary.weights.DefaultTextWatcher
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.event.CartAllCheckedEvent
import com.yyzh.goodscenter.event.UpdateTotalPriceEvent
import com.yyzh.goodscenter.getEditText
import com.yyzh.goodscenter.service.bean.CartGoods
import kotlinx.android.synthetic.main.cart_item.view.*

class CartAdapter(mContext:Context):BaseRecyclerViewAdapter<CartGoods,CartAdapter.ViewHolder>(mContext) {
    private var inflater:LayoutInflater = LayoutInflater.from(mContext)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=inflater.inflate(R.layout.cart_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val cartGoods = dataList[position]
        holder.itemView.mCheckedCb.isChecked=cartGoods.isSelected
        holder.itemView.mGoodsIconIv.loadUrl(cartGoods.goodsIcon)
        holder.itemView.mGoodsDescTv.text=cartGoods.goodsDesc
        holder.itemView.mGoodsSkuTv.text=cartGoods.goodsSku
        holder.itemView.mGoodsCountBtn.setCurrentNumber(cartGoods.goodsCount)
        holder.itemView.mGoodsPriceTv.text= YuanFenConverter.changeF2YWithUnit(cartGoods.goodsPrice)
        holder.itemView.mCheckedCb.onClick {
            cartGoods.isSelected=holder.itemView.mCheckedCb.isChecked
            var isAllSelect=dataList.all { it.isSelected }
            Bus.send(CartAllCheckedEvent(isAllSelect))
            notifyDataSetChanged()
        }
        holder.itemView.mGoodsCountBtn.getEditText().addTextChangedListener(object :DefaultTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                var inputCount:Int=1
               s?.let {
                   if(it.isNotEmpty()){
                       inputCount=it.toString().toInt()
                   }
               }
                cartGoods.goodsCount=inputCount
                Bus.send(UpdateTotalPriceEvent())
            }
        })


    }

    class ViewHolder(view : View):RecyclerView.ViewHolder(view)
}