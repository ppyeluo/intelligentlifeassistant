package com.sspu.intelligentlifeassistant.module;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.adapters.WeatherAdapter;
import com.sspu.intelligentlifeassistant.models.WeatherItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private List<WeatherItem> weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化天气数据
        weatherList = new ArrayList<>();
        adapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(adapter);

        // 加载初始的热门城市天气数据
        loadHotCities();
    }

    private void loadHotCities() {
        // 从 JSON 文件中加载热门城市天气数据,并更新 weatherList
        try {
            String jsonString = loadJsonFromAssets("json/weather_item.json");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String cityName = jsonObject.getString("cityName");
                String weatherStatus = jsonObject.getString("weatherStatus");
                String temperatureRange = jsonObject.getString("temperatureRange");
                String imageResource = jsonObject.getString("imageResource");
                weatherList.add(new WeatherItem(cityName, weatherStatus, temperatureRange, imageResource));
            }
            adapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJsonFromAssets(String fileName) throws IOException {
        // 从 assets 目录下读取 JSON 文件
        InputStream is = getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }
}

