package com.example.elashry.eleman.Fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.elashry.eleman.Adapter.OrderAdapter;
import com.example.elashry.eleman.App_URL;
import com.example.elashry.eleman.Controller;
import com.example.elashry.eleman.Model.OrderModel;
import com.example.elashry.eleman.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Delta on 06/08/2017.
 */

public class Order extends Fragment {

    private LinearLayout pbc;
    private TextView no_order_txt;
    private RecyclerView mRecyclerView;
    Context mContext;
    private ProgressBar prog_bar;
    private SwipeRefreshLayout mRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order,container,false);
        init_View(view);
        GetOrder_Data();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetOrder_Data();
            }
        });
        return view;
    }

    private void GetOrder_Data() {
        JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest(App_URL.app_orders,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("data",response.toString());
                        List<OrderModel> orderList = new ArrayList<>();
                        JSONObject object;
                        for (int index =0;index<response.length();index++)
                        {
                            try {
                                object =response.getJSONObject(index);
                                orderList.add(new OrderModel(object.get("order_id_pk").toString(),object.get("product_id_fk").toString(),object.get("quantity").toString(),object.get("client_name").toString(),object.get("client_phone").toString(),object.get("order_date").toString(),object.get("order_location").toString()));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (orderList.size()>0)
                        {
                            OrderAdapter adapter = new OrderAdapter(mContext,orderList);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            pbc.setVisibility(View.GONE);
                            no_order_txt.setVisibility(View.GONE);
                            mRefreshLayout.setRefreshing(false);
                        }
                        if (orderList.size()==0)
                        {
                            mRecyclerView.setVisibility(View.GONE);
                            pbc.setVisibility(View.GONE);
                            no_order_txt.setText(View.VISIBLE);
                            mRefreshLayout.setRefreshing(false);
                        }

                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mRefreshLayout.setRefreshing(false);
                    }
                }
        );
        Controller.getInstance().addToRequestQueue(mJsonArrayRequest,"json array req");
    }

    private void init_View(View view) {
        mContext       = view.getContext();
        pbc = (LinearLayout) view.findViewById(R.id.progressBar_container);
        pbc.setVisibility(View.VISIBLE);
        prog_bar       = (ProgressBar) view.findViewById(R.id.o_progBar);
        prog_bar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        no_order_txt   = (TextView) view.findViewById(R.id.no_order_txt);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swap_refresh);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext,R.color.colorPrimary));
        mRecyclerView  = (RecyclerView) view.findViewById(R.id.order_RecyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }


}
