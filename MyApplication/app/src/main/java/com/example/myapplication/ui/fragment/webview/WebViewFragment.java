package com.example.myapplication.ui.fragment.webview;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentWebviewBinding;
import com.example.myapplication.ui.activity.MainActivity;

public class WebViewFragment extends Fragment {
    private FragmentWebviewBinding binding;
    MainActivity activity;
    private ProgressDialog progDailog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWebviewBinding.inflate(inflater, container, false);
        Log.d("tbh_", "onCreateView: WebViewFragment");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
        progDailog.setCancelable(false);
        new Handler().postDelayed(() -> {
            binding.webView.setWebViewClient(new WebViewClient());
            binding.webView.loadUrl("https://www.themoviedb.org/about/our-history");
            progDailog.dismiss(); // auto cancel ProgressDialog
        }, 2000);

    }

    @Override
    public void onResume() {
        super.onResume();
        activity.uiToolbarOtherPage("The Movie Database");
    }
}
