package com.sspu.intelligentlifeassistant.module.entertainment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.adapters.EntertainmentAdapter;
import com.sspu.intelligentlifeassistant.adapters.NewsAdapter;
import com.sspu.intelligentlifeassistant.models.CategoryItem;
import com.sspu.intelligentlifeassistant.models.EntertainmentItem;
import com.sspu.intelligentlifeassistant.models.NewsItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EntertainmentCategoryDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EntertainmentAdapter entertainmentAdapter;
    private TextView categoryNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment_category_detail);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryNameTextView = findViewById(R.id.category_name);
        // 从 Intent 中获取 CategoryItem 对象
        CategoryItem categoryItem = (CategoryItem) getIntent().getSerializableExtra("categoryItem");
        if (categoryItem != null) {
            // 设置标题文本
            categoryNameTextView.setText(categoryItem.getName());
            // 加载该分类下的新闻列表
            List<EntertainmentItem> entertainmentList = loadNewsItemsFromJson(categoryItem.getCategoryId());

            // 创建并设置 NewsAdapter
            entertainmentAdapter = new EntertainmentAdapter(this);
            entertainmentAdapter.updateEntertainmentList(entertainmentList, NewsAdapter.DISPLAY_MODE_RECOMMEND);
            recyclerView.setAdapter(entertainmentAdapter);
        }

        // 返回
        View customButton = findViewById(R.id.back_button);

        // 为按钮设置点击事件监听器
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回到上一个页面
            }
        });
    }

    /**
     * 从 assets/json 目录读取 news_item.json 文件,并根据 categoryId 过滤出对应的新闻列表
     *
     * @param categoryId 分类 ID
     * @return 该分类下的新闻列表
     */
    private List<EntertainmentItem> loadNewsItemsFromJson(int categoryId) {
        List<EntertainmentItem> filteredNewsList = new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open("json/entertainment_item.json");
            String jsonString = readJsonFromInputStream(inputStream);
            Gson gson = new Gson();
            EntertainmentItem[] newsItems = gson.fromJson(jsonString, EntertainmentItem[].class);
            for (EntertainmentItem newsItem : newsItems) {
                if (newsItem.getCategoryId() == categoryId) {
                    filteredNewsList.add(newsItem);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredNewsList;
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
}
