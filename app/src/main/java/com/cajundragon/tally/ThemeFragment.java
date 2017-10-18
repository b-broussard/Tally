package com.cajundragon.tally;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by BMB on 6/3/2017.
 */

public class ThemeFragment extends DialogFragment {

    ThemeDialogListener mListener;

    public interface ThemeDialogListener {
        void onInputDialogPositiveClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (ThemeDialogListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement ThemeDialogListener");
//        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputDialog = inflater.inflate(R.layout.theme_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a theme:");
        builder.setView(inputDialog)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Change theme

                        mListener.onInputDialogPositiveClick(ThemeFragment.this);
                    }
                });
        
        return builder.create();
    }

}
