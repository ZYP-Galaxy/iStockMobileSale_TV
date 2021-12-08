package com.abbp.istockmobilesalenew;

public class PriceLevel {
    int level;
    String Level_name;

    public PriceLevel(int level, String level_name) {
        this.level = level;
        Level_name = level_name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevel_name() {
        return Level_name;
    }

    public void setLevel_name(String level_name) {
        Level_name = level_name;
    }
}
