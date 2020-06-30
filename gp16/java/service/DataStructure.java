/*
 * @DataStructure.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */


package uk.ac.aber.cs221.gp16.java.service;

import javafx.collections.ObservableList;


/**
 * generic data structure interface, specifies functions required from our data structures for the dictionary AND the
 * practice list
 *
 * @author sas90
 * @version 1.0
 */

public interface DataStructure {
    void put(Word word);
    ObservableList<Word> getAsObservable();

}
