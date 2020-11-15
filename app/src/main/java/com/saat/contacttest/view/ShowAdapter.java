package com.saat.contacttest.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saat.contacttest.databinding.LayoutShowAdapterBinding;

import java.util.ArrayList;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    private List<String> list;

    public ShowAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutShowAdapterBinding binding = LayoutShowAdapterBinding
                .inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<String> list){
        if(list != null)
            this.list = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private LayoutShowAdapterBinding binding;

        public ViewHolder(@NonNull LayoutShowAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(String s){
            binding.textView.setText(s);
        }
    }
}
