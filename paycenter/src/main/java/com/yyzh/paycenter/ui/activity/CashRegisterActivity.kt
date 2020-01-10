package com.yyzh.paycenter.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.paycenter.R
import com.yyzh.paycenter.injection.component.DaggerPayComponent
import com.yyzh.paycenter.injection.module.PayModule
import com.yyzh.paycenter.persenter.PayPresenter
import com.yyzh.paycenter.persenter.view.PayView
import com.yyzh.providerlibrary.common.ProviderConstant
import com.yyzh.providerlibrary.router.RouterPath
import kotlinx.android.synthetic.main.activity_cash_register.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

@Route(path = RouterPath.PaySDK.PATH_PAY)
class CashRegisterActivity : BaseMvpActivity<PayPresenter>(), PayView,View.OnClickListener {

    private var mSignResult:String?=null
    private var orderId:Int?=-1
    private var totalPrice:Long?=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)
        ARouter.getInstance().inject(this)
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        initView()
        initData()
    }

    private fun initData() {
         orderId=intent.getIntExtra(ProviderConstant.KEY_ORDER_ID,-1)
         totalPrice=intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE,0)


    }

    private fun initView() {
        mPayBtn.onClick(this)
        mAlipayTypeTv.onClick(this)
        mWeixinTypeTv.onClick(this)
        mBankCardTypeTv.onClick(this)
        mAlipayTypeTv.performClick()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.mPayBtn->{
                if(orderId!=-1){
                    mPersenter.getPaySign(orderId!!,totalPrice!!)
                }
            }
            R.id.mAlipayTypeTv->{switchPayType(b = true, b1 = false, b2 = false)}
            R.id.mWeixinTypeTv->{switchPayType(b = false, b1 = true, b2 = false)}
            R.id.mBankCardTypeTv->{switchPayType(b = false, b1 = false, b2 = true)}
        }

    }

    private fun switchPayType(b: Boolean, b1: Boolean, b2: Boolean) {
        mAlipayTypeTv.isSelected=b
        mWeixinTypeTv.isSelected=b1
        mBankCardTypeTv.isSelected=b2
    }

    private fun getPayType():Int{
        if(mAlipayTypeTv.isSelected){
            return 1
        }else if(mWeixinTypeTv.isSelected){
            return 2
        }else if(mBankCardTypeTv.isSelected){
            return 3
        }else{
            return 1
        }
    }

    override fun injectComponent() {
        DaggerPayComponent.builder().activityComponent(mActivityComponent).payModule(PayModule()).build().inject(this)
        mPersenter.mView = this
    }

    override fun onGetSignResult(result: String) {
        mSignResult=result
        result.let {
            doAsync {
                var resultMap=PayTask(this@CashRegisterActivity).payV2(it,true)
                uiThread {
                    if (resultMap["resultStatus"].equals("9000")){
                        mPersenter.payOrder(orderId!!)
                    }else{
                        toast("支付失败${resultMap["memo"]}")
                    }
                }
            }
        }
    }

    override fun onPayOrderResult(result: Boolean) {
        toast("支付成功")
        finish()
    }
}