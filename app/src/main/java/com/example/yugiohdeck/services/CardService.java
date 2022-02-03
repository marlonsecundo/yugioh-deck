package com.example.yugiohdeck.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yugiohdeck.models.Card;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class CardService extends BaseService {

    StringRequest request;
    RequestQueue requestQueue;

    public CardService(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchCards(Map<String, String> params)  {

        String path = baseUrl + "/cardinfo.php";

        request = new StringRequest(Request.Method.GET, path, response -> {
            try {
                JSONObject jsonData = new JSONObject(response);
                Card.fromJSONList(jsonData.getJSONArray("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });

        requestQueue.add(request);
        requestQueue.start();
    }


}
