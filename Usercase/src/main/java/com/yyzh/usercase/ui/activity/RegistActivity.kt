package com.yyzh.usercase.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.yyzh.baselibrary.ext.enable
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.baselibrary.utlis.MD5Util
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.usercase.R
import com.yyzh.usercase.persenter.RegistPersenter
import com.yyzh.usercase.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegistActivity : BaseMvpActivity<RegistPersenter>(),RegisterView, View.OnClickListener {


    override fun errorResult(e: String) {
        Log.e("one==",e)
        toast(e)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        mRegisterBtn.enable(mMobileEt) {isBtnEnable()}
        mRegisterBtn.enable(mVerifyCodeEt) {isBtnEnable()}
        mRegisterBtn.enable(mPwdEt) {isBtnEnable()}
        mRegisterBtn.enable(mPwdConfirmEt) {isBtnEnable()}
        mVerifyCodeBtn.onClick(this)
        mRegisterBtn.onClick(this)
    }


    /*
       Dagger注册
    */
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPersenter.mView = this
    }

    override fun registResult(result: Boolean) {
        if(result){
            toast("注册成功")
            finish()
        }else{
            toast("注册失败")
        }
    }

    /**
     * 点击事件
     */
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.mVerifyCodeBtn->{//获取验证码
                mVerifyCodeBtn.requestSendVerifyNumber()
                toast("发送验证成功")
            }
            R.id.mRegisterBtn->{//注册
                mPersenter.regist(mMobileEt.text.toString(),mVerifyCodeEt.text.toString(),MD5Util.encode(mPwdEt.text.toString()))

            }
        }
    }
    /*
       判断按钮是否可用
    */
    private fun isBtnEnable():Boolean{
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()&&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}
