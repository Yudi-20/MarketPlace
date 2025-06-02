package com.example.marketplace.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.MainActivity;
import com.example.marketplace.R;

import java.util.List;

public class PromoCarouselAdapter extends RecyclerView.Adapter<PromoCarouselAdapter.PromoViewHolder> {

    private List<MainActivity.PromoItem> promoItems;

    public PromoCarouselAdapter(List<MainActivity.PromoItem> promoItems) {
        this.promoItems = promoItems;
    }

    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promo_carousel, parent, false);
        return new PromoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        MainActivity.PromoItem promoItem = promoItems.get(position);
        holder.bind(promoItem);
    }

    @Override
    public int getItemCount() {
        return promoItems.size();
    }

    static class PromoViewHolder extends RecyclerView.ViewHolder {
        private ImageView promoImage;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            promoImage = itemView.findViewById(R.id.promo_image);
        }

        public void bind(MainActivity.PromoItem promoItem) {
            promoImage.setImageResource(promoItem.imageResource);
        }
    }
}