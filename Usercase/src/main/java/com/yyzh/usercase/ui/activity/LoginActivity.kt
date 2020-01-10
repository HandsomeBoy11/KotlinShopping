package com.yyzh.usercase.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.yyzh.baselibrary.ext.enable
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import com.yyzh.baselibrary.utlis.MD5Util
import com.yyzh.providerlibrary.PushProvider
import com.yyzh.providerlibrary.event.JumpToPosition
import com.yyzh.providerlibrary.router.RouterPath
import com.yyzh.usercase.R
import com.yyzh.usercase.persenter.LoginPersenter
import com.yyzh.usercase.service.bean.UserInfo
import com.yyzh.usercase.utils.UserPrefsUtils
import com.yyzh.usercase.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 用户登录
 */
@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity:BaseMvpActivity<LoginPersenter>(),LoginView, View.OnClickListener {

    @Autowired(name = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
    @JvmField
    var mPushProvider: PushProvider? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        mLoginBtn.enable(mMobileEt) {isBtnEnable()}
        mLoginBtn.enable(mPwdEt) {isBtnEnable()}
        mHeaderBar.getRightView().onClick(this)
        mLoginBtn.onClick(this)
        mForgetPwdTv.onClick(this)
        mHeaderBar.getLeftView().onClick(this)

    }


    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPersenter.mView = this
    }
    override fun errorResult(e: String) {
        toast(e)

    }

    /**
     * 登录成功
     */
    override fun loginResult(result: UserInfo) {
        toast("登录成功")
        UserPrefsUtils.putUserInfo(result)
        finish()

    }
    override fun onClick(p0: View?) {
        when(p0?.id){
//            R.id.mLeftIv->Bus.send()
            R.id.mRightTv->{
                startActivity<RegistActivity>()
            }
           R.id.mLoginBtn->{
               if(mPushProvider==null){
                   toast("mPushProvider为null")
                   if(mPushProvider?.getPushId()==null){
                       toast("getPushId为null")
                   }
               }
               mPersenter.login(mMobileEt.text.toString(),MD5Util.encode(mPwdEt.text.toString()),mPushProvider?.getPushId()?:"")
           }
        }
    }
    /*
     判断按钮是否可用
  */
    private fun isBtnEnable():Boolean{
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()

    }
}