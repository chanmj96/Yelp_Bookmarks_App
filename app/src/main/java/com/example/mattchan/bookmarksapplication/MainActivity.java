package com.example.mattchan.bookmarksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context = this;

    private Button mButton;
    public ArrayList<String> URLList = new ArrayList<String>();
    public ArrayList<String> RestaurantList = new ArrayList<String>();

    public void fileWrite(ArrayList<String> names, ArrayList<String> url, Context context, String s){
        //writes the bookmark results to a file
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(s, Context.MODE_PRIVATE));
            for(int i=0; i<names.size(); i++){
                outputStreamWriter.write(names.get(i) + "\t" + url.get(i) + "\n");
            }
            outputStreamWriter.close();
        }catch(IOException e){
            Log.e("Exception", "File write failed: "+ e.toString());
        }
    }
    public ArrayList<String> fileRead(String file, Context context){
        //takes a file and returns an ArrayList of Pairs of names and URLs. Use getter methods (getL and getR) to output the names(L) and URLs(R)
        ArrayList<String> output = new ArrayList<String>();

        try {
            InputStream iStream = context.openFileInput(file);
            if (iStream != null) {
                InputStreamReader iStreamReader = new InputStreamReader(iStream);
                BufferedReader bReader = new BufferedReader(iStreamReader);
                String inString = "";

                while ((inString = bReader.readLine()) != null) {
                    output.add(inString);
                }
                iStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return output;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //button to press after user has entered ID
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mButton = (Button)findViewById(R.id.enter);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getInfo();
                Intent i = new Intent(MainActivity.this, Activities.class);

                startActivity(i);

                //Activities act = new Activities();

            }
        });
    }

    public void getInfo() {
        //retrieves information of the user and writes the information to a file
        EditText userID = (EditText) findViewById(R.id.user_id);
        String text = userID.getText().toString();

        try {
            final Document doc = Jsoup.connect("https://www.yelp.com/user_details_bookmarks?userid="+text) //ADD TEXT LATER
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .timeout(10000).get();

            Elements anchors = doc.select("span a[class]");            //find 1st anchor element with href attribute
            for (Element table : anchors) {
                String outer = table.outerHtml();
                String[] outerParts = outer.split("\"");

                String inner = table.html();
                String[] innerParts = inner.split("<|>");

                RestaurantList.add(innerParts[2]);
                URLList.add("https://www.yelp.com" + outerParts[5]);
            }
            fileWrite(RestaurantList, URLList, context, "yelpInfo.txt");
            System.out.println("done");
        } catch (IOException e) {
            System.out.println(e);
        }

//        For testing purposes--------------------
//
//        System.out.println("testing the next part");
//        ArrayList<Pair> test = fileRead("yelpInfo.txt", context);
//        System.out.println("wrote text to file and read it");
    }

}
