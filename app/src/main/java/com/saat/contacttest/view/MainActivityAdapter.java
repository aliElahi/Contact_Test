package com.saat.contacttest.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saat.contacttest.R;
import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.databinding.MainActivityAdapterLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdapter
        extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private List<ContactModel> list;
    private OnItemClickListener listener;
    private Picasso picasso;

    public MainActivityAdapter(Picasso picasso) {
        this.list = new ArrayList<>();
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MainActivityAdapterLayoutBinding layoutBinding = MainActivityAdapterLayoutBinding
                .inflate(inflater,parent,false);
        return new ViewHolder(layoutBinding,picasso, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setList(List<ContactModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener listener;
        private MainActivityAdapterLayoutBinding binding;
        private Picasso picasso;
        public ViewHolder(@NonNull MainActivityAdapterLayoutBinding binding ,
                          Picasso picasso ,
                          OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.picasso = picasso;
            this.listener = listener;
        }

        public void onBind(ContactModel model){

            if(model.getPhotoUri() == null){
                binding.imageView.setImageResource(R.drawable.ic_person);
            }
            else
                picasso.load(model.getPhotoUri()).into(binding.imageView);

            binding.textViewName.setText(model.getName());

            if(model.getPhones() == null || model.getPhones().size() == 0)
                binding.textViewPhone.setText("");
            else
                binding.textViewPhone.setText(model.getPhones().get(0));

            binding.getRoot()
                    .setOnClickListener(view -> {

                        if(listener != null)
                            listener.onClick(model);
                    });
        }

    }

    public interface OnItemClickListener{
        void onClick(ContactModel model);
    }
}
