package com.example.carsipazar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapterAlisveris(fm:FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
       return 3;
    }

    override fun getPageTitle(position: Int): CharSequence? {

        when(position){
            0-> {return "Ürün Listesi"}
            1-> {return "Yeni Alışveriş +"}
            2-> {return "Alışveriş Listelerim"}
        }
        return super.getPageTitle(position)
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> { return  fragment_urun_listesi()}
            1 -> { return  fragmentYeniAlisveris() }
            2 -> { return  fragment_eski_alisveris() }
            else -> { return fragment_urun_listesi()}
        }
    }

}