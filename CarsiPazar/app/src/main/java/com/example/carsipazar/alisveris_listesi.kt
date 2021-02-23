package com.example.carsipazar

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout
import java.util.ArrayList

class alisveris_listesi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alisveris_listesi)

        findViewById<ViewPager>(R.id.viewpagerAlisVeris).adapter=PageAdapterAlisveris(supportFragmentManager)
        findViewById<TabLayout>(R.id.tablayoutAlisveris).setupWithViewPager(findViewById<ViewPager>(R.id.viewpagerAlisVeris))



    }




}


