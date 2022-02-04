package com.example.yugiohdeck.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

    public void fetchCards(Map<String, String> params, Response.Listener<String> onResponse, Response.ErrorListener onRequestError)  {


        String path = baseUrl + "/cardinfo.php";


        if (params != null)
        {
            if (params.containsKey("fname"))
            {
                path += "?fname="+params.get("fname").replaceAll("\\s","%20");
            }
        }

        request = new StringRequest(Request.Method.GET, path, onResponse, onRequestError);

        requestQueue.add(request);
        requestQueue.start();
    }





}
