package com.example.nativemovieapp.Model;

public class MovieTrailer {
    private String id;
    private String name;
    private String key;
    private String type;
    private String published_at;

    public MovieTrailer(String id, String name, String key, String type, String published_at) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.type = type;
        this.published_at = published_at;
    }

    public String getPublished_at() {
        return published_at;
    }

    @Override
    public String toString() {
        return "MovieTrailer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", published_at='" + published_at + '\'' +
                '}';
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }
}
