package com.example.carsipazar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


val database_name="URUN_DB"
val table_name="URUNLER_TABLE"
val col_urun_id="urun_id"
val col_urun_adi="urun_adi"
val col_urun_birim_fiyat="urun_birim_fiyat"
val col_urun_kategori="urun_kategori"

class DataBaseHelper(var context: Context):SQLiteOpenHelper(context, database_name, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable=" CREATE TABLE " + table_name + "(" + col_urun_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                col_urun_adi + " VARCHAR(250),"+
                col_urun_kategori+ " VARCHAR(250),"+
                col_urun_birim_fiyat + " INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertUrun(urun: Urun){
        val db=this.writableDatabase
        val cv=ContentValues()

        cv.put(col_urun_adi, urun.urunAdi)
        cv.put(col_urun_birim_fiyat, urun.urunBirimFiyat!!)
        if ( !urun.urunKategori.isNullOrBlank()){
            cv.put(col_urun_kategori, urun.urunKategori)
        }

        var result=db.insert(table_name, null, cv)
        if (result<1){
            Toast.makeText(context, "Kayıt Yapılamadı", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "Ürün Başarı ile Eklendi", Toast.LENGTH_LONG).show()

        }
    }

    fun getUrun(): MutableList<Urun> {
        var urunListesi:ArrayList<Urun> =ArrayList()
        val db=this.readableDatabase

        var sorgu="SELECT * from $table_name"
        var sonuc=db.rawQuery(sorgu, null)
        if (sonuc.moveToFirst()){
            do {
                var urun=Urun()
                urun.id=sonuc.getString(sonuc.getColumnIndex(col_urun_id)).toInt()
                urun.urunAdi=sonuc.getString(sonuc.getColumnIndex(col_urun_adi))  // Sorun olabilir getInt dene
                urun.urunKategori=sonuc.getString(sonuc.getColumnIndex(col_urun_kategori))
                urun.urunBirimFiyat=sonuc.getString(sonuc.getColumnIndex(col_urun_birim_fiyat)).toInt()
                urunListesi.add(urun)
            }while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
        return urunListesi
    }


    fun insertShopping(_urunlistesi: ArrayList<Urun>){
        val db=this.writableDatabase

        var urunlistesi=_urunlistesi



        var sorguCreateTable= "CREATE TABLE IF NOT EXISTS SHOPPINGS (IND INTEGER PRIMARY KEY AUTOINCREMENT, URUN NVARCHAR(250),TARIH DATETIME, TUTAR DECIMAL(28,8))"
        db.execSQL(sorguCreateTable)
        var shopsatir= ArrayList<String>()
        var toplamTutar:Double=0.0


        for (i in 0..urunlistesi.size-1){
           shopsatir.add(urunlistesi[i].urunAdi!!.toString() + " " + urunlistesi[i].urunBirimFiyat!!.toString())
            toplamTutar+=urunlistesi[i].urunBirimFiyat!!.toDouble()

        }

        val TARIH = LocalDateTime.now()

        var insertShopping="INSERT INTO SHOPPINGS (URUN,TARIH,TUTAR) VALUES ('$shopsatir','$TARIH',$toplamTutar)"
        try {
            db.execSQL(insertShopping)
            Toast.makeText(context,"Kayıt Başarılı",Toast.LENGTH_LONG).show()




        }catch (e: SQLException) {
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show()
        }

        }


    fun getShoppings(): MutableList<AlisverisDataClass>{
        var alisverislistesi:ArrayList<AlisverisDataClass> =ArrayList()
        val db=this.readableDatabase
        var sorgu="SELECT * from SHOPPINGS"

        var sorguCreateTable= "CREATE TABLE IF NOT EXISTS SHOPPINGS (IND INTEGER PRIMARY KEY AUTOINCREMENT, URUN NVARCHAR(250),TARIH DATETIME, TUTAR DECIMAL(28,8))"
        db.execSQL(sorguCreateTable)

        var sonuc=db.rawQuery(sorgu, null)

            if (sonuc.moveToFirst()) {
                do {
                    var alisveris = AlisverisDataClass("0", "", "", "")

                    alisveris.belgeInd = sonuc.getString(sonuc.getColumnIndex("IND"))
                    alisveris.urunler =
                        sonuc.getString(sonuc.getColumnIndex("URUN"))
                            .filter { it.isLetter() or it.isWhitespace() }
                    alisveris.tarih =
                        sonuc.getString(sonuc.getColumnIndex("TARIH")).replace("T", " ")
                            .substringBeforeLast(":")
                    alisveris.tutar = sonuc.getString(sonuc.getColumnIndex("TUTAR"))



                    alisverislistesi.add(alisveris)
                } while (sonuc.moveToNext())
            }

        sonuc.close()
        db.close()
        return alisverislistesi
    }


    }
