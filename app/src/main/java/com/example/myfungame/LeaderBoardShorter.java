package com.example.myfungame;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoardShorter {
    ArrayList<UserInfo> userInfos = new ArrayList<>();
    public LeaderBoardShorter(ArrayList<UserInfo>userInfos) {
        this.userInfos = userInfos;
    }
    public ArrayList<UserInfo> getSortedLeaderBoard() {
        Collections.sort(userInfos);
        return userInfos;
    }
}
