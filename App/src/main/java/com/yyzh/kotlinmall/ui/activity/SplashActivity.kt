package com.yyzh.kotlinmall.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.kotlinmall.R
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class SplashActivity:BaseActivity() {
    private var mCount:Int=6
    val mHandler by lazy { Handler() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        initView()
        doTimer()
    }

    private fun initView() {
        countTv.onClick(View.OnClickListener {
            mHandler.removeCallbacks(countDown)
            startActivity<MainActivity>()
        })
    }

    private fun doTimer() {
        mHandler.postDelayed(countDown,0)
    }

    /*
      倒计时
   */
    private val countDown = object : Runnable {
        override fun run() {
            countTv.text =  "${mCount.toString()}s "

            if (mCount > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                startActivity<MainActivity>()
            }
            mCount--
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(countDown)
    }
}
