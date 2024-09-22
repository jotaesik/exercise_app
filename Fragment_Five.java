package com.example.termproject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Five extends Fragment implements OnBackPressedListener  {
    private View view;
    private long backKeyPressedTime;
    private SecondActivity secondActivity;
    private ListView listView;
    private FoodAdapter foodAdapter;
    private Button open_kcal_button;
    private TextView open_kcal;
    private ArrayList<Food> foods;
    private int xy=0;
    private int total;
    private EditText find_food;
    private String parsing;
    private Button finish_button;
    private TextView food_choose;
    private String food_choose_str="";
    private String []parsing_real;
    private String[][] parsing_real_real;
    private TextView test;
    private RadioButton first_button,second_button;
    private RadioGroup radio_group;
    private int clear=0;
    private boolean next_check;
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
    public void read_food_data(){
        //식품명,1회제공량,에너지,단백질,지방,탄수화물
        InputStream txtResource = getActivity().getResources().openRawResource(R.raw.food_data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        try {
            len = txtResource.read();
            while (len != -1) {
                byteArrayOutputStream.write(len);
                len = txtResource.read();
            }
            parsing = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            txtResource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        parsing_real=parsing.split("\\n");
        parsing_real_real=new String[parsing_real.length][6];
        for(int i=0;i<parsing_real.length;i++){
            String []temp=parsing_real[i].split(",");
            foods.add(new Food(temp[0],temp[1],temp[2],temp[3],temp[4],temp[5],false));
            parsing_real_real[i][0]=temp[0];
            parsing_real_real[i][1]=temp[1];
            parsing_real_real[i][2]=temp[2];
            parsing_real_real[i][3]=temp[3];
            parsing_real_real[i][4]=temp[4];
            parsing_real_real[i][5]=temp[5];
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_five,container,false);
        secondActivity=(SecondActivity) getActivity();
        listView=(ListView) view.findViewById(R.id.listView);
        find_food=(EditText)view.findViewById(R.id.find_food);
        test=(TextView)view.findViewById(R.id.test);
        food_choose=(TextView)view.findViewById(R.id.food_choose);
        finish_button=(Button)view.findViewById(R.id.finish_button);
        open_kcal=(TextView)view.findViewById(R.id.open_kcal);
        open_kcal_button=(Button)view.findViewById(R.id.open_kcal_button);
        foods=new ArrayList<>();
        foodAdapter=new FoodAdapter(getContext(),foods);
        listView.setAdapter(foodAdapter);
        read_food_data();
        ArrayList<Food>foods1 = new ArrayList<>();
        open_kcal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.getInstance().setMy_kcal((double) 0);
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_layout6);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button yes_button = dialog.findViewById(R.id.yes_button);
                Button no_button = dialog.findViewById(R.id.no_button);
                first_button = dialog.findViewById(R.id.first_button);
                second_button = dialog.findViewById(R.id.second_button);
                radio_group = dialog.findViewById(R.id.radio_group);
                LinearLayout total_layout=dialog.findViewById(R.id.total_layout);
                TextView second=dialog.findViewById(R.id.second);
                TextView first_check=dialog.findViewById(R.id.first_check);
                EditText second_check=dialog.findViewById(R.id.second_check);
                TextView third_check=dialog.findViewById(R.id.third_check);
                EditText fourth_check=dialog.findViewById(R.id.fourth_check);
                TextView five_check=dialog.findViewById(R.id.five_check);
                EditText six_check=dialog.findViewById(R.id.six_check);
                TextView seven_check=dialog.findViewById(R.id.seven_check);
                TextView eight_textview=dialog.findViewById(R.id.eight_textview);
                TextView nine=dialog.findViewById(R.id.nine);
                TextView ten=dialog.findViewById(R.id.ten);
                RadioGroup radio_group1=(RadioGroup)dialog.findViewById(R.id.radio_group1);
                xy=-1;
                total=0;
                boolean input_check[]=new boolean[3];
                next_check=false;
                radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.first_button) {
                            total_layout.setVisibility(View.VISIBLE);
                            second.setVisibility(View.VISIBLE);
                            first_check.setVisibility(View.VISIBLE);
                            first_check.setText("66+(13.7*");
                            second_check.setVisibility(View.VISIBLE);
                            second_check.setText("");
                            third_check.setVisibility(View.VISIBLE);
                            third_check.setText("kg)+(5*");
                            fourth_check.setVisibility(View.VISIBLE);
                            fourth_check.setText("");
                            five_check.setVisibility(View.VISIBLE);
                            five_check.setText("cm)-(6.8*");
                            six_check.setVisibility(View.VISIBLE);
                            six_check.setText("");
                            seven_check.setVisibility(View.VISIBLE);
                            seven_check.setText(")");
                            RadioButton first_button1=(RadioButton)dialog.findViewById(R.id.first_button1);
                            RadioButton second_button1=(RadioButton)dialog.findViewById(R.id.second_button1);
                            RadioButton third_button1=(RadioButton)dialog.findViewById(R.id.third_button1);
                            RadioButton fourth_button1=(RadioButton)dialog.findViewById(R.id.fourth_button1);
                            RadioButton fifth_button1=(RadioButton)dialog.findViewById(R.id.fifth_button1);
                            for(int i=0;i<3;i++){
                                input_check[i]=false;
                            }
                            second_check.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if(second_check.getText().toString().equals("")){
                                        input_check[0]=false;
                                        eight_textview.setVisibility(View.GONE);
                                        nine.setVisibility(View.GONE);
                                        radio_group1.setVisibility(View.GONE);
                                    }
                                    else{
                                        input_check[0]=true;
                                    }
                                    int a=0;
                                    for(int i=0;i<3;i++){
                                        if(input_check[i]==true){
                                            a++;
                                        }
                                    }
                                    if(a==3){
                                        next_check=true;
                                    }
                                    else{
                                        next_check=false;
                                    }
                                    if(next_check==true){
                                        eight_textview.setVisibility(View.VISIBLE);
                                        double temp=66+(13.7*Integer.parseInt(second_check.getText().toString()))
                                                +(5*Integer.parseInt(fourth_check.getText().toString()))
                                                -(6.8*Integer.parseInt(six_check.getText().toString()));
                                        eight_textview.setText(String.valueOf(temp));
                                        nine.setVisibility(View.VISIBLE);
                                        radio_group1.setVisibility(View.VISIBLE);
                                        radio_group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                if(checkedId==R.id.first_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.2));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.second_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.375));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.third_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.55));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.fourth_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.725));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else{
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.9));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        eight_textview.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });
                            fourth_check.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if(fourth_check.getText().toString().equals("")){
                                        input_check[1]=false;
                                        eight_textview.setVisibility(View.GONE);
                                        nine.setVisibility(View.GONE);
                                        radio_group1.setVisibility(View.GONE);
                                    }
                                    else{
                                        input_check[1]=true;
                                    }
                                    int a=0;
                                    for(int i=0;i<3;i++){
                                        if(input_check[i]==true){
                                            a++;
                                        }
                                    }
                                    if(a==3){
                                        next_check=true;
                                    }
                                    else{
                                        next_check=false;
                                    }
                                    if(next_check==true){
                                        eight_textview.setVisibility(View.VISIBLE);
                                        double temp=66+(13.7*Integer.parseInt(second_check.getText().toString()))
                                                +(5*Integer.parseInt(fourth_check.getText().toString()))
                                                -(6.8*Integer.parseInt(six_check.getText().toString()));
                                        eight_textview.setText(String.valueOf(temp));
                                        nine.setVisibility(View.VISIBLE);
                                        radio_group1.setVisibility(View.VISIBLE);
                                        radio_group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                if(checkedId==R.id.first_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.2));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.second_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.375));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.third_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.55));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.fourth_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.725));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else{
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.9));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        eight_textview.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });
                            six_check.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if(six_check.getText().toString().equals("")){
                                        input_check[2]=false;
                                        eight_textview.setVisibility(View.GONE);
                                        nine.setVisibility(View.GONE);
                                        radio_group1.setVisibility(View.GONE);
                                    }
                                    else{
                                        input_check[2]=true;
                                    }
                                    int a=0;
                                    for(int i=0;i<3;i++){
                                        if(input_check[i]==true){
                                            a++;
                                        }
                                    }
                                    if(a==3){
                                        next_check=true;
                                    }
                                    else{
                                        next_check=false;
                                    }
                                    if(next_check==true){
                                        eight_textview.setVisibility(View.VISIBLE);
                                        double temp=66+(13.7*Integer.parseInt(second_check.getText().toString()))
                                                +(5*Integer.parseInt(fourth_check.getText().toString()))
                                                -(6.8*Integer.parseInt(six_check.getText().toString()));
                                        eight_textview.setText(String.valueOf(temp));
                                        nine.setVisibility(View.VISIBLE);
                                        radio_group1.setVisibility(View.VISIBLE);
                                        radio_group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                if(checkedId==R.id.first_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.2));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.second_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.375));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.third_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.55));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.fourth_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.725));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else{
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.9));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        eight_textview.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });
                            xy=0;
                        } else if (checkedId== R.id.second_button) {
                            total_layout.setVisibility(View.VISIBLE);
                            second.setVisibility(View.VISIBLE);
                            first_check.setVisibility(View.VISIBLE);
                            first_check.setText("655+(9.6*");
                            second_check.setVisibility(View.VISIBLE);
                            second_check.setText("");
                            third_check.setVisibility(View.VISIBLE);
                            third_check.setText("kg)+(1.7*");
                            fourth_check.setVisibility(View.VISIBLE);
                            fourth_check.setText("");
                            five_check.setVisibility(View.VISIBLE);
                            five_check.setText("cm)-(4.7*");
                            six_check.setVisibility(View.VISIBLE);
                            six_check.setText("");
                            seven_check.setVisibility(View.VISIBLE);
                            seven_check.setText(")");
                            for(int i=0;i<3;i++){
                                input_check[i]=false;
                            }
                            second_check.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if(second_check.getText().toString().equals("")){
                                        input_check[0]=false;
                                        eight_textview.setVisibility(View.GONE);
                                        nine.setVisibility(View.GONE);
                                        radio_group1.setVisibility(View.GONE);
                                    }
                                    else{
                                        input_check[0]=true;
                                    }
                                    int a=0;
                                    for(int i=0;i<3;i++){
                                        if(input_check[i]==true){
                                            a++;
                                        }
                                    }
                                    if(a==3){
                                        next_check=true;
                                    }
                                    else{
                                        next_check=false;
                                    }
                                    if(next_check==true){
                                        eight_textview.setVisibility(View.VISIBLE);
                                        double temp=655+(9.6*Integer.parseInt(second_check.getText().toString()))
                                                +(1.7*Integer.parseInt(fourth_check.getText().toString()))
                                                -(4.7*Integer.parseInt(six_check.getText().toString()));
                                        eight_textview.setText(String.valueOf(temp));
                                        nine.setVisibility(View.VISIBLE);
                                        radio_group1.setVisibility(View.VISIBLE);
                                        radio_group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                if(checkedId==R.id.first_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.2));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.second_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.375));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.third_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.55));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.fourth_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.725));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else{
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.9));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        eight_textview.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });
                            fourth_check.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if(fourth_check.getText().toString().equals("")){
                                        input_check[1]=false;
                                        eight_textview.setVisibility(View.GONE);
                                        nine.setVisibility(View.GONE);
                                        radio_group1.setVisibility(View.GONE);
                                    }
                                    else{
                                        input_check[1]=true;
                                    }
                                    int a=0;
                                    for(int i=0;i<3;i++){
                                        if(input_check[i]==true){
                                            a++;
                                        }
                                    }
                                    if(a==3){
                                        next_check=true;
                                    }
                                    else{
                                        next_check=false;
                                    }
                                    if(next_check==true){
                                        eight_textview.setVisibility(View.VISIBLE);
                                        double temp=655+(9.6*Integer.parseInt(second_check.getText().toString()))
                                                +(1.7*Integer.parseInt(fourth_check.getText().toString()))
                                                -(4.7*Integer.parseInt(six_check.getText().toString()));
                                        eight_textview.setText(String.valueOf(temp));
                                        nine.setVisibility(View.VISIBLE);
                                        radio_group1.setVisibility(View.VISIBLE);
                                        radio_group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                if(checkedId==R.id.first_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.2));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.second_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.375));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.third_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.55));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.fourth_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.725));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else{
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.9));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        eight_textview.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });
                            six_check.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if(six_check.getText().toString().equals("")){
                                        input_check[2]=false;
                                        eight_textview.setVisibility(View.GONE);
                                        nine.setVisibility(View.GONE);
                                        radio_group1.setVisibility(View.GONE);
                                    }
                                    else{
                                        input_check[2]=true;
                                    }
                                    int a=0;
                                    for(int i=0;i<3;i++){
                                        if(input_check[i]==true){
                                            a++;
                                        }
                                    }
                                    if(a==3){
                                        next_check=true;
                                    }
                                    else{
                                        next_check=false;
                                    }
                                    if(next_check==true){
                                        eight_textview.setVisibility(View.VISIBLE);
                                        double temp=655+(9.6*Integer.parseInt(second_check.getText().toString()))
                                                +(1.7*Integer.parseInt(fourth_check.getText().toString()))
                                                -(4.7*Integer.parseInt(six_check.getText().toString()));
                                        eight_textview.setText(String.valueOf(temp));
                                        nine.setVisibility(View.VISIBLE);
                                        radio_group1.setVisibility(View.VISIBLE);
                                        radio_group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                if(checkedId==R.id.first_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.2));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.second_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.375));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.third_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.55));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else if(checkedId==R.id.fourth_button1){
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.725));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                                else{
                                                    ten.setVisibility(View.VISIBLE);
                                                    ten.setText(String.valueOf(Double.parseDouble(eight_textview.getText()
                                                            .toString())*1.9));
                                                    total=1;
                                                    Global.getInstance().setMy_kcal(Double.parseDouble(ten.getText().toString()));
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        eight_textview.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });
                        }
                    }
                });
                yes_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (total == 1) {
                            Double main=Global.getInstance().getMy_kcal();
                            //단백질 지방 탄수화물
                            String first=String.valueOf(Math.round(main/100*30/4))+"g";
                            String second=String.valueOf(Math.round(main/100*20/9))+"g";
                            String third=String.valueOf(Math.round(main/100*50/4))+"g";
                            open_kcal.setText("섭취하셔야할 칼로리는 "+
                                    String.valueOf(Global.getInstance().getMy_kcal())+" 입니다.\n"+
                                    "단백질 "+first+" 지방 "+second+" 탄수화물 "+third);
                            dialog.dismiss();
                        } else {
                            Toast_Make("칼로리 계산이 완료되지 않았습니다");
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
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String filename=Global.getInstance().getIntent_file_name();
                    FileOutputStream fileOutputStream=getActivity().openFileOutput(filename, Context.MODE_APPEND);
                    String input_str;
                    if(open_kcal.getText().toString().equals("당신의 칼로리가 보여집니다")){
                       input_str=food_choose_str;
                    }
                    else{
                       input_str="\n"+open_kcal.getText().toString()+"\n"+food_choose_str;
                    }
                    fileOutputStream.write(input_str.getBytes());
                    fileOutputStream.close();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment_Third fragment_third=new Fragment_Third();
                    transaction.replace(R.id.fragment_main, fragment_third);
                    transaction.commit();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        foods1.addAll(foods);
        find_food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String temp=find_food.getText().toString();
                foods.clear();
                clear=0;
                if(temp.length()==0){
                    foods.addAll(foods1);
                    listView.setAdapter(foodAdapter);
                }
                else{
                    foods.clear();
                    for(int i=0;i<foods1.size();i++){
                        if(foods1.get(i).getFood_name().toLowerCase().contains(temp)){
                            foods.add(foods1.get(i));
                            clear++;
                            listView.setAdapter(foodAdapter);
                        }
                    }
                    if(clear==0){
                        foods.clear();
                        listView.setAdapter(foodAdapter);
                    }
                    else{
                    }
                }
            }
        });
        foods1.addAll(foods);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        secondActivity.setOnBackPressedListener(this);
    }
    public boolean isNumeric(String input){
        try{
            Double.parseDouble(input);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        secondActivity.setFragment_Five();
    }
    class FoodAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
        private Context context;
        private List list;
        public FoodAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
        }
        class ViewHolder {
            public TextView food_name;
            public Button button;
        }
        public FoodAdapter(Context context, ArrayList list){
            super(context, 0, list);
            this.context = context;
            this.list = list;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null){
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                convertView = layoutInflater.inflate(R.layout.item_food, parent, false);
            }
            viewHolder= new ViewHolder();
            viewHolder.food_name = (TextView) convertView.findViewById(R.id.item_friend_textview);
            viewHolder.button = (Button) convertView.findViewById(R.id.checkbox_friend_choose);
            final Food food = (Food) list.get(position);
            viewHolder.food_name.setText(food.getFood_name());
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean newState=!((Food) list.get(position)).isChecked();
                    ((Food) list.get(position)).checked=newState;
                    if(newState==true){
                        Dialog dialog=new Dialog(getActivity());
                        dialog.setContentView(R.layout.custom_layout5);
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        Button yes_button=dialog.findViewById(R.id.yes_button);
                        Button no_button=dialog.findViewById(R.id.no_button);
                        EditText food_edit=dialog.findViewById(R.id.food_edit);
                        TextView textview_kcal=dialog.findViewById(R.id.textview_kcal);
                        TextView textview_4kcal=dialog.findViewById(R.id.textview_4kcal);
                        TextView textview_9kcal=dialog.findViewById(R.id.textview_9kcal);
                        TextView textview_4kcal1=dialog.findViewById(R.id.textview_4kca1l);
                        food_edit.setText("");
                        String food_choose_temp="";
                        double []check_double=new double[5];
                        food_edit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }
                            @Override
                            public void onTextChanged(CharSequence s, int start, int beforvhde, int count) {
                                if(food_edit.getText().toString().equals("")){
                                    Toast_Make("그램을 입력해주새요");
                                    yes_button.setClickable(false);
                                    textview_kcal.setText("애너지 ");
                                    textview_4kcal.setText("단백질 ");
                                    textview_9kcal.setText("지방 ");
                                    textview_4kcal1.setText("탄수화물 ");
                                }
                                else{
                                    textview_kcal.setText("애너지 "+
                                            String.valueOf(Math.round(Double.parseDouble(food_edit.getText().toString())*
                                                    Double.parseDouble(parsing_real_real[position][2])/
                                                    Double.parseDouble(parsing_real_real[position][1]))));
                                    textview_4kcal.setText("단백질 "+
                                            String.valueOf(Math.round(Double.parseDouble(food_edit.getText().toString())*
                                                    Double.parseDouble(parsing_real_real[position][3])/
                                                    Double.parseDouble(parsing_real_real[position][1]))));
                                    textview_9kcal.setText("지방 "+
                                            String.valueOf(Math.round(Double.parseDouble(food_edit.getText().toString())*
                                                    Double.parseDouble(parsing_real_real[position][4])/
                                                    Double.parseDouble(parsing_real_real[position][1]))));
                                    textview_4kcal1.setText("탄수화물 "+
                                            String.valueOf(Math.round(Double.parseDouble(food_edit.getText().toString())*
                                                    Double.parseDouble(parsing_real_real[position][5])/
                                                    Double.parseDouble(parsing_real_real[position][1]))));
                                    yes_button.setClickable(true);
                                }
                            }
                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });
                        yes_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(food_edit.getText().toString().equals("")){
                                    Toast_Make("그램을 입력하셔야 합니다.");
                                }
                                else{
                                    food_choose_str=food_choose_str+food.getFood_name()+" "+food_edit.getText().toString()+
                                            "단백질 "+
                                            String.valueOf(Math.round(Double.parseDouble(food_edit.getText().toString())*
                                                    Double.parseDouble(parsing_real_real[position][3])/
                                                    Double.parseDouble(parsing_real_real[position][1])))+"g "+
                                            "지방 "+
                                            String.valueOf(Math.round(Double.parseDouble(food_edit.getText().toString())*
                                                    Double.parseDouble(parsing_real_real[position][4])/
                                                    Double.parseDouble(parsing_real_real[position][1])))+"g "+
                                            "탄수화물 "+
                                            String.valueOf(Math.round(Double.parseDouble(food_edit.getText().toString())*
                                                    Double.parseDouble(parsing_real_real[position][5])/
                                                    Double.parseDouble(parsing_real_real[position][1])))+"g "+
                                            "\n";
                                    food_choose.setText(food_choose_str);
                                    dialog.dismiss();
                                }
                            }
                        });
                        no_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((Food) list.get(position)).setChecked(false);
                                listView.setAdapter(foodAdapter);
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });
            return convertView;
        }
        private boolean isChecked(int position) {
            return((Food) list.get(position)).checked;
        }
    }
}