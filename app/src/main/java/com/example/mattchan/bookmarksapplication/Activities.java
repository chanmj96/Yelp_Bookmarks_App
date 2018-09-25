package com.example.mattchan.bookmarksapplication;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.GridView;
import android.view.ViewManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import android.widget.ArrayAdapter;

public class Activities extends AppCompatActivity {

    GridView gridView;
    ArrayList<Button> buttons = new ArrayList<Button>();

    public void refresh(ArrayList<String> arr){
        ArrayList<String> output = new ArrayList<>();

        //for(int i = 0; i < arr.size(); i++)
            //System.out.println(arr.get(i) + "\n");

        for(int i = 0; i < arr.size(); i++)
        {
            String[] inputs = arr.get(i).split("\t");
            for(String s: inputs)
                output.add(s);

          }
//            Button b = new Button(this);
//            b.setLayoutParams(new RelativeLayout.LayoutParams(0, i*200));
//            b.setText("X");
//            b.setId(i);
//            RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
//            layout.addView(b);
//            buttons.add(b);
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, output);
        gridView.setAdapter(adapter);

    }


//    public void deleteButton(View entry)
//    {
//        int id = entry.getId();
//        ((ViewManager)entry.getParent()).removeView(entry);
//        arr.remove(id*2+1);
//        arr.remove(id*2);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_list);
        gridView = (GridView) findViewById(R.id.gridView1);

        MainActivity m = new MainActivity();

        refresh(m.fileRead("yelpInfo.txt", this));

    }

    public void OnClick(View view){
//        RelativeLayout second_layout = new RelativeLayout(this);
//
//        EditText userName = (EditText) findViewById(R.id.editText4);
//        EditText password = (EditText) findViewById(R.id.editText5);
//        EditText email = (EditText) findViewById(R.id.editText3);
//
//        TextView t1 = new TextView(this);
//        TextView t2 = new TextView(this);
//        TextView t3 = new TextView(this);
//
//        t1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//        t2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//        t3.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//
//        t1.setText(userName.getText().toString());
//        t2.setText(password.getText().toString());
//        t3.setText(email.getText().toString());
//
//        second_layout.addView(t1);
//        second_layout.addView(t2);
//        second_layout.addView(t3);
//
//
//
//        setContentView(second_layout);

    }
}

