package com.example.elashry.eleman.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.elashry.eleman.Controller;
import com.example.elashry.eleman.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Delta on 06/08/2017.
 */

public class Order extends Fragment {

    private RecyclerView mRecyclerView;
    Context mContext;
    private final String order_url ="http://semicolonsoft.com/app/api/find/orders";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order,container,false);
        init_View(view);
        GetOrder_Data();
        return view;
    }

    private void GetOrder_Data() {
        new Asyn_task().execute(order_url);

    }

    private void init_View(View view) {
        mContext = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.order_RecyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }
    private class Asyn_task extends AsyncTask<String, Void,Void>
    {

        @Override
        protected Void doInBackground(String... strings) {
            JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest(strings[0],
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.e("data",response.toString());
                            JSONObject object;

                        }
                    }
                    ,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            Controller.getInstance().addToRequestQueue(mJsonArrayRequest,"json array req");
            return null;
        }

    }

}
