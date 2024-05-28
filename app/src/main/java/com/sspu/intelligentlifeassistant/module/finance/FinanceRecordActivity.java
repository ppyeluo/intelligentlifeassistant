package com.sspu.intelligentlifeassistant.module.finance;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.adapters.FinanceRecordAdapter;
import com.sspu.intelligentlifeassistant.models.FinanceRecord;
import com.sspu.intelligentlifeassistant.module.FinanceActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FinanceRecordActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FinanceRecordAdapter adapter;
    private List<FinanceRecord> records;

    private TextView tv_ledger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_record);

        // 初始化控件
        recyclerView = findViewById(R.id.recycler_view);
        tv_ledger = findViewById(R.id.tv_ledger);
        tv_ledger.setOnClickListener(v -> {
            // 跳转到FinanceActivity并删除当前页面
            Intent intent = new Intent(this, FinanceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
        // 设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FinanceRecordAdapter(records);
        recyclerView.setAdapter(adapter);

        // 加载所有记录
        loadAllRecords();

        // 设置底部导航菜单点击事件
        findViewById(R.id.tv_ledger).setOnClickListener(v -> {
            // 跳转到记账本页面
            startActivity(new Intent(this, FinanceActivity.class));
        });
        findViewById(R.id.tv_finance_record).setOnClickListener(v -> {
            // 当前就是财务记录页面,无需跳转
        });
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

    private void loadAllRecords() {
        // 从JSON文件加载所有记录,并更新适配器
        records = getAllRecords();
        adapter.setData(records);
    }

    private List<FinanceRecord> getAllRecords() {
        try {
            // 从assets/json/finance_record.json文件中加载记录数据
            String json = loadJSONFromAsset("json/finance_record.json");
            Type listType = new TypeToken<List<FinanceRecord>>() {}.getType();
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
