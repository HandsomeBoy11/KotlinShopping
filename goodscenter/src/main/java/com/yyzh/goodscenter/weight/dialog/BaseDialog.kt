package com.yyzh.goodscenter.weight.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


abstract class BaseDialog : DialogFragment() {
    lateinit var activity: Activity
    lateinit var mView: View
//    private val mHandler = object : Handler() {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            dismiss()
//            if (mBaseDialogListener != null) {
//                mBaseDialogListener!!.dismiss()
//            }
//        }
//    }
    companion object{
        const val TIME=600
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)//设置dialog没有title
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.setDimAmount(0f)
        mView = inflater.inflate(getLayoutId(), container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        val windowAttributes = window!!.attributes
        windowAttributes.dimAmount = setAlpha()
        window.setGravity(setGravity())
        window.attributes = windowAttributes
    }

    abstract fun init()
    abstract fun getLayoutId(): Int
    open fun setAlpha(): Float = 0.0f
    open fun setGravity() = Gravity.CENTER

      fun showDialog(manager: FragmentManager, tag: String) {
        show(manager, tag, false)
    }

     fun show(manager: FragmentManager, tag: String, autoHide: Boolean) {
       /* if (autoHide) {
            mHandler.sendEmptyMessageDelayed(0, TIME.toLong())
        }*/
        super.show(manager, tag)
    }


}