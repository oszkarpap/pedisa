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
        holder.txtMedEmail.setText(dataList.get(position).getMedEmail());
        holder.txtMedPhone.setText(dataList.get(position).getMedPhone());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MedicationViewHolder extends RecyclerView.ViewHolder {

        TextView txtMedName, txtMedEmail, txtMedPhone;

        MedicationViewHolder(View itemView) {
            super(itemView);
            txtMedName = (TextView) itemView.findViewById(R.id.txt_med_name);
            txtMedEmail = (TextView) itemView.findViewById(R.id.txt_med_email);
            txtMedPhone = (TextView) itemView.findViewById(R.id.txt_med_phone);
        }
    }
}
