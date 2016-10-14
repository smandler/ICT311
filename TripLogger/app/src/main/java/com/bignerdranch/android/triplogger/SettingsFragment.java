package com.bignerdranch.android.triplogger;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class SettingsFragment extends Fragment {

    private static final String ARG_SETTINGS_ID = "settings_id";
    private Settings mSettings;
    private EditText mNameField;

    private File mPhotoFile;

    private EditText mDestinationField;
    private EditText mDurationField;
    private Spinner mTypeField;
    private EditText mCommentField;
    private Button mDateButton;


    public static SettingsFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SETTINGS_ID, id);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettings = TripManager.get(getActivity()).getSettings();
    }

    @Override
    public void onPause() {
        super.onPause();
        TripManager.get(getActivity())
                .updateSettings(mSettings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mNameField = (EditText) v.findViewById(R.id.settings_name);
        mNameField.setText(mSettings.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSettings.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

    }

}
