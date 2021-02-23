package com.example.carsipazar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapterTabItemUrun(fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2;
    }

    override fun getPageTitle(position: Int): CharSequence? {

        when(position){
            0-> {return "Ürün Listesi"}
            1-> {return "Ürün Ekle"}
        }
        return super.getPageTitle(position)
    }



    override fun getItem(position: Int): Fragment {
       when(position){
           0 -> { return  fragmentUrunListe() }
           1 -> { return  fragmentUrunEkle() }
           else -> { return fragmentUrunListe()}
       }
    }

}