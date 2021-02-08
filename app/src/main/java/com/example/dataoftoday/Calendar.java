package com.example.dataoftoday;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;


class OneDayDecorator implements DayViewDecorator {
    private CalendarDay date;
    private final Drawable drawable;
    public OneDayDecorator(Calendar context){
        date=CalendarDay.today();
        drawable=context.getResources().getDrawable(R.drawable.today); //오늘을 표시하는 동그라미 drawable
    }

    public boolean shouldDecorate(CalendarDay day){
        return day.equals(date);
    }

    public void decorate(DayViewFacade view){
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1));
        //view.setSelectionDrawable(drawable);
    }
    public void setDate(Date date){
        this.date=CalendarDay.from(date);
    }
}

class SaturdayDecorator implements DayViewDecorator {
    private final java.util.Calendar calendar= java.util.Calendar.getInstance();

    public SaturdayDecorator(){
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay=calendar.get(java.util.Calendar.DAY_OF_WEEK);
        return weekDay== java.util.Calendar.SATURDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLUE));
    }
}

class SundayDecorator implements DayViewDecorator {
    private final java.util.Calendar calendar= java.util.Calendar.getInstance();
    public SundayDecorator(){

    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay=calendar.get(java.util.Calendar.DAY_OF_WEEK);

        return weekDay== java.util.Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}

class EventDecorator implements DayViewDecorator {


    private final Drawable drawable;

    private HashSet<CalendarDay> dates;
    private TextView textView;
    public EventDecorator(Collection<CalendarDay> dates, Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_background);
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day){
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }


}

public class Calendar extends Fragment{
//서브 프래그먼트 1
    private static final String TAG ="Calendar";
    private boolean Click=false; //캘린더 클릭 여부 확인
    public Context mContext;
    public ArrayList<CalendarDay> calendarDayList=new ArrayList<>(); //0월이 1월이니까 DB 삽입할때 여기에 +1 해서 넣어주기
    Spinner category_spinner; //카테고리 스피너 생성

    String[] str; //생성한 카테고리 item
    MaterialCalendarView materialCalendarView; //캘린더 뷰 사용

    /*데이터 베이스 사용*/
    DatabaseManager DBManager;

    /*Textview 사용*/
    TextView textView;

    /*달력 클릭 이벤트 발생 시 날짜  변수들*/
    int Click_Year=0;
    int Click_Month=0;
    int Click_Day=0;

    /*리스트뷰*/
    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup;
        viewGroup=(ViewGroup) inflater.inflate(R.layout.calendar,container,false);
        str=mContext.getResources().getStringArray(R.array.category); //array 카테고리에서 정보를 받아옴
        category_spinner=viewGroup.findViewById(R.id.spinner_category);
        DBManager=new DatabaseManager(getContext()); //DB 사용 관련

        materialCalendarView= viewGroup.findViewById(R.id.calendarView);
        category_spinner=viewGroup.findViewById(R.id.spinner_category);

        adapter=new ListViewAdapter();
        listView=(ListView)viewGroup.findViewById(R.id.cal_listview);
        listView.setAdapter(adapter);


        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                while(i!=0){
                    if(Click){
                        Toast.makeText(mContext.getApplicationContext(),str[i]+"을(를) 선택하였습니다.",Toast.LENGTH_SHORT).show();
                        showRecord(str[i]);
                        break;
                    }
                    else{
                        Toast.makeText(mContext.getApplicationContext(),"먼저 날짜를 선택해주세요.",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Click_Year=date.getYear();
                Click_Month=date.getMonth();
                Click_Day=date.getDay();

                Log.d(TAG,"Calendar_Click Y:"+Click_Year+" M:"+Click_Month+" D:"+Click_Day);
                Click=true;
            }
        });
        OneDayDecorator oneDayDecorator=new OneDayDecorator(this);
        addCalander();
        EventDecorator eventDecorator=new EventDecorator(calendarDayList,getActivity());

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator,
                eventDecorator
        );
        return viewGroup;
    }
    private void addCalander(){
        Cursor c=DBManager.select_date();
        int count= c.getCount();
        int i=0;
        int year;
        int month;
        int day;
        Log.d(TAG,"Calander_cursor"+count);

        for(i=0;i<count;i++){
            c.moveToNext();
            year=c.getInt(0); //첫번째 속성
            month=c.getInt(1); //두번째 속성
            day=c.getInt(2); //세번째 속성

            Log.d(TAG,"Calander_values"+year+month+day);

            calendarDayList.add(CalendarDay.from(year,month,day));
        }
    }

    private void showRecord(String Category){
        Cursor c=DBManager.showRecord(Category,Click_Year,Click_Month,Click_Day);
        int count=c.getCount();
        String Record,Title;
        int Eval=0;

        for(int i=0;i<count;i++){
            Log.d(TAG,"COUNT "+count);
            c.moveToNext();
            Title=c.getString(0);
            Record=c.getString(1);
            Eval=c.getInt(2);
            Log.d(TAG,"Title: "+Title+" Record: "+Record+" Eval: "+Eval);

            if(Eval==1){
                //좋아요인경우
                adapter.addItem(Title,R.drawable.redheart,Record);
            }
            else if(Eval==2){
                //싫어요 인경우
                adapter.addItem(Title,R.drawable.blackheart,Record);
            }


        }
        adapter.notifyDataSetChanged();
    }
}
