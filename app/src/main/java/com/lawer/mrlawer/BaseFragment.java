package com.lawer.mrlawer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

/**
 * Created by yifan on 15-12-3.
 */
public class BaseFragment extends Fragment {

    protected ProgressDialog mProgrssDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgrssDialog = new ProgressDialog(getActivity());
        mProgrssDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
