package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the GL Adapter
 */

public class GLAdapter extends RecyclerView.Adapter<GLAdapter.ViewHolder> {


    private Context context;
    private List<GL> gls;
    private LayoutInflater inflater;
    private OnItemLongClickListener longListener;
    private View.OnClickListener onClickListener;


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
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        GL gl = gls.get(position);
        holder.name.setText(gl.getName());
        holder.desc.setText(gl.getDesc());
        holder.attr.setText(gl.getAttr());



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

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView  name, desc, attr;
        public final LinearLayout linearLayout;
        public final Button arrowButton;
        LinearLayout expandableView;
        Button arrowBtn;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.row_gl_layout);
            //  ini = itemView.findViewById(R.id.txt_gl_iniciale);
            name = itemView.findViewById(R.id.txt_gl_name);
            desc = itemView.findViewById(R.id.txt_gl_desc);
            attr = itemView.findViewById(R.id.txt_gl_attr);
            attr.setVisibility(View.INVISIBLE);
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


    }


}
