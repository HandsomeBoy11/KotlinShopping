package com.yyzh.usercase.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.yyzh.baselibrary.ext.loadUrl
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import com.yyzh.providerlibrary.common.ProviderConstant.Companion.KEY_SP_USER_GENDER
import com.yyzh.providerlibrary.common.ProviderConstant.Companion.KEY_SP_USER_ICON
import com.yyzh.providerlibrary.common.ProviderConstant.Companion.KEY_SP_USER_MOBILE
import com.yyzh.providerlibrary.common.ProviderConstant.Companion.KEY_SP_USER_NAME
import com.yyzh.providerlibrary.common.ProviderConstant.Companion.KEY_SP_USER_SIGN
import com.yyzh.usercase.R
import com.yyzh.usercase.persenter.EditUserInfoPersenter
import com.yyzh.usercase.service.bean.UserInfo
import com.yyzh.usercase.utils.UserPrefsUtils
import com.yyzh.usercase.view.UserInfoView
import kotlinx.android.synthetic.main.activity_userinfo.*
import org.jetbrains.anko.toast

class UserInfoActivity : BaseMvpActivity<EditUserInfoPersenter>(), UserInfoView , View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)
        initView()
        initData()
    }

    private fun initData() {
        val userName = AppPrefsUtils.getString(KEY_SP_USER_NAME)
        val userIcon = AppPrefsUtils.getString(KEY_SP_USER_ICON)
        val userGender = AppPrefsUtils.getString(KEY_SP_USER_GENDER)
        val userMobile = AppPrefsUtils.getString(KEY_SP_USER_MOBILE)
        val userSign = AppPrefsUtils.getString(KEY_SP_USER_SIGN)

        if (userIcon.isNotEmpty()) {
            mUserIconIv.loadUrl(userIcon)
        } else {
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
        }
        mUserNameEt.setText(userName)
        if (userGender == "0") {//0是男
            mGenderMaleRb.isChecked = true
        } else {
            mGenderFemaleRb.isChecked = false
        }

        mUserMobileTv.text = userMobile
        mUserSignEt.setText(userSign)

    }

    private fun initView() {
        mHeaderBar.getRightView().onClick(this)
        mUserIconIv.onClick(this)
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.mRightTv->{
                mPersenter.editUserInfo("",
                    mUserNameEt.text?.toString()?:"",
                    if(mGenderMaleRb.isChecked)"0" else "1",
                    mUserSignEt.text?.toString()?:""
                    )
            }
            R.id.mUserIconIv->{

            }
        }
    }

    override fun onEditUserInfoResult(result: UserInfo) {
        UserPrefsUtils.putUserInfo(result)
        toast("修改用户信息成功")
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build()
            .inject(this)
        mPersenter.mView = this
    }

}