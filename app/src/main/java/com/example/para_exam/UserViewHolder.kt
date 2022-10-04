package com.example.para_exam

import Beans.Post
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder (view: View):RecyclerView.ViewHolder(view){
    val  id=  view.findViewById<TextView>(R.id.txtBDId)
    val userId = view.findViewById<TextView>(R.id.txtBDUserId)
    val title = view.findViewById<TextView>(R.id.txtBDTitle)
    val body = view.findViewById<TextView>(R.id.txtBDBody)
    val update = view.findViewById<ImageButton>(R.id.btnEditItem)
    val delete = view.findViewById<ImageButton>(R.id.btnDeleteItem)
    val btnAceptar = view.findViewById<Button>(R.id.btnAceptar)
    val edit_title = view.findViewById<EditText>(R.id.edit_title)
    val edit_body =view.findViewById<EditText>(R.id.edit_body)
    val btnCancelar = view.findViewById<Button>(R.id.btnCancelar)
    val Editando = view.findViewById<LinearLayout>(R.id.Editando)
    val opciones = view.findViewById<LinearLayout>(R.id.opciones)
    fun render(posModel: tablePost){
        id.text = posModel.id.toString()
        userId.text = posModel.userid.toString()
        title.text = posModel.title
        body.text = posModel.body
    }
}
