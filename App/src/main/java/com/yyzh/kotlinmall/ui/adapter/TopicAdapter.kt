package com.yyzh.kotlinmall.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.kotlinmall.R
import kotlinx.android.synthetic.main.layout_topic_item.view.*


class TopicAdapter(private val context: Context, private val list: List<String>): PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rooView = LayoutInflater.from(this.context).inflate(R.layout.layout_topic_item, null)
        rooView.mTopicIv.loadUrl(list[position])
        container.addView(rooView)
        return rooView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `obj`: Any) {
        container.removeView(obj as View)
    }

}