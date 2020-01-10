package com.yyzh.goodscenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.user.injection.component.DaggerCategoryComponent
import com.kotlin.user.injection.module.CategoryModule
import com.yyzh.baselibrary.ui.fragment.BaseMvpFragment
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.persenter.CategoryPersenter
import com.yyzh.goodscenter.persenter.view.CategoryView
import com.yyzh.goodscenter.service.bean.Category
import com.yyzh.goodscenter.ui.activity.GoodsActivity
import com.yyzh.goodscenter.ui.adapter.FirstCategoryAdapter
import com.yyzh.goodscenter.ui.adapter.SecondCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * 分类
 */
class CategoryFragment:BaseMvpFragment<CategoryPersenter>(),CategoryView {

    private val firstAdapter by lazy { FirstCategoryAdapter(context!!) }
    private val secondAdapter by lazy { SecondCategoryAdapter(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_category,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getCategoryData()
    }

    private fun initView() {
        var manager=LinearLayoutManager(context)
        mTopCategoryRv.layoutManager=manager
        mTopCategoryRv.adapter=firstAdapter
        firstAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<Category>{
            override fun onItemClick(item: Category, position: Int) {
                for(category in firstAdapter.dataList){
                    category.isSelected=item.id==category.id
                }
                firstAdapter.notifyDataSetChanged()
                mPresenter.getCategory(item.id)
            }

        })

        var gridManager=GridLayoutManager(context,3)
        mSecondCategoryRv.layoutManager=gridManager
        mSecondCategoryRv.adapter=secondAdapter
        secondAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<Category>{
            override fun onItemClick(item: Category, position: Int) {
                startActivity<GoodsActivity>(GoodsConstant.KEY_CATEGORY_ID to item.id)
            }
        })
    }

    private fun getCategoryData(parentId:Int=0) {
        mPresenter.getCategory(parentId)
    }

    override fun injectComponent() {
        DaggerCategoryComponent.builder().activityComponent(mActivityComponent).categoryModule(CategoryModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        if(result!=null&&result.size>0){
            if(result[0].parentId==0){
                result[0].isSelected=true
                firstAdapter.setData(result)
                mPresenter.getCategory(result[0].id)
            }else{
                secondAdapter.setData(result)
                mTopCategoryIv.visibility=View.VISIBLE
                mCategoryTitleTv.visibility=View.VISIBLE
                mMultiStateView.viewState= MultiStateView.VIEW_STATE_CONTENT
            }

        }else{
            mTopCategoryIv.visibility=View.INVISIBLE
            mCategoryTitleTv.visibility=View.INVISIBLE
            mMultiStateView.viewState= MultiStateView.VIEW_STATE_EMPTY

        }
    }
}