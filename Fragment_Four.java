package com.example.termproject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Four extends Fragment implements OnBackPressedListener  {
    private View view;
    private long backKeyPressedTime;
    private SecondActivity secondActivity;
    private ListView listView;
    private int fine_ok=0;
    private ExerciseAdapter excerciseAdapter;
    private ArrayList<Exercise> exercises;
    private EditText find_exercise;
    private Button check_button;
    private TextView test;
    private String parsing;
    private String []parsing_real;
    private String []parsing_real_real;
    private int clear=0;
    private int choose_number=0;
    int number=0;
    private String exercise_str="";
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_four,container,false);
        secondActivity=(SecondActivity) getActivity();
        listView=(ListView) view.findViewById(R.id.listView);
        find_exercise=(EditText) view.findViewById(R.id.find_exercise);
        exercise_str=Global.getInstance().getAll();
        if(Global.getInstance().getChoose_number()==0){
            exercises=new ArrayList<>();
        }else{
            exercises=Global.getInstance().getExercises();
        }
        ArrayList<Exercise>exercises1 = new ArrayList<>();
        excerciseAdapter=new ExerciseAdapter(getContext(),exercises);
        listView.setAdapter(excerciseAdapter);
        check_button=(Button) view.findViewById(R.id.check_button);
        test=(TextView)view.findViewById(R.id.test);
        InputStream txtResource = getActivity().getResources().openRawResource(R.raw.exercise);
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
        parsing_real_real=new String[parsing_real.length];
        for(int i=1;i<parsing_real.length;i++){
            String []temp=parsing_real[i].split(",");
            parsing_real_real[i]=temp[0];
            exercises.add(new Exercise(parsing_real_real[i],false));
        }
        find_exercise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String temp=find_exercise.getText().toString();
                exercises.clear();
                clear=0;
                if(temp.length()==0){
                    exercises.addAll(exercises1);
                    listView.setAdapter(excerciseAdapter);
                }
                else{
                    exercises.clear();
                    for(int i=0;i<exercises1.size();i++){
                        if(exercises1.get(i).getName().toLowerCase().contains(temp)){
                            exercises.add(exercises1.get(i));
                            clear++;
                            listView.setAdapter(excerciseAdapter);
                        }
                    }
                    if(clear==0){
                        exercises.clear();
                        listView.setAdapter(excerciseAdapter);
                    }
                    else{
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename=exercises.get(position).getName()+".txt";
                Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_layout7);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                TextView second_title=dialog.findViewById(R.id.second_title);
                Button yes_button=dialog.findViewById(R.id.yes_button);
                second_title.setText(readFile(filename));
                yes_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        exercises1.addAll(exercises);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Global.getInstance().getToday_exercise()==1){
                    Toast_Make("오늘 이미 운동했습니다");
                }
                else{
                    Global.getInstance().setExercises(exercises);
                    Toast_Make("오늘의 운동 설정이 완료되었습니다");
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment_Second fragment_second=new Fragment_Second();
                    transaction.replace(R.id.fragment_main, fragment_second);
                    BottomNavigationView bottom_menu=getActivity().findViewById(R.id.bottom_menu);
                    bottom_menu.setSelectedItemId(R.id.second_tab);
                    transaction.commit();
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
            public CheckBox checkbox;
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
                convertView = layoutInflater.inflate(R.layout.item_exercise, parent, false);
            }
            viewHolder= new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_friend_textview);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox_friend_choose);
            final Exercise exercise = (Exercise) list.get(position);
            viewHolder.name.setText(exercise.getName());
            viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean newState=!((Exercise) list.get(position)).isChecked();
                    ((Exercise) list.get(position)).checked=newState;
                    if(newState==true){
                        choose_number++;
                        Global.getInstance().setChoose_number(choose_number);
                        Dialog dialog=new Dialog(getActivity());
                        dialog.setContentView(R.layout.custom_layout);
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        Button yes_button=dialog.findViewById(R.id.yes_button);
                        Button no_button=dialog.findViewById(R.id.no_button);
                        EditText set_edit=dialog.findViewById(R.id.set_edit);
                        set_edit.setText("");
                        LinearLayout open_layout=dialog.findViewById(R.id.open_layout);
                        exercise_str=exercise_str+exercise.getName();
                        set_edit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if(set_edit.getText().toString().equals("")){
                                    Toast_Make("세트수를 입력해주세요");
                                    yes_button.setClickable(false);
                                    open_layout.removeAllViews();
                                }
                                else{
                                    yes_button.setClickable(true);
                                    number=Integer.parseInt(set_edit.getText().toString());
                                    int temp[]=new int[number];
                                    for(int i=0;i<number;i++) {
                                        EditText editText = new EditText(getActivity());
                                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT,1);
                                        editText.setLayoutParams(p);
                                        editText.setHint("무게와 쉬는시간을 입력하세요 ex) 100kg 60초");
                                        editText.setTextAppearance(context,R.style.one);
                                        editText.setId(i);
                                        temp[i]=editText.getId();
                                        open_layout.addView(editText);
                                    }
                                }
                            }
                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });
                        yes_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exercise_str=exercise_str+","+String.valueOf(number);
                                String temp_exercise_str="";
                                if(set_edit.getText().toString().equals("")){
                                    Toast_Make("세트수를 입력하셔야 합니다.");
                                }
                                else{
                                    String at="";
                                    fine_ok=0;
                                    for(int i=0;i<number;i++){
                                        EditText et = (EditText) open_layout.getChildAt(i);
                                        at=at+et.getText().toString();
                                        exercise_str=exercise_str+","+et.getText().toString();
                                        temp_exercise_str=temp_exercise_str+et.getText().toString();
                                    }
                                    for(int i=0;i<number;i++){
                                        EditText et = (EditText) open_layout.getChildAt(i);
                                        if(et.getText().toString().equals("")){
                                            fine_ok++;
                                        }
                                    }
                                    exercise_str=exercise_str+"\n";
                                    Global.getInstance().setAll(exercise_str);
                                    if(fine_ok!=0){
                                        Toast_Make("무게와 세트수를 입력하셔야 합니다");
                                    }
                                    else{
                                        find_exercise.setText("");
                                        dialog.dismiss();
                                    }
                                }
                            }
                        });
                        no_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((Exercise) list.get(position)).setChecked(false);
                                listView.setAdapter(excerciseAdapter);
                                dialog.dismiss();
                            }
                        });
                    }
                    else{
                        if(choose_number>0){
                            choose_number--;
                        }
                        Global.getInstance().setChoose_number(choose_number);
                    }
                }
            });
            viewHolder.checkbox.setChecked(isChecked(position));
            return convertView;
        }
        private boolean isChecked(int position) {
            return((Exercise) list.get(position)).checked;
        }
    }
    public String readFile(String filename){
        String return_str=null;
        FileInputStream fileInputStream;
        try{
            fileInputStream=getActivity().openFileInput(filename);
            byte []text=new byte[1024];
            fileInputStream.read(text);
            fileInputStream.close();
            return_str=(new String(text).trim());
        } catch (Exception e) {
            return_str="운동 기록없음";
            e.printStackTrace();
        }
        return return_str;
    }
}