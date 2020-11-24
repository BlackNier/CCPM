package com.iris.ccpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.tabs.TabLayout;
import com.iris.ccpm.adapter.MypagerAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class  LoginActivity extends AppCompatActivity {
    TabLayout tbSelect;
    ViewPager vpChosen;
    ArrayList<View> viewList;
    MypagerAdapter mAdapter;
    View loginView;
    View registerView;

    EditText etUsername;
    EditText etPassword;
    Button btLogin;
    CheckBox cbDisplayPassword;
    CheckBox cbRemeber;
    Boolean remeber;

    EditText etUsernameRe;
    EditText etPasswordRe;
    EditText etRePasswordRe;
    Button btRegister;
    EditText etNickname;
    EditText etRealName;
    EditText etPhone;
    EditText etPosition;
    EditText etSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();
        initTabContent();
        autoLogin();

        cbDisplayPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        cbRemeber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                remeber = isChecked;
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_str = etUsername.getText().toString();
                String pwd_str = etPassword.getText().toString();

                if(name_str.equals("") || pwd_str.equals("")) {
                    Toast.makeText(LoginActivity.this, "请填写完整信息", Toast.LENGTH_LONG).show();
                } else {
                    AsyncHttpClient client = new AsyncHttpClient();
                    JSONObject body = new JSONObject();
                    body.put("username", name_str);
                    body.put("password", pwd_str);
                    StringEntity entity = new StringEntity(body.toJSONString(), "UTF-8");
                    String url = "https://find-hdu.com/login";
                    client.post(LoginActivity.this, url, entity, "application/json", new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            Log.d("login:", response);
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            Integer code = jsonObject.getInteger("code");
                            Log.d("code:", code.toString());
                            String msg = jsonObject.getString("msg");
                            if (code != 200) {
                                Log.d("login:", msg);
                                etUsername.setText("");
                                etPassword.setText("");
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                            } else {
                                if (remeber) {
                                    save_user(LoginActivity.this, name_str, pwd_str);
                                }
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(LoginActivity.this, "出了点问题...", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        etUsernameRe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String name_str = etUsernameRe.getText().toString();
                    if (!isEmail(name_str)) {
                        Toast.makeText(LoginActivity.this, "邮箱格式不正确！", Toast.LENGTH_LONG).show();
                        etUsernameRe.setText("");
                    }
                }
            }
        });

        etRePasswordRe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String pwd_str = etPasswordRe.getText().toString();
                    String repwd_str = etRePasswordRe.getText().toString();
                    if (!pwd_str.equals(repwd_str)) {
                        Toast.makeText(LoginActivity.this, "两次输入密码不同！", Toast.LENGTH_LONG).show();
                        etRePasswordRe.setText("");
                    }
                }
            }
        });

        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String phone_str = etPhone.getText().toString();
                    if(isMobile(phone_str) || isPhone(phone_str)) {
                        Toast.makeText(LoginActivity.this, "输入号码格式不正确！", Toast.LENGTH_LONG).show();
                        etPhone.setText("");
                    }
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_str = etUsernameRe.getText().toString();
                String pwd_str = etPasswordRe.getText().toString();
                String repwd_str = etRePasswordRe.getText().toString();
                String nickname_str = etNickname.getText().toString();
                String realname_str = etRealName.getText().toString();
                String phone_str = etPhone.getText().toString();
                String synopsis_str = etSynopsis.getText().toString();
                String position_str = etPosition.getText().toString();

                if (name_str.equals("") || repwd_str.equals("")) {
                    Toast.makeText(LoginActivity.this, "请填入完整信息！", Toast.LENGTH_LONG).show();
                } else {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("username", name_str);
                    params.put("password", pwd_str);
                    params.put("nickName", nickname_str);
                    params.put("realName", realname_str);
                    params.put("phoneNum", phone_str);
                    params.put("synopsis", synopsis_str);
                    params.put("position", position_str);
                    String url = "https://find-hdu.com/account/add";
                    client.post(url, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            for (int i = 0; i < headers.length; i++) {
                                String name = headers[i].getName();
                                String value = headers[i].getValue();
                                Log.d("header", "Http request: Name—>" + name + ",Value—>" + value);
                            }
                            String content = new String(responseBody);
                            Log.d("response:", content);
                            Toast.makeText(LoginActivity.this, "content", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });

//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
                }
            }
        });

        tbSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpChosen.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public static boolean isEmail(String strEmail) {
        String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" + "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }


    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isPhone(final String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }


    private void autoLogin() {
        Map<String,String> user = getUserinfo(LoginActivity.this);
        if(user!=null) {
            String username = user.get("username");
            String password = user.get("password");
            etUsername.setText(username);
            etPassword.setText(password);
        }
    }

    private Map<String,String> getUserinfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info",MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        Map<String,String> user = new HashMap<String,String>();
        user.put("username", username);
        user.put("password", password);
        return user;
    }

    private void initTabContent() {
        viewList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();

        loginView = li.inflate(R.layout.login_login, null, false);
        registerView = li.inflate(R.layout.login_register, null, false);

        btLogin = loginView.findViewById(R.id.bt_login);
        btRegister = registerView.findViewById(R.id.bt_Register);

        etUsername = loginView.findViewById(R.id.et_username);
        etPassword = loginView.findViewById(R.id.et_password);
        cbDisplayPassword = loginView.findViewById(R.id.cb_displayPassword);
        cbRemeber = loginView.findViewById(R.id.cb_remeber);

        etUsernameRe = registerView.findViewById(R.id.et_username_re);
        etPasswordRe = registerView.findViewById(R.id.et_password_re);
        etRePasswordRe = registerView.findViewById(R.id.et_repassword_re);
        etNickname = registerView.findViewById(R.id.et_nickname);
        etPhone = registerView.findViewById(R.id.et_phoneNum);
        etRealName = registerView.findViewById(R.id.et_realname);
        etPosition = registerView.findViewById(R.id.et_position);
        etSynopsis = registerView.findViewById(R.id.et_synopsis);

        viewList.add(loginView);
        viewList.add(registerView);
        mAdapter = new MypagerAdapter(viewList);
        vpChosen.setAdapter((mAdapter));
    }



    private boolean save_user(Context context, String username, String passward) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", passward);
        editor.commit();
        return true;
    }

    private void findView() {
        vpChosen = (ViewPager) findViewById(R.id.vp_chosen);
        tbSelect = (TabLayout) findViewById(R.id.tb_select);
    }
}