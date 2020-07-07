package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

import static android.support.v7.app.AlertDialog.*;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Medication Adapter
 */

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder>{


    private final Context context;
    private List<Medication> medications;
    private final LayoutInflater inflater;
    private OnItemLongClickListener listener;
    private OnItemClickListener oneClickListener;
    private Medication medication;

    public MedAdapter(Context context, List<Medication> medications, OnItemLongClickListener listener, OnItemClickListener oneClickListener) {
        this.context = context;
        this.medications = medications;
        this.inflater= LayoutInflater.from(context);
        this.listener = listener;
        this.oneClickListener = oneClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.row_medication,parent,false);
       ViewHolder holder=new ViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        medication= medications.get(position);
        try {
            holder.ini.setText("" + medication.getName().toUpperCase().charAt(0) + medication.getName().toLowerCase().charAt(1));
        }catch (Exception e)
        {
            holder.ini.setText("" + medication.getName().toUpperCase().charAt(0));
        }
        holder.name.setText(Html.fromHtml(medication.getName()));
        holder.agent.setText(Html.fromHtml(medication.getAgent()));
        //holder.pack.setText(medication.getPack());
        //holder.ind.setText(medication.getInd());
        //holder.contra.setText(medication.getCont());
        //holder.adult.setText(medication.getAdult());
        //holder.child.setText(medication.getChild());
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClicked(position);
                return false;
            }
        });
        if(MedActivity.Searching==0){
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneClickListener.onItemClicked(position);

            }
        });}else{
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oneClickListener.onItemClicked(medication);


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView ini, name, agent;
        //pack, ind, contra, adult, child;
        public final LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout= itemView.findViewById(R.id.row_med_layout);

            ini = itemView.findViewById(R.id.txt_med_ini);
            name = itemView.findViewById(R.id.txt_med_name);
            agent = itemView.findViewById(R.id.txt_med_agent);
          //  pack = itemView.findViewById(R.id.txt_med_pack);
          //  ind = itemView.findViewById(R.id.txt_med_indication);
          //  contra = itemView.findViewById(R.id.txt_med_contra);
          //  adult = itemView.findViewById(R.id.txt_med_adult_dose);
          //  child = itemView.findViewById(R.id.txt_med_child_dose);


        }
    }

    public void updateList(List<Medication> newList) {

        medications = new ArrayList<>();
        medications.addAll(newList);
        notifyDataSetChanged();

    }

    public interface OnItemLongClickListener{
        void onItemLongClicked(int position);
    }


    public interface OnItemClickListener{
        void onItemClicked(int position);

        void onItemClicked(Medication medication);
    }

    
    public void SAS(){
        Builder builder = null;

        // Get the layout inflater
        //  LayoutInflater inflater = getLayoutInflater();

        TextView name = null, agent=null, pack=null, ind=null, contr=null, adult=null, child=null;
        name.findViewById(R.id.dialog_med_name);
        agent.findViewById(R.id. dialog_med_agent);
        pack.findViewById(R.id.dialog_med_pack);
        ind.findViewById(R.id.dialog_med_ind);
        contr.findViewById(R.id.dialog_med_contr);
        adult.findViewById(R.id.dialog_med_adult);
        child .findViewById(R.id.dialog_med_child);
        builder.setView(inflater.inflate(R.layout.dialog_med, null))
                // Add action buttons

                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();

    }

}
