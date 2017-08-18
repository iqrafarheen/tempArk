package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.HeaderRequestUrl;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Bilal on 6/23/2017.
 */

public class Login extends AppCompatActivity {
    TextView toolbarTitle;
    Toolbar toolbar;
    RelativeLayout signupBtn;
    Button signinBtn;
    //LoginButton loginButton;
    Button loginButton;
    Context mContext;
    String userId, authToken;
    AccessToken accessToken;
    User userModel;
    private CallbackManager callbackManager;
    String str_facebookname, str_facebookid, str_birthday, str_location, str_facebookemail;
    private boolean boolean_login = false;
    ImageView dp;
    EditText emailEdit, paswordEdit;
    String emailId, pasword;
    String str_facebookimage;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    public static boolean isSignup = false;
    String gender;
    AppController mAppController;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.sign_in);
        mContext = this;
        callbackManager = CallbackManager.Factory.create();
        mContext = this;
        signupBtn = (RelativeLayout) findViewById(R.id.signup_button);
        emailEdit = (EditText) findViewById(R.id.username_input);
        paswordEdit = (EditText) findViewById(R.id.pasword_input);

        signinBtn = (Button) findViewById(R.id.signin_button);
        //loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton = (Button) findViewById(R.id.fb_button);
        mAppController = (AppController) mContext.getApplicationContext();
        //getKeyHash();
        signinBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emailId = emailEdit.getText().toString();
                pasword = paswordEdit.getText().toString();
                if (emailId != null && pasword != null)
                    jsonHandling();

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile,email,user_birthday,user_location"));
                facebookLogin();
                // boolean_login = true;
                // } /*else {

                //  }

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSignup = true;
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }


    public void jsonHandling() {
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                HeaderRequestUrl.base_url + "?function=userlogin&action=post&email=" + emailId + "&password=" + pasword,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        userModel = new User();
                        JSONObject user;

                        try {
                            statusObj = (String) response.get("status");
                            user = response.getJSONObject("user");
                            errorObj = (String) response.get("message");
                            if (statusObj.contentEquals("success")) {
                                mAppController.mUser.setId(user.getString("id"));
                                mAppController.mUser.setUsername(user.getString("username"));
                                mAppController.mUser.setEmail(user.getString("email"));
                                mAppController.mUser.setName(user.getString("name"));
                                mAppController.mUser.setContactName(user.getString("contact_name"));
                                mAppController.mUser.setDob(user.getString("dob"));
                                mAppController.mUser.setMobileNumber(user.getString("mobile_number"));
                                mAppController.mUser.setGender(user.getString("gender"));
                                mAppController.mUser.setIndustryInterest(user.getString("industry_interest"));
                                mAppController.mUser.setPhysicalDisable(user.getString("physical_disable"));
                                mAppController.mUser.setRoleId(user.getString("role_id"));
                                if(mAppController.mUser.getRoleId().contentEquals("2"))
                                {
                                    mAppController.mUser.setUserAvatar(user.getString("photo"));
                                }
                                else {
                                    mAppController.mUser.setUserAvatar(user.getString("company_logo"));
                                }

                                mAppController.mUser.setAddress(user.getString("address"));
                                mAppController.mUser.setLocation(user.getString("location"));
                                mAppController.mUser.setNonPhysicalDisable(user.getString("non_physical_disable"));




                                //resObj = response.get("res").toString();
                                if (mAppController.mUser.getRoleId().contentEquals("2")) {
                                    Intent backIntent = new Intent(mContext, FindAJob.class);
                                    startActivity(backIntent);
                                } else if (mAppController.mUser.getRoleId().contentEquals("3")) {
                                    Intent backIntent = new Intent(mContext, PostJob.class);
                                    startActivity(backIntent);
                                }
                                mAppController.credits();

                            } else {
                                if (statusObj.contentEquals("failed")) {
                                    Toast.makeText(mContext, errorObj, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            String[] exception = e.toString().split(":");
                            Toast.makeText(mContext, exception[1], Toast.LENGTH_SHORT).show();
                        }
                       /* if (statusObj.contentEquals("failed")) {
                            Toast.makeText(mContext, errorObj, Toast.LENGTH_SHORT).show();
                        }*/

                        Log.d(tag_json_obj, response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        }) {


        };
        //  Volley.newRequestQueue(mContext).add(jsonObjReq);
        mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }


    private void facebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("ONSUCCESS", "User ID: " + loginResult.getAccessToken().getUserId()
                        + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken()
                );
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {

                                    //boolean_login = false;
                                    // tv_facebook.setText("Logout from Facebook");

                                    Log.e("object", object.toString());
                                    str_facebookname = object.getString("name");

                                    try {
                                        str_facebookemail = object.getString("email");
                                    } catch (Exception e) {
                                        str_facebookemail = "";
                                        e.printStackTrace();
                                    }

                                    try {
                                        str_facebookid = object.getString("id");
                                    } catch (Exception e) {
                                        str_facebookid = "";
                                        e.printStackTrace();

                                    }


                                    try {
                                        str_birthday = object.getString("birthday");
                                    } catch (Exception e) {
                                        str_birthday = "";
                                        e.printStackTrace();
                                    }

                                    try {
                                        gender = object.getString("gender");
                                    } catch (Exception e) {
                                        gender = "";
                                        e.printStackTrace();
                                    }

                                    try {
                                        JSONObject jsonobject_location = object.getJSONObject("location");
                                        str_location = jsonobject_location.getString("name");

                                    } catch (Exception e) {
                                        str_location = "";
                                        e.printStackTrace();
                                    }

                                    //  fn_profilepic();

                                } catch (Exception e) {

                                }
                                final ProgressDialog pDialog = new ProgressDialog(mContext);
                                pDialog.setMessage("Loading...");
                                pDialog.show();
                                fn_profilepic();
                                pDialog.hide();
                                //nextActivity();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email,gender,birthday,location");

                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
               if (AccessToken.getCurrentAccessToken() == null) {
                    return; // already logged out
                }
                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                      //  LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile,email"));
                      //  facebookLogin();

                    }
                }).executeAsync();


            }

            @Override
            public void onError(FacebookException e) {
                Log.e("ON ERROR", "Login attempt failed." + e.getMessage());


                // AccessToken.setCurrentAccessToken(null);
                //LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile,email,user_birthday"));
            }
        });
        // nextActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {

        }

    }

    public void nextActivity() {

        // intent.putExtra("Gender",gender);

        mAppController.mUser.setId(str_facebookid);
        mAppController.mUser.setUsername(str_facebookname);
        mAppController.mUser.setEmail(str_facebookemail);
        mAppController.mUser.setName(str_facebookname);
        mAppController.mUser.setContactName(str_facebookname);
        mAppController.mUser.setDob(str_birthday);
        mAppController.mUser.setMobileNumber("");
        mAppController.mUser.setGender(gender);
        mAppController.mUser.setIndustryInterest("");
        mAppController.mUser.setPhysicalDisable("");
        mAppController.mUser.setUserAvatar(str_facebookimage);
        mAppController.mUser.setAddress(str_location);
        mAppController.mUser.setLocation(str_location);
        mAppController.mUser.setNonPhysicalDisable("");
        mAppController.mUser.setRoleId("2");

        if (mAppController.mUser.getRoleId().contentEquals("2")) {

            intent = new Intent(Login.this, EditProfileJob.class);
        } else if (mAppController.mUser.getRoleId().contentEquals("3"))

        {
            intent = new Intent(Login.this, EditProfile.class);
        } else {
            intent = new Intent(Login.this, EditProfileJob.class);
        }

        Bundle bundle = new Bundle();
        bundle.putString("Name", str_facebookname);
        bundle.putString("gender", gender);
        bundle.putString("Birthday", str_birthday);
        bundle.putString("Email", str_facebookemail);
        bundle.putString("Location", str_location);
        bundle.putString("Picture", str_facebookimage);
        intent.putExtra("fb_bundle", bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isSignup = false;

    }



    private void fn_profilepic() {
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();
        Bundle params = new Bundle();
        params.putBoolean("redirect", false);
        params.putString("type", "large");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me/picture",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        Log.e("Response 2", response + "");

                        try {
                            str_facebookimage = (String) response.getJSONObject().getJSONObject("data").get("url");
                            Log.e("Picture", str_facebookimage);
                            nextActivity();
                            pDialog.hide();
                            LoginManager.getInstance().logOut();
                            //  Glide.with(Login.this).load(str_facebookimage).skipMemoryCache(true).into(dp);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
/*
                        tv_name.setText(str_facebookname);
                        tv_email.setText(str_facebookemail);
                        tv_dob.setText(str_birthday);
                        tv_location.setText(str_location);*/

                    }
                }
        ).executeAsync();
    }


}