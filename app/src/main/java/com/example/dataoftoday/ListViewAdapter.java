package com.example.dataoftoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ImageView imageView;
    private TextView titleTextView;
    private TextView contentTextView;

    private ArrayList<ListViewItem> listViewItemList=new ArrayList<ListViewItem>(); //Adapter에 추가된 데이터를 저장하기 위한 ArrayList

    public ListViewAdapter(){
        // 생성자
    }
    @Override
    public int getCount() {
        //listview의 아이템 개수를 리턴
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        //listview에서 지정된 위치 i에 있는 데이터 리턴
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        //listview에서 관계된 아이템의 포지션 리턴
        return i;
    }

    public void addItem(String title, int icon,String content){
        ListViewItem item=new ListViewItem();

        item.setTitle(title);
        item.setIcon(icon);
        item.setContet(content);

        listViewItemList.add(item);
    }

    //position i에 위치한 데이터를 화면에 출력하는데 사용될 View 리턴
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       final int pos=i;
       final Context context=viewGroup.getContext();

       //listview_item layout을 inflate하여 contentview 참조 획득
        if(view==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.listview_item,viewGroup,false);
        }

        //화면에 표시된 view로 부터 위젯에 대한 참조 획득
        titleTextView=(TextView)view.findViewById(R.id.listview_textTitle);
        imageView=(ImageView)view.findViewById(R.id.listview_icon);
        contentTextView=(TextView)view.findViewById(R.id.listview_text);

        ListViewItem listViewItem=listViewItemList.get(i);

        //아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        imageView.setImageResource(listViewItem.getIcon());
        contentTextView.setText(listViewItem.getContent());

        return view;
    }
}
