package com.example.termproject;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class SecondActivity extends AppCompatActivity {
    private Fragment_First fragment_first=new Fragment_First();
    private Fragment_Second fragment_second=new Fragment_Second();
    private Fragment_Third fragment_third=new Fragment_Third();
    private Fragment_Four fragment_four=new Fragment_Four();
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    OnBackPressedListener listener;
    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener;
    }
    @Override
    public void onBackPressed() {
        if(listener!=null){
            listener.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }
    public void setFragment_Five(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment_Third fragment_third=new Fragment_Third();
        transaction.replace(R.id.fragment_main, fragment_third);
        transaction.commit();
    }
    public void onShutDown(){
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    public void Toast_Make(String message){
        LayoutInflater inflater=getLayoutInflater();
        View layout=inflater.inflate(R.layout.toast_design_layout,(ViewGroup)findViewById(R.id.toast_design_layout_main));
        Toast toast=Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT);
        TextView textView=layout.findViewById(R.id.toast_textview);
        textView.setText(message);
        toast.setGravity(Gravity.BOTTOM,0,500);
        toast.setView(layout);
        toast.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        fragmentManager =getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_main,fragment_second).commit();
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setItemIconTintList(null);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.first_tab:    //운동기록
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragment_main,fragment_first).commitAllowingStateLoss();
                        return true;
                    case R.id.second_tab:   //운동시작
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragment_main,fragment_second).commitAllowingStateLoss();
                        return true;
                    case R.id.third_tab:    //식단정리
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragment_main,fragment_third).commitAllowingStateLoss();
                        return true;
                    case R.id.fourth_tab:   //운동결정
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.fragment_main,fragment_four).commitAllowingStateLoss();
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}