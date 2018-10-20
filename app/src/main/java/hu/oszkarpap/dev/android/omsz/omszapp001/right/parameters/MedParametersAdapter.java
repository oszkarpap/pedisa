package hu.oszkarpap.dev.android.omsz.omszapp001.right.parameters;

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
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.Medication;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Medication Adapter
 */

public class MedParametersAdapter extends RecyclerView.Adapter<MedParametersAdapter.ViewHolder> {


    private final Context context;
    private List<Medication> medications;
    private final LayoutInflater inflater;
    private OnItemLongClickListener listener;
    private String name;
    private String dose01;
    private String dose02;
    private String doseMax;
    private String doseDesc;

    public MedParametersAdapter(Context context, List<Medication> medications) {
        this.context = context;
        this.medications = medications;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_parameters, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Medication medication = medications.get(position);
        name = medication.getName() + ": ";

        if (!(medication.getChild01() == null)) {
            dose01 = (String.valueOf(ParametersActivity.progressValue * Double.parseDouble(medication.getChild01())) + " mg - ");

        } else {
            dose01 = "";
        }

        if (!(medication.getChild02() == null)) {
            dose02 = (String.valueOf(ParametersActivity.progressValue * Double.parseDouble(medication.getChild02())) + " mg.    Max: ");

        } else {
            dose02 = "";
        }
        if (!(medication.getChildDMax() == null)) {
            doseMax = (medication.getChildDMax() + ".    Egyéb: ");

        } else {
            doseMax = "";
        }
        if (!(medication.getChild02() == null)) {
            doseDesc = (medication.getChildDMax());

        } else {
            doseDesc = "";
        }

        holder.name.setText(name + dose01 + dose02 + doseMax + doseDesc);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView name;
        public final LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.row_par_layout);
            name = itemView.findViewById(R.id.txt_med_name);


        }
    }

    public void updateList(List<Medication> newList) {

        medications = new ArrayList<>();
        medications.addAll(newList);
        notifyDataSetChanged();

    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }


}
