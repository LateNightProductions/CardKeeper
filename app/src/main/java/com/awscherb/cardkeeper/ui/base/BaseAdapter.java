package com.awscherb.cardkeeper.ui.base;

import android.support.v7.widget.RecyclerView;

import com.awscherb.cardkeeper.data.model.BaseModel;

import java.util.List;

public abstract class BaseAdapter<T extends BaseModel, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private List<T> objects;

    public BaseAdapter(List<T> objects) {
        this.objects = objects;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, getItem(position));
    }

    public abstract void onBindViewHolder(VH holder, T item);

    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void swapObjects(List<T> newObjects) {
        objects = newObjects;
        notifyDataSetChanged();
    }

}
