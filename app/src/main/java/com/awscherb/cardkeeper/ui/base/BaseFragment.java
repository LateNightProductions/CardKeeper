package com.awscherb.cardkeeper.ui.base;


import com.trello.rxlifecycle2.components.support.RxFragment;

public class BaseFragment extends RxFragment {

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

}
