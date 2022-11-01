package com.example.jalavidapp

import com.example.jalavidapp.adapter.RumahSakitRujukanAdapter
import com.example.jalavidapp.databinding.ActivityRumahSakitRujukanBinding
import com.example.jalavidapp.model.RumahSakitItem
import com.example.jalavidapp.viewmodel.RumahSakitRujukanViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jalavidapp.api.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RumahSakitRujukanActivity : AppCompatActivity() {
    private val viewModel: RumahSakitRujukanViewModel by viewModels()
    private lateinit var binding: ActivityRumahSakitRujukanBinding
    private val spinnerValue: List<String> = listOf(
        "Jakarta Barat",
        "Jakarta Selatan",
        "Jakarta Timur",
        "Jakarta Utara",
        "Jakarta Pusat"
    )

    private var selectedValue: Int = 5
    private var success: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faskes)
        binding = ActivityRumahSakitRujukanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.rvRS.layoutManager = layoutManager
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerValue)
        binding.spinner2.adapter = adapter
        viewModel.findRS()
        dataFailed()
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = findViewById<TextView>(R.id.action_bar_title)
        title.text = getString(R.string.rumah_sakit_rujukan)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedValue = 5
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val mutableList = mutableListOf<RumahSakitItem>()
                selectedValue = position
                if (success) {

                    viewModel.rsDetail.observe(this@RumahSakitRujukanActivity) {
                        when (selectedValue) {
                            0 -> checker(it, "JAKARTA BARAT", mutableList)
                            1 -> checker(it, "JAKARTA SELATAN", mutableList)
                            2 -> checker(it, "JAKARTA TIMUR", mutableList)
                            3 -> checker(it, "JAKARTA UTARA", mutableList)
                            4 -> checker(it, "JAKARTA PUSAT", mutableList)
                        }
                    }
                } else {
                    val jsonFileString =
                        getJsonDataFromAsset(applicationContext, "rumahsakitrujukan.json")
                    if (jsonFileString != null) {
                        Log.i("data", jsonFileString)
                    }
                    val gson = Gson()
                    val listRumahSakit = object : TypeToken<List<RumahSakitItem>>() {}.type
                    val persons: List<RumahSakitItem> =
                        gson.fromJson(jsonFileString, listRumahSakit)
                    when (selectedValue) {
                        0 -> checker(persons, "JAKARTA BARAT", mutableList)
                        1 -> checker(persons, "JAKARTA SELATAN", mutableList)
                        2 -> checker(persons, "JAKARTA TIMUR", mutableList)
                        3 -> checker(persons, "JAKARTA UTARA", mutableList)
                        4 -> checker(persons, "JAKARTA PUSAT", mutableList)
                    }
                }
            }
        }
    }

    private fun checker(
        item: List<RumahSakitItem>,
        selectedCity: String,
        mutable: MutableList<RumahSakitItem>
    ) {
        for (data in item) {
            if (data.wilayah?.contains(selectedCity) == true) {
                mutable.add(data)
            }
        }
        setData(mutable)
    }

    private fun setData(item: List<RumahSakitItem>) {
        val listRSAdapter = RumahSakitRujukanAdapter(item)
        binding.rvRS.adapter = listRSAdapter
        listRSAdapter.setOnItemClickCallback(
            object : RumahSakitRujukanAdapter.OnItemClickCallback {
                override fun onItemClicked(data: RumahSakitItem) {
                    val intentToDetail =
                        Intent(
                            this@RumahSakitRujukanActivity,
                            DetailRumahSakitRujukanActivity::class.java
                        )
                    intentToDetail.putExtra(DetailRumahSakitRujukanActivity.DATA, data)
                    startActivity(intentToDetail)
                }
            }
        )
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE

        }
    }

    private fun dataFailed() {
        viewModel.isSuccess.observe(this) {
            if (it == false) {
                Toast.makeText(
                    this,
                    "Gagal mengambil data terbaru, terdapat masalah pada server ataupun jaringan koneksi anda",
                    Toast.LENGTH_SHORT
                ).show()
            }
            success = it
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}