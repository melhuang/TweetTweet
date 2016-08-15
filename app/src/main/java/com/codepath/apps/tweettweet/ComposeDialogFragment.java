package com.codepath.apps.tweettweet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by melissahuang on 8/6/16.
 */
public class ComposeDialogFragment extends DialogFragment {
    private EditText mEditText;

    public ComposeDialogFragment() { }

    public interface ComposeDialogFragmentListener {
        void onSaveTweet(String body);
    }

    public static ComposeDialogFragment newInstance() {
        ComposeDialogFragment frag = new ComposeDialogFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.compose);
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etTweet);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button cancelButton = (Button) view.findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComposeDialogFragmentListener listener = (ComposeDialogFragmentListener) getTargetFragment();
                String body = mEditText.getText().toString();
                listener.onSaveTweet(body);
                dismiss();
            }
        });
    }

}
