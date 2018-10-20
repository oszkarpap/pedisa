package hu.oszkarpap.dev.android.omsz.omszapp001.right.memory;

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
/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Medication Memory Adapter
 */

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder>{


    private final Context context;
    private List<Memory> memories;
    private final LayoutInflater inflater;
    private OnItemLongClickListener listener;

    public MemoryAdapter(Context context, List<Memory> memories, OnItemLongClickListener listener) {
        this.context = context;
        this.memories = memories;
        this.inflater= LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.li_memory,parent,false);
       ViewHolder holder=new ViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Memory memory=memories.get(position);
        holder.name.setText(memory.getName());
        holder.agent.setText(memory.getAgent());
        holder.pack.setText(memory.getPack());
        holder.ind.setText(memory.getInd());
        holder.contra.setText(memory.getCont());
        holder.adult.setText(memory.getAdult());
        holder.child.setText(memory.getChild());
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
        return memories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView name, agent, pack, ind, contra, adult, child;
        public final LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout= itemView.findViewById(R.id.layout_memory);
            name = itemView.findViewById(R.id.nameTV);
            agent = itemView.findViewById(R.id.agentTV);
            pack = itemView.findViewById(R.id.packTV);
            ind = itemView.findViewById(R.id.indTV);
            contra = itemView.findViewById(R.id.contTV);
            adult = itemView.findViewById(R.id.adultTV);
            child = itemView.findViewById(R.id.childTV);


        }
    }

    public void updateList(List<Memory> newList) {

        memories = new ArrayList<>();
        memories.addAll(newList);
        notifyDataSetChanged();

    }

    public interface OnItemLongClickListener{
        void onItemLongClicked(int position);
    }

}
