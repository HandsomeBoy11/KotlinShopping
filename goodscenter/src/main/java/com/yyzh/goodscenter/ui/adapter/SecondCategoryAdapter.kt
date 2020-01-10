package com.yyzh.goodscenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.service.bean.Category
import kotlinx.android.synthetic.main.seconed_category_item.view.*

class SecondCategoryAdapter(mContext:Context):BaseRecyclerViewAdapter<Category, SecondCategoryAdapter.ViewHolder>(mContext) {
    private val inflater by lazy { LayoutInflater.from(mContext) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =inflater.inflate(R.layout.seconed_category_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val category = dataList[position]
        holder.itemView.mCategoryIconIv.loadUrl(category.categoryIcon)
        holder.itemView.mSecondCategoryNameTv.text=category.categoryName

    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}