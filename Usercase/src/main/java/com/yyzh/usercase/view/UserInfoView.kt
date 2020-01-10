package com.yyzh.usercase.view

import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.usercase.service.bean.UserInfo

interface UserInfoView :BaseView{
    fun onEditUserInfoResult(result: UserInfo)
}