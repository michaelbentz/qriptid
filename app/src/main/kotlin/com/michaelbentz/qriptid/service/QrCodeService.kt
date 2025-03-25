package com.michaelbentz.qriptid.service

import com.michaelbentz.qriptid.Constants.Network.Param.DATA
import com.michaelbentz.qriptid.Constants.Network.Route.CREATE_QR_CODE
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QrCodeService {

    @GET(CREATE_QR_CODE)
    fun createQrCode(@Query(DATA) data: String): Call<ResponseBody>
}
