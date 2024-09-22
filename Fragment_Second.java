package com.example.termproject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fragment_Second extends Fragment implements OnBackPressedListener  {
    private View view;
    private TextView time_date,exercise_d_day;
    private Button exercise_button;
    private ListView listView;
    private Button reset_button;
    private int already_exist=0;
    private int already_number=0;
    private Button exercise_start_button;
    private ExerciseAdapter exerciseAdapter;
    private ArrayList<Exercise> exercises1;
    private ArrayList<Exercise>exercises2;
    private long backKeyPressedTime;
    private EditText timer_textview;
    private Button timer_button;
    private TextView past_data;
    String filename;
    private DatePicker date_picker;
    int check_ok=0;
    private SecondActivity secondActivity;
    private Button finish_button;
    private TextView past_time_date;
    private int choose_number=0;
    String []first_data;
    String [][]second_data;
    int first_number=0;
    int second_number=0;
    String all_str;
    String filename1="d_day.txt";
    public void Toast_Make(String message){
        LayoutInflater inflater=getLayoutInflater();
        View layout=inflater.inflate(R.layout.toast_design_layout,(ViewGroup) getActivity().findViewById(R.id.toast_design_layout_main));
        Toast toast=Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT);
        TextView textView=layout.findViewById(R.id.toast_textview);
        textView.setText(message);
        toast.setGravity(Gravity.BOTTOM,0,500);
        toast.setView(layout);
        toast.show();
    }
    public void parsing_data(){
        String data=Global.getInstance().getAll();
        if(data.equals("")){
        }
        else{
            first_data=data.split("\\n");
            for(int i=0;i< first_data.length;i++){
            }
            second_data=new String[first_data.length][10];
            for(int i=0;i<first_data.length;i++){
                for(int j=0;j<10;j++){
                    second_data[i][j]=null;
                }
            }
            first_number=first_data.length;
            second_number=10;
            for(int i=0;i< first_data.length;i++){
            }
            int a=0;
            for(int i=0;i<first_data.length;i++){
                String third_data[]=first_data[i].split(",");
                for(int j=0;j<third_data.length;j++){
                    second_data[i][j]=third_data[j];
                    a++;
                }
            }
        }
    }
    public String readExercise(String filename){
        String return_str=null;
        FileInputStream fileInputStream;
        try{
            fileInputStream=getActivity().openFileInput(filename);
            byte []text=new byte[1024];
            fileInputStream.read(text);
            fileInputStream.close();
            return_str=(new String(text).trim());
        } catch (Exception e) {
            return_str="운동 저장없음";
            e.printStackTrace();
        }
        return return_str;
    }
    public String readD_Day(String filename){
        String return_str=null;
        FileInputStream fileInputStream;
        try{
            fileInputStream=getActivity().openFileInput(filename);
            byte []text=new byte[1024];
            fileInputStream.read(text);
            fileInputStream.close();
            return_str=(new String(text).trim());
        } catch (Exception e) {
            return_str="+0번";
            e.printStackTrace();
        }
        return return_str;
    }
    public void d_day_set(){
        String file_str=readD_Day(filename1);
        exercise_d_day.setText(file_str);
    }
    public void navigation_check(){
        if(readExercise(filename).equals("운동 저장없음")){
            Global.getInstance().setToday_exercise(0);
        }
        else{
            Global.getInstance().setToday_exercise(1);
        }
    }
    public void second_set(){
        exercise_button.setVisibility(View.INVISIBLE);
        exercise_start_button.setVisibility(View.VISIBLE);
        exercises1=new ArrayList<>();
        exerciseAdapter=new ExerciseAdapter(getContext(),exercises1);
        listView.setAdapter(exerciseAdapter);
        if(readExercise(filename).equals("운동 저장없음")){
            exercise_button.setVisibility(View.VISIBLE);
            exercise_start_button.setVisibility(View.INVISIBLE);
        }
        else{
            already_exist=1;
            String data=readExercise(filename);
            first_data=data.split("\\n");
            second_data=new String[first_data.length][10];
            first_number=first_data.length;
            second_number=10;
            int a=0;
            for(int i=0;i<first_data.length;i++){
                String third_data[]=first_data[i].split(",");
                for(int j=0;j<third_data.length;j++){
                    second_data[i][j]=third_data[j];
                    a++;
                }
                exercises1.add(new Exercise(second_data[i][0],true));
                try{
                    String filename=second_data[i][0]+".txt";
                    FileOutputStream fileOutputStream=getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                    String input_str = null;
                    for(int k=2;k<10;k++){
                            if(k==2){
                                input_str=second_data[i][k]+"\n";
                            }
                            else{
                                input_str=input_str+second_data[i][k]+"\n";
                            }
                    }
                    String []test_str=input_str.split("n");
                    fileOutputStream.write(test_str[0].getBytes());
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                already_number++;
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_second,container,false);
        secondActivity=(SecondActivity) getActivity();
        time_date=(TextView) view.findViewById(R.id.time_date);
        past_data=(TextView)view.findViewById(R.id.past_data);
        reset_button=(Button)view.findViewById(R.id.reset_button);
        listView=(ListView) view.findViewById(R.id.listView);
        exercise_start_button=(Button)view.findViewById(R.id.exercise_start_button);
        timer_textview=(EditText) view.findViewById(R.id.timer_textview);
        timer_textview.setFocusable(false);
        timer_button=(Button)view.findViewById(R.id.timer_button);
        exercise_button=(Button)view.findViewById(R.id.exercise_button);
        past_time_date=(TextView)view.findViewById(R.id.past_time_date);
        finish_button=(Button)view.findViewById(R.id.finish_button);
        exercises1=new ArrayList<>();
        exerciseAdapter=new ExerciseAdapter(getContext(),exercises1);
        listView.setAdapter(exerciseAdapter);
        exercise_d_day=(TextView) view.findViewById(R.id.exercise_d_day);
        choose_number=Global.getInstance().getChoose_number();
        Date now= Calendar.getInstance().getTime();
        SimpleDateFormat year=new SimpleDateFormat("yyyy", Locale.KOREA);
        SimpleDateFormat month=new SimpleDateFormat("MM",Locale.KOREA);
        SimpleDateFormat day=new SimpleDateFormat("dd",Locale.KOREA);
        String now_year=year.format(now);
        String now_month=month.format(now);
        String now_day=day.format(now);
        Global.getInstance().setNow_year(now_year);
        Global.getInstance().setNow_month(now_month);
        Global.getInstance().setNow_day(now_day);
        Calendar calendar=Calendar.getInstance();
        int year1=calendar.get(Calendar.YEAR);
        int month1=calendar.get(Calendar.MONTH);
        int day1=calendar.get(Calendar.DAY_OF_MONTH);
        filename=year1+"년"+(month1+1)+"월"+
                day1+"일.txt";
        second_set();
        parsing_data();
        d_day_set();
        navigation_check();
        if(choose_number==0){
            exercise_button.setText("운동 정하러가기");
        }
        else{
            exercise_button.setText("운동 수정하러가기");
        }
        timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer_textview.getText().toString().equals("타이머")){
                    Toast_Make("타이머가 설정되지 않았습니다");
                }
                else{
                    int timer=Integer.parseInt(timer_textview.getText().toString().
                            substring(0,timer_textview.getText().toString().length()-1));
                    Dialog dialog=new Dialog(getActivity());
                    dialog.setContentView(R.layout.custom_layout4);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    TextView second_title=dialog.findViewById(R.id.second_title);
                    CountDownTimer countDownTimer=new CountDownTimer(timer*1000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            int time_left = (int) (millisUntilFinished / 1000);
                            second_title.setText(String.valueOf(time_left)+"초");
                        }
                        @Override
                        public void onFinish() {
                            timer_textview.setText("타이머");
                            dialog.dismiss();
                        }
                    }.start();
                }
            }
        });
        timer_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_layout2);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button yes_button=dialog.findViewById(R.id.yes_button);
                EditText set_time_date=dialog.findViewById(R.id.set_time_date);
                yes_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(set_time_date.getText().toString().equals("")){
                            Toast_Make("쉬는시간을 입력해주세요");
                        }
                        else{
                            boolean output=true;
                            for(int i=0;i<set_time_date.getText().toString().length();i++){
                                char temp=set_time_date.getText().toString().charAt(i);
                                if(Character.isDigit(temp)==false){
                                    output=false;
                                }
                            }
                            if(output==true){
                                timer_textview.setText(set_time_date.getText().toString()+"초");
                                dialog.dismiss();
                            }
                            else{
                                Toast_Make("숫자를 입력하셔야 합니다");
                            }
                        }
                    }
                });
            }
        });
        exercise_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readExercise(filename).equals("운동 저장없음")){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment_Four fragment_four=new Fragment_Four();
                    transaction.replace(R.id.fragment_main, fragment_four);
                    BottomNavigationView bottom_menu=getActivity().findViewById(R.id.bottom_menu);
                    bottom_menu.setSelectedItemId(R.id.fourth_tab);
                    transaction.commit();
                }
                else{
                    Toast_Make("이미 있어서 내일 하세요");
                }
            }
        });
        time_date.setText(now_year+"년 "+now_month+"월 "+now_day+"일");
        time_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_layout1);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button yes_button=dialog.findViewById(R.id.yes_button);
                Button no_button=dialog.findViewById(R.id.no_button);
                EditText set_time_date=dialog.findViewById(R.id.set_time_date);
                yes_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time_date.setText(now_year+"년 "+now_month+"월 "+now_day+"일");
                        if(set_time_date.getText().toString().equals("")){
                            Toast_Make("날짜를 입력하셔야 기록을 볼수있습니다");
                        }
                        else{
                            filename=set_time_date.getText().toString()+".txt";
                            past_time_date.setText(set_time_date.getText().toString());
                            String file_str=readExercise(filename);
                            past_data.setText("운동종류 세트수 (무게,쉬는시간)"+"\n"+file_str);
                            dialog.dismiss();
                            filename=year1+"년"+(month1+1)+"월"+
                                    day1+"일.txt";
                        }
                    }
                });
                no_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        int exercise_number=0;
        ArrayList <Exercise> exercises=Global.getInstance().getExercises();
        for(int i=0;i< exercises.size();i++){
            if(exercises.get(i).getChecked()==true){
                exercise_number++;
            }
        }
        if(exercise_number==0){
        }
        else{
            for(int i=0;i< exercises.size();i++){
                if(exercises.get(i).getChecked()==true){
                   exercises1.add(exercises.get(i));
                }
            }
        }
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!readExercise(filename).equals("운동 저장없음")){
                    Toast_Make("오늘 이미 운동을 저장했습니다");
                }
                else if(choose_number!=0){
                    try{
                        filename=year1+"년"+(month1+1)+"월"+
                                day1+"일.txt";
                        FileOutputStream fileOutputStream=getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                        String input_str=Global.getInstance().getAll();
                        fileOutputStream.write(input_str.getBytes());
                        fileOutputStream.close();
                        FileOutputStream fileOutputStream1=getActivity().openFileOutput(filename1, Context.MODE_PRIVATE);
                        String []temp=exercise_d_day.getText().toString().split("번");
                        String temp1=temp[0];
                        temp1=temp1.substring(1);
                        int temp2=Integer.parseInt(temp1)+1;
                        String temp3="+"+String.valueOf(temp2)+"번";
                        fileOutputStream1.write(temp3.getBytes());
                        fileOutputStream1.close();
                        d_day_set();
                        Global.getInstance().setToday_exercise(1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    check_ok=1;
                    ArrayList <Exercise> exercises=Global.getInstance().getExercises();
                    for(int i=0;i<exercises.size();i++){
                        if(exercises.get(i).isChecked()==true){
                            exercises.get(i).setChecked(false);
                        }
                    }
                    Global.getInstance().setExercises(exercises);
                    past_time_date.setText("과거의 운동기록을 보려면 날짜를 클릭해주세요");
                    past_data.setText("");
                    exercises1.clear();
                    listView.setAdapter(exerciseAdapter);
                    exercise_button.setVisibility(View.INVISIBLE);
                    exercise_start_button.setVisibility(View.VISIBLE);
                }
                else{
                    Toast_Make("오늘의 운동이 선택되지 않았습니다");
                }
                second_set();
            }
        });
        exercise_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer_textview.setVisibility(View.VISIBLE);
                timer_button.setVisibility(View.VISIBLE);
                Toast_Make("운동을 시작합니다");
            }
        });
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise_button.setText("운동 정하러가기");
                past_time_date.setText("과거의 운동기록을 보려면 날짜를 클릭해주세요");
                past_data.setText("");
                ArrayList <Exercise> exercises=Global.getInstance().getExercises();
                for(int i=0;i<exercises.size();i++){
                    if(exercises.get(i).isChecked()==true){
                        exercises.get(i).setChecked(false);
                    }
                }
                Global.getInstance().setExercises(exercises);
                Global.getInstance().setChoose_number(0);
                choose_number=0;
                Global.getInstance().setAll("");
                exercises1.clear();
                listView.setAdapter(exerciseAdapter);
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        secondActivity.setOnBackPressedListener(this);
    }
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            Toast_Make("한번더 뒤로가기 누르면 종료");
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            getActivity().finish();
            secondActivity.onShutDown();
        }
    }
    class ExerciseAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
        private Context context;
        private List list;
        public ExerciseAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
        }
        class ViewHolder {
            public TextView name;
            public Button item_friend_delete;
            public TextView textView;
        }
        public ExerciseAdapter(Context context, ArrayList list){
            super(context, 0, list);
            this.context = context;
            this.list = list;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null){
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                convertView = layoutInflater.inflate(R.layout.item_exercise_new, parent, false);
            }
            viewHolder= new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_friend_textview);
            viewHolder.item_friend_delete = (Button) convertView.findViewById(R.id.item_friend_delete);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.item_textview);
            final Exercise exercise = (Exercise) list.get(position);
            viewHolder.name.setText(exercise.getName());
            viewHolder.item_friend_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(already_exist==1){
                        Dialog dialog=new Dialog(getActivity());
                        dialog.setContentView(R.layout.custom_layout3);
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        Button yes_button=dialog.findViewById(R.id.yes_button);
                        Button no_button=dialog.findViewById(R.id.no_button);
                        yes_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list.remove(position);
                                listView.setAdapter(exerciseAdapter);
                                choose_number--;
                                already_number--;
                                if(already_number==0){
                                    Toast_Make("오늘도 운동 수고하셨습니다");
                                }
                                dialog.dismiss();
                            }
                        });
                        no_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                    else{
                        if(check_ok==0){
                            Toast_Make("먼저 오늘 운동을 저장해주세요");
                        }
                    }
                }
            });
            String str="";
            for(int j=0;j<first_number;j++){
                if(((Exercise) list.get(position)).getName().equals(second_data[j][0])){
                    for(int k=1;k<10;k++){
                        if(k==1){
                            str=str+"세트수 "+second_data[j][k];
                        }
                        else{
                                str=str+"  "+second_data[j][k]+"(무게, 쉬는시간) ";
                        }
                    }
                    String []test=str.split("n");
                    viewHolder.textView.setText(test[0]);
                }
            }
            all_str=str;
            return convertView;
        }
        private boolean isChecked(int position) {
            return((Exercise) list.get(position)).checked;
        }
    }
}