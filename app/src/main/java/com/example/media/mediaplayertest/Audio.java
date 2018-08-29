package com.example.media.mediaplayertest;

public class Audio {

    String title;
    String description;
    String streamingUrl;
    String thumbnailUrl;
    boolean isPurchased;

    public Audio(String title, String description, String streamingUrl, String thumbnailUrl, boolean isPurchased){
        this.title = title;
        this.description = description;
        this.streamingUrl = streamingUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.isPurchased = isPurchased;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreamingUrl() {
        return streamingUrl;
    }

    public void setStreamingUrl(String streamingUrl) {
        this.streamingUrl = streamingUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
