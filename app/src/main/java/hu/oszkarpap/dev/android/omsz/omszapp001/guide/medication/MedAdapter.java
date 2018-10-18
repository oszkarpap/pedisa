package hu.oszkarpap.dev.android.omsz.omszapp001.guide.medication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;


public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder>{


    private final Context context;
    private List<Medication> medications;
    private final LayoutInflater inflater;
    private OnItemLongClickListener listener;

    public MedAdapter(Context context, List<Medication> medications, OnItemLongClickListener listener) {
        this.context = context;
        this.medications = medications;
        this.inflater= LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.row_medication,parent,false);
       ViewHolder holder=new ViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Medication medication= medications.get(position);
        holder.name.setText(medication.getName());
        holder.agent.setText(medication.getAgent());
        holder.pack.setText(medication.getPack());
        holder.ind.setText(medication.getInd());
        holder.contra.setText(medication.getCont());
        holder.adult.setText(medication.getAdult());
        holder.child.setText(medication.getChild());
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClicked(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView name, agent, pack, ind, contra, adult, child;
        public final LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout= itemView.findViewById(R.id.row_med_layout);
            name = itemView.findViewById(R.id.txt_med_name);
            agent = itemView.findViewById(R.id.txt_med_agent);
            pack = itemView.findViewById(R.id.txt_med_pack);
            ind = itemView.findViewById(R.id.txt_med_indication);
            contra = itemView.findViewById(R.id.txt_med_contra);
            adult = itemView.findViewById(R.id.txt_med_adult_dose);
            child = itemView.findViewById(R.id.txt_med_child_dose);


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

}
