package com.example.myapplication.ui.fragment.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.databinding.ItemCastNCrewBinding;
import com.example.myapplication.domain.model.castncrew.CastNCrewResponse;
import com.example.myapplication.domain.repo.ImageLoader;

public class CastNCrewAdapter extends RecyclerView.Adapter<CastNCrewAdapter.CastNCrewViewHolder> {
    CastNCrewResponse castNCrew;
    ImageLoader imageLoader;

    public CastNCrewAdapter(ImageLoader imageLoader, CastNCrewResponse castNCrew) {
        this.castNCrew = castNCrew;
        this.imageLoader = imageLoader;
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
        imageLoader.loadImage(AppConfig.Companion.BASE_IMAGE+castNCrew.getCast().get(position).getProfilePath(), holder.binding.imvCastNCrew);
    }

    @Override
    public int getItemCount() {
        return castNCrew.getCast().size();
    }

    public static class CastNCrewViewHolder extends RecyclerView.ViewHolder {
        ItemCastNCrewBinding binding;

        public CastNCrewViewHolder(ItemCastNCrewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
