/*
 * @IDictionary.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.service;


import java.util.ArrayList;

/**
 * defines the required functions of the dictionary
 *
 * @author sas90
 * @version 1.0
 */

public interface IDictionary extends DataStructure {
    ArrayList<Word> search(String word);
}
