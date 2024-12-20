package com.mobile.wishtrack.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.wishtrack.R;
import com.mobile.wishtrack.WHApplication;
import com.mobile.wishtrack.ui.adapter.FragmentAdapter;
import com.mobile.wishtrack.ui.fragment.searchLayout.ProductSearchLayoutFragment;
import com.mobile.wishtrack.ui.fragment.searchList.WishSearchListFragment;
import com.mobile.wishtrack.ui.viewModel.ProductSearchViewModel;
import com.mobile.wishtrack.ui.viewModel.WishSearchViewModel;
import com.mobile.wishtrack.ui.viewModel.VMProvider;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private FragmentContainerView viewItemFragment;
    protected WishSearchViewModel wishSearchViewModel;
    protected ProductSearchViewModel productSearchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* view setting */
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        viewItemFragment = findViewById(R.id.viewItemFragment);

        /* viewModel setting */
        VMProvider vmProvider = new VMProvider((WHApplication) getApplication());
        wishSearchViewModel = new ViewModelProvider(this, vmProvider).get(WishSearchViewModel.class);
        productSearchViewModel = new ViewModelProvider(this, vmProvider).get(ProductSearchViewModel.class);

        /* view item visible setting */
        wishSearchViewModel.getVisible().observe(this, visible -> {
            if (visible) {
                viewItemFragment.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
            } else {
                viewItemFragment.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
        });

        productSearchViewModel.getVisible().observe(this, visible -> {
            if (visible) {
                viewItemFragment.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
            } else {
                viewItemFragment.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
       });

        /* ViewPager setting */
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this);
        fragmentAdapter.addFragment(WishSearchListFragment.newInstance());
//        fragmentAdapter.addFragment(WishSearchLayoutFragment.newInstance());
        fragmentAdapter.addFragment(ProductSearchLayoutFragment.newInstance());
        viewPager.setAdapter(fragmentAdapter);

        /* event setting */
        bottomNavigationView.setOnItemSelectedListener(item -> {
            final int itemId = item.getItemId();
            if (itemId == R.id.navCart) {
                wishSearchViewModel.setVisible(false);
                viewPager.setCurrentItem(0, true);
                return true;
            }
            else if (itemId == R.id.navSearch){
                wishSearchViewModel.setVisible(false);
                viewPager.setCurrentItem(1, true);
                return true;
            }
            return false;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.navCart);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.navSearch);
                        break;
                }
            }
        });

        /* init setting */
        viewPager.setCurrentItem(0);
    }
}
