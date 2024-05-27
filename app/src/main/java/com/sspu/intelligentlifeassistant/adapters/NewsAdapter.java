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
import com.sspu.intelligentlifeassistant.models.NewsItem;
import com.sspu.intelligentlifeassistant.module.news.NewsCategoryDetailActivity;
import com.sspu.intelligentlifeassistant.module.news.NewsDetailActivity;

import java.util.List;
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<NewsItem> newsList;
    private List<CategoryItem> categoryList;
    private int currentDisplayMode;

    // 定义三种不同的显示模式常量
    public static final int DISPLAY_MODE_RECOMMEND = 0;
    public static final int DISPLAY_MODE_HOT = 1;
    public static final int DISPLAY_MODE_CATEGORY = 2;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // 根据当前的显示模式,inflater 不同的布局文件
        if (viewType == DISPLAY_MODE_RECOMMEND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recommend_item, parent, false);
            return new NewsViewHolder(view);
        } else if (viewType == DISPLAY_MODE_HOT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_hot_item, parent, false);
            return new NewsViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_category_item, parent, false);
            return new CategoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 根据当前的显示模式,调用不同的 ViewHolder 的 bind() 方法
        if (currentDisplayMode == DISPLAY_MODE_RECOMMEND) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            NewsItem newsItem = newsList.get(position);
            newsViewHolder.bindRecommendNews(newsItem, context);
        } else if (currentDisplayMode == DISPLAY_MODE_HOT) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            NewsItem newsItem = newsList.get(position);
            newsViewHolder.bindHotNews(newsItem, context);
        } else {
            CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
            CategoryItem categoryItem = categoryList.get(position);
            categoryViewHolder.bind(categoryItem, context);
        }
    }

    @Override
    public int getItemCount() {
        // 根据当前的显示模式,返回相应的列表大小
        if (currentDisplayMode == DISPLAY_MODE_RECOMMEND || currentDisplayMode == DISPLAY_MODE_HOT) {
            return newsList != null ? newsList.size() : 0;
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
     * @param newsList     新闻列表
     * @param displayMode  当前的显示模式
     */
    public void updateNewsList(List<NewsItem> newsList, int displayMode) {
        this.newsList = newsList;
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

    private static class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView;
        private TextView descriptionTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image);
            titleTextView = itemView.findViewById(R.id.news_title);
            descriptionTextView = itemView.findViewById(R.id.news_description);
        }

        public void bindRecommendNews(NewsItem newsItem, Context context) {
            // 使用 NewsItem 中的图片、标题和描述信息来绑定视图
            Bitmap bitmap = newsItem.getImage(context);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.temp_main_top2);
            }
            titleTextView.setText(newsItem.getTitle());
            descriptionTextView.setText(newsItem.getDescription());
            // 推荐新闻时间点击器
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("newsItem", newsItem);
                context.startActivity(intent);
            });
        }

        public void bindHotNews(NewsItem newsItem, Context context) {
            // 使用 NewsItem 中的标题信息来绑定视图
            TextView hotNewsNumberTextView = itemView.findViewById(R.id.hot_news_number);
            TextView hotNewsTitleTextView = itemView.findViewById(R.id.hot_news_title);

            hotNewsNumberTextView.setText(String.valueOf(getAdapterPosition() + 1));
            hotNewsTitleTextView.setText(newsItem.getTitle());
            // 热门新闻的点击事件监听器
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("newsItem", newsItem);
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
                Intent intent = new Intent(context, NewsCategoryDetailActivity.class);
                intent.putExtra("categoryItem", categoryItem);
                context.startActivity(intent);
            });
        }
    }
}
