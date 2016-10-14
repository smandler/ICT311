package com.bignerdranch.android.triplogger;

import android.app.Activity;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class TripFragment extends Fragment {

    private static final String ARG_TRIP_ID = "trip_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO= 2;

    private Trip mTrip;
    private File mPhotoFile;
    private EditText mTitleField;
    private EditText mDestinationField;
    private EditText mDurationField;
    private Spinner mTypeField;
    private EditText mCommentField;
    private Button mDateButton;

    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    private ImageView mDeleteButton;
    private ImageView mSaveButton;
    private ImageView mCancelButton;

    public static TripFragment newInstance(UUID tripId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        TripFragment fragment = new TripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID tripId = (UUID) getArguments().getSerializable(ARG_TRIP_ID);
        mTrip = TripManager.get(getActivity()).getTrip(tripId);
        mPhotoFile = TripManager.get(getActivity()).getPhotoFile(mTrip);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTrip.getTitle() != null) {
            TripManager.get(getActivity())
                    .updateTrip(mTrip);
        } else {
            TripManager.get(getActivity())
                    .deleteTrip(mTrip);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trip, container, false);
        ViewStub stub = (ViewStub) v.findViewById(R.id.layout_btn);

        if (mTrip.getTitle() != null) {
            stub.setLayoutResource(R.layout.trip_edit);
            View inflated = stub.inflate();

            mDeleteButton = (ImageView) v.findViewById(R.id.trip_delete);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TripManager.get(getActivity())
                            .deleteTrip(mTrip);
                    getActivity().onBackPressed();
                }
            });
        } else {
            stub.setLayoutResource(R.layout.trip_add);
            View inflated = stub.inflate();

            mCancelButton= (ImageView) v.findViewById(R.id.trip_cancel);
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TripManager.get(getActivity())
                            .deleteTrip(mTrip);
                    getActivity().onBackPressed();
                }
            });

            mSaveButton = (ImageView) v.findViewById(R.id.trip_save);
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TripManager.get(getActivity())
                            .updateTrip(mTrip);
                    getActivity().onBackPressed();
                }
            });
        }


        mTitleField = (EditText) v.findViewById(R.id.trip_title);
        mTitleField.setText(mTrip.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTrip.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDestinationField = (EditText) v.findViewById(R.id.trip_destination);
        mDestinationField.setText(mTrip.getDestination());
        mDestinationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTrip.setDestination(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCommentField = (EditText) v.findViewById(R.id.trip_comments);
        mCommentField.setText(mTrip.getComment());
        mCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTrip.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTypeField = (Spinner) v.findViewById(R.id.trip_type);
        if (mTrip.getType() != null)
            mTypeField.setSelection(Integer.parseInt(mTrip.getType()));

        mTypeField.setOnItemSelectedListener(new TripTypeOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                mTrip.setType(Integer.toString(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        mDateButton = (Button) v.findViewById(R.id.trip_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mTrip.getDate());
                dialog.setTargetFragment(TripFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mDurationField = (EditText) v.findViewById(R.id.trip_duration);
        mDurationField.setText(mTrip.getDuration());
        mDurationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTrip.setDuration(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPhotoButton = (ImageButton) v.findViewById(R.id.trip_camera);
        PackageManager packageManager = getActivity().getPackageManager();

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.trip_photo);
        updatePhotoView();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTrip.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    private void updateDate() {
        mDateButton.setText(mTrip.getDate().toString());
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = ImageScale.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
