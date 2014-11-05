package org.oyvang.hangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Geir Morten Larsen on 24.10.2014.
 */
public class HangmanActivity extends Activity {
    public final static int GAME_OVER = -1;
    public final static int GAME_CONTINUE = 0;
    public final static int GAME_WON = 1;
    public final static int END_OF_GAME = 2;
    public final static int RULES = 3;

    private HangmanHandler hangmanHandler;
    private String[] guessedWord;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangman_activity);
        hangmanHandler = new HangmanHandler(getResources().getConfiguration().locale.getLanguage());
        guessedWord = new String[hangmanHandler.newWord()];
        updateGuessedWord(null);
        updateGameInfoView();
    }

    /**
     * Updates the hangman image view
     */
    private void updateHangman() {
        ImageView imageView = (ImageView) findViewById(R.id.hangman);
        int error = hangmanHandler.getWrongGuesses();
        switch (error) {
            case 0:
                imageView.setImageResource(R.drawable.error0);
                break;
            case 1:
                imageView.setImageResource(R.drawable.error1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.error2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.error3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.error4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.error5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.error6);
                break;
            case 7:
                imageView.setImageResource(R.drawable.error7);
                break;
            case 8:
                imageView.setImageResource(R.drawable.error8);
                break;
            case 9:
                imageView.setImageResource(R.drawable.error9);
                break;
            default:
                break;
        }
    }

    /**
     * Appends a string to TextView with id "guessed_letters" for wrong guessed letters and
     * change picture to be equal to the correct wrong guesses status.
     *
     * @param letter - String to append the view with
     * @see private void updateHangman() - Calls this metode
     */
    private void updateWrongGuessedLetters(String letter) {
        TextView textView = (TextView) findViewById(R.id.guessed_letters);
        if (letter.isEmpty()) {
            textView.setText("");
        }
        textView.append(letter + "  ");
        updateHangman();
    }

    /**
     * Sets a button view to be invisible and checks if the correct word contains the buttons letter.
     *
     * @param view - clicked view
     * @see private void updateWrongGuessedLetters(String) or private void updateGuessedWord(ArrayList<Integer>)
     */
    public void onClickButtonLetter(View view) {
        Button button = (Button) view;
        button.setVisibility(view.INVISIBLE);
        String letter = button.getText().toString();
        ArrayList<Integer> pos = hangmanHandler.guessLetter(letter);
        if (pos.size() == 0) {
            updateWrongGuessedLetters(letter);
        } else {
            updateGuessedWord(pos);
        }
        checkGameStatus();
    }

    /**
     * Updates game info with Wins/Losses and nr of games game nr/total
     */
    private void updateGameInfoView() {
        TextView textView = (TextView) findViewById(R.id.game_info_view);
        info = getResources().getString(R.string.win_loss) + ":\t\t" + hangmanHandler.getWins() + "/" + hangmanHandler.getLoss() +
                "\n" + getResources().getString(R.string.word) + ":\t\t" + hangmanHandler.getWordsRemaining() + "/" + hangmanHandler.getTotalWords();
        textView.setText(info);
    }

    /**
     * Fill letter and empty letter space "_" in the guessed word view.
     * Calls updateGuessedWordVeiw()
     *
     * @param pos positions of guessed letter.
     * @see private void updateGuessedWordView()
     */
    private void updateGuessedWord(ArrayList<Integer> pos) {
        if (pos == null) {
            for (int i = 1; i < guessedWord.length; i++) {
                if (hangmanHandler.getLetter(i).equals(" ")) {
                    guessedWord[i] = "  ";
                } else {
                    guessedWord[i] = "_";
                }
            }
        } else {
            for (int i = 0; i < pos.size(); i++) {
                guessedWord[pos.get(i)] = hangmanHandler.getLetter(pos.get(i));
            }
        }
        updateGuessedWordView();
    }

    /**
     * Updates GuessedWordView to see which letter that is correct
     */
    private void updateGuessedWordView() {
        TextView textView = (TextView) findViewById(R.id.guessed_word);
        textView.setText(getWordFromGuessedWord());
    }

    /**
     * Help metode to transform guessedWord array to a string with two spaces at the end
     * of each letter to make it easier to see each letter on screen
     *
     * @return String word which contains each letter in guessedWord array pluss two spaces at the end of each letter
     */
    private String getWordFromGuessedWord() {
        String word = "";
        for (int i = 1; i < guessedWord.length; i++) {
            word += guessedWord[i] + "  ";
        }
        return word;
    }

    /**
     * Metode to check how far in the game you are. Calls same method with different variables or non method.
     *
     * @see private void updateGameInfoView()
     * @see private void gameDialog(String, int)
     */
    private void checkGameStatus() {
        int status = hangmanHandler.whatIsGameStatus(getWordFromGuessedWord());
        switch (status) {
            case GAME_OVER:
                updateGameInfoView();
                gameDialog(getResources().getString(R.string.game_over), END_OF_GAME);
                break;
            case GAME_WON:
                updateGameInfoView();
                gameDialog(getResources().getString(R.string.game_congratulation), END_OF_GAME);
                break;
            case GAME_CONTINUE:
                break;
        }
    }

    /**
     * Shows three different views depending on status and how far into the game it is.
     *
     * @param title  String which contains the title on the AlertDialog
     * @param status Integer, RULES or END_OF_GAME
     */
    private void gameDialog(String title, int status) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        switch (status) {
            case RULES:
                alertDialogBuilder
                        .setMessage(getResources().getString(R.string.game_rules))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.continue_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                break;
            case END_OF_GAME:
                if (hangmanHandler.getWordsRemaining() == 1) {
                    alertDialogBuilder
                            .setMessage(getResources().getString(R.string.end_of_game) + "\t\t" + getResources().getString(R.string.win_loss) + ":\t\t" + hangmanHandler.getWins() + "/" + hangmanHandler.getLoss())
                            .setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.main_menu), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    goToMainMenu();
                                }
                            });
                } else {
                    alertDialogBuilder
                            .setMessage(info + "\n\n" + getResources().getString(R.string.game_end_message))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.main_menu), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    goToMainMenu();
                                }
                            })
                            .setPositiveButton(getResources().getString(R.string.continue_button), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    newGame();
                                    dialog.cancel();
                                }
                            });
                }
                break;
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Creates a new game.
     * Calls for a new word to guess on (GuessedWord) and
     * updates all the views.
     *
     * @see private void updateGuessedWord(null)
     * @see private void updateGameInfoView()
     * @see private void updateWrongGuessedLetters("")
     * @see private voidresetButtons()
     */
    private void newGame() {
        guessedWord = new String[hangmanHandler.newWord()];
        updateGuessedWord(null);
        updateGameInfoView();
        updateWrongGuessedLetters("");
        resetButtons();
    }

    /**
     * Help method that goes through all letter buttons and make them visible.
     */
    private void resetButtons() {
        TableLayout buttonGroup = (TableLayout) findViewById(R.id.button_group);
        for (int i = 0; i < buttonGroup.getChildCount(); i++) {
            TableRow row = (TableRow) buttonGroup.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                Button button = (Button) row.getChildAt(j);
                button.setVisibility(View.VISIBLE);
            }
        }


    }

    /**
     * Method to go back to main menu and finishes this activity
     */
    private void goToMainMenu() {
        startActivity(new Intent(this, MainMenu.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hangman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_menu) {
            goToMainMenu();
            return true;
        }
        if (id == R.id.info_button) {
            gameDialog(getResources().getString(R.string.game_info_button), RULES);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        /** Disable the go-back button on android phone. Not every phone has
         *  this button or they might remove it later phones. Uses only in-application
         *  buttons to go through the application.
         */

    }

}
