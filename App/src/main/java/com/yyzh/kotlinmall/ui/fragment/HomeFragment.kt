package com.yyzh.kotlinmall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.ashokvarma.bottomnavigation.utils.Utils.dp2px
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.fragment.BaseFragment
import com.yyzh.baselibrary.weights.BannerImageLoader
import com.yyzh.goodscenter.ui.activity.SearchGoodsActivity
import com.yyzh.kotlinmall.R
import com.yyzh.kotlinmall.common.*
import com.yyzh.kotlinmall.ui.adapter.HomeDiscountAdapter
import com.yyzh.kotlinmall.ui.adapter.TopicAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import me.crosswall.lib.coverflow.CoverFlow
import org.jetbrains.anko.support.v4.startActivity

/**
 * 首页
 */

class HomeFragment : BaseFragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        mSearchEt.onClick(this)
        mScanIv.onClick(this)
        inintBanner()
        initNews()
        initDiscountGoods()
        initTheme()
    }

    private fun initTheme() {
        mTopicPager.adapter=TopicAdapter(activity!!, listOf(HOME_TOPIC_ONE, HOME_TOPIC_THREE, HOME_TOPIC_FOUR, HOME_TOPIC_FIVE))
        mTopicPager.currentItem=1
        mTopicPager.offscreenPageLimit=5
        CoverFlow.Builder()
            .with(mTopicPager)
            .pagerMargin(-30f)
            .scale(0.3f)
            .spaceSize(0f)
            .rotationY(0f)
            .build()
        mTopicContainer.setPageItemClickListener { _, i ->
            toast(i.toString())
        }
    }

    private fun initDiscountGoods() {
        var manager=LinearLayoutManager(activity)
        manager.orientation=LinearLayoutManager.HORIZONTAL
        mHomeDiscountRv.layoutManager=manager
        var adapter= HomeDiscountAdapter(activity!!)
        mHomeDiscountRv.adapter=adapter
        adapter.setData(mutableListOf(HOME_DISCOUNT_ONE, HOME_DISCOUNT_TWO, HOME_DISCOUNT_THREE, HOME_DISCOUNT_FOUR, HOME_DISCOUNT_FIVE))

        adapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<String>{
            override fun onItemClick(item: String, position: Int) {
                toast(position.toString())
            }

        })

    }

    private fun initNews() {
        //滚动通知
        mNewsFlipperView.setData(arrayOf("夏日炎炎，第一波福利还有30秒到达战场", "新用户立领1000元优惠券"))
    }

    private fun inintBanner() {
        mHomeBanner.find<ViewPager>(R.id.bannerViewPager).pageMargin = dp2px(activity, 5f)
        mHomeBanner.apply {
            setImageLoader(BannerImageLoader())
            setImages(listOf(HOME_BANNER_ONE, HOME_BANNER_TWO, HOME_BANNER_FOUR))
            setBannerAnimation(Transformer.Default)
            setDelayTime(2000)
            //设置指示器位置（当banner模式中有指示器时）
            setIndicatorGravity(BannerConfig.RIGHT)
            //banner设置方法全部调用完毕时最后调用
            start()
            setOnBannerListener { position ->
                toast(position.toString())
            }
        }
    }

    companion object {
        fun getInstance(index: Int): HomeFragment {
            var fragment = HomeFragment()
            var bundle = Bundle()
            bundle.putInt("index", index)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mSearchEt -> {//搜索
                startActivity< SearchGoodsActivity>()
            }
            R.id.mScanIv -> {//扫描
                toast("扫描")
            }
        }

    }
}