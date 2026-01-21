package com.example.expensetracker;

import android.graphics.Color;

public class ColorUtils {
    
    // Vibrant palette matching colors.xml
    private static final int[] COLORS = {
        Color.parseColor("#FF6B6B"), // Coral
        Color.parseColor("#4ECDC4"), // Turquoise
        Color.parseColor("#45B7D1"), // Sky Blue
        Color.parseColor("#FFA502"), // Orange
        Color.parseColor("#A3CB38"), // Olive
        Color.parseColor("#D980FA"), // Lavender
        Color.parseColor("#12CBC4"), // Sea Green
        Color.parseColor("#ED4C67")  // Bara Red
    };

    public static int getColorForCategory(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) return Color.GRAY;
        int hash = Math.abs(categoryName.hashCode());
        return COLORS[hash % COLORS.length];
    }
}
