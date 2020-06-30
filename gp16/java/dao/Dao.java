/*
 * @Dao.java 1.0 28/04/2020
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
 * defines required methods for the DAO
 *
 * @author sas90
 */
public interface Dao {
    Dictionary getDictionary();

    PracticeSet getPracticeList();

    void updateDictionary(Word word);

    void delete(Word word);

    void favorite(Word word);

    void setLang(Lang en);

    ObservableList<Word> getAsObservableDictionary();

    ObservableList<Word> getAsObservablePracticeList();

    ArrayList<Word> search(String searchWord);

    Word getRandomElementFromPractice();

    Word getRandomElementFromDictionary();

    ArrayList<Word> getAsListPracticeList();

    int getPracticeSize();
}
