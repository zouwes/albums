package com.daw.swapp.model.albums;

public class AlbumQuery {
    private String artists;
    private Integer minDuration;
    private Integer maxDuration;

    public String getAlbumQueryArtists() {
        return artists;
    }

    public void setAlbumQueryArtists(String artists) {
        this.artists = artists;
    }

    public Integer getAlbumQueryMinDuration() {
        return minDuration;
    }

    public void setAlbumQueryMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    public Integer getAlbumQueryMaxDuration() {
        return maxDuration;
    }

    public void setAlbumQueryMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }
}
