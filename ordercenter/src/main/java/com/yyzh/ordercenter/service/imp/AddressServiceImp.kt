package com.yyzh.ordercenter.service.imp

import com.kotlin.order.data.protocol.AddShipAddressReq
import com.kotlin.order.data.protocol.DeleteShipAddressReq
import com.kotlin.order.data.protocol.EditShipAddressReq
import com.kotlin.order.data.protocol.ShipAddress
import com.yyzh.baselibrary.data.net.RetrfitFactory
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.ext.convert
import com.yyzh.baselibrary.ext.convertBoolean
import com.yyzh.baselibrary.ext.convertNull
import com.yyzh.baselibrary.rx.DataNotNull
import com.yyzh.ordercenter.data.api.ShipAddressApi
import com.yyzh.ordercenter.service.AddressService
import rx.Observable
import javax.inject.Inject

class AddressServiceImp @Inject constructor() : AddressService {
    override fun editShipAddress(address: ShipAddress): Observable<Boolean> {
        return RetrfitFactory.instance.create(ShipAddressApi::class.java)
            .editShipAddress(
                EditShipAddressReq(
                    address.id,
                    address.shipUserName,
                    address.shipUserMobile,
                    address.shipAddress,
                    address.shipIsDefault
                )
            ).convertBoolean()
    }

    override fun deleteAddressList(id: Int): Observable<BaseResp<String>> {
        return RetrfitFactory.instance.create(ShipAddressApi::class.java)
            .deleteShipAddress(DeleteShipAddressReq(id)).convertNull()
    }

    override fun addShipAddress(
        shipUserName: String,
        shipUserMobile: String,
        shipAddress: String
    ): Observable<BaseResp<String>> {
        return RetrfitFactory.instance.create(ShipAddressApi::class.java)
            .addShipAddress(AddShipAddressReq(shipUserName, shipUserMobile, shipAddress)).convertNull()
    }

    override fun getAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>> {
        return RetrfitFactory.instance.create(ShipAddressApi::class.java).getShipAddressList().convertNull()
    }
}