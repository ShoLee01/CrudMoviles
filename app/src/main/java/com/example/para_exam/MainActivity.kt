package com.example.para_exam

import Beans.Post
import Interface.PlaceHolderApi
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var service:PlaceHolderApi
    lateinit var postsList: List<Post>
    lateinit var pos : Post
    lateinit var appDataBase: AppDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize variables
        val txtId : TextView = findViewById(R.id.txtId)
        var btnMostrar : Button = findViewById(R.id.btnMostrar)
        val btnConsultar : Button = findViewById(R.id.btnConsultar)
        val txtRes :TextView = findViewById(R.id.txtRes)
        val btnGrabar : Button = findViewById(R.id.btnGrabar)
        // Retrofit
        service = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlaceHolderApi::class.java)
        // Initialize database
        appDataBase = AppDataBase.getDatabase(this)
        //
        btnGrabar.setOnClickListener {
            // Grabar en la base de datos
            val regPost = tablePost(pos.id,pos.userId,pos.title,pos.body)
            GlobalScope.launch( Dispatchers.IO) {
                appDataBase.postDao().insert(regPost)
            }
            val txtRes:TextView=findViewById(R.id.txtRes)
            txtRes.text="Se registro con Exito"
        }



        btnConsultar.setOnClickListener {
            txtRes.text = ""
            getById(txtId.text.toString().toInt());
            //getByUserId(txtId.text.toString().toInt());
        }

        btnGrabar.setOnClickListener{
            val regPost=tablePost(pos.id,pos.userId,pos.title,pos.body)

            GlobalScope.launch( Dispatchers.IO) {
                appDataBase.postDao().insert(regPost)
            }

            val txtRes:TextView=findViewById(R.id.txtRes)
            txtRes.text="Se registro con Exito"
        }
        btnMostrar.setOnClickListener{
            Intent(this, MainActivity2::class.java).also {
                startActivity(it)
            }
        }

        //getAllUsers();

    }

    private fun postDaofun(Vale: Int) {
        service.getById(Vale).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val pos = response.body()
                //(id: Int, title: String, body: String, userId: Int)
                //tablePost = tablePost(pos!!.id, pos.title, pos.body, pos.userId)
                if (pos != null) {
                    lifecycleScope.launch{
                        //postDao.insert(post)
                    }
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

        })
    }

    private fun getAllUsers() {
        service.getListado().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
               var pos = response?.body()!!
                val txtRes :TextView = findViewById(R.id.txtRes)
                if (pos != null) {
                    for (p in pos){
                        var texto: String = ""
                        texto += ("Id: ${p.id} \n")
                        texto += ("UserId: ${p.userId} \n")
                        texto += ("Body: ${p.body} \n")
                        texto += ("Title: ${p.title} \n\n")
                        txtRes.append(texto)
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

        })
    }

    private fun getById(Vale:Int){
        service.getById(Vale).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val txtRes :TextView = findViewById(R.id.txtRes)
                    pos = response?.body()!!
                if (pos != null) {
                    var texto: String = ""
                    texto += ("Id: ${pos.id} \n")
                    texto += ("UserId: ${pos.userId} \n")
                    texto += ("Body: ${pos.body} \n")
                    texto += ("Title: ${pos.title} \n\n")
                    txtRes.append(texto)
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

        })
    }

    private fun getByUserId(Vale:Int){
        service.getByUserId(Vale).enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val pos = response.body()
                val txtRes :TextView = findViewById(R.id.txtRes)
                if (pos != null) {
                    for (p in pos){
                        var texto: String = ""
                        texto += ("Id: ${p.id} \n")
                        texto += ("UserId: ${p.userId} \n")
                        texto += ("Body: ${p.body} \n")
                        texto += ("Title: ${p.title} \n\n")
                        txtRes.append(texto)
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

        })
    }
}