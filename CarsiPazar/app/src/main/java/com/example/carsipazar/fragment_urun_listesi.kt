package com.example.carsipazar

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class fragment_urun_listesi : Fragment() {

    lateinit var adapter: UrunAdapterRecyclerView

    companion object {
        lateinit var mctx:Context

    }
    private var model: Communicator?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model= ViewModelProvider(activity!!).get(Communicator::class.java)



    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        var v: View? = inflater.inflate(R.layout.fragment_urun_listesi, container, false)

        val db = DataBaseHelper(requireContext())
        var urunData = db.getUrun()

        // searchview kısmı
        var searchView = v!!.findViewById<SearchView>(R.id.searchViewUrunAraAlisveris) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val adpFilter = adapter.getFilter()

                adpFilter.filter(newText)

                return false
            }

        })
        var context=requireContext()


        adapter = UrunAdapterRecyclerView(urunData as ArrayList<Urun>, context,activity as FragmentActivity)
        v!!.findViewById<RecyclerView>(R.id.recyclerViewYeniAlisveris).layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        v!!.findViewById<RecyclerView>(R.id.recyclerViewYeniAlisveris).adapter =adapter

        val dividerItemDecoration = DividerItemDecoration(requireContext() , LinearLayoutManager(requireContext()).orientation)
        v!!.findViewById<RecyclerView>(R.id.recyclerViewYeniAlisveris).addItemDecoration(dividerItemDecoration)


        return v
    }

    // ürün listesi Adapteri ve Recyclerview i
    class UrunAdapterRecyclerView(var urun_Listesi: ArrayList<Urun>, context: Context,_activity: FragmentActivity) :
            RecyclerView.Adapter<UrunAdapterRecyclerView.UrunViewHolder>(), Filterable {

        var secilen_urunler=ArrayList<Urun>()
        var urunFilterList = ArrayList<Urun>()
        var myContext:Context
        lateinit var activity:FragmentActivity
        init {
            urunFilterList = urun_Listesi
            myContext=context
            activity=_activity
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrunViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.urun_karti, parent, false)

            return UrunViewHolder(v)
        }

        override fun onBindViewHolder(
                holder: UrunAdapterRecyclerView.UrunViewHolder,
                position: Int
        ) {

            var urunItem = urunFilterList[position]

            holder.itemView.findViewById<TextView>(R.id.txtUrunAdi).text = urunItem.urunAdi;



            var model: Communicator?=null
            model= ViewModelProvider(activity!!).get(Communicator::class.java)

            holder.itemView.findViewById<Button>(R.id.btnAlisverisListesineEkle).setOnClickListener {

                secilen_urunler.add(urunItem)
                notifyDataSetChanged()
                model!!.urunGonder(urunItem)
                urunFilterList.remove(urunItem);
                notifyDataSetChanged()
                val manager = (myContext as AppCompatActivity).supportFragmentManager
                val myfragment = fragmentYeniAlisveris()
                val fragmentTransaction = manager!!.beginTransaction()
                fragmentTransaction.replace(R.id.content_id, myfragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        override fun getItemCount(): Int {
            return urunFilterList.size
        }

        class UrunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
        override fun getFilter(): Filter {

            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {

                    val charSearch = constraint.toString()

                    if (charSearch.isEmpty()) {
                        urunFilterList = urun_Listesi

                    } else {
                        val resultList = ArrayList<Urun>()

                        for (row in urun_Listesi) {
                            if (row.urunAdi.toString().contains(charSearch.toUpperCase())
                            ) {


                                resultList.add(row)
                            }
                        }
                        urunFilterList = resultList as ArrayList<Urun>
                    }
                    val filterResults = FilterResults()

                    filterResults.values = urunFilterList

                    return filterResults
                }

                @Suppress("UNCHECKED_CAST")
                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    urunFilterList = results?.values as ArrayList<Urun>
                    notifyDataSetChanged()
                }


            }

        }


    }
}


