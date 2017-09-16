package com.cajundragon.tally;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by BMB on 6/3/2017.
 */

public class TeamNameRenameDialogFragment extends DialogFragment {

    InputDialogListener mListener;

    public interface InputDialogListener {
        void onInputDialogPositiveClick(DialogFragment dialog);
        void onInputDialogNegativeClick();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (InputDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement InputDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputDialog = inflater.inflate(R.layout.team_rename, null);

        final EditText mInput = (EditText) inputDialog.findViewById(R.id.new_team_name);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Rename your team:");
        builder.setView(inputDialog)
                .setPositiveButton(R.string.change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Rename team
//                        String newTeam = mInput.getText().toString();
                        mListener.onInputDialogPositiveClick(TeamNameRenameDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Keep current team name
                        mListener.onInputDialogNegativeClick();
                    }
                });
        
        return builder.create();
    }

}
