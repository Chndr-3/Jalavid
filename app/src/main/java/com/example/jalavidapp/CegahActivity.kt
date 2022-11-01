package com.example.jalavidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jalavidapp.adapter.CegahAdapter
import com.example.jalavidapp.databinding.ActivityCegahBinding
import com.example.jalavidapp.model.Cegah
import java.util.ArrayList

class CegahActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCegahBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCegahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = findViewById<TextView>(R.id.action_bar_title)
        title.text = getString(R.string.mencegah_penularan_covid_19)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
        setData(listCegah)
    }
    private fun setData(item : ArrayList<Cegah>){
        val layoutManager = LinearLayoutManager(this)
        binding.rvCegah.layoutManager = layoutManager
        val listTipsAdapter = CegahAdapter(item)
        binding.rvCegah.adapter = listTipsAdapter

    }
    private val listCegah: ArrayList<Cegah>
        get() {
            val cegahImage = resources.obtainTypedArray(R.array.image)
            val prevention = resources.getStringArray(R.array.prevention)
            val preventionDescription = resources.getStringArray(R.array.preventionDescription)
            val listCegah = ArrayList<Cegah>()
            for (i in prevention.indices) {
                val tips = Cegah(cegahImage.getResourceId(i, -1),prevention[i],preventionDescription[i])
                listCegah.add(tips)
            }
            return listCegah
        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}