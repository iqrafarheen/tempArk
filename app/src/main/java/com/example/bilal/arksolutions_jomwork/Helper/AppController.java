package com.example.bilal.arksolutions_jomwork.Helper;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cyber on 5/12/2016.
 */
public class AppController extends MultiDexApplication {
    public static final String TAG = AppController.class.getSimpleName();
    public RequestQueue requestQueue;
    public ImageLoader imageLoader;
    AppController mInstance;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    public User mUser;
    public String credits;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mUser = new User();
    }

    public AppController getmInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;

    }

    public ImageLoader getImageLoader() {
        requestQueue = getRequestQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(requestQueue, new LruBitmapCache());
        }
        return this.imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            getRequestQueue().cancelAll(tag);
        }
    }

    public void credits() {

        jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                HeaderRequestUrl.base_url + "?function=usercredit&action=get&email=" + this.mUser.getEmail(),
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            credits = response.get("credit").toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d(tag_json_obj, response.toString());
                        //pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        this.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public void AddCredits(int creditAmount) {

        jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                HeaderRequestUrl.base_url + "?function=addusercredit&action=post&user_id=" + this.mUser.getId() + "&amount=" + creditAmount,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            if (response.get("status").toString().contentEquals("success")) {
                                credits();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d(tag_json_obj, response.toString());
                        //pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        this.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public void SubtractCredits(int creditAmount) {

        jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                HeaderRequestUrl.base_url + "?function=subtractusercredit&action=post&user_id=" + this.mUser.getId() + "&amount=" + creditAmount,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            if (response.get("status").toString().contentEquals("success")) {
                                credits();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d(tag_json_obj, response.toString());
                        //pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        this.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }
}

