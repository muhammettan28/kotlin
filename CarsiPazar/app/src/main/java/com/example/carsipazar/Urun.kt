package com.example.carsipazar

class Urun {
    var id:Int?=0
    var urunAdi:String? = null
    var urunGorsel:Int? = null
    var urunBirimFiyat:Int?= null
    var urunKategori:String?=null

    constructor(urunAdi:String,urunBirimFiyat:Int){
        this.urunAdi=urunAdi
        this.urunBirimFiyat=urunBirimFiyat
    }


    constructor(urunAdi:String,urunGorsel:Int,urunBirimFiyat:Int){
        this.urunAdi=urunAdi
        this.urunGorsel=urunGorsel
        this.urunBirimFiyat=urunBirimFiyat
    }


    constructor(){

    }


    constructor(urunAdi:String,urunBirimFiyat:Int,urunKategori:String){
        this.urunAdi=urunAdi
        this.urunBirimFiyat=urunBirimFiyat
        this.urunKategori=urunKategori
    }
}