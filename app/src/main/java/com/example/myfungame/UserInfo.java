package com.example.myfungame;

public class UserInfo implements Comparable<UserInfo> {
    private String name;
    private int point;
    private String game;

    public UserInfo(String name,String game,int point){
        this.name = name;
        this.point=point;
        this.game = game;

    }

    public String getGame() {
        return game;
    }


    public int getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    @Override
    public int compareTo(UserInfo info){
        return (this.getPoint()>info.getPoint()?-1:(this.getPoint()==info.getPoint()?0:1));
    }

}
