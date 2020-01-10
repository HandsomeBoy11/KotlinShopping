package com.yyzh.kotlinmall.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yyzh.baselibrary.utlis.GlideUtils
import com.yyzh.kotlinmall.R
import kotlinx.android.synthetic.main.home_discount_item.view.*

class HomeDiscountAdapter(mContext:Context):BaseRecyclerViewAdapter<String, HomeDiscountAdapter.ViewHolder>(mContext) {
    private var inflater: LayoutInflater? = null

    init {
         inflater = LayoutInflater.from(mContext)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=inflater?.inflate(R.layout.home_discount_item, parent, false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        //加载图片
        GlideUtils.loadUrlImage(mContext,dataList[position],holder.itemView.mGoodsIconIv)
        //静态假数据
        holder.itemView.mDiscountAfterTv.text = "￥123.00"
        holder.itemView.mDiscountBeforeTv.text = "￥1000.00"

    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        init {
            //设置TextView样式
            view.mDiscountBeforeTv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            view.mDiscountBeforeTv.paint.isAntiAlias = true
        }
    }
}