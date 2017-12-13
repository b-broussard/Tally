package com.cajundragon.tally;

import android.app.Activity;
import android.app.Dialog;
//import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;



/**
 * Created by BMB on 6/3/2017.
 */

public class ThemeFragment extends DialogFragment {

    ThemeDialogListener mListener;

    public interface ThemeDialogListener {
        void onThemeChoiceClick(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ThemeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ThemeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        final View themeDialog = inflater.inflate(R.layout.theme_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a theme:");
        builder.setSingleChoiceItems(ThemeNames.themes, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        mListener.onThemeChoiceClick(position);
                    }
                }).setNegativeButton("Cancel", null);

//                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        // Get the chosen theme's radio button
//                        int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
//                        mListener.onThemeChoiceClick(position);
//                    }
//                });
        
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
    }

}
