package com.yyzh.baselibrary.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trello.rxlifecycle.components.RxActivity
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.yyzh.baselibrary.R
import com.yyzh.baselibrary.common.AppManager

open class BaseActivity:RxAppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置为竖屏模式
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        setTheme(R.style.ActivityTheme)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        AppManager.instance.finishActivity(this)
        super.onDestroy()
    }
}