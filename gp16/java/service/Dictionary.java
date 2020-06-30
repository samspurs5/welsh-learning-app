/*
 * @Dictionary.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.service;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.ac.aber.cs221.gp16.java.service.simple.json.org.JSONArray;
import uk.ac.aber.cs221.gp16.java.service.simple.json.org.JSONObject;
import uk.ac.aber.cs221.gp16.java.service.simple.json.org.parser.JSONParser;
import uk.ac.aber.cs221.gp16.java.service.simple.json.org.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * backend implementation for the dictionary
 * <p>
 * supports searching, ordering, adding and loading the dictionary
 * <p>
 * uses the ArrayList data structure, with a class that implements comparator for ordering by welsh or english
 *
 * @author sas90
 * @version 1.0
 */

public class Dictionary
        implements IDictionary {

    // //////////////// //
    // Class variables. //
    // //////////////// //

    private final ArrayList<Word> dictionary;
    private final SortByLanguage comparator = new SortByLanguage();
    private Lang language;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * initializes the dictionary in english and loads it
     */

    public Dictionary() {
        language = Lang.EN;
        dictionary = load();
    }

    /**
     * returns the dictionary values as a list which can be displayed in javafx
     *
     * @return dictionary values
     */

    public ObservableList<Word> getAsObservable() {
        return FXCollections.observableArrayList(dictionary);
    }

    /**
     * sets the language of the dictionary, changing the value of the keys to english or welsh depending on the language
     *
     * @param language - language to set the dictionary to
     */

    public void setLang(Lang language) {
        if (language != this.language) {
            this.language = language;
        }
        comparator.setLang(language);
        dictionary.sort(comparator);

    }


    /**
     * Checks for if word is present in the given dictionary
     * file and sets equal to button value
     *
     * @param word   - word to find
     * @param button - true/false if button selected
     */

    public void setFavorite(Word word, boolean button) {
        if (dictionary.contains(word)) {
            dictionary.get(dictionary.indexOf(word)).setFavorite(button);
        }
    }

    /**
     * adds a word to the dictionary, removing illegal characters
     *
     * @param word word to add
     */

    @Override
    public void put(Word word) {
        if (!dictionary.contains(word)) {
            dictionary.add(word);
            dictionary.sort(comparator);
        }
    }

    /**
     * Looks for a word starting with the given input.
     *
     * @param search substring to search for
     */
     
    @Override
    public ArrayList<Word> search(String search) {
        search = search.toLowerCase();
        ArrayList<Word> matches = new ArrayList<>();
        /*search through the dictionary, adding anything that matches the substring to an array list, then returning
        the array list*/
        for (Word word : dictionary) {
            {
                if (language == Lang.EN) {
                    if (word.getEnglish().toLowerCase().startsWith(search)) {
                        matches.add(word);
                    }
                } else {
                    if (word.getWelsh().toLowerCase().startsWith(search)) {
                        matches.add(word);
                    }
                }
            }
        }
        return matches;
    }

    /**
     * selects a random element from the dictionary
     *
     * @return random word from dictionary
     */

    public Word getRandomElement() {
        RandomGenerator randomGenerator = new RandomGenerator();
        if (!dictionary.isEmpty()) {
            return randomGenerator.getRandomElement(dictionary);
        }
        return null;
    }

    /**
     * loads the dictionary from the json file
     *
     * @return the dictionary as a TreeMap
     */

    private ArrayList<Word> load() {
        ArrayList<Word> arrayList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String dictFile = "src/uk/ac/aber/cs221/gp16/resources/json/dictionary.json";
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(dictFile));
            //iterates over every JSON word, converts them into a Word object and adds them to a temporary dictionary
            for (Object newWord : a) {
                JSONObject word = (JSONObject) newWord;
                String english = (String) word.get("english");
                String welsh = (String) word.get("welsh");
                arrayList.add(new Word(english, welsh, Type.valueOf((String) word.get("wordType"))));
            }
            arrayList.sort(comparator);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Empty file");
        } catch (IOException e) {
            System.out.println("dictionary file doesn't exist");
        }
        return arrayList;
    }
}

/**
 * Controls how sorting is done, if the language is set to welsh, the list is ordered by welsh, otherwise it is ordered
 * by english. This isn't essential but may be useful for optimising searching later
 *
 * @author sas90
 * @version 1.0
 */

class SortByLanguage
implements Comparator<Word> {

    private Lang language;
    
    /**
     * sets language to originally sort into english
     */
     
    public SortByLanguage() {
        this.language = Lang.EN;
    }
    
    /**
     * set language to parameter input
     *
     * @param language
     */
     
    public void setLang(Lang language) {
        this.language = language;
    }
    
    /**
     * compares 2 given words ignoring letter cases
     *
     * @param wordCompare1, wordCompare2
     * @return 0 if they're the same word
     */
     
    @Override
    public int compare(Word wordCompare1, Word wordCompare2) {
        if (language == Lang.CY) {
            return wordCompare1.getWelsh().compareToIgnoreCase(wordCompare2.getWelsh());
        }
        return wordCompare1.getEnglish().compareToIgnoreCase(wordCompare2.getEnglish());
    }

}