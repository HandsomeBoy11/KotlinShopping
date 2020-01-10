package com.yyzh.ordercenter.service

import com.kotlin.order.data.protocol.ShipAddress
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.rx.DataNotNull
import rx.Observable

interface AddressService {
    fun getAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>>
    fun deleteAddressList(id:Int): Observable<BaseResp<String>>
    fun addShipAddress( shipUserName: String,  shipUserMobile: String,  shipAddress: String):Observable<BaseResp<String>>
    fun editShipAddress(address:ShipAddress): Observable<Boolean>
}