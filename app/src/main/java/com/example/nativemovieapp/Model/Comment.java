package com.example.nativemovieapp.Model;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Comment {
    private String avatar;
    private String name;
    private String userID;
    private String timestamp;
    private String content;

    public Comment(String avatar, String name, String userID, String content) {
        this.avatar = avatar;
        this.name = name;
        this.userID = userID;
        this.content = content;
        //Auto set time
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma dd-MM-yyyy");
        this.timestamp = currentTime.format(formatter);


    }

    public Comment() {
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }
}
