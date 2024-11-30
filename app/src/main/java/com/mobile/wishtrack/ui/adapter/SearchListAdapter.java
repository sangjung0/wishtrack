package com.mobile.wishtrack.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.customUi.SearchProduct;
import com.mobile.wishtrack.ui.customUi.SearchProductViewHolder;

public class SearchListAdapter extends ListAdapter<Product, SearchProductViewHolder> {
    private final Context context;
    private OnProductClickListener onProductClickListener;

    public SearchListAdapter(Context context) {
        super(new DiffUtil.ItemCallback<Product>(){
            @Override
            public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                // 같은 객체인지 판별
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                // 같은 내용인지 판별. 일단 내용 변경될 일이 없어서 id로 함
                return oldItem.getId() == newItem.getId();
            }
        });
        this.context = context;
    }

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public SearchProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchProduct item = new SearchProduct(context);
        item.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new SearchProductViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductViewHolder holder, int position) {
        Product product = getItem(position);
        holder.bind(product);

        holder.itemView.setOnClickListener(v -> {
            onProductClickListener.onProductClick(product);
        });
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}

