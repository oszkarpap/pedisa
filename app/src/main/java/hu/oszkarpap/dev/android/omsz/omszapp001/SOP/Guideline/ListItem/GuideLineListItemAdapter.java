package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity.ShowNumber;
import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity.TEXTSIZE;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the GLLI Adapter
 */

public class GuideLineListItemAdapter extends RecyclerView.Adapter<GuideLineListItemAdapter.ViewHolder> {


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private Context context;
    private List<GuideLineListItem> guideLineListItems;
    private LayoutInflater inflater;
    private OnItemLongClickListener longListener;
    public static final String LIST_ITEM_COUNT = "LIST_ITEM_COUNT";
    public static final String LIST_ITEM_NAME = "LIST_ITEM_NAME";
    public static final String LIST_ITEM_PARENT = "LIST_ITEM_PARENT";
    public static final String LIST_ITEM_SEC_KEY = "LIST_ITEM_SEC_KEY";

    public GuideLineListItemAdapter(Context context, List<GuideLineListItem> gls, OnItemLongClickListener longListener) {
        this.context = context;
        this.guideLineListItems = gls;
        this.inflater = LayoutInflater.from(context);
        this.longListener = longListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_gl_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final GuideLineListItem guideLineListItem = guideLineListItems.get(position);
        //GuideLineListItem guideLineListItem = new GuideLineListItem("SAS", "SAS", "SAS", "SAS");
        //guideLineListItems.add(guideLineListItem);
        //guideLineListItemAdapter.notifyDataSetChanged();

        holder.item.setText(Html.fromHtml(guideLineListItem.getItem()));

        holder.attr.setText(guideLineListItem.getAttributum());
        holder.count.setText("("+String.valueOf(guideLineListItem.getCount())+")");
        holder.count.setVisibility(View.INVISIBLE);
        holder.parent.setText(guideLineListItem.getParent());
        holder.secKey.setText(guideLineListItem.getSecondKey());
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(context, "BÖFF"+holder.item.getText()+"\n"+holder.attr.getText()+"\n"+holder.count.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CreateGLListItemActivity.class);
                String temp = holder.item.getText().toString();
                String temp2 = holder.count.getText().toString().replace("(","").replace(")","");
                intent.putExtra(LIST_ITEM_NAME, temp);
                intent.putExtra(LIST_ITEM_COUNT, temp2);
                intent.putExtra(LIST_ITEM_PARENT, holder.parent.getText());
                intent.putExtra(LIST_ITEM_SEC_KEY, holder.secKey.getText());
                context.startActivity(intent);
                return false;
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Módosításhoz kattintson hosszan!", Toast.LENGTH_SHORT).show();

            }
        });


        String savedImage = holder.secKey.getText().toString();
        //Toast.makeText(context, "" + savedImage, Toast.LENGTH_SHORT).show();
        storageRef.child("images/" + savedImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(800, 800).centerInside().onlyScaleDown().into(holder.photoView);

                // Toast.makeText(context, "Sikeres "+uri, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //  Toast.makeText(context, "Sikertelen "+exception.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });



        //holder.item.setText(guideLineListItem.getItem());

       // Toast.makeText(context, ""+holder.attr.getText().toString(), Toast.LENGTH_SHORT).show();

        new CountDownTimer(90000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Toast.makeText(context, "RUN", Toast.LENGTH_SHORT).show();
                holder.item.setTextSize(TEXTSIZE);

                if(ShowNumber%2 == 0){
                    holder.count.setVisibility(View.INVISIBLE);}else{
                    holder.count.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                // Toast.makeText(context, "FINISH", Toast.LENGTH_SHORT).show();
                holder.item.setTextSize(TEXTSIZE);
                if(ShowNumber%2 == 0){
                    holder.count.setVisibility(View.INVISIBLE);}else{
                    holder.count.setVisibility(View.VISIBLE);
                }
            }
        }.start();
        holder.item.setTextSize(TEXTSIZE);

       /* if (holder.attr.getText().toString().contains("f10")) {
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
*/

        if (holder.attr.getText().toString().contains("X")) {

            String color = holder.attr.getText().toString().substring(7, 13);
//             Toast.makeText(context, ""+color, Toast.LENGTH_SHORT).show();
            holder.item.setTextColor(Color.parseColor("#" + color));
            holder.count.setTextColor(Color.parseColor("#"+color));
        }

        if (holder.attr.getText().toString().contains("Y")) {
            //String color2 = holder.attr.getText().toString().substring(18, 24);
            //  Toast.makeText(context, ""+color2, Toast.LENGTH_SHORT).show();
            //holder.item.setTextColor(Color.parseColor("#" + color2));
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

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView item;
        public final TextView attr;
        public final TextView count;
        public final TextView parent, secKey;
        public final LinearLayout linearLayout;
        public Context context;
        public PhotoView photoView;

        public ViewHolder(final View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.row_guideLineListItemLinearLayout);
            count = itemView.findViewById(R.id.txt_glli_count);

            item = itemView.findViewById(R.id.txt_glli_item);
            attr = itemView.findViewById(R.id.txt_glli_attr);
            parent = itemView.findViewById(R.id.txt_glli_parent);
            parent.setVisibility(View.INVISIBLE);
            attr.setVisibility(View.INVISIBLE);
            photoView = itemView.findViewById(R.id.row_glli_image);

            secKey = itemView.findViewById(R.id.txt_glli_secKey);
            secKey.setVisibility(View.INVISIBLE);


            // name.setTypeface(null, Typeface.BOLD_ITALIC);
            // name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        }


    }

}
