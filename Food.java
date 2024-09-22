package com.example.termproject;


//식품명,1회제공량,에너지,단백질,지방,탄수화물
public class Food {
    private String food_name;
    public String food_one_meal;
    public String food_kcal;
    public String food_4kcal;
    public String food_9kcal;
    public String food_4kcal1;
    public boolean checked;
    public Food(String food_name,String food_one_meal,String food_kcal,String food_4kcal,
                String food_9kcal,String food_4kcal1,boolean checked){
      this.food_name=food_name;
      this.food_one_meal=food_one_meal;
      this.food_kcal=food_kcal;
      this.food_4kcal=food_4kcal;
      this.food_9kcal=food_9kcal;
      this.food_4kcal1=food_4kcal1;
      this.checked=checked;
    }
    public boolean getChecked() {
        return checked;
    }
    public String getFood_name() {
        return food_name;
    }
    public String getFood_one_meal(){
        return food_one_meal;
    }
    public String getFood_kcal(){
        return food_kcal;
    }
    public void setChecked(boolean checked){
        this.checked=checked;
    }
    public String getFood_4kcal(){
        return food_4kcal;
    }
    public String getFood_9kcal(){
        return food_9kcal;
    }
    public String getFood_4kcal1(){
        return food_4kcal1;
    }
    public boolean isChecked() {
        return checked;
    }
}