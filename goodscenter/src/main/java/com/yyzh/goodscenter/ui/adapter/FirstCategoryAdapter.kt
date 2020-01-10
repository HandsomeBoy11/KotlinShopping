package com.yyzh.goodscenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.service.bean.Category
import kotlinx.android.synthetic.main.first_category_item.view.*

class FirstCategoryAdapter(mContext: Context) :
    BaseRecyclerViewAdapter<Category, FirstCategoryAdapter.ViewHolder>(mContext) {

    private val inflater: LayoutInflater by lazy { LayoutInflater.from(mContext) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = inflater.inflate(R.layout.first_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val category = dataList.get(position)
        holder.itemView.mTopCategoryNameTv.text=category.categoryName
        holder.itemView.mTopCategoryNameTv.isSelected=category.isSelected
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}