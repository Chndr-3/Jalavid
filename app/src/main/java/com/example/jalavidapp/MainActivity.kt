package com.example.jalavidapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.jalavidapp.api.getJsonDataFromAsset
import com.example.jalavidapp.databinding.ActivityMainBinding
import com.example.jalavidapp.model.ListDataItem
import com.example.jalavidapp.model.ResponseData
import com.example.jalavidapp.viewmodel.MainViewModel
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title_main_layout)
        binding.textView8.paint.isUnderlineText = true
        binding.textView8.setOnClickListener {
            startActivity(Intent(this,AboutActivity::class.java))
        }
        viewModel.findCovidProvince()
        buttonSelection()
        observer()
        dataFailed()
    }

    private fun dataFailed() {
        viewModel.isSuccess.observe(this) {
            if (it == false) {
                Toast.makeText(
                    this,
                    "Gagal mengambil data terbaru, server sedang bermasalah",
                    Toast.LENGTH_SHORT
                ).show()
                val jsonFileString = getJsonDataFromAsset(applicationContext, "kasusharian.json")
                if (jsonFileString != null) {
                    Log.i("data", jsonFileString)
                }
                val gson = Gson()

                val persons: ResponseData = gson.fromJson(jsonFileString, ResponseData::class.java)
                for (data in persons.listData){
                    if(data.key == "DKI JAKARTA"){
                        setCount(data)
                    }
                }
                binding.update.text = getString(R.string.update, persons.lastDate)
            }
        }

    }


    private fun setCount(item: ListDataItem) {
        binding.tvDeath.text = String.format("%,d", item.jumlahMeninggal)
        binding.tvDirawat.text = String.format("%,d", item.jumlahDirawat)
        binding.tvKasus.text = String.format("%,d", item.jumlahKasus)
    }

    private fun setUpdated(item: ResponseData) {
        binding.update.text = getString(R.string.update, item.lastDate)
    }

    private fun showLoading(bol: Boolean) {
        if (bol) {
            binding.progressBar.visibility = View.VISIBLE
            binding.cardView.visibility = View.INVISIBLE
            binding.cardView2.visibility = View.INVISIBLE
            binding.cardView3.visibility = View.INVISIBLE
            binding.update.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.cardView.visibility = View.VISIBLE
            binding.cardView2.visibility = View.VISIBLE
            binding.cardView3.visibility = View.VISIBLE
            binding.update.visibility = View.VISIBLE
        }
    }

    private fun buttonSelection() {
        binding.buttonFaskes.setOnClickListener {
            intent = Intent(this@MainActivity, FaskesActivity::class.java)
            startActivity(intent)
        }
        binding.buttonCegah.setOnClickListener {
            intent = Intent(this@MainActivity, CegahActivity::class.java)
            startActivity(intent)
        }
        binding.buttonFAQ.setOnClickListener {
            intent = Intent(this@MainActivity, TanyaJawabActivity::class.java)
            startActivity(intent)
        }
        binding.buttonRSR.setOnClickListener {
            intent = Intent(this@MainActivity, RumahSakitRujukanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observer() {
        viewModel.covidProvince.observe(this) { dataItem ->
            for (data in dataItem) {
                if (data.key == "DKI JAKARTA") {
                    setCount(data)
                }
            }
        }
        viewModel.additionalData.observe(this) {
            setUpdated(it)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

}