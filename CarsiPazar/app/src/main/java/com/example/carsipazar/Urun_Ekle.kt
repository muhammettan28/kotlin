package com.example.carsipazar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.carsipazar.databinding.ActivityUrunEkleBinding
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import java.util.zip.Inflater

class Urun_Ekle : AppCompatActivity() {

    lateinit var binding:ActivityUrunEkleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_urun__ekle)

        val binding=ActivityUrunEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = this
        var db= DataBaseHelper(context)

        findViewById<ViewPager>(R.id.viewpager).adapter=PageAdapterTabItemUrun(supportFragmentManager)
        findViewById<TabLayout>(R.id.tablayoutUrunEkle).setupWithViewPager(findViewById<ViewPager>(R.id.viewpager))

       // findViewById<EditText>(R.id.editTextUrunAdi).text.toString()
      //  var birimFiyat=findViewById<EditText>(R.id.editTextUrunBirimFiyat) as EditText








    }


}