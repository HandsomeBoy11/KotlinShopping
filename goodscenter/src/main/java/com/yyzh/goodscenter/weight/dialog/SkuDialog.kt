package com.yyzh.goodscenter.weight.dialog

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.FragmentManager
import com.eightbitlab.rxbus.Bus
import com.google.gson.Gson
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.utlis.YuanFenConverter
import com.yyzh.baselibrary.weights.DefaultTextWatcher
import com.yyzh.goodscenter.R
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.event.SkuChangedEvent
import com.yyzh.goodscenter.getEditText
import com.yyzh.goodscenter.service.bean.Goods
import com.yyzh.goodscenter.service.bean.GoodsSku
import com.yyzh.goodscenter.weight.SkuView
import kotlinx.android.synthetic.main.dialog_sku.*
import kotlinx.android.synthetic.main.dialog_sku.view.*

class SkuDialog : BaseDialog(), View.OnClickListener {


    companion object {
        const val TIME: Long = 300
    }

    lateinit var mCurGoods: List<GoodsSku>
    private val mSkuViewList = arrayListOf<SkuView>()

    private var mHeightPixels: Float = 0f
    override fun init() {
        initView()
        initScreen()
        openAnim()
        initData()
    }

    private fun initData() {
        var goods = arguments?.getParcelable<Goods>("goods")
        goods?.let {
            setGoodsIcon(goods.goodsDefaultIcon)
            setGoodsPrice(goods.goodsDefaultPrice)
            setGoodsCode(goods.goodsCode)
            setSkuData(goods.goodsSku)
        }
    }

    private fun initView() {
        mView.mCloseIv.onClick(this)
        mView.mSkuCountBtn.setCurrentNumber(1)
        mView.mSkuCountBtn.getEditText().addTextChangedListener(object :DefaultTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                Bus.send(SkuChangedEvent())
            }
        })
        this.dialog!!.setOnDismissListener {
            closeAnim()

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_sku
    }

    fun showBottomDialog(manager: FragmentManager, tag: String) {
        super.show(manager, tag)
    }

    /*
          设置商品图标
       */
    fun setGoodsIcon(text: String) {
        mView.mGoodsIconIv.loadUrl(text)
    }

    /*
        设置商品价格
     */
    fun setGoodsPrice(text: Long) {
        mView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(text)
    }

    /*
        设置商品编号
     */
    fun setGoodsCode(text: String) {
        mView.mGoodsCodeTv.text = "商品编号:$text"
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun setAlpha(): Float = 0.3f
    override fun setGravity(): Int = Gravity.BOTTOM
    private fun initScreen() {
        val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        // 获取屏幕的高度
        mHeightPixels = dm.heightPixels.toFloat()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mCloseIv -> {
                closeAnim()
            }
        }

    }

    /**
     * 获取被选择的sku
     */
    fun getSelectData(): String {
        var str = ""
        for (skuView in mSkuViewList) {
            str += skuView.getSkuInfo().split(GoodsConstant.SKU_SEPARATOR)[1]+GoodsConstant.SKU_SEPARATOR
        }
        return str.take(str.length - 1)
    }

    fun openAnim() {
        ObjectAnimator.ofFloat()
        val objectAnimator = ObjectAnimator.ofFloat(mllBg, "translationY", mHeightPixels, 0f)
        objectAnimator.duration = TIME
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }

    fun closeAnim() {
        val objectAnimator = ObjectAnimator.ofFloat(mllBg, "translationY", 0f, mHeightPixels)
        objectAnimator.duration = TIME
        objectAnimator.interpolator = AccelerateInterpolator()
        objectAnimator.start()
        objectAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (mBaseDialogListener != null) {
                    mBaseDialogListener!!.dismiss()
                }
                dismiss()
            }
        })
    }

    private var mBaseDialogListener: BaseDialogListener? = null
    fun setBaseDialogListener(baseDialogListener: BaseDialogListener) {
        mBaseDialogListener = baseDialogListener
    }

    fun setSkuData(goodsSku: List<GoodsSku>) {
        mView.mSkuView.removeAllViews()
        for (goodsSku in goodsSku) {
            var skuView = SkuView(activity)
            skuView.setSkuData(goodsSku)
            mSkuViewList.add(skuView)
            mView.mSkuView.addView(skuView)
        }
    }

    fun getSelectCount(): String {
       return  mView.mSkuCountBtn.getEditText().text.toString().trim()
    }

    interface BaseDialogListener {
        fun dismiss()
    }
}