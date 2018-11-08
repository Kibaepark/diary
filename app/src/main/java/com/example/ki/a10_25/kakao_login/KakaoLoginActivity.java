package com.example.ki.a10_25.kakao_login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.example.ki.a10_25.MainActivity;
import com.example.ki.a10_25.R;
import com.example.ki.a10_25.Task.Url;
import com.example.ki.a10_25.Task.jsonSend;
import com.example.ki.a10_25.Task.UserVO;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;


import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class KakaoLoginActivity extends AppCompatActivity {
    private Context mContext;
    private SessionCallback callback;
    private LoginButton btn_kakao_login;
    private JSONObject json;
    Map<String,Object> jsonObject;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
        getHashKey(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }


    // 프로젝트의 해시키를 반환
    @Nullable
    public static String getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }
    protected void redirectSignupActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        makeJson();
        new jsonSend(jsonObject,Url.getUrl()+"/user").execute();
        UserVO.setId(id);
        UserVO.setNick(nickName);
        UserVO.setUrl(thumnailPath);
        startActivity(intent);
        finish();
    }

    private String nickName;
    private long id;
    private String thumnailPath;
    private String profileImagePath;

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            requestMe();
        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
        // 사용자 정보 요청

        public void requestMe() {
            // 사용자정보 요청 결과에 대한 Callback
            UserManagement.requestMe(new MeResponseCallback() {

                // 세션 오픈 실패. 세션이 삭제된 경우,

                @Override

                public void onSessionClosed(ErrorResult errorResult) {

                    Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());

                }
                // 회원이 아닌 경우,

                @Override

                public void onNotSignedUp() {

                    Log.e("SessionCallback :: ", "onNotSignedUp");

                }



                // 사용자정보 요청에 성공한 경우,

                @Override

                public void onSuccess(UserProfile userProfile) {
                    Log.e("SessionCallback :: ", "onSuccess");
                    nickName = userProfile.getNickname();
//                    String email = userProfile.getEmail();
                    profileImagePath = userProfile.getProfileImagePath();
                    thumnailPath = userProfile.getThumbnailImagePath();
//                    String UUID = userProfile.getUUID();
                    id = userProfile.getId();
                    Log.e("Profile : ", nickName + "");
                    Log.e("Profile id : ", id + "");
                    Log.e("url",profileImagePath);
                    redirectSignupActivity();
                    makeJson();
                }

                // 사용자 정보 요청 실패
                @Override
                public void onFailure(ErrorResult errorResult) {

                    Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());

                }

            });

        }
    }
    protected void makeJson(){
        jsonObject=new HashMap<>();
        jsonObject.put("nickname",nickName);
        jsonObject.put("id",id);
        jsonObject.put("profileimage",thumnailPath);
        Log.e("json : ",jsonObject.toString());
    }




}

