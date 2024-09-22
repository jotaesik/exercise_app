package com.example.termproject;

import java.util.ArrayList;

public class Global {
    private String now_year,now_month,now_day;
    private int choose_number;
    private String all="";
    private int today_exercise;
    private double my_kcal;
    private String intent_file_name;
    public void setIntent_file_name(String intent_file_name){
        this.intent_file_name=intent_file_name;
    }
    public String getIntent_file_name(){
        return intent_file_name;
    }
    public void setMy_kcal(Double my_kcal){
        this.my_kcal=my_kcal;
    }
    public Double getMy_kcal(){
        return my_kcal;
    }
    public String food_choose;
    public void setFood_choose(String food_choose){
        this.food_choose=food_choose;
    }
    public String getFood_choose(){
        return food_choose;
    }
    public void setToday_exercise(int today_exercise){
        this.today_exercise=today_exercise;
    }
    public int getToday_exercise(){
        return today_exercise;
    }
    public void setAll(String all){
        this.all=all;
    }
    public String getAll(){
        return all;
    }
    public void setChoose_number(int choose_number){
        this.choose_number=choose_number;
    }
    public int getChoose_number(){
        return choose_number;
    }
    public void setNow_year(String now_year){
        this.now_year=now_year;
    }
    public void setNow_month(String now_month){
        this.now_month=now_month;
    }
    public void setNow_day(String now_day){
        this.now_day=now_day;
    }
    public String getNow_year(){
        return now_year;
    }
    public String getNow_month(){
        return now_month;
    }
    public String getNow_day(){
        return now_day;
    }
    private ArrayList<Exercise> exercises = new ArrayList<>();
    public ArrayList<Exercise> getExercises(){
        return exercises;
    }
    public void setExercises(ArrayList<Exercise>exercises){
        this.exercises=exercises;
    }
    private static Global instance=null;
    public static synchronized Global getInstance(){
        if(instance==null){
            instance=new Global();
        }
        return instance;
    }
}
