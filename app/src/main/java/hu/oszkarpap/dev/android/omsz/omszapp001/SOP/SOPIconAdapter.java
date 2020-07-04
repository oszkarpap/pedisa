package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;



/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the SOP Adapter
 */

public class SOPIconAdapter extends RecyclerView.Adapter<SOPIconAdapter.ViewHolder> {


    private Context context;
    private List<SOPIcon> sopIcons;
    private LayoutInflater inflater;
    private OnItemLongClickListener longListener;
    private OnItemClickListener oneClickListener;
    private SOPIcon sopIcon;
    public static String sopIconTemp = "";

    public SOPIconAdapter(Context context, List<SOPIcon> sopIcons, SOPIcon sopIcon, OnItemClickListener oneClickListener ) {
        this.context = context;
        this.sopIcons = sopIcons;
        this.sopIcon = sopIcon;
        this.oneClickListener = oneClickListener;
    }

    public SOPIconAdapter(Context context, List<SOPIcon> sopicons, OnItemLongClickListener longListener, OnItemClickListener oneClickListener, SOPIcon sopIcon) {
        this.context = context;
        this.sopIcons = sopicons;
        this.inflater = LayoutInflater.from(context);
        this.longListener = longListener;
        this.oneClickListener = oneClickListener;
        this.sopIcon = sopIcon;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_create_icon_sop, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        sopIcon = sopIcons.get(position);
        holder.icon.setBackgroundResource(sopIcon.getIcon());
        //holder.ini.setText(String.valueOf(sop.getName().charAt(0)));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneClickListener.onItemClicked(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return sopIcons.size();
    }

    public void updateList(List<SOPIcon> newList) {

        sopIcons = new ArrayList<>();
        sopIcons.addAll(newList);
        notifyDataSetChanged();

    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout linearLayout;
        public final ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.row_create_icon_sop_layout);
            icon = itemView.findViewById(R.id.row_create_sop_icon);


        }
    }
}
