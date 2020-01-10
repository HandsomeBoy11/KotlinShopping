package com.yyzh.kotlinmall.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Window
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.yyzh.baselibrary.common.AppManager
import com.yyzh.baselibrary.ui.activity.BaseActivity
import com.yyzh.baselibrary.ui.fragment.BaseFragment
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.event.UpdateCartSizeEvent
import com.yyzh.goodscenter.ui.fragment.CartFragment
import com.yyzh.goodscenter.ui.fragment.CategoryFragment
import com.yyzh.kotlinmall.R
import com.yyzh.kotlinmall.ui.fragment.HomeFragment
import com.yyzh.kotlinmall.ui.fragment.MeFragment
import com.yyzh.message.ui.fragment.MessageFragment
import com.yyzh.providerlibrary.common.afterLogin
import com.yyzh.providerlibrary.common.isLogined
import com.yyzh.providerlibrary.event.JumpToPosition
import com.yyzh.providerlibrary.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {
    private val mHomeFragment by lazy { HomeFragment.getInstance(0) }
    private val mCategoryFragment by lazy { CategoryFragment() }
    private val mCartFragment by lazy { CartFragment() }
    private val mMessageFragment by lazy { MessageFragment() }
    private val mMeFragment by lazy { MeFragment() }
    private var fragments = ArrayList<BaseFragment>()
    private var currentFragment: BaseFragment? = null
    private var currentIndex: Int = 0
    private var lastIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        initFragment(savedInstanceState)
        switchFragment(0)
        initBottomNav()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        //解决登录与未登录首页页面的切换
        if(isLogined()){
            switchFragment(currentIndex)
        }else{
            if(currentIndex==lastIndex){
               lastIndex=0
            }
            mBottomNavBar.selectTab(lastIndex)
            switchFragment(lastIndex)
        }
    }

    /**
     * 时间监听
     */
    private fun initObserver() {
        Bus.observe<UpdateCartSizeEvent>().subscribe {
            setCartCount(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
        }.registerInBus(this)
        Bus.observe<MessageBadgeEvent>().subscribe {
            mBottomNavBar.checkMsgBadge(it.isVisible)
        }.registerInBus(this)

    }

    /**
     * 设置购物车数量
     */
    private fun setCartCount(int: Int) {
        mBottomNavBar.checkCartBadge(int)
    }

    private fun switchFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()

        val fragment = fragments[index]
        if (currentFragment == null) {
            transaction.add(R.id.flContaner, fragment)
        } else {
            transaction.hide(currentFragment!!)
            if (fragment.isAdded) {
                transaction.show(fragment)
            } else {
                transaction.add(R.id.flContaner, fragment)
            }
        }
        transaction.commit()
        currentFragment = fragment
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            fragments.add(mHomeFragment)
            fragments.add(mCategoryFragment)
            fragments.add(mCartFragment)
            fragments.add(mMessageFragment)
            fragments.add(mMeFragment)
        }
    }
    /*
      初始化底部导航切换事件
   */
    private fun initBottomNav() {
        setCartCount(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))//初始购物车数量
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
                lastIndex=position
            }

            override fun onTabSelected(position: Int) {
                currentIndex=position
                if (position == 2 || position == 3) {
                    afterLogin{
                        switchFragment(position)
                    }
                } else {
                    switchFragment(position)
                }
            }
        })

        mBottomNavBar.checkMsgBadge(false)
    }

    var lastTime: Long = 0
    override fun onBackPressed() {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastTime >= 500) {
            toast("再次点击退出应用")
        } else {
            AppManager.instance.exitApp(this)
        }
        lastTime = currentTimeMillis
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}
