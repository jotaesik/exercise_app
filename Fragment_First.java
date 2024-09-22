package com.example.termproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Fragment_First extends Fragment implements OnBackPressedListener  {
    private View view;
    private long backKeyPressedTime;
    private SecondActivity secondActivity;
    private LineChart weight_chart;
    private EditText add_weight;
    private LineChart muscle_chart;
    private EditText add_muscle;
    private LineChart test_chart;
    private EditText add_test;
    private TextView test;
    private LineChart else_chart;
    private EditText add_else;
    private Button add_button,add_button1,add_button2,add_button3;
    private int []weight_value=new int[100];
    private int []muscle_value=new int[100];
    private int []test_value=new int[100];
    private int []else_value=new int[100];
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
    public void write_file(String filename,String input){
        try{
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(getActivity().
                    openFileOutput(filename,Context.MODE_APPEND));
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(input);
            bufferedWriter.newLine();
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String read_file(String filename){
        String file_str=null;
        try{
            InputStream inputStream=getActivity().openFileInput(filename);
            if(inputStream!=null){
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String temp="";
                StringBuffer stringBuffer=new StringBuffer();
                while((temp=bufferedReader.readLine())!=null){
                    stringBuffer.append(temp);
                    stringBuffer.append("\n");
                }
                inputStream.close();
                file_str=stringBuffer.toString();
            }
        }catch (Exception e){
            file_str="";
            e.printStackTrace();
        }
        return file_str;
    }
    public void make_weight_chart_no(){
        for(int i=0;i<100;i++){
            weight_value[i]=0;
        }
        String weight_read_file=read_file("weight.txt");
        if(weight_read_file.equals("")){

        }
        else{
            String real_weight_read_file[]=weight_read_file.split("\\n");
            for(int i=0;i<real_weight_read_file.length;i++){
                weight_value[i]=Integer.parseInt(real_weight_read_file[i]);
            }
            ArrayList<Entry> entry_chart1=new ArrayList<>();
            LineData lineData=new LineData();
            for(int i=0;i<real_weight_read_file.length;i++){
                entry_chart1.add(new Entry(i+1,weight_value[i]));
            }
            LineDataSet lineDataSet=new LineDataSet(entry_chart1,"체중");
            lineDataSet.setColor(Color.RED);
            lineDataSet.setCircleColor(Color.BLACK);
            lineData.addDataSet(lineDataSet);
            weight_chart.setData(lineData);
            weight_chart.invalidate();
        }
    }
    public void make_weight_chart(String input){
        for(int i=0;i<100;i++){
            weight_value[i]=0;
        }
        try{
            String filename="weight.txt";
            write_file(filename,input);
        }catch (Exception e){
            e.printStackTrace();
        }
        String weight_read_file=read_file("weight.txt");
        String real_weight_read_file[]=weight_read_file.split("\\n");
        for(int i=0;i<real_weight_read_file.length;i++){
            weight_value[i]=Integer.parseInt(real_weight_read_file[i]);
        }
        ArrayList<Entry> entry_chart1=new ArrayList<>();
        LineData lineData=new LineData();
        for(int i=0;i<real_weight_read_file.length;i++){
            entry_chart1.add(new Entry(i+1,weight_value[i]));
        }
        LineDataSet lineDataSet=new LineDataSet(entry_chart1,"체중");
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.BLACK);
        lineData.addDataSet(lineDataSet);
        weight_chart.setData(lineData);
        weight_chart.invalidate();
    }
    public void make_muscle_chart_no(){
        for(int i=0;i<100;i++){
            muscle_value[i]=0;
        }
        String muscle_read_file=read_file("muscle.txt");
        if(muscle_read_file.equals("")){
        }
        else{
            String real_muscle_read_file[]=muscle_read_file.split("\\n");
            for(int i=0;i<real_muscle_read_file.length;i++){
                muscle_value[i]=Integer.parseInt(real_muscle_read_file[i]);
            }
            ArrayList<Entry> entry_chart1=new ArrayList<>();
            LineData lineData=new LineData();
            for(int i=0;i<real_muscle_read_file.length;i++){
                entry_chart1.add(new Entry(i+1,muscle_value[i]));
            }
            LineDataSet lineDataSet=new LineDataSet(entry_chart1,"골격근량");
            lineDataSet.setCircleColor(Color.BLACK);
            lineDataSet.setColor(Color.RED);
            lineData.addDataSet(lineDataSet);
            muscle_chart.setData(lineData);
            muscle_chart.invalidate();
        }
    }
    public void make_muscle_chart(String input){
        for(int i=0;i<100;i++){
            muscle_value[i]=0;
        }
        try{
            String filename="muscle.txt";
            write_file(filename,input);
        }catch (Exception e){
            e.printStackTrace();
        }
        String muscle_read_file=read_file("muscle.txt");
        String real_muscle_read_file[]=muscle_read_file.split("\\n");
        for(int i=0;i<real_muscle_read_file.length;i++){
            muscle_value[i]=Integer.parseInt(real_muscle_read_file[i]);
        }
        ArrayList<Entry> entry_chart1=new ArrayList<>();
        LineData lineData=new LineData();
        for(int i=0;i<real_muscle_read_file.length;i++){
            entry_chart1.add(new Entry(i+1,muscle_value[i]));
        }
        LineDataSet lineDataSet=new LineDataSet(entry_chart1,"골격근량");
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setColor(Color.RED);
        lineData.addDataSet(lineDataSet);
        muscle_chart.setData(lineData);
        muscle_chart.invalidate();
    }
    public void make_test_chart_no(){
        for(int i=0;i<100;i++){
            test_value[i]=0;
        }
        String test_read_file=read_file("test.txt");
        if(test_read_file.equals("")){
        }else{
            String real_test_read_file[]=test_read_file.split("\\n");
            for(int i=0;i<real_test_read_file.length;i++){
                test_value[i]=Integer.parseInt(real_test_read_file[i]);
            }
            ArrayList<Entry> entry_chart1=new ArrayList<>();
            LineData lineData=new LineData();
            for(int i=0;i<real_test_read_file.length;i++){
                entry_chart1.add(new Entry(i+1,test_value[i]));
            }
            LineDataSet lineDataSet=new LineDataSet(entry_chart1,"체지방량");
            lineDataSet.setCircleColor(Color.BLACK);
            lineDataSet.setColor(Color.RED);
            lineData.addDataSet(lineDataSet);
            test_chart.setData(lineData);
            test_chart.invalidate();
        }
    }
    public void make_test_chart(String input){
        for(int i=0;i<100;i++){
            test_value[i]=0;
        }
        try{
            String filename="test.txt";
            write_file(filename,input);
        }catch (Exception e){
            e.printStackTrace();
        }
        String test_read_file=read_file("test.txt");
        String real_test_read_file[]=test_read_file.split("\\n");
        for(int i=0;i<real_test_read_file.length;i++){
            test_value[i]=Integer.parseInt(real_test_read_file[i]);
        }
        ArrayList<Entry> entry_chart1=new ArrayList<>();
        LineData lineData=new LineData();
        for(int i=0;i<real_test_read_file.length;i++){
            entry_chart1.add(new Entry(i+1,test_value[i]));
        }
        LineDataSet lineDataSet=new LineDataSet(entry_chart1,"체지방량");
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setColor(Color.RED);
        lineData.addDataSet(lineDataSet);
        test_chart.setData(lineData);
        test_chart.invalidate();
    }
    public void make_else_chart_no(){
        for(int i=0;i<100;i++){
            else_value[i]=0;
        }
        String else_read_file=read_file("else.txt");
        if(else_read_file.equals("")){
        }else{
            String real_else_read_file[]=else_read_file.split("\\n");
            for(int i=0;i<real_else_read_file.length;i++){
                else_value[i]=Integer.parseInt(real_else_read_file[i]);
            }
            ArrayList<Entry> entry_chart1=new ArrayList<>();
            LineData lineData=new LineData();
            for(int i=0;i<real_else_read_file.length;i++){
                entry_chart1.add(new Entry(i+1,else_value[i]));
            }
            LineDataSet lineDataSet=new LineDataSet(entry_chart1,"기초대사량");
            lineDataSet.setCircleColor(Color.BLACK);
            lineDataSet.setColor(Color.RED);
            lineData.addDataSet(lineDataSet);
            else_chart.setData(lineData);
            else_chart.invalidate();
        }
    }
    public void make_else_chart(String input){
        for(int i=0;i<100;i++){
            else_value[i]=0;
        }
        try{
            String filename="else.txt";
            write_file(filename,input);
        }catch (Exception e){
            e.printStackTrace();
        }
        String else_read_file=read_file("else.txt");
        String real_else_read_file[]=else_read_file.split("\\n");
        for(int i=0;i<real_else_read_file.length;i++){
            else_value[i]=Integer.parseInt(real_else_read_file[i]);
        }
        ArrayList<Entry> entry_chart1=new ArrayList<>();
        LineData lineData=new LineData();
        for(int i=0;i<real_else_read_file.length;i++){
            entry_chart1.add(new Entry(i+1,else_value[i]));
        }
        LineDataSet lineDataSet=new LineDataSet(entry_chart1,"기초대사량");
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setColor(Color.RED);
        lineData.addDataSet(lineDataSet);
        else_chart.setData(lineData);
        else_chart.invalidate();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_first,container,false);
        secondActivity=(SecondActivity) getActivity();
        add_button=(Button)view.findViewById(R.id.add_button);
        add_button1=(Button)view.findViewById(R.id.add_button1);
        add_button2=(Button)view.findViewById(R.id.add_button2);
        add_button3=(Button)view.findViewById(R.id.add_button3);
        weight_chart=(LineChart) view.findViewById(R.id.weight_chart);
        muscle_chart=(LineChart)view.findViewById(R.id.muscle_chart);
        test_chart=(LineChart)view.findViewById(R.id.test_chart);
        else_chart=(LineChart)view.findViewById(R.id.else_chart);
        add_weight=(EditText)view.findViewById(R.id.add_weight);
        add_muscle=(EditText)view.findViewById(R.id.add_muscle);
        add_test=(EditText)view.findViewById(R.id.add_test);
        add_else=(EditText)view.findViewById(R.id.add_else);
        test=(TextView)view.findViewById(R.id.test);
        make_weight_chart_no();
        make_muscle_chart_no();
        make_test_chart_no();
        make_else_chart_no();
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_weight.getText().toString().equals("")){
                    Toast_Make("체중을 입력해주세요");
                }
                else{
                    make_weight_chart(add_weight.getText().toString());
                    add_weight.setText("");
                }
            }
        });
        add_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(add_muscle.getText().toString().equals("")){
                   Toast_Make("골격근량을 입력해주세요");
               }
               else{
                   make_muscle_chart(add_muscle.getText().toString());
                   add_muscle.setText("");
               }
            }
        });
        add_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_test.getText().toString().equals("")){
                    Toast_Make("체지방량을 입력해주세요");
                }
                else{
                    make_test_chart(add_test.getText().toString());
                    add_test.setText("");
                }
            }
        });
        add_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_else.getText().toString().equals("")){
                    Toast_Make("기초대사량을 입력해주세요");
                }
                else{
                    make_else_chart(add_else.getText().toString());
                    add_else.setText("");
                }
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