package com.example.jalavidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.jalavidapp.databinding.ActivityDetailFaskesBinding
import com.example.jalavidapp.model.DataItemFaskes

class DetailFaskesActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityDetailFaskesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_faskes)
        binding = ActivityDetailFaskesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = findViewById<TextView>(R.id.action_bar_title)
        title.text = getString(R.string.detail_fasilitas_kesehatan_vaksinasi)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        val data = intent.getParcelableExtra<DataItemFaskes>(DATA)!!
        setData(data)
        binding.mapButton.setOnClickListener{
            intent = Intent(this@DetailFaskesActivity, MapsActivity::class.java)
            intent.putExtra(MapsActivity.DATAFASKES, data)
            startActivity(intent)
        }

    }

    private fun setData(item: DataItemFaskes){
        binding.namaFaskes.text = item.nama ?: "-"
        binding.alamatFaskes.text = item.alamat ?: "-"
        binding.telpFaskes.text = item.telp ?: "-"
        binding.statFaskes.text = item.status ?: "-"
        binding.jenisFaskes.text = item.jenisFaskes ?: "-"
        binding.dataSource.text = getString(R.string.data_diatas_berasal, item.sourceData)
        nullChecker(item)
    }
    private fun nullChecker(item: DataItemFaskes){
        if(item.status == "" || item.jenisFaskes == ""){
            binding.jenisFaskes.text = "-"
            binding.statFaskes.text ="-"
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    companion object{
        const val DATA = "DATA"
    }
}