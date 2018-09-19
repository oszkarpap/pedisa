package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    private ArrayList<Medication> dataList;

    public MedicationAdapter(ArrayList<Medication> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MedicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_medication, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicationViewHolder holder, int position) {
        holder.txtMedName.setText(dataList.get(position).getMedName());
        holder.txtmedAgent.setText(dataList.get(position).getMedAgent());
        holder.txtMedPack.setText(dataList.get(position).getMedPack());
        holder.txtMedIndication.setText(dataList.get(position).getMedIndication());
        holder.txtMedContra.setText(dataList.get(position).getMedContra());
        holder.txtMedAdultDose.setText(dataList.get(position).getMedAdultDose());
        holder.txtMedChildDose.setText(dataList.get(position).getMedChildDose());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MedicationViewHolder extends RecyclerView.ViewHolder {

        TextView txtMedName, txtmedAgent, txtMedPack, txtMedIndication, txtMedContra, txtMedAdultDose, txtMedChildDose;

        MedicationViewHolder(View itemView) {
            super(itemView);
            txtMedName = (TextView) itemView.findViewById(R.id.txt_med_name);
            txtmedAgent = (TextView) itemView.findViewById(R.id.txt_med_agent);
            txtMedPack = (TextView) itemView.findViewById(R.id.txt_med_pack);
            txtMedIndication = (TextView) itemView.findViewById(R.id.txt_med_indication);
            txtMedContra = (TextView) itemView.findViewById(R.id.txt_med_contra);
            txtMedAdultDose = (TextView) itemView.findViewById(R.id.txt_med_adult_dose);
            txtMedChildDose = (TextView) itemView.findViewById(R.id.txt_med_child_dose);
        }
    }
}
