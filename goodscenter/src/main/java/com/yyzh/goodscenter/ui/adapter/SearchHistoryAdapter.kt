package com.yyzh.goodscenter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yyzh.goodscenter.R
import kotlinx.android.synthetic.main.search_history.view.*

class SearchHistoryAdapter(mContext: Context):BaseRecyclerViewAdapter<String, SearchHistoryAdapter.ViewHolder> (mContext){
    private val inflater by lazy { LayoutInflater.from(mContext) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=inflater.inflate(R.layout.search_history,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.mSearchHistoryTv.text=dataList[position]
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}