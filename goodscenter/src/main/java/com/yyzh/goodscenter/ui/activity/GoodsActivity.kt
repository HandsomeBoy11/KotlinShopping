package com.yyzh.goodscenter.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.user.injection.component.DaggerGoodsComponent
import com.kotlin.user.injection.module.GoodsModule
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.persenter.GoodsPersenter
import com.yyzh.goodscenter.persenter.view.GoodsView
import com.yyzh.goodscenter.service.bean.Goods
import com.yyzh.goodscenter.ui.adapter.GoodsAdapter
import kotlinx.android.synthetic.main.activity_goods.*
import org.jetbrains.anko.startActivity

/**
 * 商品
 */
class GoodsActivity : BaseMvpActivity<GoodsPersenter>(), GoodsView,
    BGARefreshLayout.BGARefreshLayoutDelegate {

    private val goodsAdapter by lazy { GoodsAdapter(this) }
    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)
        initRefreshLayout()
        initView()
        loadData()
    }

    private fun initView() {
        var manager=GridLayoutManager(this,2)
        mGoodsRv.layoutManager=manager
        mGoodsRv.adapter=goodsAdapter
        goodsAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<Goods>{
            override fun onItemClick(item: Goods, position: Int) {
                startActivity<GoodsDetailActivity>(GoodsConstant.KEY_GOODS_ID to item.id)
            }

        })

    }

    /*
       初始化刷新视图
    */
    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build()
            .inject(this)
        mPersenter.mView = this
    }

    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            loadData()
            return true
        } else {
            return false
        }
    }

    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }

    private fun loadData() {
        if(intent.getIntExtra(GoodsConstant.KEY_SEARCH_GOODS_TYPE, -1)==1){//来自关键字搜索
            mPersenter.getGoodsByKeyWord(intent.getStringExtra(GoodsConstant.KEY_GOODS_KEYWORD), mCurrentPage)
        }else{
            val id = intent.getIntExtra(GoodsConstant.KEY_CATEGORY_ID, -1)
            mPersenter.getGoodsList(id, mCurrentPage)
        }

    }

    override fun onGetGoodsListResult(result: MutableList<Goods>?) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if(result!=null&&result.size>0){
            mMaxPage=result[0].maxPage
            if(mCurrentPage==1){
                goodsAdapter.setData(result)
            }else{
                goodsAdapter.dataList.addAll(result)
                goodsAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState=MultiStateView.VIEW_STATE_CONTENT
        }else{
            mMultiStateView.viewState=MultiStateView.VIEW_STATE_EMPTY
        }
    }
}