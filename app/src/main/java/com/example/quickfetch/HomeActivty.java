package com.example.quickfetch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;

public class HomeActivty extends AppCompatActivity {
    TextView tvFood, tvEntertainment, tvEducation, tvOther,tvLifestyle;
    PieChart pieChart;
    ImageView bankLogo;
    TextView bankName,accHolderName,accHolderPhoneNumber,accHolderNominee,accHolderNumber,accHolderIfscCode;
    RequestQueue requestQueue;
    int selectedPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activty);

        pieChart = findViewById(R.id.piechart);
        tvFood = findViewById(R.id.tvFood);
        tvEducation = findViewById(R.id.tvEducation);
        tvEntertainment = findViewById(R.id.tvEntertainment);
        tvLifestyle = findViewById(R.id.tvLifestyle);
        tvOther = findViewById(R.id.tvOther);
        bankName=findViewById(R.id.bankName);
        accHolderName=findViewById(R.id.accHolderName);
        accHolderPhoneNumber=findViewById(R.id.accHolderPhoneNumber);
        accHolderNominee=findViewById(R.id.accHolderNominee);
        accHolderNumber=findViewById(R.id.accHolderNumber);
        accHolderIfscCode=findViewById(R.id.accHolderIfscCode);
        bankLogo=findViewById(R.id.bankLogo);
        requestQueue= Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://quickkfetch.free.beeceptor.com", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivty.this);
                    int data = prefs.getInt("selectedPosition",selectedPosition);

                    tvFood.setText(Integer.toString((response.getJSONObject(data).getJSONArray("track").getJSONObject(0).getInt("food"))));
                    tvEntertainment.setText(Integer.toString(response.getJSONObject(data).getJSONArray("track").getJSONObject(0).getInt("entertainment")));
                    tvEducation.setText(Integer.toString(response.getJSONObject(data).getJSONArray("track").getJSONObject(0).getInt("education")));
                    tvLifestyle.setText(Integer.toString(response.getJSONObject(data).getJSONArray("track").getJSONObject(0).getInt("lifestyle")));
                    tvOther.setText(Integer.toString(response.getJSONObject(data).getJSONArray("track").getJSONObject(0).getInt("other")));

                    pieChart.addPieSlice(
                            new PieModel(
                                    "Food",
                                    Integer.parseInt(tvFood.getText().toString()),
                                    Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Entertainment",
                                    Integer.parseInt(tvEntertainment.getText().toString()),
                                    Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Education",
                                    Integer.parseInt(tvEducation.getText().toString()),
                                    Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Lifestyle",
                                    Integer.parseInt(tvLifestyle.getText().toString()),
                                    Color.parseColor("#29B6F6")));
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Other",
                                    Integer.parseInt(tvOther.getText().toString()),
                                    Color.parseColor("#3321DF")));

                    pieChart.startAnimation();



                    bankName.setText(response.getJSONObject(data).getString("bank"));
                    accHolderName.setText(response.getJSONObject(data).getString("name"));
                    accHolderIfscCode.setText(response.getJSONObject(data).getString("ifsc"));
                    accHolderPhoneNumber.setText(response.getJSONObject(data).getString("phoneNumber"));
                    accHolderNominee.setText(response.getJSONObject(data).getString("nominee"));
                    accHolderNumber.setText(response.getJSONObject(data).getString("accountNumber"));

                    Picasso.get().load(response.getJSONObject(data).getString("logo")).placeholder(R.drawable.round_shape).fit().into(bankLogo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp", "Something went wrong");

            }
        });

        requestQueue.add(jsonArrayRequest);


    }

}