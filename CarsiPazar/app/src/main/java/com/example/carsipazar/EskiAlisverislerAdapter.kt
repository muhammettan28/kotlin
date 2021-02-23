package com.example.carsipazar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class EskiAlisverislerAdapter(
    val alisverisListesi: MutableList<AlisverisDataClass>,
    _context: Context
):
RecyclerView.Adapter<EskiAlisverislerAdapter.AlisverisViewHolder>()
{

    class AlisverisViewHolder(view: View):RecyclerView.ViewHolder(view){
        val urunAdi: TextView = view.findViewById(R.id.txtUrunAdi)
        val urunMiktar:TextView=view.findViewById(R.id.txtUrunMiktar)
        val urunTutar: TextView =view.findViewById(R.id.txtUrunTutar)

        fun bindItems(item: AlisverisDataClass) {
            urunAdi.setText(item.urunler)
            urunMiktar.setText(item.tarih)
            urunTutar.setText(item.tutar + " TL")

        }
    }

    var context:Context?=null

    init {
        context=_context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EskiAlisverislerAdapter.AlisverisViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.urun_item, parent, false)

        return AlisverisViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EskiAlisverislerAdapter.AlisverisViewHolder,
        position: Int
    ) {
        holder.bindItems(alisverisListesi.get(position))

    }

    override fun getItemCount(): Int {
        return alisverisListesi.size
    }
}