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
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.Medication;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Parameters Adapter
 */

public class ParametersAdapter extends RecyclerView.Adapter<ParametersAdapter.ViewHolder> {


    private List<Medication> medications;
    private final LayoutInflater inflater;

    private String name, dose01, dose02, doseMaxPrefix, doseMax, doseMax02, dose03, doseDesc;
    private Medication medication;


    public ParametersAdapter(Context context, List<Medication> medications) {
        this.medications = medications;
        this.inflater = LayoutInflater.from(context);
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
        String parameters = name + dose01 + dose02 + dose03 + doseMaxPrefix + doseMax + doseMax02 + doseDesc;
        holder.name.setText(parameters);

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
        /*
         * Create Med Name always
         * */
        name = medication.getName() + ": ";

        /*
         * Create Child dose 01
         * (if have 2 dose, this is low dose, if low dose per kg is bigger max dose 01,
         * set the low dose is the max dose 01)
         * */

        if (!(medication.getChild01() == null)) {
            if (ParametersActivity.progressValue * Float.parseFloat(medication.getChild01())
                    > Float.parseFloat(medication.getChildDMax())) {
                dose01 = medication.getChildDMax() + " " + medication.getUnit() + " ";

            } else {
                dose01 = (String.valueOf(ParametersActivity.progressValue * Float.parseFloat(medication.getChild01())) +
                        " " + medication.getUnit());
            }
        } else {
            dose01 = "";
        }

        /*
         * Create Child dose 02
         * (if have 2 dose, this is high dose, if high dose per kg is bigger max dose 02,
         * set the high dose is the max dose 02)
         * */

        if (!(medication.getChild02() == null)) {
            if (ParametersActivity.progressValue * Float.parseFloat(medication.getChild02())
                    > Float.parseFloat(medication.getChildDMax02())) {
                dose02 = " - " + medication.getChildDMax02() + " " + medication.getUnit() + ". ";

            } else {
                dose02 = " - " + (String.valueOf(ParametersActivity.progressValue * Float.parseFloat(medication.getChild02())) +
                        " " + medication.getUnit() + " . ");
            }
        } else {
            dose02 = "";
        }

        /*
         * The recyclerView show the original Med dose per kg
         * */

        if (medication.getChild01() != null && medication.getChild02() != null) {
            dose03 = "(" + medication.getChild01() + " " + medication.getUnit() + "/ttkg - " +
                    medication.getChild02() + " " + medication.getUnit() + " /ttkg). ";
        } else if (medication.getChild01() != null && medication.getChild02() == null) {
            dose03 = "(" + medication.getChild01() + " " + medication.getUnit() + "/ttkg). ";
        } else {
            dose03 = "";
        }

        /*
         * If have got Max dose, show it
         * */

        if (medication.getChildDMax() != null || medication.getChildDMax02() != null) {
            doseMaxPrefix = "Max dózis: ";
        } else {
            doseMaxPrefix = "";
        }

        if (!(medication.getChildDMax() == null)) {
            doseMax = (medication.getChildDMax() + medication.getUnit() + "/ttkg ");

        } else {
            doseMax = "";
        }

        /*
         * If have got Max dose 02, show it
         * */

        if (!(medication.getChildDMax02() == null)) {
            doseMax02 = " - " + (medication.getChildDMax02() + medication.getUnit() + "/ttkg . ");

        } else {
            doseMax02 = "";
        }

        /*
         * If have other description about medication, show it on the end of TextView
         * */

        if (!(medication.getChild02() == null)) {
            doseDesc = " Egyéb: " + (medication.getChildDDesc());

        } else {
            doseDesc = "";
        }

    }


}
