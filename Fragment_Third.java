package com.example.termproject;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fragment_Third extends Fragment implements OnBackPressedListener  {
    private View view;
    private long backKeyPressedTime;
    private SecondActivity secondActivity;
    private DatePicker date_picker;
    private Button meal_button;
    private Button food_button;
    private EditText food_input;
    private String filename;
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
    public String readFood(String filename){
        String return_str=null;
        FileInputStream fileInputStream;
        try{
            fileInputStream=getActivity().openFileInput(filename);
            byte []text=new byte[1024];
            fileInputStream.read(text);
            fileInputStream.close();
            return_str=(new String(text).trim());
            food_button.setText("식단 수정하기");
        } catch (Exception e) {
            return_str="식단 저장없음";
            food_input.setText("식단 저장없음");
            food_button.setText("식단 저장하기");
            e.printStackTrace();
        }
        return return_str;
    }
    public void first_set(int year,int month,int day){
        String filename1=year+"_"+(month+1)+"_"+day+".txt";
        food_input.setText(readFood(filename1));
        food_button.setEnabled(true);
        filename=filename1;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_third,container,false);
        secondActivity=(SecondActivity) getActivity();
        date_picker=(DatePicker) view.findViewById(R.id.date_picker);
        food_button=(Button) view.findViewById(R.id.food_button);
        food_input=(EditText)view.findViewById(R.id.food_input);
        meal_button=(Button)view.findViewById(R.id.meal_button);
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        first_set(year,month,day);
        date_picker.init(year,month,day,
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        filename=year+"_"+(monthOfYear+1)+"_"+
                                dayOfMonth+".txt";
                        String file_str=readFood(filename);
                        food_input.setText(file_str);
                        food_button.setEnabled(true);
                    }
                });
        food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FileOutputStream fileOutputStream=getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                    String input_str=food_input.getText().toString();
                    fileOutputStream.write(input_str.getBytes());
                    fileOutputStream.close();
                    if(food_button.getText().toString().equals("식단 저장하기")){
                        Toast_Make(filename+"저장되었습니다.");
                    }
                    else{
                        Toast_Make(filename+"수정되었습니다.");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        meal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.getInstance().setIntent_file_name(filename);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment_Five fragment_five=new Fragment_Five();
                transaction.replace(R.id.fragment_main, fragment_five);
                transaction.commit();
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
}