package com.example.carsipazar

import android.os.Debug
import android.util.Log
import android.util.Log.DEBUG
import android.util.Log.INFO
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsipazar.BuildConfig.DEBUG
import java.util.logging.Level.INFO

class Communicator:ViewModel() {

    val urunListesi= MutableLiveData<Any>()

    val message=MutableLiveData<Any>()

    val sepetListesi=MutableLiveData<Any>()

    fun setMsgCommunicator(msg: String){
        message.value=msg

    }

 



    var urunlistesiKontrol:ArrayList<Urun> =ArrayList<Urun>()

    fun urunGonder(urun: Urun){
        urunListesi.postValue(urun)
        urunlistesiKontrol.add(urun)
    }



    var urunFiyat= mutableMapOf<Urun,Int>()
    fun sepetKaydet(tutar:Int,urun: Urun){
        if(urun in urunlistesiKontrol){
            urunFiyat.put(urun,tutar)

            sepetListesi.postValue(urunFiyat)
            //sepetListesi.postValue(urun)
        }
    }





}