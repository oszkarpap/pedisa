package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TintTypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity.SOPSearching;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the SOP Adapter
 */

public class SOPAdapter extends RecyclerView.Adapter<SOPAdapter.ViewHolder>{


    private final Context context;
    private List<SOP> sops;
    private LayoutInflater inflater;
    private OnItemLongClickListener longListener;
    private OnItemClickListener oneClickListener;
    private SOP sop;

    public SOPAdapter(Context context, List<SOP> sops) {
        this.context = context;
        this.sops = sops;
    }

    public SOPAdapter(Context context, List<SOP> sops, OnItemLongClickListener longListener, OnItemClickListener oneClickListener) {
        this.context = context;
        this.sops = sops;
        this.inflater= LayoutInflater.from(context);
        this.longListener = longListener;
        this.oneClickListener =  oneClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.row_sop,parent,false);
       ViewHolder holder=new ViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        sop= sops.get(position);
        holder.name.setText(sop.getName());
        holder.desc.setText(sop.getDesc());
        //holder.ini.setText(String.valueOf(sop.getName().charAt(0)));
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longListener.onItemLongClicked(position);

                return false;
            }
        });
        if(SOPSearching == 0 ) {
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneClickListener.onItemClicked(position);
            }
        });}else{
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oneClickListener.onItemClicked(sop);
                }
            });
        }
        //holder.setIsRecyclable(true);
         }

    @Override
    public int getItemCount() {
        return sops.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView name, desc;
        public final LinearLayout linearLayout;
        public final ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout= itemView.findViewById(R.id.row_sop_layout);
            //ini = itemView.findViewById(R.id.txt_sop_iniciale);
            name = itemView.findViewById(R.id.txt_sop_name);
            desc = itemView.findViewById(R.id.txt_sop_desc);
            icon = itemView.findViewById(R.id.row_sop_icon);

        }
    }

    public void updateList(List<SOP> newList) {

        sops = new ArrayList<>();
        sops.addAll(newList);
        notifyDataSetChanged();

    }

    public interface OnItemLongClickListener{
        void onItemLongClicked(int position);
    }

    public interface OnItemClickListener{
        void onItemClicked(int position);

        void onItemClicked(SOP sop);
    }
}
