package com.example.jalavidapp

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
import com.example.jalavidapp.adapter.FaskesAdapter
import com.example.jalavidapp.api.getJsonDataFromAsset
import com.example.jalavidapp.databinding.ActivityFaskesBinding
import com.example.jalavidapp.model.DataItemFaskes
import com.example.jalavidapp.model.Faskes
import com.example.jalavidapp.viewmodel.FaskesViewModel
import com.google.gson.Gson

class FaskesActivity : AppCompatActivity() {
    private val viewModel: FaskesViewModel by viewModels()
    private lateinit var binding: ActivityFaskesBinding
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
        binding = ActivityFaskesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.rvFaskes.layoutManager = layoutManager
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValue)
        binding.spinner1.adapter = adapter
        viewModel.findFaskes()
        dataFailed()
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = findViewById<TextView>(R.id.action_bar_title)
        title.text = getString(R.string.fasilitas_kesehatan_vaksinasi)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedValue = 5
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val mutableList = mutableListOf<DataItemFaskes>()
                selectedValue = position
                if (success) {
                    viewModel.faskesDetail.observe(this@FaskesActivity) {
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
                        getJsonDataFromAsset(applicationContext, "faskesjakarta.json")
                    if (jsonFileString != null) {
                        Log.i("data", jsonFileString)
                    }
                    val gson = Gson()
                    val persons: Faskes = gson.fromJson(jsonFileString, Faskes::class.java)
                    val listFaskesItem: List<DataItemFaskes> = persons.data
                    when (selectedValue) {
                        0 -> checker(listFaskesItem, "JAKARTA BARAT", mutableList)
                        1 -> checker(listFaskesItem, "JAKARTA SELATAN", mutableList)
                        2 -> checker(listFaskesItem, "JAKARTA TIMUR", mutableList)
                        3 -> checker(listFaskesItem, "JAKARTA UTARA", mutableList)
                        4 -> checker(listFaskesItem, "JAKARTA PUSAT", mutableList)
                    }
                }
            }

        }

    }

    private fun checker(
        item: List<DataItemFaskes>,
        selectedCity: String,
        mutable: MutableList<DataItemFaskes>
    ) {
        for (data in item) {
            if (data.kota?.contains(selectedCity) == true) {
                mutable.add(data)
            }
        }
        setData(mutable)
    }

    private fun setData(item: List<DataItemFaskes>) {
        val listFaskesAdapter = FaskesAdapter(item)
        binding.rvFaskes.adapter = listFaskesAdapter
        listFaskesAdapter.setOnItemClickCallback(
            object : FaskesAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DataItemFaskes) {
                    val intentToDetail =
                        Intent(this@FaskesActivity, DetailFaskesActivity::class.java)
                    intentToDetail.putExtra(DetailFaskesActivity.DATA, data)
                    startActivity(intentToDetail)
                }
            }
        )
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE

        }
    }

    private fun dataFailed() {
        viewModel.isSuccess.observe(this) {
            if (it == false) {
                Toast.makeText(
                    this,
                    "Gagal mengambil data, periksa kembali internet anda",
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