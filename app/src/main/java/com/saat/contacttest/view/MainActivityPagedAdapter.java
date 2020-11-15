package com.saat.contacttest.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saat.contacttest.R;
import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.databinding.MainActivityAdapterLayoutBinding;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class MainActivityPagedAdapter extends PagedListAdapter<ContactModel, MainActivityPagedAdapter.ViewHolder> {


    private MainActivityAdapter.OnItemClickListener listener;
    private Picasso picasso;

    @Inject
    public MainActivityPagedAdapter(Picasso picasso) {
        super(DIFF_CALLBACK);
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MainActivityAdapterLayoutBinding layoutBinding = MainActivityAdapterLayoutBinding
                .inflate(inflater,parent,false);
        return new ViewHolder(layoutBinding, picasso, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContactModel model = getItem(holder.getAdapterPosition());
        if(model != null)
            holder.onBind(model);
    }

    public void setListener(MainActivityAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    private static DiffUtil.ItemCallback<ContactModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ContactModel>() {
                @Override
                public boolean areItemsTheSame(ContactModel oldConcert, ContactModel newConcert) {
                    return oldConcert.getId() == newConcert.getId();
                }

                @Override
                public boolean areContentsTheSame(ContactModel oldConcert,
                                                  ContactModel newConcert) {
                    return oldConcert.isSameValue(newConcert);
                }
            };

    static class ViewHolder extends RecyclerView.ViewHolder {
        private MainActivityAdapter.OnItemClickListener listener;
        private MainActivityAdapterLayoutBinding binding;
        private Picasso picasso;
        public ViewHolder(@NonNull MainActivityAdapterLayoutBinding binding ,
                          Picasso picasso ,
                          MainActivityAdapter.OnItemClickListener listener) {
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
