package com.example.termproject;



public class Exercise {
    private String name;
    public boolean checked;
    public String no_else;

    public Exercise(String name, boolean checked){
        this.name=name;
        this.checked=checked;
    }
    public Exercise(String name,boolean checked,String no_else){
        this.name=name;
        this.checked=checked;
        this.no_else=no_else;
    }

    public boolean getChecked() {
        return checked;
    }
    public void setChecked(boolean checked){
        this.checked=checked;
    }
    public String getName() {
        return name;
    }
    public boolean isChecked() {
        return checked;
    }
}