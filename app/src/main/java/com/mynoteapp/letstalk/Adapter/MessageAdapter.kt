package com.mynoteapp.letstalk.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.mynoteapp.letstalk.Modal.Message
import com.mynoteapp.letstalk.R


class MessageAdapter(private val context: android.content.Context, private val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEVIE = 1
    private val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == 1){  //INFLATE RECEIVE

            val view: View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            ReceiveViewHolder(view)

        }else{    //INFLATE SENT

            val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            SentViewHolder(view)
        }

    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if(holder.javaClass == SentViewHolder::class.java){
            // DO THE STUFF FOR SENT VIEW HOLDER

            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message



        }else{

            // DO THE STUFF FOR RECEIVE VIEW HOLDER
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message

        }
    }


    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            ITEM_SENT
        }else{
             return ITEM_RECEVIE
        }
    }


    override fun getItemCount(): Int {

        return messageList.size
    }


    

    class SentViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val sentMessage: TextView = itemView.findViewById(R.id.txt_sent_message)

    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView = itemView.findViewById(R.id.txt_receive_message)


    }
}


