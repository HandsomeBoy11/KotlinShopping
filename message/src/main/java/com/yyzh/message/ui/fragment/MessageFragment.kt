package com.yyzh.message.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.kotlin.user.injection.component.DaggerMessageComponent
import com.kotlin.user.injection.module.MessageModule
import com.yyzh.baselibrary.ui.fragment.BaseMvpFragment
import com.yyzh.message.R
import com.yyzh.message.persenter.MessagePersenter
import com.yyzh.message.persenter.view.MessageView
import com.yyzh.message.service.bean.Message
import com.yyzh.message.ui.adapter.MessageAdapter
import com.yyzh.providerlibrary.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : BaseMvpFragment<MessagePersenter>(), MessageView {
    private val mAdapter by lazy { MessageAdapter(activity!!) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_message,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mMessageRv.layoutManager = LinearLayoutManager(context)
        mMessageRv.adapter = mAdapter
    }

    override fun injectComponent() {
        DaggerMessageComponent.builder().activityComponent(mActivityComponent).messageModule(MessageModule()).build()
            .inject(this)
        mPresenter.mView=this
    }

    override fun onStart() {
        super.onStart()
        mPresenter.getMessageList()
    }

    override fun onGetMessageList(list: MutableList<Message>?) {
        if(list!=null&&list.size>0){
            mMultiStateView.viewState=MultiStateView.VIEW_STATE_CONTENT
            mAdapter.setData(list)
        }else{
            mMultiStateView.viewState=MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            Bus.send(MessageBadgeEvent(false))
        }
    }

}