package com.sspu.intelligentlifeassistant.module;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.adapters.NewsAdapter;
import com.sspu.intelligentlifeassistant.models.CategoryItem;
import com.sspu.intelligentlifeassistant.models.NewsItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsList;
    private List<CategoryItem> categoryList;

    private Button recommendButton, hotButton, categoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsAdapter = new NewsAdapter(this);
        recyclerView.setAdapter(newsAdapter);

        // 初始化按钮视图并添加点击事件监听器
        recommendButton = findViewById(R.id.btn_recommend);
        hotButton = findViewById(R.id.btn_hot);
        categoryButton = findViewById(R.id.btn_category);

        recommendButton.setOnClickListener(this);
        hotButton.setOnClickListener(this);
        categoryButton.setOnClickListener(this);

        // 加载新闻列表和分类信息列表,默认显示推荐新闻
        loadNewsData();
        loadCategoryData();
        showRecommendedNews();

        // 返回按钮
        View customButton = findViewById(R.id.btn_back);

        // 为按钮设置点击事件监听器
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回到上一个页面
            }
        });
    }

    /**
     * 从 JSON 文件中加载新闻数据,并更新 NewsAdapter 的新闻列表
     */
    private void loadNewsData() {
        newsList = loadNewsItemsFromJson();
    }

    /**
     * 从 JSON 文件中加载分类信息数据,并更新 NewsAdapter 的分类信息列表
     */
    private void loadCategoryData() {
        categoryList = loadCategoryItemsFromJson();
    }

    /**
     * 显示推荐新闻
     */
    private void showRecommendedNews() {
        if (newsList != null && !newsList.isEmpty()) {
            // 按照 recommendationScore 字段对新闻列表进行排序,取前 10 条
            Collections.sort(newsList, (a, b) -> Float.compare(b.getRecommendationScore(), a.getRecommendationScore()));
            if (newsList.size() > 10) {
                newsList = newsList.subList(0, 10);
            }
            newsAdapter.updateNewsList(newsList, NewsAdapter.DISPLAY_MODE_RECOMMEND);
        }
    }

    /**
     * 显示热门新闻
     */
    private void showHotNews() {
        if (newsList != null && !newsList.isEmpty()) {
            // 按照 popularityScore 字段对新闻列表进行排序,取前 10 条
            Collections.sort(newsList, (a, b) -> Float.compare(b.getPopularityScore(), a.getPopularityScore()));
            if (newsList.size() > 10) {
                newsList = newsList.subList(0, 10);
            }
            newsAdapter.updateNewsList(newsList, NewsAdapter.DISPLAY_MODE_HOT);
        }
    }

    /**
     * 显示分类信息
     */
    private void showCategoryInfo() {
        if (categoryList != null && !categoryList.isEmpty()) {
            newsAdapter.updateCategoryList(categoryList);
        }
    }

    /**
     * 从 assets/json 目录读取 news_item.json 文件,并使用 Gson 解析 JSON 数据
     *
     * @return 新闻列表
     */
    private List<NewsItem> loadNewsItemsFromJson() {
        List<NewsItem> newsList = new ArrayList<>();
        try {
            // 从 assets/json 目录读取 news_item.json 文件
            InputStream inputStream = getAssets().open("json/news_item.json");
            String jsonString = readJsonFromInputStream(inputStream);

            // 使用 Gson 解析 JSON 数据
            Gson gson = new Gson();
            NewsItem[] newsItems = gson.fromJson(jsonString, NewsItem[].class);
            newsList.addAll(Arrays.asList(newsItems));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    /**
     * 从 assets/json 目录读取 news_category.json 文件,并使用 Gson 解析 JSON 数据
     *
     * @return 分类信息列表
     */
    private List<CategoryItem> loadCategoryItemsFromJson() {
        List<CategoryItem> categoryList = new ArrayList<>();
        try {
            // 从 assets/json 目录读取 news_category.json 文件
            InputStream inputStream = getAssets().open("json/news_category.json");
            String jsonString = readJsonFromInputStream(inputStream);

            // 使用 Gson 解析 JSON 数据
            Gson gson = new Gson();
            CategoryItem[] categoryItems = gson.fromJson(jsonString, CategoryItem[].class);
            categoryList.addAll(Arrays.asList(categoryItems));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    /**
     * 从输入流中读取 JSON 字符串
     *
     * @param inputStream 输入流
     * @return JSON 字符串
     * @throws IOException 读取输入流时可能发生的异常
     */
    private String readJsonFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        return jsonBuilder.toString();
    }

    /**
     * 根据点击的按钮 ID,更新 NewsAdapter 的显示模式
     *
     * @param v 被点击的按钮视图
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_recommend) {
            showRecommendedNews();
        } else if (v.getId() == R.id.btn_hot) {
            showHotNews();
        } else if (v.getId() == R.id.btn_category) {
            showCategoryInfo();
        }
    }
}



