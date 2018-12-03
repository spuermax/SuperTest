package com.supe.supertest.common.wdiget.bookpage.show;

import android.widget.ListView;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2018/11/28 16:05
 * @Description 行Bean
 */
public class ShowLine {

    public List<ShowChar>  CharsData = null;
    @Override
    public String toString() {
        return "ShowLine [CharsData=" + getLineData() + "]";
    }


    public String getLineData(){
        String linedata = "";
        if(CharsData==null||CharsData.size()==0) return linedata;
        for(ShowChar c:CharsData){
            linedata = linedata+c.charData;
        }
        return linedata;
    }

    public float lintHeight;

}
