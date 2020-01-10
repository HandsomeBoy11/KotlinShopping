package com.yyzh.goodscenter.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.viewpager.widget.ViewPager
import com.ashokvarma.bottomnavigation.utils.Utils.dp2px
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.gson.Gson
import com.kotlin.user.injection.component.DaggerGoodsDetailComponent
import com.kotlin.user.injection.module.GoodsDetailModule
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.baselibrary.ui.fragment.BaseMvpFragment
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.baselibrary.weights.BannerImageLoader
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.event.AddCartEvent
import com.yyzh.goodscenter.event.GoodsDetailEvent
import com.yyzh.goodscenter.event.SkuChangedEvent
import com.yyzh.goodscenter.event.UpdateCartSizeEvent
import com.yyzh.goodscenter.persenter.GoodsDetailPersenter
import com.yyzh.goodscenter.persenter.view.GoodsDetailView
import com.yyzh.goodscenter.service.bean.Goods
import com.yyzh.goodscenter.ui.activity.GoodsDetailActivity
import com.yyzh.goodscenter.weight.GoodsSkuPopView
import com.yyzh.goodscenter.weight.dialog.SkuDialog
import kotlinx.android.synthetic.main.fragment_goods_detail1.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast

class GoodsDetailTabOneFragment : BaseMvpFragment<GoodsDetailPersenter>(), GoodsDetailView, View.OnClickListener {


    private lateinit var mSkuPop: GoodsSkuPopView
    private var mCurGoods: Goods? = null
    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_goods_detail1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        val goodsId = activity?.intent?.getIntExtra(GoodsConstant.KEY_GOODS_ID, 0) ?: 0
        initAnim()
        initSkuPop()
        loadData(goodsId)
        initObserve()
    }


    private fun initView() {
        mGoodsDetailBanner.find<ViewPager>(R.id.bannerViewPager).pageMargin = dp2px(context, 8f)
        mGoodsDetailBanner.setImageLoader(BannerImageLoader())
        mGoodsDetailBanner.setBannerAnimation(Transformer.Default)
        mGoodsDetailBanner.setDelayTime(2000)
        //设置指示器位置（当banner模式中有指示器时）
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mSkuView.onClick(this)
    }

    /**
     * 监听
     */
    private fun initObserve() {

        Bus.observe<SkuChangedEvent>().subscribe {
            val selectSku = mSkuPop.getSelectSku()
            val selectCount = mSkuPop.getSelectCount()
            mSkuSelectedTv.text = "$selectSku ,$selectCount 件"
            Log.e("tag", mSkuSelectedTv.text.toString().trim())
        }.registerInBus(this)

        //添加购物车
        Bus.observe<AddCartEvent>().subscribe {
            doAddCart()
        }.registerInBus(this)

    }

    private fun doAddCart() {
        mCurGoods?.let {
            mPresenter.addCart(
                it.id,
                it.goodsDesc,
                it.goodsDefaultIcon,
                it.goodsDefaultPrice,
                mSkuPop.getSelectCount(),
                mSkuPop.getSelectSku()
            )
        }
    }

    /*
       初始化sku弹层
    */
    private fun initSkuPop() {
        mSkuPop = GoodsSkuPopView(activity!!)
        mSkuPop.setOnDismissListener {
            (activity as BaseActivity).contentView!!.startAnimation(mAnimationEnd)
        }
    }

    /*
     初始化缩放动画
  */
    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
            1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
            0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mSkuView -> {//sku选择
                mSkuPop.showAtLocation(
                    (activity as GoodsDetailActivity).contentView
                    , Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL,
                    0, 0
                )
                (activity as BaseActivity).contentView!!.startAnimation(mAnimationStart)
            }
        }
    }

    private fun loadData(goodsId: Int) {
        mPresenter.getGoodsList(goodsId)
    }

    override fun injectComponent() {
        DaggerGoodsDetailComponent.builder().activityComponent(mActivityComponent)
            .goodsDetailModule(GoodsDetailModule()).build().inject(this)
        mPresenter.mView = this
    }

    /**
     * 获取商品详情结果
     */
    override fun onGoodsDetailResult(result: Goods) {
        mCurGoods = result
        mGoodsDetailBanner.setImages(result.goodsBanner.split(","))
        mGoodsDetailBanner.start()

        mGoodsDescTv.text = result.goodsDesc
        mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(result.goodsDefaultPrice)
        mSkuSelectedTv.text = result.goodsDefaultSku

        Bus.send(GoodsDetailEvent(result.goodsDetailOne, result.goodsDetailTwo))//给详情2页传递数据

        loadPopData(result)
    }

    override fun onAddCartResult(i: Int) {
        toast("添加购物车成功")
        Bus.send(UpdateCartSizeEvent())
    }

    /*
          加载SKU数据
       */
    private fun loadPopData(result: Goods) {
        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)

        mSkuPop.setSkuData(result.goodsSku)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Bus.unregister(this)
    }
}