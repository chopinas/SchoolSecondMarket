package com.pzy.chopin.schoolsecondmarket.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chopin on 2017/9/6.
 */

public class CateBean {

    private int cateid;

    List<String> catelist=new ArrayList<>();

    public CateBean(){
        catelist.add("电器");
        catelist.add("日用品");
        catelist.add("数码产品");
        catelist.add("书籍");
    }

    public List<String> getGoodcatelist() {
        return catelist;
    }

    public void setGoodcatelist(List<String> goodcatelist) {
        this.catelist = goodcatelist;
    }

    public int getCateid() {
        return cateid;
    }

    public void setCateid(int cateid) {
        this.cateid = cateid;
    }

}
