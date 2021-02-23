package com.example.carsipazar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class fragment_eski_alisveris : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view= inflater.inflate(R.layout.fragment_eski_alisveris, container, false)

        val model = ViewModelProvider(activity!!).get(Communicator::class.java)



                val db=DataBaseHelper(requireContext())
                var urunData=db.getShoppings()







        view.findViewById<RecyclerView>(R.id.recyclerViewEskiAlisveris).layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.findViewById<RecyclerView>(R.id.recyclerViewEskiAlisveris).adapter =EskiAlisverislerAdapter(urunData, requireContext())
        return  view
    }



}