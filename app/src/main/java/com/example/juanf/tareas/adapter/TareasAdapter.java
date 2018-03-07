package com.example.juanf.tareas.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juanf.tareas.R;
import com.example.juanf.tareas.model.Tarea;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {
    public ArrayList<Tarea> tareas;

    public TareasAdapter(){
        this.tareas = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textName) TextView name;
        @BindView(R.id.textDescription) TextView description;
        @BindView(R.id.textImportancia) TextView importancia;
        @BindView(R.id.textDeadLine) TextView deadLine;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View tareaView = inflater.inflate(R.layout.item_view, parent, false);
        return new ViewHolder(tareaView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        holder.name.setText(tarea.getName());
        holder.description.setText(tarea.getDescription());
        holder.importancia.setText("Importancia: "+tarea.getImportancia());
        holder.deadLine.setText(tarea.getDeadline());
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public Tarea getAt(int position){
        Tarea tarea;
        tarea = this.tareas.get(position);
        return  tarea;
    }

    public void add(Tarea tarea) {
        this.tareas.add(tarea);
        notifyItemInserted(tareas.size() - 1);
        notifyItemRangeChanged(0, tareas.size() - 1);
    }

    public void modifyAt(Tarea tarea, int position) {
        this.tareas.set(position, tarea);
        notifyItemChanged(position);
    }

    public void removeAt(int position) {
        this.tareas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, tareas.size() - 1);
    }
}
