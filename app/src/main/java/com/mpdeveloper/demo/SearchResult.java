package com.mpdeveloper.demo;

//import android.text.Html;
//// Inside the SearchResult class
//public class SearchResult {
//    private String title;
//    private String snippet;
//    private int pageId;
//    private String imageUrl;
//
//    public SearchResult(String title, String snippet, int pageId, String imageUrl) {
//        this.title = title;
//        // Remove HTML tags from snippet
//        this.snippet = Html.fromHtml(snippet).toString();
//        this.pageId = pageId;
//        this.imageUrl = imageUrl;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getSnippet() {
//        return snippet;
//    }
//
//    public int getPageId() {
//        return pageId;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//}

import android.text.Html;

// Inside the SearchResult class
public class SearchResult {
    private String title;
    private String snippet;
    private int pageId;
    private String imageUrl;

    public SearchResult(String title, String snippet, int pageId, String imageUrl) {
        this.title = title;
        // Remove HTML tags from snippet
        this.snippet = Html.fromHtml(snippet).toString();
        this.pageId = pageId;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getPageId() {
        return pageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}