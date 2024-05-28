package com.sspu.intelligentlifeassistant.module.entertainment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.models.EntertainmentItem;

public class EntertainmentDetailActivity extends AppCompatActivity {
    private ImageView backButton;
    private ImageView entertainmentImageView;
    private TextView titleTextView;
    private TextView contentTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_entertainment_detail);

        initView();
    }

    private void initView() {
        backButton = findViewById(R.id.back_button);
        entertainmentImageView = findViewById(R.id.image);
        titleTextView = findViewById(R.id.title);
        contentTextView = findViewById(R.id.content);

        backButton.setOnClickListener(v -> finish());

        // 从 Intent 中获取传递的 EntertainmentItem 对象
        EntertainmentItem entertainmentItem = (EntertainmentItem) getIntent().getSerializableExtra("entertainmentItem");

        if (entertainmentItem != null) {
            titleTextView.setText(entertainmentItem.getTitle());
            Bitmap bitmap = entertainmentItem.getImage(this);
            if (bitmap != null) {
                entertainmentImageView.setImageBitmap(bitmap);
            } else {
                entertainmentImageView.setImageResource(R.drawable.temp_main_top);
            }
            contentTextView.setText(entertainmentItem.getContent());
        }
    }
}
