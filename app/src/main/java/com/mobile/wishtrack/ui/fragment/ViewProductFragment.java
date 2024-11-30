package com.mobile.wishtrack.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobile.wishtrack.R;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.viewModel.ProductSearchViewModel;
import com.mobile.wishtrack.ui.viewModel.WishSearchViewModel;


public class ViewProductFragment extends Fragment {
    private ImageView backButton;
    private TextView goToProductPage;
    private ImageView itemImage;
    private TextView itemTitle, itemLPrice, itemHPrice, itemMaker, itemBrand, itemCategory, itemProductID, itemProductType;
    private WishSearchViewModel wishSearchViewModel;
    private ProductSearchViewModel productSearchViewModel;
    private String productPageUrl;

    public ViewProductFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* view model setting */
        wishSearchViewModel = new ViewModelProvider(requireActivity()).get(WishSearchViewModel.class);
        productSearchViewModel = new ViewModelProvider(requireActivity()).get(ProductSearchViewModel.class);

        /* view setting */
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);

        backButton = view.findViewById(R.id.back_button);
        goToProductPage = view.findViewById(R.id.go_to_product_page);
        itemImage = view.findViewById(R.id.item_image);
        itemTitle = view.findViewById(R.id.item_title);
        itemLPrice = view.findViewById(R.id.item_l_price);
        itemHPrice = view.findViewById(R.id.item_h_price);
        itemMaker = view.findViewById(R.id.item_maker);
        itemBrand = view.findViewById(R.id.item_brand);
        itemCategory = view.findViewById(R.id.item_category);
        itemProductID = view.findViewById(R.id.item_product_id);
        itemProductType = view.findViewById(R.id.item_product_type);

        /* event setting */
        backButton.setOnClickListener((v) -> {
            wishSearchViewModel.setVisible(false);
            productSearchViewModel.setVisible(false);
        });

        goToProductPage.setOnClickListener((v)->{
            if (productPageUrl.startsWith("http")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productPageUrl));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
            }
        });

        wishSearchViewModel.getVisible().observe(getViewLifecycleOwner(), visible -> {
            setProduct(wishSearchViewModel.getProduct().getValue());
        });
        productSearchViewModel.getVisible().observe(getViewLifecycleOwner(), visible -> {
            setProduct(productSearchViewModel.getProduct().getValue());
        });

        return view;
    }

    private void setProduct(Product product) {
        if (product == null) return;
        productPageUrl = product.getLink();
        goToProductPage.setText(product.getMallName());
        Glide.with(this).load(product.getImageUrl()).into(itemImage);
        itemTitle.setText(product.getTitle());
        itemLPrice.setText(String.valueOf(product.getLPrices().get(0)));
        itemHPrice.setText(String.valueOf(product.getHPrices().get(0)));
        itemProductID.setText(String.valueOf(product.getId()));
    }
}