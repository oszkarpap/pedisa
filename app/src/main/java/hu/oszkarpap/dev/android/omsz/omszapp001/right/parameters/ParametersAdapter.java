package hu.oszkarpap.dev.android.omsz.omszapp001.right.parameters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.MedAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.Medication;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Parameters Adapter
 */

public class ParametersAdapter extends RecyclerView.Adapter<ParametersAdapter.ViewHolder> {

    private final Context context;
    private List<Medication> medications;
    private final LayoutInflater inflater;
    private OnItemLongClickListener listener;

    private String name, dose01, dose02, doseMax, doseMax02, dose03, doseDesc;
    private Medication medication;

    private String unit;


    public ParametersAdapter(Context context, List<Medication> medications, OnItemLongClickListener listener) {
        this.context = context;
        this.medications = medications;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_parameters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        medication = medications.get(position);
        makeParametersStringMethod();
        String parameters = name + dose01 + dose02 + dose03 + doseMax + doseDesc;

        holder.name.setText(parameters);
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
            name = itemView.findViewById(R.id.txt_parameters);


        }
    }

    /**
     * This method update the recyclerview Parameters via SearchView
     */

    public void updateList(List<Medication> newList) {

        medications = new ArrayList<>();
        medications.addAll(newList);
        notifyDataSetChanged();

    }

    /**
     * This method create the Parameters textView from Firebase Database constans and check the Seekbar (1-50Kg) and
     * perform the child drug dose (01 - 02 ), and max dose (01 - 02), and other descriptions
     */

    private void makeParametersStringMethod() {


        try {
            unit = " " + medication.getUnit();
        } catch (Exception e) {
            unit = "";
        }

        try {
            double a;
            try {
                a = Double.parseDouble(medication.getChildDMax());
            } catch (Exception e) {
                a = 100000;
            }

            if (Double.parseDouble(medication.getChild01()) * ParametersActivity.progressValue >
                    a) {

                dose01 = medication.getChildDMax() + unit;
            } else {
                double x = 1000 * Double.parseDouble(medication.getChild01()) * ParametersActivity.progressValue;

                int z = (int) x;
                x = ((double) z) / 1000;


                dose01 = String.valueOf(x) + unit;
            }
        } catch (Exception e) {
            dose01 = "";
        }

        try {
            double a;
            try {
                a = Double.parseDouble(medication.getChildDMax02());
            } catch (Exception e) {
                a = 100000;
            }

            if (Double.parseDouble(medication.getChild02()) * ParametersActivity.progressValue >
                    a) {
                dose02 = " - " + medication.getChildDMax02() + unit;
            } else {
                double x = 1000 * Double.parseDouble(medication.getChild02()) * ParametersActivity.progressValue;

                int z = (int) x;
                x = ((double) z) / 1000;

                dose02 = " - " + String.valueOf(x) + unit;
            }
        } catch (Exception e) {
            dose02 = "";
        }

        try {
            if (medication.getChildDMax() != null && !medication.getChildDMax02().equals("")) {
                doseMax = medication.getChildDMax();
            } else {
                doseMax = "";
            }
        } catch (Exception e) {
            doseMax = "";
        }

        try {
            if (medication.getChildDMax02() != null && !medication.getChildDMax02().equals("")) {
                doseMax02 = medication.getChildDMax02();
            } else {
                doseMax02 = "";
            }


        } catch (Exception e) {
            doseMax02 = "";
        }


        if (doseMax != "") {
            doseMax = " " + doseMax + unit;
        } else {
            doseMax = "";
        }
        if (doseMax02 != "") {
            doseMax += " - " + doseMax02 + unit;
        } else {
            doseMax += "";
        }

        if (doseMax != "") {
            doseMax = ". Max: " + doseMax + ". ";
        } else {
            doseMax += "";
        }



        try {

            name = medication.getName() + ": ";

        } catch (Exception e) {
            name = "";
        }

        try {
            if (dose01 != "") {
                dose03 = medication.getChild01() + unit;
            } else {
                dose03 = "";
            }
            if (dose02 != "") {
                dose03 += " - " + medication.getChild02() + unit;
            } else {
                dose03 += "";
            }

            if (dose03 != "") {
                dose03 = " (" + dose03 + " /ttkg)";
            } else {
                dose03 += "";
            }


        } catch (Exception e) {
            dose03 = "";
        }

        try {
            if (medication.getChildDDesc() != null) {
                doseDesc = " Egyéb leírás: " + medication.getChildDDesc();
            } else {
                doseDesc = "";
            }
        } catch (Exception e) {
            doseDesc = "";
        }

    }

    public interface OnItemLongClickListener{
        void onItemLongClicked(int position);
    }


}
