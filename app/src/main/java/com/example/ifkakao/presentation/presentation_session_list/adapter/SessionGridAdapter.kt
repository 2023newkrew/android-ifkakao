package com.example.ifkakao.presentation.presentation_session_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ifkakao.R
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.presentation.presentation_session_list.fragment.SessionListFragmentListener

class SessionGridAdapter(var list: MutableList<Session>, private val listener: SessionListFragmentListener) : RecyclerView.Adapter<SessionGridAdapter.GridAdapter>() {

    class GridAdapter(layout: View) : RecyclerView.ViewHolder(layout){
// Adapter 에서 ClickEvent 설정
//            var layout: View
//        init {
//            this.layout = layout
//            layout.setOnClickListener(View.OnClickListener {
//                println("allen : $bindingAdapterPosition")
//            })
//            layout.setOnLongClickListener(View.OnLongClickListener {
//                println("allen : $bindingAdapterPosition Long")
//                return@OnLongClickListener true
//            })
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session_item, parent, false)

        return GridAdapter(view)
    }

    override fun onBindViewHolder(holder: GridAdapter, position: Int) {
        val textOne = holder.itemView.findViewById<TextView>(R.id.session_item_text_one)
        textOne.text = list[position].date
        val textTwo = holder.itemView.findViewById<TextView>(R.id.session_item_text_two)
        textTwo.text = list[position].company
        val textThree = holder.itemView.findViewById<TextView>(R.id.session_item_text_three)
        textThree.text = list[position].title
        val textFour = holder.itemView.findViewById<TextView>(R.id.session_item_text_four)
        var tempText = ""
        for (trackText in list[position].track) {
            tempText = "$tempText $trackText"
        }
        tempText = list[position].type + tempText
        textFour.text = tempText

        holder.itemView.setOnClickListener {
            listener.callBack(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}