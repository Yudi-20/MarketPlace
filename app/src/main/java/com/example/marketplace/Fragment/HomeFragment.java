package com.example.marketplace.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.marketplace.MainActivity;
import com.example.marketplace.Adapter.ProductsAdapter;
import com.example.marketplace.Adapter.PromoCarouselAdapter;
import com.example.marketplace.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private ViewPager2 promoViewPager;
    private LinearLayout dotsIndicator;
    private Timer carouselTimer;
    private Handler carouselHandler;
    private int currentCarouselPage = 0;
    private PromoCarouselAdapter promoAdapter;

    private NestedScrollView nestedScrollView;
    private LinearLayout forYouSection;
    private TabLayout tabLayout;
    private RecyclerView productsRecyclerView;
    private ProductsAdapter productsAdapter;

    private List<MainActivity.PromoItem> promoItems;
    private List<MainActivity.Product> products;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupPromoCarousel();
        setupTabLayout();
        setupProductsRecyclerView();
        setupScrollBehavior();

        return view;
    }

    private void initViews(View view) {
        promoViewPager = view.findViewById(R.id.promo_viewpager);
        dotsIndicator = view.findViewById(R.id.dots_indicator);
        nestedScrollView = view.findViewById(R.id.nested_scroll_view);
        forYouSection = view.findViewById(R.id.for_you_section);
        tabLayout = view.findViewById(R.id.tab_layout);
        productsRecyclerView = view.findViewById(R.id.products_recycler_view);

        carouselHandler = new Handler();
    }

    private void setupPromoCarousel() {
        // Initialize sample promo data
        promoItems = new ArrayList<>();
        promoItems.add(new MainActivity.PromoItem("Promo 1", R.drawable.promo_image_1));
        promoItems.add(new MainActivity.PromoItem("Promo 2", R.drawable.promo_image_2));
        promoItems.add(new MainActivity.PromoItem("Promo 3", R.drawable.promo_image_3));

        promoAdapter = new PromoCarouselAdapter(promoItems);
        promoViewPager.setAdapter(promoAdapter);

        setupDotsIndicator();
        startAutoCarousel();

        promoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentCarouselPage = position;
                updateDotsIndicator(position);
            }
        });
    }

    private void setupDotsIndicator() {
        dotsIndicator.removeAllViews();
        ImageView[] dots = new ImageView[promoItems.size()];

        for (int i = 0; i < promoItems.size(); i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);

            dotsIndicator.addView(dots[i], params);
        }

        if (dots.length > 0) {
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.dot_active));
        }
    }

    private void updateDotsIndicator(int position) {
        for (int i = 0; i < dotsIndicator.getChildCount(); i++) {
            ImageView dot = (ImageView) dotsIndicator.getChildAt(i);
            if (i == position) {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_active));
            } else {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_inactive));
            }
        }
    }

    private void startAutoCarousel() {
        carouselTimer = new Timer();
        carouselTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (carouselHandler != null && getActivity() != null) {
                    carouselHandler.post(() -> {
                        if (currentCarouselPage == promoItems.size() - 1) {
                            currentCarouselPage = 0;
                        } else {
                            currentCarouselPage++;
                        }
                        promoViewPager.setCurrentItem(currentCarouselPage, true);
                    });
                }
            }
        }, 3000, 3000);
    }

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("For You"));
        tabLayout.addTab(tabLayout.newTab().setText("Most Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("Spring Sale"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadProductsForTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupProductsRecyclerView() {
        products = new ArrayList<>();
        productsAdapter = new ProductsAdapter(products);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        productsRecyclerView.setLayoutManager(layoutManager);
        productsRecyclerView.setAdapter(productsAdapter);

        loadProductsForTab(0);
    }

    private void loadProductsForTab(int tabPosition) {
        products.clear();

        switch (tabPosition) {
            case 0: // For You
                products.add(new MainActivity.Product("ROG Strix Scar RTX 4...", "Rp. 17.500.200", R.drawable.product_laptop_1));
                products.add(new MainActivity.Product("ROG Strix Scar RTX 4...", "Rp. 17.500.200", R.drawable.product_laptop_2));
                products.add(new MainActivity.Product("Gaming Laptop ASUS", "Rp. 15.200.000", R.drawable.product_laptop_3));
                products.add(new MainActivity.Product("MacBook Pro M2", "Rp. 22.000.000", R.drawable.product_laptop_4));
                break;
            case 1: // Most Popular
                products.add(new MainActivity.Product("iPhone 15 Pro", "Rp. 18.500.000", R.drawable.product_phone_1));
                products.add(new MainActivity.Product("Samsung Galaxy S24", "Rp. 16.200.000", R.drawable.product_phone_2));
                products.add(new MainActivity.Product("iPhone 15 Pro", "Rp. 18.500.000", R.drawable.product_phone_1));
                products.add(new MainActivity.Product("Samsung Galaxy S24", "Rp. 16.200.000", R.drawable.product_phone_2));
                break;
            case 2: // Spring Sale
                products.add(new MainActivity.Product("Gaming Mouse RGB", "Rp. 450.000", R.drawable.product_mouse_1));
                products.add(new MainActivity.Product("Mechanical Keyboard", "Rp. 850.000", R.drawable.product_keyboard_1));
                products.add(new MainActivity.Product("Gaming Mouse RGB", "Rp. 450.000", R.drawable.product_mouse_1));
                products.add(new MainActivity.Product("Mechanical Keyboard", "Rp. 850.000", R.drawable.product_keyboard_1));
                break;
        }

        productsAdapter.notifyDataSetChanged();
    }

    private void setupScrollBehavior() {
        // Get MainActivity to access header
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity == null) return;

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
                forYouSection.getLocationOnScreen(location);
                int forYouTop = location[1];

                // Get header height from MainActivity
                int headerHeight = mainActivity.getHeaderHeight();

                if (forYouTop <= headerHeight + 20) {
                    forYouSection.setTranslationY(scrollY - (forYouSection.getTop() - headerHeight - 20));
                } else {
                    forYouSection.setTranslationY(0);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (carouselTimer != null) {
            carouselTimer.cancel();
            carouselTimer = null;
        }
    }
}