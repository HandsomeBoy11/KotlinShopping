package com.yyzh.message.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.message.R
import com.yyzh.message.service.bean.Message
import kotlinx.android.synthetic.main.message_item.view.*

class MessageAdapter(mContext: Context) : BaseRecyclerViewAdapter<Message, MessageAdapter.ViewHolder>(mContext) {
    private val inflater: LayoutInflater by lazy { LayoutInflater.from(mContext) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = inflater.inflate(R.layout.message_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val message = dataList[position]

        holder.itemView.mMsgIconIv.loadUrl(message.msgIcon)
        holder.itemView.mMsgTitleTv.text = message.msgTitle
        holder.itemView.mMsgContentTv.text = message.msgContent
        holder.itemView.mMsgTimeTv.text = message.msgTime

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}