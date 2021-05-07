package com.example.cickmovie.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cickmovie.R;
import com.example.cickmovie.activities.MainActivity;

public class LibraryFragment extends Fragment {

    public static LibraryFragment newInstance() {
        LibraryFragment libraryFragment = new LibraryFragment();
        Bundle args = new Bundle();

        args.putString(MainActivity.TOOLBAR_TITLE, "Library");
        libraryFragment.setArguments(args);

        return libraryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }
}