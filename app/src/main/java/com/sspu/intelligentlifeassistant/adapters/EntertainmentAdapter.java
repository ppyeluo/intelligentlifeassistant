package com.sspu.intelligentlifeassistant.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.models.CategoryItem;
import com.sspu.intelligentlifeassistant.models.EntertainmentItem;
import com.sspu.intelligentlifeassistant.module.entertainment.EntertainmentCategoryDetailActivity;
import com.sspu.intelligentlifeassistant.module.entertainment.EntertainmentDetailActivity;

import java.util.List;

public class EntertainmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<EntertainmentItem> entertainmentList;
    private List<CategoryItem> categoryList;
    private int currentDisplayMode;

    // 定义三种不同的显示模式常量
    public static final int DISPLAY_MODE_RECOMMEND = 0;
    public static final int DISPLAY_MODE_HOT = 1;
    public static final int DISPLAY_MODE_CATEGORY = 2;

    public EntertainmentAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // 根据当前的显示模式,inflater 不同的布局文件
        if (viewType == DISPLAY_MODE_RECOMMEND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entertainment_recommend_item, parent, false);
            return new EntertainmentAdapter.EntertainmentViewHolder(view);
        } else if (viewType == DISPLAY_MODE_HOT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entertainment_hot_item, parent, false);
            return new EntertainmentAdapter.EntertainmentViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entertainment_category_item, parent, false);
            return new EntertainmentAdapter.CategoryViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 根据当前的显示模式,调用不同的 ViewHolder 的 bind() 方法
        if (currentDisplayMode == DISPLAY_MODE_RECOMMEND) {
            EntertainmentAdapter.EntertainmentViewHolder entertainmentAdapter = (EntertainmentAdapter.EntertainmentViewHolder) holder;
            EntertainmentItem EntertainmentItem = entertainmentList.get(position);
            entertainmentAdapter.bindRecommendEntertainment(EntertainmentItem, context);
        } else if (currentDisplayMode == DISPLAY_MODE_HOT) {
            EntertainmentAdapter.EntertainmentViewHolder entertainmentAdapter = (EntertainmentAdapter.EntertainmentViewHolder) holder;
            EntertainmentItem EntertainmentItem = entertainmentList.get(position);
            entertainmentAdapter.bindHotEntertainment(EntertainmentItem, context);
        } else {
            EntertainmentAdapter.CategoryViewHolder categoryViewHolder = (EntertainmentAdapter.CategoryViewHolder) holder;
            CategoryItem categoryItem = categoryList.get(position);
            categoryViewHolder.bind(categoryItem, context);
        }
    }
    @Override
    public int getItemCount() {
        // 根据当前的显示模式,返回相应的列表大小
        if (currentDisplayMode == DISPLAY_MODE_RECOMMEND || currentDisplayMode == DISPLAY_MODE_HOT) {
            return entertainmentList != null ? entertainmentList.size() : 0;
        } else {
            return categoryList != null ? categoryList.size() : 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 返回当前的显示模式
        return currentDisplayMode;
    }

    /**
     * 更新新闻列表并设置当前的显示模式
     *
     * @param entertainmentList     新闻列表
     * @param displayMode  当前的显示模式
     */
    public void updateEntertainmentList(List<EntertainmentItem> entertainmentList, int displayMode) {
        this.entertainmentList = entertainmentList;
        this.currentDisplayMode = displayMode;
        notifyDataSetChanged();
    }

    /**
     * 更新分类信息列表并设置当前的显示模式为分类信息
     *
     * @param categoryList 分类信息列表
     */
    public void updateCategoryList(List<CategoryItem> categoryList) {
        this.categoryList = categoryList;
        this.currentDisplayMode = DISPLAY_MODE_CATEGORY;
        notifyDataSetChanged();
    }

    private static class EntertainmentViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView;
        private TextView descriptionTextView;

        public EntertainmentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.entertainment_image);
            titleTextView = itemView.findViewById(R.id.entertainment_title);
            descriptionTextView = itemView.findViewById(R.id.entertainment_description);
        }

        public void bindRecommendEntertainment(EntertainmentItem entertainmentItem, Context context) {
            // 使用 EntertainmentItem 中的图片、标题和描述信息来绑定视图
            Bitmap bitmap = entertainmentItem.getImage(context);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.temp_main_top2);
            }
            titleTextView.setText(entertainmentItem.getTitle());
            descriptionTextView.setText(entertainmentItem.getDescription());
            // 推荐新闻时间点击器
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EntertainmentDetailActivity.class);
                intent.putExtra("entertainmentItem", entertainmentItem);
                context.startActivity(intent);
            });
        }

        public void bindHotEntertainment(EntertainmentItem entertainmentItem, Context context) {
            // 使用 entertainmentItem 中的标题信息来绑定视图
            TextView hotEntertainmentNumberTextView = itemView.findViewById(R.id.hot_entertainment_number);
            TextView hotEntertainmentTitleTextView = itemView.findViewById(R.id.hot_entertainment_title);

            hotEntertainmentNumberTextView.setText(String.valueOf(getAdapterPosition() + 1));
            hotEntertainmentTitleTextView.setText(entertainmentItem.getTitle());
            // 热门新闻的点击事件监听器
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EntertainmentDetailActivity.class);
                intent.putExtra("entertainmentItem", entertainmentItem);
                context.startActivity(intent);
            });
        }
    }

    private static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImageView;
        private TextView categoryNameTextView;
        private TextView categoryDetailsTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.category_image);
            categoryNameTextView = itemView.findViewById(R.id.category_name);
            categoryDetailsTextView = itemView.findViewById(R.id.category_details);
        }

        public void bind(CategoryItem categoryItem, Context context) {
            // 使用 CategoryItem 中的图片和名称信息来绑定视图
            Bitmap bitmap = categoryItem.getImage(context);
            if (bitmap != null) {
                categoryImageView.setImageBitmap(bitmap);
            } else {
                categoryImageView.setImageResource(R.drawable.temp_main_top);
            }
            categoryNameTextView.setText(categoryItem.getName());
            // 分类时间点击器
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EntertainmentCategoryDetailActivity.class);
                intent.putExtra("categoryItem", categoryItem);
                context.startActivity(intent);
            });
        }
    }
}
