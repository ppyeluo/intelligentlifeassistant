package com.sspu.intelligentlifeassistant.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class EntertainmentItem  implements Serializable {
    private int id;
    private String image;
    private String title;
    private String description;
    private String content;
    private int categoryId;
    private float recommendationScore;
    private float popularityScore;

    public EntertainmentItem(int id, String image, String title, String description, String content, int categoryId, float recommendationScore, float popularityScore) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.content = content;
        this.categoryId = categoryId;
        this.recommendationScore = recommendationScore;
        this.popularityScore = popularityScore;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public float getRecommendationScore() {
        return recommendationScore;
    }

    public float getPopularityScore() {
        return popularityScore;
    }
    public Bitmap getImage(Context context) {
        try {
            InputStream is = context.getAssets().open("images/entertainment/" + image);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}