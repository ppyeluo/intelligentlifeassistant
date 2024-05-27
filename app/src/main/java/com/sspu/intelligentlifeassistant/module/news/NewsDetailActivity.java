package com.sspu.intelligentlifeassistant.module.news;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.models.NewsItem;

public class NewsDetailActivity extends AppCompatActivity {
    private ImageView backButton;
    private ImageView newsImageView;
    private TextView titleTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        backButton = findViewById(R.id.back_button);
        newsImageView = findViewById(R.id.news_image);
        titleTextView = findViewById(R.id.news_title);
        contentTextView = findViewById(R.id.news_content);

        backButton.setOnClickListener(v -> finish());

        // 从 Intent 中获取传递的 NewsItem 对象
        NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra("newsItem");
        if (newsItem != null) {
            titleTextView.setText(newsItem.getTitle());
            Bitmap bitmap = newsItem.getImage(this);
            if (bitmap != null) {
                newsImageView.setImageBitmap(bitmap);
            } else {
                newsImageView.setImageResource(R.drawable.temp_main_top);
            }
            contentTextView.setText(newsItem.getContent());
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
}

