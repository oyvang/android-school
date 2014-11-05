package org.oyvang.hangman;

import java.util.ArrayList;

/**
 * Created by Geir Morten Larsen on 24.10.2014.
 */
public class HangmanHandler {
    private final int MAX_GUESSES = 9;
    private final int totalWords;
    private int wrongGuesses;
    private ArrayList<String> words;
    private String[] letters;
    private String correctWord;
    private int wins;
    private int loss;


    public HangmanHandler(String language) {
        createWords(language);
        wins = 0;
        loss = 0;
        totalWords = words.size();
    }

    public int getWins() {
        return wins;
    }

    public int getLoss() {
        return loss;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getWordsRemaining() {
        return words.size() + 1;
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    /**
     * Creates one out of two list with word to guess at depending on the language chosen.
     *
     * @param language String "en" or "no". Default is "en".
     * @deprecated Should remove createWords as it is and use a database instead to get words
     */
    private void createWords(String language) {
        if (language.equals("no")) {
            ArrayList noWords = new ArrayList();
            noWords.add("Mikke Mus");
            noWords.add("Minni Mus");
            noWords.add("Donald Duck");
            noWords.add("Dolly Duck");
            noWords.add("Langbein");
            noWords.add("Pluto");
            noWords.add("Svarte Petter");
            noWords.add("Klara Ku");
            noWords.add("Magica fra Tryll");
            noWords.add("Darkwing Duck");
            noWords.add("Ole Duck");
            noWords.add("Dole Duck");
            noWords.add("Doffen Duck");
            noWords.add("Petter Smart");
            noWords.add("Gulbrand Gråstein");
            noWords.add("Max Goof");
            noWords.add("Klaus Knegg");
            noWords.add("Skrue McDuck");
            noWords.add("Spøkelseskladden");
            noWords.add("Rikerud");
            noWords.add("B Gjengen");
            noWords.add("Snipp og Snapp");
            words = noWords;

        } else {
            ArrayList enWords = new ArrayList();
            enWords.add("Mickey Mouse");
            enWords.add("Minnie Mouse");
            enWords.add("Donald Duck");
            enWords.add("Daisy Duck");
            enWords.add("Goofy");
            enWords.add("Pluto");
            enWords.add("Peg-Leg Pete");
            enWords.add("Clarabelle Cow");
            enWords.add("Magica De Spell");
            enWords.add("Darkwing Duck");
            enWords.add("Huey Duck");
            enWords.add("Dewey Duck");
            enWords.add("Louie Duck");
            enWords.add("Gyro Gearloose");
            enWords.add("Flintheart Glomgold");
            enWords.add("Max Goof");
            enWords.add("Horace Horsecollar");
            enWords.add("Scrooge McDuck");
            enWords.add("The Phantom Blot");
            enWords.add("John D. Rockerduck");
            enWords.add("The Beagle Boys");
            enWords.add("Chip /'n/' Dale");
            words = enWords;
        }
    }

    /**
     * Find a new word with help of getWord();
     *
     * @return the length of the letter, if there is no letter it will return 0
     */
    public int newWord() {
        wrongGuesses = 0;
        letters = getWord();
        return letters.length;
    }

    /**
     * Find a new word and delete it from the table words
     *
     * @return returns a table with the correct word. Each element contains one letter
     */
    private String[] getWord() {
        String[] temp = new String[0];
        if (words.size() != 0) {
            int num = generateRandomNr();
            correctWord = words.get(num);
            words.remove(num);
            temp = correctWord.split("");
        }
        return temp;
    }

    /**
     * Finds a letter at the given pos. There is no check on the position exist.
     *
     * @param pos position in the correct word
     * @return String letter at the given position
     */
    public String getLetter(int pos) {
        return letters[pos];
    }

    /**
     * Generates a random number from 0 to words.size()
     *
     * @return int random
     */
    private int generateRandomNr() {
        java.util.Random r = new java.util.Random();
        int rand = r.nextInt(words.size());
        return rand;
    }

    /**
     * Check if a letter exist in the correct word if it does not it will add +1 to wrongGuesses
     *
     * @param letter given letter to be guessed at
     * @return positions of a given letter or non positions if letter does not exist.
     * @see private ArrayList<Integer> getLetterInWordPos(String letter)
     */
    public ArrayList<Integer> guessLetter(String letter) {
        ArrayList pos = getLetterInWordPos(letter);
        if (pos.size() == 0) {
            wrongGuesses++;
        }
        return pos;
    }

    /**
     * Finds every position of a given letter
     *
     * @param letter String with he given letter
     * @return ArrayList<Integer> with every position of the given letter or no positions at all
     * if the given letter is not in the correct word
     */
    private ArrayList<Integer> getLetterInWordPos(String letter) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < letters.length; i++) {
            if (letters[i].toUpperCase().equals(letter)) {
                temp.add(i);
            }
        }
        return temp;
    }

    /**
     * Checking the game statuses:
     * Game over -> if you have reached max wrongGuesses
     * Game won -> if the word you have found equals the correct word
     * Game continues -> if non of the above have happend
     *
     * @param word String with the current word you have guessed
     * @return GAME_OVER = -1, GAME_WON = 1, GAME_CONTINUE = 0
     */
    public int whatIsGameStatus(String word) {
        if (MAX_GUESSES <= wrongGuesses) {
            loss++;
            return HangmanActivity.GAME_OVER;
        } else if (word.replaceAll(" ", "").equals(correctWord.replaceAll(" ", ""))) {
            wins++;
            return HangmanActivity.GAME_WON;
        } else {
            return HangmanActivity.GAME_CONTINUE;
        }
    }
}
