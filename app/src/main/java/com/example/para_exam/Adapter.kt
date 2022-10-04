package com.example.para_exam

import Beans.Post
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Adapter(val listaPost:List<tablePost>): RecyclerView.Adapter<UserViewHolder>() {
    lateinit var pos : Post
    lateinit var appDataBase: AppDataBase
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val layoutInflater=LayoutInflater.from(parent.context)
        return UserViewHolder(layoutInflater.inflate(R.layout.view_item,parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // Initialize database
        appDataBase = AppDataBase.getDatabase(holder.itemView.context)
        val item=listaPost[position]
        holder.render(item)
        holder.update.setOnClickListener {
            Toast.makeText(holder.itemView.context,"Editando",Toast.LENGTH_SHORT).show()
            holder.Editando.visibility= View.VISIBLE
            holder.opciones.visibility= View.GONE
            holder.edit_body.setText(item.body.toString())
            holder.edit_title.setText(item.title.toString())
        }
        holder.btnAceptar.setOnClickListener {
            Toast.makeText(holder.itemView.context,item.userid!!.toString(),Toast.LENGTH_SHORT).show()
            val regPost=tablePost(item.id!!.toInt(),item.userid!!.toInt(),holder.edit_title.text.toString(),holder.edit_body.text.toString())
            GlobalScope.launch(Dispatchers.IO) {
                appDataBase.postDao().update(regPost)
            }
            Toast.makeText(holder.itemView.context,"Editado",Toast.LENGTH_SHORT).show()
            holder.Editando.visibility= View.GONE
            holder.opciones.visibility= View.VISIBLE
            // Refresh recycler view

        }
        holder.delete.setOnClickListener {
            Toast.makeText(holder.itemView.context,"Eliminado",Toast.LENGTH_SHORT).show()
            val regPost=tablePost(item.id!!.toInt(),item.userid!!.toInt(),item.title.toString(),item.body.toString())
            GlobalScope.launch(Dispatchers.IO) {
                appDataBase.postDao().delete(regPost)
            }
            // Refresh recycler view

        }
        holder.btnCancelar.setOnClickListener {
            Toast.makeText(holder.itemView.context,"Cancelando",Toast.LENGTH_SHORT).show()
            holder.Editando.visibility= View.GONE
            holder.opciones.visibility= View.VISIBLE
        }

    }

    override fun getItemCount(): Int =listaPost.size
}