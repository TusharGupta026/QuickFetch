package com.example.quickfetch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.quickfetch.model.Bank;
import com.example.quickfetch.util.BankRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BankSelectActivity extends AppCompatActivity {

        RecyclerView mRecyclerView;
        List<Object> viewItems = new ArrayList<>();
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        private static final String TAG = "BankSelectActivity";
        Button consentBtn,submitBtn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bank_select);

            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.user_consent, null);
            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            submitBtn=(Button) findViewById(R.id.submitBtn);
            consentBtn=(Button) popupView.findViewById(R.id.consentBtn);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);

            // specify an adapter (see also next example)
            mAdapter = new BankRecyclerAdapter(this, viewItems);
            mRecyclerView.setAdapter(mAdapter);

            addItemsFromJSON();
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // create the popup window
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    int height = LinearLayout.LayoutParams.MATCH_PARENT;
                    // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height);
                    popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                    popupWindow.setFocusable(true);
                    popupWindow.update();
                }
            });

            consentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(BankSelectActivity.this,FetchActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }

        private void addItemsFromJSON() {
            try {

                String jsonDataString = readJSONDataFromFile();
                JSONArray jsonArray = new JSONArray(jsonDataString);

                for (int i=0; i<jsonArray.length(); ++i) {

                    JSONObject itemObj = jsonArray.getJSONObject(i);

                    String name = itemObj.getString("name");
                    String logo = itemObj.getString("logo");

                    Bank bank = new Bank(name, logo);
                    viewItems.add(bank);
                }

            } catch (JSONException | IOException e) {

            }
        }

        private String readJSONDataFromFile() throws IOException{

            InputStream inputStream = null;
            StringBuilder builder = new StringBuilder();

            try {

                String jsonString = null;
                inputStream = getResources().openRawResource(R.raw.bank);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"));

                while ((jsonString = bufferedReader.readLine()) != null) {
                    builder.append(jsonString);
                }

            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return new String(builder);
        }
    }
