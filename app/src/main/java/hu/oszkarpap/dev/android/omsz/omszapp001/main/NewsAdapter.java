package hu.oszkarpap.dev.android.omsz.omszapp001.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem.GuideLineListItemAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOP;

import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity.SOPSearching;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the SOP Adapter
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {


    private final Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private List<News> newsList;
    private LayoutInflater inflater;
    private OnItemLongClickListener longListener;
    private News news;


    public NewsAdapter(Context context, List<News> newsList, OnItemLongClickListener longListener) {
        this.context = context;
        this.newsList = newsList;
        this.inflater = LayoutInflater.from(context);
        this.longListener = longListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_news, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        news = newsList.get(position);
        holder.date.setText(news.getDate());
        holder.name.setText(news.getName());
        holder.text.setText(news.getText());
        try {
            storageRef.child("images/" + news.getKey()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).resize(800, 800).centerInside().onlyScaleDown().into(holder.image);
                    holder.image.setVisibility(View.VISIBLE);
                    // Toast.makeText(context, "Sikeres "+uri, Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //  Toast.makeText(context, "Sikertelen "+exception.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        } catch (Exception e) {
            holder.image.setVisibility(View.INVISIBLE);
        }
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
        return newsList.size();
    }

    public void updateList(List<News> tempNewList) {

        newsList = new ArrayList<>();
        newsList.addAll(tempNewList);
        notifyDataSetChanged();

    }


    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView date, name, text;
        public final LinearLayout linearLayout;
        public final ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.row_news_layout);
            date = itemView.findViewById(R.id.row_news_date);
            name = itemView.findViewById(R.id.row_news_name);
            text = itemView.findViewById(R.id.row_news_text);
            image = itemView.findViewById(R.id.row_news_image);

        }
    }


}
