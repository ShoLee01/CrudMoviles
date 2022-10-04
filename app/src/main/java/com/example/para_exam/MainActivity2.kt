package com.example.para_exam

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import com.example.para_exam.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {
    lateinit var Lista : List<tablePost>
    lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val recycler : RecyclerView = findViewById(R.id.recycler)
        val appDB:AppDataBase by lazy { AppDataBase.getDatabase(this) }
        val btnRefresh = findViewById<Button>(R.id.btn_Refresh)
        recycler.layoutManager=LinearLayoutManager(applicationContext)

        btnRefresh.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                Lista = appDB.postDao().getAll()
                adapter = Adapter(Lista)
                runOnUiThread {
                    recycler.adapter = adapter
                }
            }
        }

        runOnUiThread {
            GlobalScope.launch( Dispatchers.IO) {
                Lista= appDB.postDao().getAll()
                adapter = Adapter(Lista)
                adapter.notifyDataSetChanged()
                recycler.adapter=adapter
            }
        }
    }




}