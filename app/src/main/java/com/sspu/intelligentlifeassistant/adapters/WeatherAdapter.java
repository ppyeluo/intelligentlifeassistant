package com.sspu.intelligentlifeassistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.models.WeatherItem;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherItem> weatherList;

    public WeatherAdapter(List<WeatherItem> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_city_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        // 绑定第一个城市的数据
        WeatherItem weather1 = weatherList.get(position * 2);
        holder.bindWeather1(weather1);

        // 如果有第二个城市的数据,则绑定
        if (position * 2 + 1 < weatherList.size()) {
            WeatherItem weather2 = weatherList.get(position * 2 + 1);
            holder.bindWeather2(weather2);
        } else {
            // 没有第二个城市的数据,隐藏第二个城市的布局
            holder.hideWeather2();
        }
    }

    @Override
    public int getItemCount() {
        return (weatherList.size() + 1) / 2;
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        private ImageView cityImage1, cityImage2;
        private TextView cityName1, cityName2;
        private TextView weatherStatus1, weatherStatus2;
        private TextView temperatureRange1, temperatureRange2;
        private View weather2Layout;

        WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            cityImage1 = itemView.findViewById(R.id.city_image_1);
            cityName1 = itemView.findViewById(R.id.city_name_1);
            weatherStatus1 = itemView.findViewById(R.id.weather_status_1);
            temperatureRange1 = itemView.findViewById(R.id.temperature_range_1);

            cityImage2 = itemView.findViewById(R.id.city_image_2);
            cityName2 = itemView.findViewById(R.id.city_name_2);
            weatherStatus2 = itemView.findViewById(R.id.weather_status_2);
            temperatureRange2 = itemView.findViewById(R.id.temperature_range_2);
            weather2Layout = itemView.findViewById(R.id.weather2_layout);
        }

        void bindWeather1(WeatherItem weather) {
            Glide.with(itemView).load("file:///android_asset/images/weather/" + weather.getImageResource()).into(cityImage1);
            cityName1.setText(weather.getCityName());
            weatherStatus1.setText(weather.getWeatherStatus());
            temperatureRange1.setText(weather.getTemperatureRange());
        }

        void bindWeather2(WeatherItem weather) {
            Glide.with(itemView).load("file:///android_asset/images/weather/" + weather.getImageResource()).into(cityImage2);
            cityName2.setText(weather.getCityName());
            weatherStatus2.setText(weather.getWeatherStatus());
            temperatureRange2.setText(weather.getTemperatureRange());
        }

        void hideWeather2() {
            weather2Layout.setVisibility(View.GONE);
        }
    }
}
