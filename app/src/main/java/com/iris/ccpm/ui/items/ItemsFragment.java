package com.iris.ccpm.ui.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.iris.ccpm.R;

public class ItemsFragment extends Fragment {

    private ItemsViewModel itemsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemsViewModel =
                new ViewModelProvider(this).get(ItemsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_items, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        itemsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}