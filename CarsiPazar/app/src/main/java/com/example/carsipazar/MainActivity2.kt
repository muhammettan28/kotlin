package com.example.carsipazar

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.InputStream

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        findViewById<Button>(R.id.btnKayitEkleAll).setOnClickListener {


            val lineList = mutableListOf<String>()
            val bufferedReader: BufferedReader = assets.open("Urunler.txt",1).bufferedReader()

            val inputString = bufferedReader.forEachLine { lineList.add(it) }
            val db=DataBaseHelper(this)

            lineList.forEach {

                findViewById<TextView>(R.id.txtAllSonuc).setText(findViewById<TextView>(R.id.txtAllSonuc).text.toString()+ it + lineList.indexOf(it) + "\n")
                var urun=Urun(it,0,"")
                db.insertUrun(urun)
            }

           // val wordList = mutableListOf<Word>()
           // var reader= File(fileToRead).readLines()
           // var wrdLst:List<Word>
        }
    }
}