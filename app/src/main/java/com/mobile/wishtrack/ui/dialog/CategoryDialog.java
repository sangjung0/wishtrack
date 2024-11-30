package com.mobile.wishtrack.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.R;
import com.mobile.wishtrack.domain.model.QueryOptions;
import com.mobile.wishtrack.ui.viewModel.ProductSearchViewModel;

public class CategoryDialog extends DialogFragment {

    private EditText minPriceInput, maxPriceInput, minRateInput, maxRateInput;
    private RadioGroup sortOptions;
    private RadioButton sortByPriceASE, sortByPriceDESE, sortByRateASE, sortByRateDESE;
    private Button resetButton, applyButton;
    private QueryOptions.Sort sortOption;

    private ProductSearchViewModel productSearchViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productSearchViewModel = new ViewModelProvider(requireActivity()).get(ProductSearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_dialog, container, false);

        /* view setting */
        minPriceInput = view.findViewById(R.id.minPriceInput);
        maxPriceInput = view.findViewById(R.id.maxPriceInput);
        minRateInput = view.findViewById(R.id.minRateInput);
        maxRateInput = view.findViewById(R.id.maxRateInput);
        sortOptions = view.findViewById(R.id.sortOptions);
        sortByPriceASE = view.findViewById(R.id.sortByPriceASE);
        sortByPriceDESE = view.findViewById(R.id.sortByPriceDESE);
        sortByRateASE = view.findViewById(R.id.sortByRateASE);
        sortByRateDESE = view.findViewById(R.id.sortByRateDESE);
        resetButton = view.findViewById(R.id.resetButton);
        applyButton = view.findViewById(R.id.applyButton);

        /* event setting */
        sortOptions.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.sortByPriceASE) {
                sortOption = QueryOptions.Sort.ASC;
            } else if (checkedId == R.id.sortByPriceDESE) {
                sortOption = QueryOptions.Sort.DSC;
            } else if (checkedId == R.id.sortByRateASE) {
                sortOption = QueryOptions.Sort.RASC;
            } else if (checkedId == R.id.sortByRateDESE) {
                sortOption = QueryOptions.Sort.RDSC;
            }
        });

        resetButton.setOnClickListener(v -> {
            minPriceInput.setText("");
            maxPriceInput.setText("");
            minRateInput.setText("");
            maxRateInput.setText("");
            sortOptions.clearCheck();
            sortByPriceASE.setChecked(true);
        });

        applyButton.setOnClickListener(v -> {
            final int minPrice = minPriceInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(minPriceInput.getText().toString());
            final int maxPrice = maxPriceInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(maxPriceInput.getText().toString());
            final int minRate = minRateInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(minRateInput.getText().toString());
            final int maxRate = maxRateInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(maxRateInput.getText().toString());

            productSearchViewModel.setQueryOptions(QueryOptions.newInstance(
                    sortOption,
                    minPrice,
                    maxPrice,
                    minRate,
                    maxRate
            ));
            dismiss();
        });

        /* init */
        QueryOptions queryOptions = productSearchViewModel.getQueryOptions();
        minPriceInput.setText(String.valueOf(queryOptions.getMinPrice()));
        maxPriceInput.setText(String.valueOf(queryOptions.getMaxPrice()));
        minRateInput.setText(String.valueOf(queryOptions.getMinRate()));
        maxRateInput.setText(String.valueOf(queryOptions.getMaxRate()));
        switch (queryOptions.getSort()) {
            case DSC:
                sortByPriceDESE.setChecked(true);
                break;
            case RASC:
                sortByRateASE.setChecked(true);
                break;
            case RDSC:
                sortByRateDESE.setChecked(true);
                break;
            default:
                sortByPriceASE.setChecked(true);
        }

        return view;
    }
}

