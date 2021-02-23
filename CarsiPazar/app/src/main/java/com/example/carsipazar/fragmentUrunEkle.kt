package com.example.carsipazar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class fragmentUrunEkle : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this

        var v: View? = inflater.inflate(R.layout.fragment_urun_ekle, container, false)

        var btnUrunEkle= v!!.findViewById<Button>(R.id.btnUrunEkle) as Button

        var editTextUrunAdi=v!!.findViewById<EditText>(R.id.editTextUrunAdi) as EditText

        var editTextUrunBirimFiyat=v!!.findViewById<EditText>(R.id.editTextUrunBirimFiyat) as EditText

        btnUrunEkle.setOnClickListener {
            if (editTextUrunAdi.text.toString().length<1 || editTextUrunBirimFiyat.text.toString().length<1){
                Toast.makeText(context,editTextUrunBirimFiyat.text.toString(), Toast.LENGTH_LONG).show()

            }else{

                var db= DataBaseHelper(getContext()!!.applicationContext)
                var urun=Urun(  editTextUrunAdi.text.toString().toUpperCase(), editTextUrunBirimFiyat.text.toString().toInt()   )

                db.insertUrun(urun)
            }


        }

       // return inflater.inflate(R.layout.fragment_urun_ekle, container, false)
        return v
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragmentUrunEkle().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}