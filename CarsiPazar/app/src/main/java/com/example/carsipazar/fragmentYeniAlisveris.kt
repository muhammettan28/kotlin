package com.example.carsipazar

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class fragmentYeniAlisveris : Fragment() {


    lateinit var adapterSepet: SecilenUrunAdapter
    var secilenUrunListesi: ArrayList<Urun> = ArrayList<Urun>()
    var sepetUrunTutarlar=ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v: View? = inflater.inflate(R.layout.fragment_yeni_alisveris, container, false)

        var txtSepetUrunSayisi = v!!.findViewById<TextView>(R.id.txtSepetUrunSayisi)
        var txtSepetTutar = v!!.findViewById<TextView>(R.id.txtSepetTutar)
        adapterSepet = SecilenUrunAdapter(
            secilenUrunListesi as ArrayList<Urun>,
            context!!,
            txtSepetUrunSayisi,
            txtSepetTutar,
            activity as FragmentActivity
        )
        v!!.findViewById<RecyclerView>(R.id.recycleViewSepet).layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        v!!.findViewById<RecyclerView>(R.id.recycleViewSepet).adapter = adapterSepet

        //sepetUrunTutarlar.clear()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProvider(activity!!).get(Communicator::class.java)

        model.urunListesi.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {

                var urun: Urun
                urun = o as Urun

                if (urun !in secilenUrunListesi) {
                    secilenUrunListesi.add(urun)
                } else {
                    Toast.makeText(context, "Ürünü zaten sepete eklediniz", Toast.LENGTH_SHORT)
                        .show()
                }

                var txtSepetUrunSayisi = view.findViewById<TextView>(R.id.txtSepetUrunSayisi)
                txtSepetUrunSayisi.setText("${secilenUrunListesi.size}")

                var txtSepetTutar = view.findViewById<TextView>(R.id.txtSepetTutar)

                var kayitkontrolu = false

                view.findViewById<ImageView>(R.id.imgSepetShare).setOnClickListener {
                    if (!kayitkontrolu) {
                        Toast.makeText(
                            context,
                            "Paylaşım için öncelikle sepeti kaydetmelisiniz",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val shareIntent = Intent()
                        shareIntent.action = Intent.ACTION_SEND

                        shareIntent.type = "text/plain"

                        var urunlistesishare = ArrayList<Any>()

                        for (i in 0..secilenUrunListesi.size - 1) {
                            urunlistesishare.add(secilenUrunListesi[i].urunAdi.toString())

                        }
                        shareIntent.putExtra(
                            Intent.EXTRA_TEXT, urunlistesishare.toString().replace(
                                "[",
                                ""
                            ).replace("]", "") + "\n"
                        );
                        startActivity(Intent.createChooser(shareIntent, "Çarşı Pazar"))
                    }
                }

                var alert: AlertDialog = AlertDialog.Builder(context).create()
                var editView = layoutInflater.inflate(R.layout.diaolog_sepet_kaydet, null)
                alert.setView(editView)

                view.findViewById<ImageView>(R.id.imgSepetSave).setOnClickListener {

                    view.findViewById<EditText>(R.id.editTextSepetTutar).clearFocus()
                    view.findViewById<LinearLayout>(R.id.content_id).requestFocus()
                    alert.setTitle("Sepet Kayıt")
                    alert.show()
                    var tutar = 0

                    editView.findViewById<Button>(R.id.btnEvet).setOnClickListener {
                        var db = DataBaseHelper(getContext()!!.applicationContext)

                        db.insertShopping(secilenUrunListesi)

                        for (i in 0..secilenUrunListesi.size - 1) {
                            tutar += secilenUrunListesi[i].urunBirimFiyat!!
                        }
                        view.findViewById<TextView>(R.id.txtSepetTutar).setText(tutar.toString());

                        alert.dismiss()
                       // fragmentManager!!.beginTransaction().add(R.id.fea, fragment_eski_alisveris()).addToBackStack(null).commit()

                    }
                    editView.findViewById<Button>(R.id.btnHayır).setOnClickListener {
                        view.findViewById<TextView>(R.id.txtSepetTutar).setText(tutar.toString());
                        alert.dismiss()
                    }

                    kayitkontrolu = true

                }

                adapterSepet = SecilenUrunAdapter(
                    secilenUrunListesi as ArrayList<Urun>,
                    context!!,
                    txtSepetUrunSayisi,
                    txtSepetTutar,
                    activity as FragmentActivity
                )
                view.findViewById<RecyclerView>(R.id.recycleViewSepet).adapter = adapterSepet
            }
        })

        model.message.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
            }

        })

        sepetUrunTutarlar.clear()
        model.sepetListesi.observe(this, object : Observer<Any> {
            override fun onChanged(t: Any?) {


                var myMap = mutableMapOf<Urun, Int>()
                myMap = t as MutableMap<Urun, Int>;

                for (i in myMap.keys) {
                    if (i in secilenUrunListesi) {
                        secilenUrunListesi[secilenUrunListesi.indexOf(i)].urunBirimFiyat =
                            myMap.get(i)
                        Log.d("LOG35", secilenUrunListesi.toString())

                    }
                }
            }
        })
    }

    class SecilenUrunAdapter(
        var secilenUrunListesi2: ArrayList<Urun>,
        _context: Context,
        _txtSepetUrunSayisi: TextView,
        _txtSepetTutar: TextView,
        _activity: FragmentActivity
    ) :
            RecyclerView.Adapter<SecilenUrunAdapter.SecilenViewHolder>() {

        var secilenUrunListesi: ArrayList<Urun> = ArrayList<Urun>()
        var txtSepetUrunSayisi: TextView
        var txtSepetTutar: TextView
        lateinit var context:Context
        lateinit var activity:FragmentActivity

        init {
            secilenUrunListesi = secilenUrunListesi2
            txtSepetUrunSayisi = _txtSepetUrunSayisi
            txtSepetTutar = _txtSepetTutar
            context=_context
            activity=_activity
        }

        override fun onBindViewHolder(holder: SecilenViewHolder, position: Int) {
            var urunItem = secilenUrunListesi[position]

            holder.itemView.findViewById<TextView>(R.id.txtSepetUrunAdi).text = urunItem.urunAdi.toString()

            var birimfiyat= if (urunItem.urunBirimFiyat==0) "" else  urunItem.urunBirimFiyat
            holder.itemView.findViewById<EditText>(R.id.editTextSepetTutar).setText(birimfiyat.toString())

            var model: Communicator?=null
            model= ViewModelProvider(activity!!).get(Communicator::class.java)
                holder.itemView.findViewById<EditText>(R.id.editTextSepetTutar).addTextChangedListener(
                    object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {

                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {

                            holder.itemView.findViewById<EditText>(R.id.editTextSepetTutar).onFocusChangeListener =
                                View.OnFocusChangeListener { view, b ->
                                    if (b) {

                                    } else {


                                        if (!s.isNullOrEmpty()) {
                                            var sa = s.toString()
                                            var fiyat = sa.toInt()

                                            model!!.sepetKaydet(fiyat, urunItem)

                                        }
                                    }
                                }
                        }
                    })


              txtSepetTutar.setText(holder.itemView.findViewById<EditText>(R.id.editTextSepetTutar).text.toString())

            //ürün kaldır
            holder.itemView.findViewById<ImageView>(R.id.btnSepettenKaldir).setOnClickListener {
                secilenUrunListesi.remove(urunItem)
                txtSepetUrunSayisi.setText(secilenUrunListesi.size.toString())

                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecilenViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.urun_sepet, parent, false)
            return SecilenViewHolder(v)
        }

        override fun getItemCount(): Int {
            return secilenUrunListesi.size
        }

        class SecilenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
    }

}

