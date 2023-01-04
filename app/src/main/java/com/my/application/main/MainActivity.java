package com.my.application.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public LinearLayout linear3, linear4, linear1;
    public TextView text;
    public CheckBox option1, option2, option3, option4;
    public Button button;
    public Animation shake;
    public ArrayList<HashMap<String, String>> arrli;
    public String answer, checkString;
    public boolean checkAnswer = false;
    public boolean isShake = false;
    public double length, a;
    public static int checked = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeValues();
        arrli.clear();
        ChangeBackground(new View[]{linear1, linear3, option3, option4, option1, option2});
        loadNext();
        showMessage("Made With 💖 by Ayan");

    }

    public void initializeValues() {
        option1 = (CheckBox) findViewById(R.id.option1);
        option2 = (CheckBox) findViewById(R.id.option2);
        option3 = (CheckBox) findViewById(R.id.option3);
        option4 = (CheckBox) findViewById(R.id.option4);
        button = (Button) findViewById(R.id.button1);
        text = (TextView) findViewById(R.id.question);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        linear1 = (LinearLayout) findViewById(R.id.linear2);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        arrli = new ArrayList<>();


    }

    public void QuizMaker() {
        try {
            JSONObject jsonobject = new JSONObject(loadJsonFromAssets());
            JSONArray jarray = (JSONArray) jsonobject.getJSONArray("Quiz");
            arrli.clear();
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jb = jarray.getJSONObject(i);
                String ques = jb.getString("ques");
                String opt = jb.getString("option1");
                String opt2 = jb.getString("option2");
                String opt3 = jb.getString("option3");
                String opt4 = jb.getString("option4");
                answer = jb.getString("answer");
                addValuefromhashmap(ques, opt, opt2, opt3, opt4, answer);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addValuefromhashmap(String ques, String option1, String option2, String option3, String option4, String answer) {
        HashMap<String, String> value = new HashMap<>();
        value.put("ques", ques);
        value.put("option1", option1);
        value.put("option2", option2);
        value.put("option3", option3);
        value.put("option4", option4);
        value.put("answer", answer);
        arrli.add(value);
    }
    public void SkipQuiz(View v){
      try {
          if (checked == length) {
              showMessage("No More");
          } else {
              checked++;
              loadNext();
          }
      }catch (Exception e){
          e.printStackTrace();
      }
    }


    public void checkAnswer(View view) {
        isShakable();
        if (checkAnswer) {
            if(checked == length){
               showMessage("You win");
            }else{
                loadNext();
            }

        }else{

    }
    }

    private void isShakable() {
        if (!checkString.equals(option1.getText().toString())) {
            if (option1.isChecked()) {
                option1.startAnimation(shake);
            }
        }
      if (!checkString.equals(option2.getText().toString())){
          if(option2.isChecked()){
              option2.startAnimation(shake);
          }
      }
      if (!checkString.equals(option3.getText().toString())){
                if(option3.isChecked()){
                    option3.startAnimation(shake);
                }
            }
            if (!checkString.equals(option4.getText().toString())){
                if(option4.isChecked()){
                    option4.startAnimation(shake);
                }
            }
    }

    private void showMessage(String str) {
        Toast toast = new Toast(this);
        LinearLayout lin = new LinearLayout(this);
        TextView text = new TextView(this);
        text.setText(str);
        text.setTextColor(Color.parseColor("#ff000000"));
        GradientDrawable grad = new GradientDrawable();
        grad.setCornerRadii(new float[]{16,16,16,16,16,16,16,16});
        grad.setColor(Color.parseColor("#ffffffff"));
        grad.setCornerRadius(14.0F);
        grad.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        lin.setBackground(grad);
        lin.setGravity(1);
        lin.setElevation(10);
        lin.setPadding(20,8,20,8);
        lin.addView(text);
        toast.setView(lin);
        toast.show();

    }

    public void loadNext(){
        QuizMaker();
        length = arrli.size();
        a = checked;
        System.out.println(a);
        for (int i = 0; i < length; i++) {
            String ques = arrli.get((int) a).get("ques").toString();
            if(!ques.equals("")) {
                text.setText(ques);

            }String op = arrli.get((int) a).get("option1").toString();
            if(!op.equals("")){
                option1.setText(op);
            }
            String op1 = arrli.get((int) a).get("option2").toString();
            if(!op1.equals("")){
                option2.setText(op1);
            }
            String op2 = arrli.get((int) a).get("option3").toString();
            if(!op2.equals("")){
                option3.setText(op2);
            }
            String op3 = arrli.get((int) a).get("option4").toString();
            if(!op3.equals("")){
                option4.setText(op3);
            }
            if(!answer.equals("")){
                checkString = arrli.get((int) a).get("answer").toString();

            }
            else {
                arrli.remove(a);
            }


        }

    }

    public void onCheckboxClicked(View view){
        try {
            switch (view.getId()) {
                case R.id.option1: {
                    option2.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);
                    if (checkString.equals(option1.getText().toString())) {
                        if (option1.isChecked()) {
                            checkAnswer = true;
                            checked++;
                        }
                    } else {
                        checkAnswer = false;
                    }

                }
                break;
                case R.id.option2: {
                    option1.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);

                    if (checkString.equals(option2.getText().toString())) {
                        if (option2.isChecked()) {
                            checkAnswer = true;
                            checked++;
                        }
                    } else {
                        checkAnswer = false;

                    }
                }
                break;
                case R.id.option3: {
                    option2.setChecked(false);
                    option1.setChecked(false);
                    option4.setChecked(false);

                    if (checkString.equals(option3.getText().toString())) {
                        if (option3.isChecked()) {
                            checkAnswer = true;
                            checked++;
                        }
                    } else {

                        checkAnswer = false;

                    }
                }
                break;

                case R.id.option4: {
                    option2.setChecked(false);
                    option3.setChecked(false);
                    option1.setChecked(false);

                    if (checkString.equals(option4.getText().toString())) {
                        if (option4.isChecked()) {
                            checked++;
                            checkAnswer = true;
                        }
                    } else {

                        checkAnswer = false;

                    }
                }
                break;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String loadJsonFromAssets() {
        String str = null;
        try {

            InputStream manager = getAssets().open("Action.json");
            int size = manager.available();
            byte[] bytes = new byte[size];
            manager.read(bytes);
            manager.close();
            str = new String(bytes, "UTF-8");

        } catch (Exception e) {
            return null;
        }
        return str;
    }
    public void ChangeBackground(View[] view){
        int[] colorsCHOCT = { Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF") };
        android.graphics.drawable.GradientDrawable grad = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCHOCT);
        grad.setCornerRadii(new float[]{(int)36,(int)36,(int)36,(int)36,(int)36,(int)36,(int)36,(int)36});
        grad.setStroke((int) 0, Color.parseColor("#000000"));

        for(View lin : view)
        {
            lin.setElevation((float) 10);
            lin.setBackground(grad);
            if(lin.getId()==R.id.button1){
                button.setBackgroundColor(Color.parseColor("#7ABBFF"));
            }
        }


    }
}