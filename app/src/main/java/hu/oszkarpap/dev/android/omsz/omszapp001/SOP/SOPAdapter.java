package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TintTypedArray;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity.SOPSearching;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the SOP Adapter
 */

public class SOPAdapter extends RecyclerView.Adapter<SOPAdapter.ViewHolder> {


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
        this.inflater = LayoutInflater.from(context);
        this.longListener = longListener;
        this.oneClickListener = oneClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_sop, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        sop = sops.get(position);
        holder.name.setText(Html.fromHtml(sop.getName()));
        holder.desc.setText(Html.fromHtml(sop.getDesc()));
        holder.iconText.setText(sop.getIcon());

        if (holder.iconText.getText().toString().contains("bag")) {
            holder.icon.setBackgroundResource(R.drawable.icon_bag);
        }
        if (holder.iconText.getText().toString().contains("ballon")) {
            holder.icon.setBackgroundResource(R.drawable.icon_ballon);

        }
        if (holder.iconText.getText().toString().contains("asthma")) {
            holder.icon.setBackgroundResource(R.drawable.icon_asthma);
        }
        if (holder.iconText.getText().toString().contains("vent")) {
            holder.icon.setBackgroundResource(R.drawable.icon_ventolin);
        }
        if (holder.iconText.getText().toString().contains("vent02")) {
            holder.icon.setBackgroundResource(R.drawable.icon_ventolin02);
        }
        if (holder.iconText.getText().toString().contains("tox")) {
            holder.icon.setBackgroundResource(R.drawable.icon_tox);
        }

        if (holder.iconText.getText().toString().contains("bed")) {
            holder.icon.setBackgroundResource(R.drawable.icon_bed);
        }
        if (holder.iconText.getText().toString().contains("sinus")) {
            holder.icon.setBackgroundResource(R.drawable.icon_sr);
        }
        if (holder.iconText.getText().toString().contains("vt")) {
            holder.icon.setBackgroundResource(R.drawable.icon_vt);
        }
        if (holder.iconText.getText().toString().contains("stemi")) {
            holder.icon.setBackgroundResource(R.drawable.icon_stemi);
        }
        if (holder.iconText.getText().toString().contains("brady")) {
            holder.icon.setBackgroundResource(R.drawable.icon_brady);
        }
        if (holder.iconText.getText().toString().contains("cpr")) {
            holder.icon.setBackgroundResource(R.drawable.icon_cpr);
        }
        if (holder.iconText.getText().toString().contains("cprhand")) {
            holder.icon.setBackgroundResource(R.drawable.icon_cpr_hand);
        }
        if (holder.iconText.getText().toString().contains("crossfour")) {
            holder.icon.setBackgroundResource(R.drawable.icon_cross_four);
        }
        if (holder.iconText.getText().toString().contains("crosssix")) {
            holder.icon.setBackgroundResource(R.drawable.icon_cross_six);
        }
        if (holder.iconText.getText().toString().contains("defi")) {

            holder.icon.setBackgroundResource(R.drawable.icon_defi);
        }
        if (holder.iconText.getText().toString().contains("drop")) {
            holder.icon.setBackgroundResource(R.drawable.icon_drop);
        }
        if (holder.iconText.getText().toString().contains("foni")) {
            holder.icon.setBackgroundResource(R.drawable.icon_fonendoscope);
        }
        if (holder.iconText.getText().toString().contains("foni02")) {
            holder.icon.setBackgroundResource(R.drawable.icon_fonendoscope02);
        }
        if (holder.iconText.getText().toString().contains("heart")) {
            holder.icon.setBackgroundResource(R.drawable.icon_heart);
        }
        if (holder.iconText.getText().toString().contains("heart02")) {
            holder.icon.setBackgroundResource(R.drawable.icon_heart02);
        }
        if (holder.iconText.getText().toString().contains("heart03")) {
            holder.icon.setBackgroundResource(R.drawable.icon_heart03);
        }

        if (holder.iconText.getText().toString().contains("inf")) {
            holder.icon.setBackgroundResource(R.drawable.icon_infusion);
        }
        if (holder.iconText.getText().toString().contains("inj")) {
            holder.icon.setBackgroundResource(R.drawable.icon_injection);
        }
        if (holder.iconText.getText().toString().contains("inj02")) {
            holder.icon.setBackgroundResource(R.drawable.icon_injection02);
        }
        if (holder.iconText.getText().toString().contains("lung")) {
            holder.icon.setBackgroundResource(R.drawable.icon_lung);
        }
        if (holder.iconText.getText().toString().contains("monitor")) {
            holder.icon.setBackgroundResource(R.drawable.icon_monitor);
        }
        if (holder.iconText.getText().toString().contains("patch")) {
            holder.icon.setBackgroundResource(R.drawable.icon_patch);
        }
        if (holder.iconText.getText().toString().contains("pill")) {
            holder.icon.setBackgroundResource(R.drawable.icon_pill);
        }
        if (holder.iconText.getText().toString().contains("scal")) {
            holder.icon.setBackgroundResource(R.drawable.icon_scalpel);
        }
        if (holder.iconText.getText().toString().contains("temp")) {
            holder.icon.setBackgroundResource(R.drawable.icon_temp);
        }
        if (holder.iconText.getText().toString().contains("wound")) {
            holder.icon.setBackgroundResource(R.drawable.icon_wound);
        }
        //holder.ini.setText(String.valueOf(sop.getName().charAt(0)));
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longListener.onItemLongClicked(position);

                return false;
            }
        });
        if (SOPSearching == 0) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oneClickListener.onItemClicked(position);
                }
            });
        } else {
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

    public void updateList(List<SOP> newList) {

        sops = new ArrayList<>();
        sops.addAll(newList);
        notifyDataSetChanged();

    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);

        void onItemClicked(SOP sop);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView name, desc, iconText;
        public final LinearLayout linearLayout;
        public final ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.row_sop_layout);
            //ini = itemView.findViewById(R.id.txt_sop_iniciale);
            name = itemView.findViewById(R.id.txt_sop_name);
            desc = itemView.findViewById(R.id.txt_sop_desc);
            icon = itemView.findViewById(R.id.row_sop_icon);
            iconText = itemView.findViewById(R.id.txt_sop_icon);

        }
    }
}
