package com.example.capitalsocial.bases;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class BaseFragment extends Fragment {

    public BaseFragment() {}

    public void showMessage(Integer type, String title, String message) {
        BaseActivity activity = ((BaseActivity) getActivity());
        if (activity != null) {
            activity.showMessage(type, title, message);
        }
    }

    public void setTitle(String title) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(title);
        }
    }

    public void hideKeyboard() {
        BaseActivity activity = ((BaseActivity) getActivity());
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public void launchLogin () {
        BaseActivity activity = ((BaseActivity) getActivity());
        if (activity != null) {
            activity.launchLoginActivity();
            activity.finish();
        }
    }

}
