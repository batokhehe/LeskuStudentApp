package com.lescepat.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment
{

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * method used for first UI initialization & manipulation
     */
    public abstract void initUI();

    /**
     * method used for first event initialization & manipulation
     */
    public abstract void initEvent();

    protected void hideKeyboard ()
    {
        View view = getActivity().getCurrentFocus();
        if (view != null)
        {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
