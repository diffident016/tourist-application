package com.example.touristapplicationbyhighhopes;
public class ActivityItem {
    private String name;
    private int imageResource;

    public ActivityItem(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
