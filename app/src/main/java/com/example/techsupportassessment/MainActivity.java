package com.example.techsupportassessment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> list;
    String randomString;
    String[] listOfMethods = {"Backwards Iteration", "Contains Method"};
    TextView generatedTV, operationTv, lastUniqueCharTv, timeTakenTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        initializeListView();
        randomString = getRandomString(20);
        generatedTV.setText(randomString);
    }

    private void initializeViews(){
        generatedTV = findViewById(R.id.generatedStringTV);
        operationTv = findViewById(R.id.operationTV);
        lastUniqueCharTv = findViewById(R.id.lastUniqueCharTV);
        timeTakenTv = findViewById(R.id.timeTakenTV);
    }

    private void initializeListView() {
        ListView listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listOfMethods);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            String value=adapter.getItem(position);
            initiateMethod(value, 20);
            Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
        });
}
    private void displayAnswers(String generatedString, String operationName, String uniqueCharAnswer, long timeTaken){
        generatedTV.setText( "String : \n" + generatedString);
        operationTv.setText("Operation : " + operationName);
        lastUniqueCharTv.setText("Unique Char : " + uniqueCharAnswer);
        double elapsedTimeInSecond = (double) timeTaken / 1_000_000_000;
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(12);
        timeTakenTv.setText("Time taken in nanotimes : " + df.format(elapsedTimeInSecond));
    }
/*
 given an input string, returns the last unique character.
 Explore different solutions, pros and cons for each solution and make sure to
  mention the time and space complexity of your algorithm.
 */

    private void initiateMethod(String value, int bound) {
        String uniqueChar = "none Unique";
        long end = 0, start = 0;
        randomString = getRandomString(bound);
        switch(value){
            case "Backwards Iteration":
                start = System.nanoTime();
                String repeating = "";
                outerloop:
                for(int i = randomString.length() - 1; i > -1; i-- ){
                    char currentChar = randomString.charAt(i);
                    boolean isUnique = true;
                    for(int p = (i - 1); p > -1; p --){
                        char prevChar = randomString.charAt(p);
                        if(currentChar == prevChar){
                            isUnique = false;
                            repeating = repeating + currentChar;
                            continue outerloop;
                        }
                    }
                    if(isUnique && !repeating.contains(String.valueOf(currentChar))){
                        uniqueChar = String.valueOf(currentChar);
                        break;
                    }
                }
                end = System.nanoTime();
                break;

            case "Contains Method":
                start = System.nanoTime();
                Map<Character,Integer> hash = new HashMap<Character,Integer>();
                for(int i=0;i<randomString.length();i++) {
                    if(hash.containsKey(randomString.charAt(i)))
                        hash.put(randomString.charAt(i), hash.get(randomString.charAt(i))+1);

                    else
                        hash.put(randomString.charAt(i), 1);

                    int result = hash.get(randomString.charAt(i));
                    if(result == 1){
                        uniqueChar = String.valueOf(randomString.charAt(i));
                    }
                }
                end = System.nanoTime();
                break;
        }
        long durationLong = end - start;
        displayAnswers(randomString, value, uniqueChar, durationLong);
    }

    private String getRandomString(int bound) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < bound) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}