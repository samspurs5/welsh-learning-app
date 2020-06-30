/*
 * @RandomGenerator.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.service;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author mat80
 * @version 1.0
 */
public class RandomGenerator {

    final Random random = new Random();

    /**
     * A method used to get a chance, true or false.
     * Chance = numerator/denominator
     *
     * @param numerator   the numerator of the chance
     * @param denominator the denominator of the chance
     * @return true if you are lucky.
     */
    public boolean isSuccessful(int numerator, int denominator) {
        int randomValue = random.nextInt(denominator * 1000);
        int valueFromRange = randomValue % denominator;
        for (int currentNumeratorValue = 0; currentNumeratorValue < numerator; currentNumeratorValue++) {
            if (valueFromRange == currentNumeratorValue) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method used to shuffle order of string values
     * in an array object using built in random function
     * 
     * @param array - the array of strings
     * @return array of strings
     */
    public Object[] shuffleStrings(Object[] array) {
        for (int indexVariable = array.length - 1; indexVariable > 0; indexVariable--) {
            int randomInteger = random.nextInt(indexVariable);
            Object temporaryWord = array[indexVariable];
            array[indexVariable] = array[randomInteger];
            array[randomInteger] = temporaryWord;
        }
        return array;
    }

    /**
     * A method used to shuffle order of string values
     * in an array list using built in random function
     * 
     * @param array - the array list of strings
     * @return array list of strings
     */
    public ArrayList<Word> shuffleWord(ArrayList<Word> array) {
        for (int indexVariable = array.size() - 1; indexVariable > 0; indexVariable--) {
            int randomInteger = random.nextInt(indexVariable);
            Word temporaryWord = array.get(indexVariable);
            array.set(indexVariable, array.get(randomInteger));
            array.set(randomInteger, temporaryWord);
        }
        return array;
    }
    
    /**
     * A method to get a random word from an array list
     * using built in random function
     * 
     * @param wordList - the array list of strings
     * @return random word
     */
    public Word getRandomElement(ArrayList<Word> wordList) {
        return wordList.get(random.nextInt(wordList.size()));
    }

}
