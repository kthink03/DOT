package com.example.dataoftoday;

public class ListViewItem {
    private int iconDrawabale;
    private String contentStr;
    private String titleStr;

    public void setTitle(String title){
        titleStr=title;
    }
    public void setIcon(int icon){
        iconDrawabale=icon;
    }
    public void setContet(String content){
        contentStr=content;
    }

    public int getIcon(){
        return this.iconDrawabale;
    }
    public String getContent(){
        return this.contentStr;
    }
    public String getTitle(){
        return this.titleStr;
    }
}
