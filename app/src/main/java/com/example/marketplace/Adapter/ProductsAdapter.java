package com.example.marketplace.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.MainActivity;
import com.example.marketplace.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<MainActivity.Product> products;

    public ProductsAdapter(List<MainActivity.Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        MainActivity.Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.product_card);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
        }

        public void bind(MainActivity.Product product) {
            productImage.setImageResource(product.imageResource);
            productName.setText(product.name);
            productPrice.setText(product.price);

            cardView.setOnClickListener(v -> {
                // Handle product click
                // You can implement navigation to product detail here
            });
        }
    }
}