package com.thomas.simple;

import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.thomas.fragmentkey.Inject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Thomas.<br/>
 * Date: 2020/1/17 9:10<br/>
 * GitHub: https://github.com/TanZhiL<br/>
 * CSDN: https://blog.csdn.net/weixin_42703445<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:
 */
public class TFragment extends Fragment {
    @Inject
    protected String s;
    @Inject
    protected Integer i;
    @Inject
    protected boolean b;
    @Inject
    protected float f;
    @Inject
    protected double d;
    @Inject
    protected long l;
    @Inject
    protected ArrayList<String> ls;
    @Inject
    protected ArrayList<Integer> li;
    @Inject
    protected ArrayList<Parcelable> lp;
    @Inject
    protected Serializable se;
    @Inject
    protected Parcelable p;

}
