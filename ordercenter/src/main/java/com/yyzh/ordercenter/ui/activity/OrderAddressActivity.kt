package com.yyzh.ordercenter.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.user.injection.component.DaggerAddressComponent
import com.kotlin.user.injection.module.AddressModule
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.ordercenter.R
import com.yyzh.ordercenter.common.OrderConstant
import com.yyzh.ordercenter.event.UpdateAddressEvent
import com.yyzh.ordercenter.persenter.AddressPersenter
import com.yyzh.ordercenter.persenter.view.AddressView
import com.yyzh.ordercenter.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_order_address.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class OrderAddressActivity : BaseMvpActivity<AddressPersenter>(), AddressView, View.OnClickListener {


    private val addressAdapter by lazy { ShipAddressAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_address)
        initView()
    }

    private fun initView() {
        mAddAddressBtn.onClick(this)
        var manager = LinearLayoutManager(this)
        mAddressRv.layoutManager = manager
        mAddressRv.adapter = addressAdapter
        addressAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener {
            override fun onSetDefault(address: ShipAddress) {
                mPersenter.editShipAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                showDeleteDialog(address.id)
            }

        }
        addressAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress>{
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(UpdateAddressEvent(item))
                finish()
            }

        })
    }

    private fun showDeleteDialog(id: Int) {
        AlertView(
            "删除地址",
            "确定删除该地址？",
            "取消",
            null,
            arrayOf("确定"),
            this,
            AlertView.Style.Alert,
            OnItemClickListener { o, position ->
                if (position == 0) {
                    mPersenter.deleteAddressList(id)
                }
            }

        ).show()
    }

    override fun onStart() {
        super.onStart()
        mPersenter.getAddressList()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mAddAddressBtn -> {
                startActivity<ShipAddressEditActivity>()
            }
        }
    }

    /**
     * 获取地址列表结果
     */
    override fun onGetAddressResult(result: MutableList<ShipAddress>?) {
        if (result != null && result.size > 0) {
            addressAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            addressAdapter.dataList.clear()
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /**
     * 修改结果
     */
    override fun onEditddressResult(result: Boolean) {
        if(result){
            mPersenter.getAddressList()
        }
    }

    override fun injectComponent() {
        DaggerAddressComponent.builder().activityComponent(mActivityComponent).addressModule(AddressModule()).build()
            .inject(this)
        mPersenter.mView = this
    }

    override fun onDeleteAddressResult(result: String) {
        toast("删除成功")
        addressAdapter.notifyDataSetChanged()
        mPersenter.getAddressList()
    }

}