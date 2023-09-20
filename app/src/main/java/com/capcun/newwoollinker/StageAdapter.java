package com.capcun.newwoollinker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageClass>{

    List<StageModelClass> data;
    final String sharedPreferencesFileTitle = "wool_linker";
    Context context;

    public StageAdapter(List<StageModelClass> data, Context context) {
        this.data = data;
        this.context = context;
    }



    @NonNull
    @Override
    public StageClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_stage_design,parent,false);
        return new StageAdapter.StageClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StageClass holder, int position) {

        holder.title.setText(data.get(position).getTitle());
        holder.sub_title.setText(data.get(position).getSub_tit());
        holder.desc.setText(data.get(position).getDesc());
        holder.stage_no.setText(data.get(position).getStage_no());

        Boolean isExpandable = data.get(position).getExpandable();
        holder.expandable.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class StageClass extends RecyclerView.ViewHolder
    {
        TextView title,sub_title,desc,stage_no;
        RelativeLayout relativeLayout;
        LinearLayout expandable;

        ImageView line_expand;



        public StageClass(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.stage_title);
            sub_title = itemView.findViewById(R.id.stage_sub_title);
            desc = itemView.findViewById(R.id.stage_desc);
            stage_no = itemView.findViewById(R.id.stage_no);

            relativeLayout = itemView.findViewById(R.id.relative1);
            expandable = itemView.findViewById(R.id.linear2);


            line_expand = itemView.findViewById(R.id.stage_line);
            line_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StageModelClass dataLead_backLog = data.get(getAdapterPosition());
                    dataLead_backLog.setExpandable(!dataLead_backLog.getExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

}
