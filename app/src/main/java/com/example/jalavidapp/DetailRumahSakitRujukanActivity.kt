package com.example.jalavidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.jalavidapp.databinding.ActivityDetailRumahSakitRujukanBinding
import com.example.jalavidapp.model.RumahSakitItem

class DetailRumahSakitRujukanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRumahSakitRujukanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_rumah_sakit_rujukan)
        binding = ActivityDetailRumahSakitRujukanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = findViewById<TextView>(R.id.action_bar_title)
        title.text = getString(R.string.detail_rumah_sakit_rujukan)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        val data = intent.getParcelableExtra<RumahSakitItem>(DATA)!!
        setData(data)
        binding.mapButton.setOnClickListener {
            intent = Intent(this, MapsActivity::class.java)
            intent.putExtra(MapsActivity.DATARS, data)
            startActivity(intent)
        }
    }

    private fun setData(item: RumahSakitItem){
        binding.namaRS.text = item.nama.toString()
        binding.telpRS.text =  item.telepon ?: "-"
        binding.alamatRS.text = item.alamat
        binding.tempatTidurTersedia.text = item.tempatTidur.toString()

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