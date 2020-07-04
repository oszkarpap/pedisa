package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.CreateSOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem.GuideLineListItem;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem.GuideLineListItemAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SingleChoiceDialogFragment;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;

import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity.ShowNumber;
import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity.TEXTSIZE;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the GL Adapter
 */

public class GLAdapter extends RecyclerView.Adapter<GLAdapter.ViewHolder> implements GuideLineListItemAdapter.OnItemLongClickListener {


    public static String savedImage;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private Context context;
    private List<GL> gls;
    private GuideLineListItem guideLineListItem;
    private LayoutInflater inflater;
    private OnItemLongClickListener longListener;
    private View.OnClickListener onClickListener;
    private GuideLineListItemAdapter guideLineListItemAdapter;


    public GLAdapter(Context context, List<GL> gls) {
        this.context = context;
        this.gls = gls;
    }

    public GLAdapter(Context context, List<GL> gls, OnItemLongClickListener longListener) {
        this.context = context;
        this.gls = gls;
        this.inflater = LayoutInflater.from(context);
        this.longListener = longListener;
        this.onClickListener = onClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_gl, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        GL gl = gls.get(position);

        holder.name.setText(gl.getName());
        holder.desc.setText(gl.getDesc());
        holder.attr.setText(gl.getAttr());
        holder.key.setText(gl.getAsc());

        //Toast.makeText(context, ""+holder.key.getText().toString(), Toast.LENGTH_SHORT).show();
        final List<GuideLineListItem> guideLineListItems = new ArrayList<>();
        //TODO:
        guideLineListItemAdapter = new GuideLineListItemAdapter(context, guideLineListItems, this);
        // holder.recyclerViewList.setHasFixedSize(true);
        holder.recyclerViewList.setLayoutManager(new LinearLayoutManager(holder.context));
        holder.recyclerViewList.setAdapter(guideLineListItemAdapter);

        // holder.recyclerViewList.setNestedScrollingEnabled(false);

        //GuideLineListItem guideLineListItem = new GuideLineListItem("SAs", "SAS", "ASA", "ASA");

        //guideLineListItems.add(guideLineListItem);
        // Toast.makeText(context, "ADAPTER", Toast.LENGTH_SHORT).show();


        FirebaseDatabase.getInstance().getReference().child("glli").child(holder.key.getText().toString()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //GuideLineListItem glli = dataSnapshot.getValue(GuideLineListItem.class);

                GuideLineListItem guideLineListItem = new GuideLineListItem();
                guideLineListItem.setKey(dataSnapshot.child("key").getValue().toString());
                guideLineListItem.setAttributum(dataSnapshot.child("attributum").getValue().toString());
                guideLineListItem.setItem(dataSnapshot.child("item").getValue().toString());
                guideLineListItem.setCount(dataSnapshot.child("count").getValue().toString());
                guideLineListItem.setParent(dataSnapshot.child("parent").getValue().toString());
                guideLineListItem.setSecondKey(dataSnapshot.child("secondKey").getValue().toString());
                //Toast.makeText(context, ""+guideLineListItem.getItem(), Toast.LENGTH_SHORT).show();
                guideLineListItems.add(guideLineListItem);
                Collections.sort(guideLineListItems);

                //}
                // guideLineListItemAdapter.updateList(guideLineListItems);
                guideLineListItemAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                GuideLineListItem guideLineListItem = new GuideLineListItem();
                guideLineListItem.setKey(dataSnapshot.child("key").getValue().toString());
                guideLineListItem.setAttributum(dataSnapshot.child("attributum").getValue().toString());
                guideLineListItem.setItem(dataSnapshot.child("item").getValue().toString());
                guideLineListItem.setCount(dataSnapshot.child("count").getValue().toString());
                guideLineListItem.setParent(dataSnapshot.child("parent").getValue().toString());
                guideLineListItem.setSecondKey(dataSnapshot.child("secondKey").getValue().toString());
                guideLineListItems.remove(guideLineListItem);
                guideLineListItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //guideLineListItemAdapter.notifyDataSetChanged();


        holder.numb.setText(String.valueOf(gl.getCount()));
        savedImage = holder.key.getText().toString();
        //Toast.makeText(context, "" + savedImage, Toast.LENGTH_SHORT).show();
        storageRef.child("images/" + savedImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(800, 800).centerInside().onlyScaleDown().into(holder.imageView);

                // Toast.makeText(context, "Sikeres "+uri, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //  Toast.makeText(context, "Sikertelen "+exception.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

        new CountDownTimer(90000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Toast.makeText(context, "RUN", Toast.LENGTH_SHORT).show();
                holder.name.setTextSize(TEXTSIZE);
                holder.desc.setTextSize(TEXTSIZE);
                if (ShowNumber % 2 == 0) {
                    holder.numb.setVisibility(View.INVISIBLE);
                } else {
                    holder.numb.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                // Toast.makeText(context, "FINISH", Toast.LENGTH_SHORT).show();
                holder.name.setTextSize(TEXTSIZE);
                holder.desc.setTextSize(TEXTSIZE);
                if (ShowNumber % 2 == 0) {
                    holder.numb.setVisibility(View.INVISIBLE);
                } else {
                    holder.numb.setVisibility(View.VISIBLE);
                }
            }
        }.start();
        holder.name.setTextSize(TEXTSIZE);
        holder.desc.setTextSize(TEXTSIZE);

        /**if (holder.attr.getText().toString().contains("W12")) {
         holder.name.setTextSize(16);
         } else if (holder.attr.getText().toString().contains("W14")) {
         holder.name.setTextSize(40);
         } else if (holder.attr.getText().toString().contains("W16")){
         holder.name.setTextSize(60);
         }

         if (holder.attr.getText().toString().contains("Z12")) {
         holder.desc.setTextSize(16);
         } else if (holder.attr.getText().toString().contains("Z14")) {
         holder.desc.setTextSize(40);
         } else if (holder.attr.getText().toString().contains("Z16")){
         holder.desc.setTextSize(60);
         } */


        if (holder.attr.getText().toString().contains("f10")) {
            holder.name.setTypeface(null, Typeface.BOLD);
        } else if (holder.attr.getText().toString().contains("f01")) {
            holder.name.setTypeface(null, Typeface.ITALIC);
        } else if (holder.attr.getText().toString().contains("f11")) {
            holder.name.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            holder.name.setTypeface(null, Typeface.NORMAL);
        }


        if (holder.attr.getText().toString().contains("s10")) {
            holder.desc.setTypeface(null, Typeface.BOLD);
        } else if (holder.attr.getText().toString().contains("s01")) {
            holder.desc.setTypeface(null, Typeface.ITALIC);
        } else if (holder.attr.getText().toString().contains("s11")) {
            holder.desc.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            holder.desc.setTypeface(null, Typeface.NORMAL);
        }

        if (holder.attr.getText().toString().contains("1s")) {
            holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        if (holder.attr.getText().toString().contains("1X")) {
            holder.desc.setPaintFlags(holder.desc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }


        if (holder.attr.getText().toString().contains("X1")) {
            String color = holder.attr.getText().toString().substring(10, 16);
            // Toast.makeText(context, ""+color, Toast.LENGTH_SHORT).show();
            holder.name.setTextColor(Color.parseColor("#" + color));
        }

        if (holder.attr.getText().toString().contains("Y1")) {
            String color2 = holder.attr.getText().toString().substring(18, 24);
            //  Toast.makeText(context, ""+color2, Toast.LENGTH_SHORT).show();
            holder.desc.setTextColor(Color.parseColor("#" + color2));
        }


        //holder.ini.setText(String.valueOf(gl.getName().charAt(0)));
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longListener.onItemLongClicked(position);

                return false;
            }
        });




    }

    @Override
    public int getItemCount() {
        return gls.size();
    }

    public void updateList(List<GL> newList) {

        gls = new ArrayList<>();
        gls.addAll(newList);
        notifyDataSetChanged();

    }

    @Override
    public void onItemLongClicked(int position) {

    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements GuideLineListItemAdapter.OnItemLongClickListener {


        public final TextView name, desc, attr, numb, key, asc;
        public final LinearLayout linearLayout;
        public final Button arrowButton;
        public final PhotoView imageView;
        public final RecyclerView recyclerViewList;
        public Context context;
        LinearLayout expandableView;
        Button arrowBtn;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.row_gl_layout);
            //  ini = itemView.findViewById(R.id.txt_gl_iniciale);
            imageView = itemView.findViewById(R.id.row_gl_image);


            recyclerViewList = itemView.findViewById(R.id.recycler_view_gl_list);
            numb = itemView.findViewById(R.id.txt_gl_number);
            name = itemView.findViewById(R.id.txt_gl_name);
            desc = itemView.findViewById(R.id.txt_gl_desc);
            attr = itemView.findViewById(R.id.txt_gl_attr);
            key = itemView.findViewById(R.id.txt_gl_key);
            asc = itemView.findViewById(R.id.txt_gl_asc);
            asc.setVisibility(View.INVISIBLE);

            attr.setVisibility(View.INVISIBLE);
            key.setVisibility(View.INVISIBLE);
            arrowButton = itemView.findViewById(R.id.gl_row_button);
            expandableView = itemView.findViewById(R.id.expandableView);
            arrowBtn = itemView.findViewById(R.id.gl_row_button);
            cardView = itemView.findViewById(R.id.gl_cardView);

            // name.setTypeface(null, Typeface.BOLD_ITALIC);
            // name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            arrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView.getVisibility() == View.GONE) {
                        expandableView.setVisibility(View.VISIBLE);
                        arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    } else {
                        expandableView.setVisibility(View.GONE);
                        arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }
                }
            });
        }


        @Override
        public void onItemLongClicked(int position) {

        }


    }


}
