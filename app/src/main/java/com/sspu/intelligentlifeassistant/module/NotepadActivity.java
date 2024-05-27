package com.sspu.intelligentlifeassistant.module;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.adapters.NotepadPendingAdapter;
import com.sspu.intelligentlifeassistant.models.NotepadItem;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotepadActivity extends AppCompatActivity {
    private View newNoteLayout;
    private View pendingNoteLayout;
    private TextView newNoteButton;
    private TextView pendingNoteButton;

    // 适配器
    private RecyclerView recyclerView;
    private NotepadPendingAdapter adapter;
    private List<NotepadItem> records;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        // 初始化布局引用
        newNoteLayout = findViewById(R.id.notepad_new);
        pendingNoteLayout = findViewById(R.id.notepad_pending);
        newNoteButton = findViewById(R.id.tv_ledger);
        pendingNoteButton = findViewById(R.id.tv_finance_record);

        recyclerView = findViewById(R.id.recycler_view);

        // 设置初始状态
        showNewNoteLayout();
        updateNavigationButtonState(true, false);
        // 设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotepadPendingAdapter(records);
        recyclerView.setAdapter(adapter);
        // 加载所有记录
        loadAllRecords();

        // 设置点击事件监听器
        newNoteButton.setOnClickListener(v -> showNewNoteLayout());
        pendingNoteButton.setOnClickListener(v -> showPendingNoteLayout());
    }

    private void showNewNoteLayout() {
        newNoteLayout.setVisibility(View.VISIBLE);
        pendingNoteLayout.setVisibility(View.GONE);
        updateNavigationButtonState(true, false);
    }

    private void showPendingNoteLayout() {
        newNoteLayout.setVisibility(View.GONE);
        pendingNoteLayout.setVisibility(View.VISIBLE);
        updateNavigationButtonState(false, true);
    }

    private void updateNavigationButtonState(boolean newNoteSelected, boolean pendingNoteSelected) {
        if (newNoteSelected) {
            newNoteButton.setBackgroundColor(Color.parseColor("#3e8140"));
            pendingNoteButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else {
            newNoteButton.setBackgroundColor(Color.parseColor("#4CAF50"));
            pendingNoteButton.setBackgroundColor(Color.parseColor("#3e8140"));
        }
    }

    private void loadAllRecords() {
        // 从JSON文件加载所有记录,并更新适配器
        records = getAllRecords();
        adapter.setData(records);
    }
    private List<NotepadItem> getAllRecords() {
        try {
            // 从assets/json/notepad_item.json文件中加载记录数据
            String json = loadJSONFromAsset("json/notepad_item.json");
            Type listType = new TypeToken<List<NotepadItem>>() {}.getType();
            return new Gson().fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String loadJSONFromAsset(String fileName) throws IOException {
        // 从assets目录读取JSON文件
        AssetManager assetManager = getAssets();
        InputStream is = assetManager.open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }
}