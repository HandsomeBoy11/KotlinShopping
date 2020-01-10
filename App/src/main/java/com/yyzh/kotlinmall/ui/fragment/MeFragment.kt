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
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.fragment.BaseFragment
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import com.yyzh.baselibrary.weights.BannerImageLoader
import com.yyzh.kotlinmall.R
import com.yyzh.kotlinmall.common.*
import com.yyzh.kotlinmall.ui.activity.SettingActivity
import com.yyzh.kotlinmall.ui.adapter.HomeDiscountAdapter
import com.yyzh.kotlinmall.ui.adapter.TopicAdapter
import com.yyzh.ordercenter.common.OrderConstant
import com.yyzh.ordercenter.common.OrderStatus
import com.yyzh.ordercenter.ui.activity.OrderActivity
import com.yyzh.ordercenter.ui.activity.OrderAddressActivity
import com.yyzh.providerlibrary.common.ProviderConstant.Companion.KEY_SP_USER_ICON
import com.yyzh.providerlibrary.common.ProviderConstant.Companion.KEY_SP_USER_NAME
import com.yyzh.providerlibrary.common.afterLogin
import com.yyzh.providerlibrary.common.isLogined
import com.yyzh.usercase.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import me.crosswall.lib.coverflow.CoverFlow
import org.jetbrains.anko.support.v4.startActivity

/**
 * 我的
 */

class MeFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    override fun onStart() {
        super.onStart()
        if(isLogined()){
            mUserNameTv.text=AppPrefsUtils.getString(KEY_SP_USER_NAME)
            val headUrl = AppPrefsUtils.getString(KEY_SP_USER_ICON)
            if(headUrl.isNotEmpty()){
                mUserIconIv.loadUrl(headUrl)
            }else{
                mUserIconIv.setImageResource(R.drawable.icon_default_user)
            }

        }else{
            mUserNameTv.text=getString(R.string.un_login_text)
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
        }
    }

    private fun initView() {
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)
        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
        mAllOrderTv.onClick(this)
        mAddressTv.onClick(this)
        mShareTv.onClick(this)
        mSettingTv.onClick(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.mUserIconIv, R.id.mUserNameTv->{
                afterLogin {
                    startActivity<UserInfoActivity>()
                }
            }
            R.id.mWaitPayOrderTv->{
                afterLogin {
                    startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
                }
            }
            R.id.mWaitConfirmOrderTv->{
                afterLogin {
                    startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
                }
            }
            R.id.mCompleteOrderTv->{
                afterLogin {
                    startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
                }
            }
            R.id.mAllOrderTv->{
                afterLogin {
                    startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_ALL)
                }
            }
            R.id.mAddressTv->{
                startActivity<OrderAddressActivity>()
            }
            R.id.mShareTv->{}
            R.id.mSettingTv->{
                startActivity<SettingActivity>()
            }
        }

    }
}