package com.example.nativemovieapp.Model;

import java.util.List;

public class CommentList {
    private int listID;
    private List<Comment> listData;

    public int getListID() {
        return listID;
    }

    public List<Comment> getListData() {
        return listData;
    }

    public CommentList(int listID, List<Comment> listData) {
        this.listID = listID;
        this.listData = listData;
    }
}
