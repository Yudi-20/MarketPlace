package com.example.marketplace;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.marketplace.Fragment.HomeFragment;
import com.example.marketplace.Fragment.ProductFragment;
import com.example.marketplace.Fragment.ProfileFragment;
import com.example.marketplace.Fragment.TransactionFragment;
import com.example.marketplace.Fragment.WishlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Spinner addressSpinner;
    private TextView cartBadge;
    private LinearLayout headerLayout;
    private FrameLayout fragmentContainer;

    // Sample data
    private String[] addresses = {"Rumah", "Kantor", "Kost", "Alamat Lain"};
    private CardView searchCardView;

    // Fragment instances
    private HomeFragment homeFragment;
    private ProductFragment productFragment;
    private WishlistFragment wishlistFragment;
    private TransactionFragment transactionFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupSystemBars();
        setContentView(R.layout.activity_main);

        initViews();
        setupHeaderInsets();
        setupAddressSpinner();
        setupBottomNavigation();

        // Load default fragment (Home)
        if (savedInstanceState == null) {
            loadFragment(getHomeFragment());
        }
    }

    private void setupSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
    }

    private void setupHeaderInsets() {
        headerLayout = findViewById(R.id.header_layout);

        ViewCompat.setOnApplyWindowInsetsListener(headerLayout, (view, insets) -> {
            int systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;

            view.setPadding(
                    view.getPaddingLeft(),
                    systemBarsInsets + dpToPx(16),
                    view.getPaddingRight(),
                    view.getPaddingBottom()
            );

            return insets;
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void initViews() {
        searchCardView = findViewById(R.id.search_card_view);
        addressSpinner = findViewById(R.id.address_spinner);
        cartBadge = findViewById(R.id.cart_badge);
        headerLayout = findViewById(R.id.header_layout);
        fragmentContainer = findViewById(R.id.fragment_container);
    }

    private void setupAddressSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, addresses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressSpinner.setAdapter(adapter);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;

            if (itemId == R.id.nav_home) {
                selectedFragment = getHomeFragment();
            } else if (itemId == R.id.nav_product) {
                selectedFragment = getProductFragment();
            } else if (itemId == R.id.nav_wishlist) {
                selectedFragment = getWishlistFragment();
            } else if (itemId == R.id.nav_transaction) {
                selectedFragment = getTransactionFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = getProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });
    }

    // Fragment lazy initialization methods
    private HomeFragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    private ProductFragment getProductFragment() {
        if (productFragment == null) {
            productFragment = new ProductFragment();
        }
        return productFragment;
    }

    private WishlistFragment getWishlistFragment() {
        if (wishlistFragment == null) {
            wishlistFragment = new WishlistFragment();
        }
        return wishlistFragment;
    }

    private TransactionFragment getTransactionFragment() {
        if (transactionFragment == null) {
            transactionFragment = new TransactionFragment();
        }
        return transactionFragment;
    }

    private ProfileFragment getProfileFragment() {
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
        }
        return profileFragment;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    // Helper method untuk fragment yang membutuhkan akses ke header height
    public int getHeaderHeight() {
        return headerLayout != null ? headerLayout.getHeight() : 0;
    }

    // Inner classes for data models (masih dibutuhkan oleh fragment)
    public static class PromoItem {
        public String title;
        public int imageResource;

        public PromoItem(String title, int imageResource) {
            this.title = title;
            this.imageResource = imageResource;
        }
    }

    public static class Product {
        public String name;
        public String price;
        public int imageResource;

        public Product(String name, String price, int imageResource) {
            this.name = name;
            this.price = price;
            this.imageResource = imageResource;
        }
    }
}