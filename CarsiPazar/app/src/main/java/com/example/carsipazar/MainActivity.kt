package com.example.carsipazar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Ürün Ekle Sayfasına Git
        findViewById<TextView>(R.id.txtUrunEkle).setOnClickListener {
            var intent= Intent(this, Urun_Ekle::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.imgUrunEkle).setOnClickListener {
            var a= Intent(this, Urun_Ekle::class.java)
            startActivity(a)
        }




        // Alışverişe Git Sayfasına Git
        findViewById<TextView>(R.id.txtAlisveriseGit).setOnClickListener {
            var intent= Intent(this, alisveris_listesi::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.imgAlisveriseGit).setOnClickListener {
            var intentImg=Intent(this,alisveris_listesi::class.java)
            startActivity(intentImg)
        }

        // Dengeye git
        findViewById<ImageView>(R.id.imgBalance).setOnClickListener {
            var intent=Intent(this,Denge::class.java)
            startActivity(intent)
        }
        findViewById<TextView>(R.id.txtBalance).setOnClickListener {
            var intent= Intent(this, Denge::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.imgPlan).setOnClickListener {
            var intentPlan= Intent(this, MainActivity2::class.java)
            startActivity(intentPlan)
        }






    }
}


