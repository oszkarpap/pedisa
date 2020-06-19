package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.CreateGLActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GL;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the GLLI Adapter
 */

public class GuideLineListItemAdapter extends RecyclerView.Adapter<GuideLineListItemAdapter.ViewHolder> {


    private Context context;
    private List<GuideLineListItem> guideLineListItems;
    private LayoutInflater inflater;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    public GuideLineListItemAdapter(Context context, List<GuideLineListItem> gls) {
        this.context = context;
        this.guideLineListItems = gls;
        this.inflater = LayoutInflater.from(context);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_gl_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        GuideLineListItem guideLineListItem = guideLineListItems.get(position);
        //GuideLineListItem guideLineListItem = new GuideLineListItem("SAS", "SAS", "SAS", "SAS");
        //guideLineListItems.add(guideLineListItem);
        //guideLineListItemAdapter.notifyDataSetChanged();

        holder.item.setText(guideLineListItem.getItem());
        holder.attr.setText(guideLineListItem.getAttributum());



        if (holder.attr.getText().toString().contains("f10")) {
            holder.item.setTypeface(null, Typeface.BOLD);
        } else if (holder.attr.getText().toString().contains("f01")) {
            holder.item.setTypeface(null, Typeface.ITALIC);
        } else if (holder.attr.getText().toString().contains("f11")) {
            holder.item.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            holder.item.setTypeface(null, Typeface.NORMAL);
        }


        if (holder.attr.getText().toString().contains("s10")) {
            holder.item.setTypeface(null, Typeface.BOLD);
        } else if (holder.attr.getText().toString().contains("s01")) {
            holder.item.setTypeface(null, Typeface.ITALIC);
        } else if (holder.attr.getText().toString().contains("s11")) {
            holder.item.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            holder.item.setTypeface(null, Typeface.NORMAL);
        }

        if (holder.attr.getText().toString().contains("1s")) {
            holder.item.setPaintFlags(holder.item.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        if (holder.attr.getText().toString().contains("1X")) {
            holder.item.setPaintFlags(holder.item.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }


        if (holder.attr.getText().toString().contains("X1")) {
            String color = holder.attr.getText().toString().substring(10, 16);
            // Toast.makeText(context, ""+color, Toast.LENGTH_SHORT).show();
            holder.item.setTextColor(Color.parseColor("#" + color));
        }

        if (holder.attr.getText().toString().contains("Y1")) {
            String color2 = holder.attr.getText().toString().substring(18, 24);
            //  Toast.makeText(context, ""+color2, Toast.LENGTH_SHORT).show();
            holder.item.setTextColor(Color.parseColor("#" + color2));
        }


        //holder.ini.setText(String.valueOf(gl.getName().charAt(0)));



    }

    @Override
    public int getItemCount() {
        return guideLineListItems.size();
    }

    public void updateList(List<GuideLineListItem> newList) {

        guideLineListItems = new ArrayList<>();
        guideLineListItems.addAll(newList);
        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView item;
        public final TextView attr;
        public final LinearLayout linearLayout;


        public ViewHolder(final View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.row_guideLineListItemLinearLayout);
            item = itemView.findViewById(R.id.txt_glli_item);
            attr = itemView.findViewById(R.id.txt_glli_attr);



            // name.setTypeface(null, Typeface.BOLD_ITALIC);
            // name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        }


    }


}
