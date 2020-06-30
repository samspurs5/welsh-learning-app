/*
 * @PracticeSet.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * Backend implementation of practice list
 * <p>
 * Supports the adding, removing and random sampling of words
 *
 * @author sas90
 * @version 1.0
 */
 
public class PracticeSet
implements IPracticeSet {
    
    // //////////////// //
    // Class variables. //
    // //////////////// //


    /**
     * Initializes the practice list as an empty hash set
     */
     
    private final HashSet<Word> practiceList;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    public PracticeSet() {
        practiceList = new HashSet<>();
    }

    /**
     * Adds a word to the practice list
     *
     * @param word
     */

    @Override
    public void put(Word word) {
        practiceList.add(word);
    }

    /**
     * Removes a word from the practice list
     *
     * @param word word to remove
     */
     
    @Override
    public void remove(Word word) {
        practiceList.remove(word);
    }

    /**
     * Returns practice list as a array which can be viewed in javafx
     *
     * @return observable array
     */
     
    public ObservableList<Word> getAsObservable() {
        return FXCollections.observableArrayList(practiceList);
    }

    /**
     * Gets a random word from the practice list
     *
     * @return random word
     */
     
    public Word getRandomElement() {
        RandomGenerator randomGenerator = new RandomGenerator();
        if (!practiceList.isEmpty()) {
            return randomGenerator.getRandomElement(new ArrayList<>(practiceList));
        }
        return null;
    }
	
	/**
	 * Returns size of the practice list
	 *
	 * @return practiceList size
	 */
	 
    public int getSize() {
        return practiceList.size();
    }

    /**
     * Returns the practice list as a list (convert from hash set)
     * @return practice list
     */
     
    public ArrayList<Word> getList() {
        return new ArrayList<>(practiceList);
    }


}
