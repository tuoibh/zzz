package com.example.myapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.OnItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.databinding.ItemCastNCrewBinding;
import com.example.myapplication.domain.model.castncrew.CastNCrewResponse;
import com.example.myapplication.ui.fragment.detail.MovieDetailViewModel;

public class CastNCrewAdapter extends RecyclerView.Adapter<CastNCrewAdapter.CastNCrewViewHolder> {
    CastNCrewResponse castNCrew;
    OnItemClickListener listener;
    MovieDetailViewModel viewModel;

    public CastNCrewAdapter(CastNCrewResponse castNCrew, MovieDetailViewModel viewModel, OnItemClickListener listener) {
        this.castNCrew = castNCrew;
        this.listener = listener;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CastNCrewAdapter.CastNCrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCastNCrewBinding binding = ItemCastNCrewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CastNCrewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastNCrewAdapter.CastNCrewViewHolder holder, int position) {
        holder.binding.txtCncName.setText(castNCrew.getCast().get(position).getName());
        Glide.with(holder.binding.getRoot())
                .load(AppConfig.Companion.BASE_IMAGE+castNCrew.getCast().get(position).getProfilePath())
                .into(holder.binding.imvCastNCrew);
    }

    @Override
    public int getItemCount() {
        return castNCrew.getCast().size() /*+ castNCrew.getCrew().size()*/;
    }

    public static class CastNCrewViewHolder extends RecyclerView.ViewHolder {
        ItemCastNCrewBinding binding;

        public CastNCrewViewHolder(ItemCastNCrewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onClickItem(OnItemClickListener listener, int position){
            itemView.setOnClickListener(v -> {
                listener.onItemClick(v, position);
            });
        }
    }
}
