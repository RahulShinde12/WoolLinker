

package com.capcun.newwoollinker;

//import static com.av.avmessenger.chatwindo.reciverIImg;
//import static com.av.avmessenger.chatwindo.senderImg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class messagesAdpter extends RecyclerView.Adapter<messagesAdpter.msgClass>{
    List<chats_datalead> data;
    final String sharedPreferencesFileTitle = "wool_linker";
    Context context;

    public messagesAdpter(List<chats_datalead> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public messagesAdpter.msgClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reciver_layout,parent,false);
        return new messagesAdpter.msgClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull messagesAdpter.msgClass holder, int position) {

        holder.msg.setText(data.get(position).getMessage());
        holder.date.setText(data.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class msgClass extends RecyclerView.ViewHolder
    {
        TextView msg,date;



        public msgClass(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.recivertextset);
            date = itemView.findViewById(R.id.date);


        }
    }

}
