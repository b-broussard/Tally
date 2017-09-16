package com.example.android.courtcounter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NewGameDialogFragment.AlertDialogListener,
        TeamNameRenameDialogFragment.InputDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
    }

    public Button getNewGameButton() {
        return (Button) findViewById(R.id.new_game_button);
    }

    //Keeps track of Team A's score
    int scoreTeamA = 0;

    //Keeps track of Team B's score
    int scoreTeamB = 0;

    //Stores name of Team A
    String teamNameA = "A";

    //Stores name of Team B
    String teamNameB = "B";

    int currentView = 0;

    private EditText teamANameField, teamBNameField;

    public void enableNewGameButton() {
        getNewGameButton().setEnabled(true);
    }

    public void addFreeThrowToA(View view) {
        scoreTeamA += 1;
        displayForTeamA(scoreTeamA);
        if (scoreTeamA != 0) {
            enableNewGameButton();
        }
    }

    public void addTwoPointsToA(View view) {
        scoreTeamA += 2;
        displayForTeamA(scoreTeamA);
        if (scoreTeamA != 0) {
            enableNewGameButton();
        }
    }

    public void addThreePointsToA(View view) {
        scoreTeamA += 3;
        displayForTeamA(scoreTeamA);
        if (scoreTeamA != 0) {
            enableNewGameButton();
        }
    }

    public void clearScoreA(View view) {
        scoreTeamA = 0;
        displayForTeamA(scoreTeamA);
        if (scoreTeamA == 0 && scoreTeamB == 0) {
            getNewGameButton().setEnabled(false);
        }
    }

    public void addFreeThrowToB(View view) {
        scoreTeamB += 1;
        displayForTeamB(scoreTeamB);
        if (scoreTeamB != 0) {
            enableNewGameButton();
        }
    }

    public void addTwoPointsToB(View view) {
        scoreTeamB += 2;
        displayForTeamB(scoreTeamB);
        if (scoreTeamB != 0) {
            enableNewGameButton();
        }
    }

    public void addThreePointsToB(View view) {
        scoreTeamB += 3;
        displayForTeamB(scoreTeamB);
        if (scoreTeamB != 0) {
            enableNewGameButton();
        }
    }

    public void clearScoreB(View view) {
        scoreTeamB = 0;
        displayForTeamB(scoreTeamB);
        if (scoreTeamA == 0 && scoreTeamB == 0) {
            getNewGameButton().setEnabled(false);
        }
    }

    public void changeTeamNameA(View view) {
        TeamNameRenameDialogFragment renameTeamA = new TeamNameRenameDialogFragment();
        currentView = view.getId();
        renameTeamA.show(getSupportFragmentManager(), "renameA");
    }

    public void changeTeamNameB(View view) {
        TeamNameRenameDialogFragment renameTeamB = new TeamNameRenameDialogFragment();
        currentView = view.getId();
        renameTeamB.show(getSupportFragmentManager(), "renameB");
    }

    @Override
    public void onInputDialogPositiveClick(DialogFragment dialog) {
        LayoutInflater linf = LayoutInflater.from(this);
        linf.inflate(R.layout.team_rename, null);
        switch (currentView) {
            case R.id.team_a:
                teamANameField = (EditText) dialog.getDialog().findViewById(R.id.new_team_name);
                teamNameA = teamANameField.getText().toString();
                displayTeamAName(teamNameA);
            break;
            case R.id.team_b:
                teamBNameField = (EditText) dialog.getDialog().findViewById(R.id.new_team_name);
                teamNameB = teamBNameField.getText().toString();
                displayTeamBName(teamNameB);
            break;
            default:
                throw new RuntimeException("Unknown ID");
        }
    }


    @Override
    public void onInputDialogNegativeClick() {

    }

    public void shareScores(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
//        intent.setData(Uri.parse("mailto:"));
        intent.setData(Uri.parse("sms:"));
        intent.putExtra("sms body", "" + teamNameA + "'s score is " + scoreTeamA + ", while "
                + teamNameB + " has " + scoreTeamB + " points.");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Current basketball scores for Teams A and B");
//        intent.putExtra(Intent.EXTRA_TEXT, "Team A's score is " + scoreTeamA + ", while Team B has "
//                + scoreTeamB + " points.");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Share via"));
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        getNewGameButton().setEnabled(false);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Context context = getApplicationContext();
        CharSequence text = "Scores have not been cleared.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void clearScores(View view) {
        if (scoreTeamA != 0 || scoreTeamB != 0) {
            DialogFragment clearScores = new NewGameDialogFragment();
            clearScores.show(getSupportFragmentManager(), "nuke");
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Neither team has scored any points yet!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 300);
            toast.show();
        }
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayTeamAName(String name) {
        TextView nameViewA = (TextView) findViewById(R.id.team_a);
        nameViewA.setText(String.valueOf(name));
    }

    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayTeamBName(String name) {
        TextView nameViewB = (TextView) findViewById(R.id.team_b);
        nameViewB.setText(String.valueOf(name));
    }
}
