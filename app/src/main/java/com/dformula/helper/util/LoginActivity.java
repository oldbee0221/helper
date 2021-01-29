package com.dformula.helper.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dformula.helper.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {


    LinearLayout ll_naver_login;

    private LoginButton btn_Facebook_Login;
    private CallbackManager callbackManager;
    private LoginCallback loginCallack;


    private Button btn_custom_login;
    private Button btn_custom_login_out;
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;




    OAuthLogin mOAuthLoginModule;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        callbackManager = CallbackManager.Factory.create();
        loginCallack = new LoginCallback();

        btn_Facebook_Login = (LoginButton) findViewById(R.id.facebook_login);
        btn_Facebook_Login.setReadPermissions(Arrays.asList("public_profile", "email"));
        btn_Facebook_Login.registerCallback(callbackManager, loginCallack);


        mContext = getApplicationContext();

        ll_naver_login = findViewById(R.id.ll_naver_login);





        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        btn_custom_login_out = (Button) findViewById(R.id.btn_custom_login_out);

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
            }
        });

        btn_custom_login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Toast.makeText(LoginActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



        ll_naver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.init(
                        mContext
                        , getString(R.string.naver_client_id)
                        , getString(R.string.naver_client_secret)
                        , getString(R.string.naver_client_name)
                        //,OAUTH_CALLBACK_INTENT
                        // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
                );

                @SuppressLint("HandlerLeak")
                OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
                    @Override
                    public void run(boolean success) {
                        if (success) {
                            String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                            String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                            long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                            String tokenType = mOAuthLoginModule.getTokenType(mContext);

                            Log.i("LoginData", "accessToken : " + accessToken);
                            Log.i("LoginData", "refreshToken : " + refreshToken);
                            Log.i("LoginData", "expiresAt : " + expiresAt);
                            Log.i("LoginData", "tokenType : " + tokenType);


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            String errorCode = mOAuthLoginModule
                                    .getLastErrorCode(mContext).getCode();
                            String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                            Toast.makeText(mContext, "errorCode:" + errorCode
                                    + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                        }


                    }

                    ;


                };

                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
            }


        });


    }

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }
*/



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }







    }

