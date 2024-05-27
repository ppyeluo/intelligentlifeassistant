package com.sspu.intelligentlifeassistant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sspu.intelligentlifeassistant.module.EntertainmentActivity;
import com.sspu.intelligentlifeassistant.module.FinanceActivity;
import com.sspu.intelligentlifeassistant.module.NewsActivity;
import com.sspu.intelligentlifeassistant.module.NotepadActivity;
import com.sspu.intelligentlifeassistant.module.ScheduleManagementActivity;
import com.sspu.intelligentlifeassistant.module.WeatherActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager; // 轮播图的 ViewPager2 控件
    private List<Integer> imageList = Arrays.asList( // 轮播图的图片资源列表
            R.drawable.temp_main_top,
            R.drawable.temp_main_top2,
            R.drawable.temp_main_top3
    );
    private ViewPagerAdapter adapter; // 轮播图的适配器
    private CircleIndicator3 indicator; // 轮播图的指示点控件
    private List<ImageView> indicators = new ArrayList<>(); // 指示点图标的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCarousel();
        initModule();
    }

    // 初始化模块
    private void initModule() {
        // 设置每个模块的图标和名称
        setModule(R.id.news_module, R.drawable.ic_news, "资讯");
        setModule(R.id.calendar_module, R.drawable.ic_calendar, "日程管理");
        setModule(R.id.finance_module, R.drawable.ic_finance, "财务管理");
        setModule(R.id.notepad_module, R.drawable.ic_note, "记事本");
        setModule(R.id.weather_module, R.drawable.ic_weather, "天气预报");
        setModule(R.id.entertainment_module, R.drawable.ic_entertainment, "娱乐活动");
    }

    // 初始化轮播图
    private void initCarousel() {
        // 获取轮播图和指示点的引用
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);
        // 初始化轮播图适配器
        adapter = new ViewPagerAdapter(imageList);
        viewPager.setAdapter(adapter);
        // 设置指示点与轮播图的关联
        indicator.setViewPager(viewPager);
        // 设置自动轮播
        startAutoScroll();
    }

    /**
     * 启动自动轮播
     * 每隔 3 秒切换到下一个页面
     */
    private void startAutoScroll() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    int currentItem = viewPager.getCurrentItem();
                    int totalCount = adapter.getItemCount();
                    viewPager.setCurrentItem((currentItem + 1) % totalCount, true);
                }
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    /**
     * 设置每个模块的图标和名称
     *
     * @param moduleId   模块控件的 ID
     * @param iconResId  模块图标的资源 ID
     * @param name       模块名称
     */
    private void setModule(int moduleId, int iconResId, String name) {
        View moduleView = findViewById(moduleId);
        ImageView iconView = moduleView.findViewById(R.id.module_icon);
        TextView nameView = moduleView.findViewById(R.id.module_name);
        iconView.setImageResource(iconResId);
        nameView.setText(name);
    }

    /**
     * 打开对应模块的 Activity
     * 根据模块名称启动相应的 Activity
     *
     * @param view 被点击的模块视图
     */
    public void openModule(View view) {
        TextView nameView = view.findViewById(R.id.module_name);
        String moduleName = nameView.getText().toString();

        // 根据模块名称启动相应的 Activity
        switch (moduleName) {
            case "资讯":
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case "日程管理":
                startActivity(new Intent(this, ScheduleManagementActivity.class));
                break;
            case "财务管理":
                startActivity(new Intent(this, FinanceActivity.class));
                break;
            case "记事本":
                startActivity(new Intent(this, NotepadActivity.class));
                break;
            case "天气预报":
                startActivity(new Intent(this, WeatherActivity.class));
                break;
            case "娱乐活动":
                startActivity(new Intent(this, EntertainmentActivity.class));
                break;
            default:
                // 如果没有匹配的模块名,则不做任何操作
                break;
        }
    }

    /**
     * ViewPager2 的适配器
     * 负责管理轮播图的数据和视图
     */
    private class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
        private List<Integer> imageList;
        public ViewPagerAdapter(List<Integer> imageList) {
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_viewpager, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Glide.with(MainActivity.this)
                    .load(imageList.get(position))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))
                    .into(holder.imageView);
        }


        @Override
        public int getItemCount() {
            return imageList.size();
        }

        /**
         * ViewPager2 item 的视图持有者
         * 负责管理每个轮播图片的视图
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
}
