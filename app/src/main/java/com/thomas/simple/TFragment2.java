package com.thomas.simple;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thomas.fragmentkey.Inject;

/**
 * Author: Thomas.<br/>
 * Date: 2020/1/17 9:10<br/>
 * GitHub: https://github.com/TanZhiL<br/>
 * CSDN: https://blog.csdn.net/weixin_42703445<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:
 */
public class TFragment2 extends Fragment {
    private static final String TAG = "TFragment2";
    @Inject
    public String mUsername;
    @Inject(name = "password1")
    public String mPassword;
    @Inject
    public int age;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, mUsername);
        Log.d(TAG, mPassword);
        Log.d(TAG, String.valueOf(age));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public TFragment newInstance(String username, String password, int age) {
        TFragment tFragment = new TFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putInt("age", age);
        tFragment.setArguments(bundle);
        return tFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        mUsername = arguments.getString("username");
        mPassword = arguments.getString("password");
        age = arguments.getInt("age");
    }
}
