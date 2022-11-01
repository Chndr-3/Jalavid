package com.example.jalavidapp

import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jalavidapp.adapter.TanyaJawabAdapter
import com.example.jalavidapp.databinding.ActivityTanyaJawabBinding
import com.example.jalavidapp.model.TanyaJawab
import java.util.ArrayList

class TanyaJawabActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTanyaJawabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityTanyaJawabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTanyaJawab.setHasFixedSize(true)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = findViewById<TextView>(R.id.action_bar_title)
        title.text = getString(R.string.tanya_jawab)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        setData(listTanyaJawab)
    }

    private val listTanyaJawab: ArrayList<TanyaJawab>
        get() {
            val dataQuestion = resources.getStringArray(R.array.data_question)
            val dataAnswer = resources.getStringArray(R.array.data_answer)
            val listTanyaJawab = ArrayList<TanyaJawab>()
            for (i in dataQuestion.indices) {
                val hero = TanyaJawab(dataQuestion[i],dataAnswer[i])
                listTanyaJawab.add(hero)
            }
            return listTanyaJawab
        }

    private fun setData(item : ArrayList<TanyaJawab>){
        val layoutManager = LinearLayoutManager(this)
        binding.rvTanyaJawab.layoutManager = layoutManager
        val listTipsAdapter = TanyaJawabAdapter(item)
        binding.rvTanyaJawab.adapter = listTipsAdapter

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}