package com.sspu.intelligentlifeassistant.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class CategoryItem implements Serializable {
    private int categoryId;
    private String name;
    private String image;
    private String description;

    public CategoryItem(int categoryId, String name, String imagePath, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CategoryItem{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Bitmap getImage(Context context) {
        try {
            InputStream is = context.getAssets().open("images/category/" + image);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
