package com.yyzh.goodscenter.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ext.setVisable
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.ui.adapter.SearchHistoryAdapter
import kotlinx.android.synthetic.main.activity_search_goods.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchGoodsActivity : BaseActivity(), View.OnClickListener {

    private val adapter by lazy { SearchHistoryAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_goods)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        val set = AppPrefsUtils.getStringSet(GoodsConstant.SP_SEARCH_HISTORY)
        mNoDataTv.setVisable(set.isEmpty())
        mDataView.setVisable(set.isNotEmpty())
        if(set.isNotEmpty()){
            var list= mutableListOf<String>()
            list.addAll(set)
            adapter.setData(list)
        }
    }

    private fun initView() {
        mLeftIv.onClick(this)
        mClearBtn.onClick(this)
        mSearchTv.onClick(this)
        var manager=LinearLayoutManager(this)
        mSearchHistoryRv.layoutManager=manager
        mSearchHistoryRv.adapter=adapter
        adapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<String>{
            override fun onItemClick(item: String, position: Int) {
                enterSearch(item)
            }

        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mLeftIv -> finish()
            R.id.mClearBtn -> {
                AppPrefsUtils.remove(GoodsConstant.SP_SEARCH_HISTORY)
                loadData()
            }
            R.id.mSearchTv ->doSearch()
        }
    }

    private fun doSearch() {
        val keyWord = mKeywordEt.text.toString().trim()
        if(keyWord.isEmpty()){
            toast("请输入搜索内容")
        }else{
            AppPrefsUtils.putStringSet(GoodsConstant.SP_SEARCH_HISTORY, setOf(keyWord))//本地存储
            enterSearch(keyWord)
        }
    }

    private fun enterSearch(keyWord: String) {
        startActivity<GoodsActivity>(GoodsConstant.KEY_SEARCH_GOODS_TYPE to GoodsConstant.SEARCH_GOODS_TYPE_KEYWORD,
            GoodsConstant.KEY_GOODS_KEYWORD to keyWord)
    }
}