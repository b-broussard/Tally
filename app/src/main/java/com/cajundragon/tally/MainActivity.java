package com.cajundragon.tally;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NewGameFragment.AlertDialogListener,
        TeamRenameFragment.InputDialogListener,
        ThemeFragment.ThemeDialogListener {

    private int currentView = 0;
    private int currentTheme;

    public final String SHARED_PREFERENCES_NAME = "tally_preferences";
    public final String PREF_THEME_RESID_ID = "theme_resid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            applySharedTheme();
        }

        setContentView(R.layout.activity_main);

        Toolbar appBar = findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        final ActionBar toolBar = getSupportActionBar();
        toolBar.setTitle(getString(R.string.app_name));
        toolBar.setSubtitle(getString(R.string.basketball_counter));

        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share_menu:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra("sms body", "The score for Team " + teamNameA + " is " + teamScoreA + ", while Team "
                                + teamNameB + " have " + teamScoreB + " points.");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Current basketball scores for " + teamNameA + " and " + teamNameB);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "The score for Team " + teamNameA + " is " + teamScoreA + ", while Team "
                                + teamNameB + " have " + teamScoreB + " points.");
                        if (shareIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(Intent.createChooser(shareIntent, "Share via"));
                        }

                        return true;
                    case R.id.theme_menu:
                        ThemeFragment themeFragment = new ThemeFragment();
                        themeFragment.show(getSupportFragmentManager(), "themeChanging");
                        return true;

                    case R.id.action_settings:
                        //TODO: Implement a settings menu and move into navigation activity; may include theme picker
                        return true;

                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Keeps track of Team A's score
    int teamScoreA = 0;

    //Keeps track of Team B's score
    int teamScoreB = 0;

    //Stores name of Team A
    String teamNameA = "Team A";

    //Stores name of Team B
    String teamNameB = "Team B";

    /**
     * Gets the value of the current theme from SharedPreferences and sets theme by that value.
     */
    public void applySharedTheme() {
        SharedPreferences sPref = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        int themeID = sPref.getInt(PREF_THEME_RESID_ID, currentTheme);
        setTheme(themeID);
    }

    public void addFreeThrowToA(View view) {
        teamScoreA += 1;
        displayForTeamA(teamScoreA);
        enableNewGameButton();
    }

    public void addTwoPointsToA(View view) {
        teamScoreA += 2;
        displayForTeamA(teamScoreA);
        enableNewGameButton();
    }

    public void addThreePointsToA(View view) {
        teamScoreA += 3;
        displayForTeamA(teamScoreA);
        enableNewGameButton();
    }

    public void clearScoreA(View view) {
        teamScoreA = 0;
        displayForTeamA(teamScoreA);
        disableNewGameButton();
    }

    public void addFreeThrowToB(View view) {
        teamScoreB += 1;
        displayForTeamB(teamScoreB);
        enableNewGameButton();
    }

    public void addTwoPointsToB(View view) {
        teamScoreB += 2;
        displayForTeamB(teamScoreB);
        enableNewGameButton();
    }

    public void addThreePointsToB(View view) {
        teamScoreB += 3;
        displayForTeamB(teamScoreB);
        enableNewGameButton();
    }

    public void clearScoreB(View view) {
        teamScoreB = 0;
        displayForTeamB(teamScoreB);
        disableNewGameButton();
    }

    public void enableNewGameButton() {
        if (teamScoreA != 0 || teamScoreB != 0) {
            getNewGameButton().setEnabled(true);
        }
    }

    public void disableNewGameButton() {
        if (teamScoreA == 0 && teamScoreB == 0) {
            getNewGameButton().setEnabled(false);
        }
    }

    public Button getNewGameButton() {
        return (Button) findViewById(R.id.new_game_button);
    }

    public void changeTeamNameA(View view) {
        TeamRenameFragment renameTeamA = new TeamRenameFragment();
        currentView = view.getId();
        renameTeamA.show(getSupportFragmentManager(), "renameA");
    }

    public void changeTeamNameB(View view) {
        TeamRenameFragment renameTeamB = new TeamRenameFragment();
        currentView = view.getId();
        renameTeamB.show(getSupportFragmentManager(), "renameB");
    }

    public void displayOnRestore() {
        displayForTeamA(teamScoreA);
        displayForTeamB(teamScoreB);
        displayTeamAName(teamNameA);
        displayTeamBName(teamNameB);
    }

    /**
     * Takes position of chosen theme option and sets the value of the style
     * to be passed to SharedPreferences.
     */
    @Override
    public void onThemeChoiceClick(int position) {
        if (Build.VERSION.SDK_INT >= 21) {
            switch (position) {
                case 0:
                    currentTheme = R.style.BaseTheme;
                    break;
                case 1:
                    currentTheme = R.style.DarkTheme;
                    break;
                case 2:
                    currentTheme = R.style.BlackTheme;
                    break;
            }

            restartActivity(this);
        } else {
            switch (position) {
                case 0:
                    currentTheme = R.style.BaseAppTheme;
                    break;
                case 1:
                    currentTheme = R.style.DarkAppTheme;
                    break;
                case 2:
                    currentTheme = R.style.BlackAppTheme;
                    break;
            }

            restartActivity(this);
        }
    }

    /**
     * Saves team names, scores, and theme changes before restarting activity per restartActivity().
     */
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("TeamAName", teamNameA);
        editor.putString("TeamBName", teamNameB);
        editor.putInt("TeamAScore", teamScoreA);
        editor.putInt("TeamBScore", teamScoreB);
        editor.putInt("focusedEditTextDialog", currentView);
        editor.putInt(PREF_THEME_RESID_ID, currentTheme);
        editor.apply();
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        teamNameA = preferences.getString("TeamAName", teamNameA);
        teamNameB = preferences.getString("TeamBName", teamNameB);
        teamScoreA = preferences.getInt("TeamAScore", teamScoreA);
        teamScoreB = preferences.getInt("TeamBScore", teamScoreB);
        currentTheme = preferences.getInt(PREF_THEME_RESID_ID, currentTheme);
        currentView = preferences.getInt("focusedEditTextDialog", currentView);
        displayOnRestore();
        enableNewGameButton();
    }

    /**
     * Needed for updating the UI with preference changes.
     * NOTE: causes a brief black flicker on API Level 22 before showing updates.
     */
    public void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.finish();
            overridePendingTransition(0, 0);

            activity.startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    /**
     * Finds the tapped team name view, captures a new team name,
     * and sends the name to the view for display.
     */
    @Override
    public void onInputDialogPositiveClick(DialogFragment dialog) {
        LayoutInflater linf = LayoutInflater.from(this);
        linf.inflate(R.layout.team_rename, null);
        switch (currentView) {
            case R.id.team_a:
                EditText teamANameField = (EditText) dialog.getDialog().findViewById(R.id.new_team_name);
                teamNameA = teamANameField.getText().toString();
                displayTeamAName(teamNameA);
            break;
            case R.id.team_b:
                EditText teamBNameField = (EditText) dialog.getDialog().findViewById(R.id.new_team_name);
                teamNameB = teamBNameField.getText().toString();
                displayTeamBName(teamNameB);
            break;
            default:
                throw new RuntimeException("Unknown ID");
        }
    }

    /**
     * Resets both scores to zero, displays the updated scores,
     * and disables the "New Game" button.
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        teamScoreA = 0;
        teamScoreB = 0;
        displayForTeamA(teamScoreA);
        displayForTeamB(teamScoreB);
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


    /**
     * Checks if either team's score is a non-zero integer,
     * and shows a confirmation dialog if true.
     */
    public void clearScores(View view) {
        if (teamScoreA != 0 || teamScoreB != 0) {
            DialogFragment clearScores = new NewGameFragment();
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
     * Displays the name specified for Team A.
     */
    public void displayTeamAName(String name) {
        TextView nameViewA = (TextView) findViewById(R.id.team_a);
        nameViewA.setText(String.valueOf(name));
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the name specified for Team B.
     */
    public void displayTeamBName(String name) {
        TextView nameViewB = (TextView) findViewById(R.id.team_b);
        nameViewB.setText(String.valueOf(name));
    }

    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }
}
