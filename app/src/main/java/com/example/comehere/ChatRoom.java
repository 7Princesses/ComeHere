package com.example.comehere;

import java.util.List;

public class ChatRoom {
    private String roomName;
    private List<String> chatRoomUsers;
    int maxNum;
    int currentNum;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<String> getChatRoomUsers() {
        return chatRoomUsers;
    }

    public void setChatRoomUsers(List<String> chatRoomUsers) {
        this.chatRoomUsers = chatRoomUsers;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }
}
