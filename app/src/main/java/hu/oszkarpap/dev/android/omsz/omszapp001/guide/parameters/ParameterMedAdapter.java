package hu.oszkarpap.dev.android.omsz.omszapp001.guide.parameters;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class ParameterMedAdapter extends RecyclerView.Adapter<ParameterMedAdapter.ParameterMedViewHolder>{

    private ArrayList<ParametersMed> dataList;

    public ParameterMedAdapter(ArrayList<ParametersMed> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ParameterMedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ecw_par_child_med_dose_row, parent, false);
        return new ParameterMedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ParameterMedViewHolder holder, final int position) {
        holder.name.setText(dataList.get(position).getMedLabel());
        holder.dose.setText(dataList.get(position).getMedDose());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ParameterMedViewHolder extends RecyclerView.ViewHolder {

        TextView name,dose;
        SeekBar seekBar;

        ParameterMedViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.par_med_name_label);
            dose = itemView.findViewById(R.id.par_med_dose);
            seekBar = itemView.findViewById(R.id.par_med_sb);
        }
    }
    public void updateList(List<ParametersMed> newList) {

        dataList = new ArrayList<>();
        dataList.addAll(newList);
        notifyDataSetChanged();

    }

}
