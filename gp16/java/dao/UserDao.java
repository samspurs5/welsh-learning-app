/*
 * @UserDao.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.dao;

import javafx.collections.ObservableList;
import uk.ac.aber.cs221.gp16.java.service.Dictionary;
import uk.ac.aber.cs221.gp16.java.service.Lang;
import uk.ac.aber.cs221.gp16.java.service.PracticeSet;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.util.ArrayList;

/**
 * data access object
 * <p>
 * controls data being passed between the front end and back end
 *
 * @author sas90
 */
public class UserDao implements Dao {

    private final Dictionary dictionary;
    private final PracticeSet practiceList;

    /**
     * initializes the dao with an empty and practice list and a loaded dictionary
     */
    public UserDao() {
        practiceList = new PracticeSet();
        dictionary = new Dictionary();
    }

    /**
     * returns the dictionary object
     * @return dictionary
     */

    @Override
    public Dictionary getDictionary() {
        return dictionary;
    }

    /**
     * returns the practice list object
     * @return practice list
     */

    @Override
    public PracticeSet getPracticeList() {
        return practiceList;
    }

    /**
     * adds a new word, updating the dictionary and adding it to the practice list
     * @param word word to add
     */
    @Override
    public void updateDictionary(Word word) {
        word.setFavorite(true);
        dictionary.put(word);
        practiceList.put(word);
    }


    /**
     * removes a word from the practice list
     * @param word word to remove
     */
    @Override
    public void delete(Word word) {
        dictionary.setFavorite(word, false);
        practiceList.remove(word);
    }

    /**
     * adds a word to the practice list
     * @param word
     */

    public void favorite(Word word) {
        dictionary.setFavorite(word, true);
        practiceList.put(word);
    }

    /**
     * sets the dictionaries language ordering
     *
     * @param lang
     */
    public void setLang(Lang lang) {
        dictionary.setLang(lang);
    }


    @Override
    public ObservableList<Word> getAsObservableDictionary() {
        return dictionary.getAsObservable();
    }

    @Override
    public ObservableList<Word> getAsObservablePracticeList() {
        return practiceList.getAsObservable();
    }

    @Override
    public Word getRandomElementFromPractice() {
        return practiceList.getRandomElement();
    }

    @Override
    public ArrayList<Word> search(String searchWord) {
        return dictionary.search(searchWord);
    }

    @Override
    public ArrayList<Word> getAsListPracticeList() {
        return practiceList.getList();
    }

    @Override
    public Word getRandomElementFromDictionary() {
        return dictionary.getRandomElement();
    }

    @Override
    public int getPracticeSize() {
        return practiceList.getSize();
    }
}
