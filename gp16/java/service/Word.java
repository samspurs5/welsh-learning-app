/*
 * @Word.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.service;

import java.util.Objects;

/**
 * Word object, stores a word, its english/welsh translations, formatted versions of the english and welsh and the
 * type of word
 *
 * @author sas90
 * @version 1.0
 */

public class Word {

    // ////////// //
    // Constants. //
    // ////////// //

    private final String displayWelsh;
    private final String displayEnglish;
    private final String english;
    private final String welsh;
    private final Type wordType;
    private boolean favorite;


    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * Whitelists all legal characters, if a new character is required, add it to the valid chars regex
     *
     * @param word word to parse
     * @return legal part of the word
     */

    private String stripChars(String word) {
        String validChars = "[^A-Za-z âÂêÊîÎôÔûÛŵŷ’-]";
        if (word.split(validChars).length == 0) {
            return "";
        }
        return word.split(validChars)[0].stripTrailing();
    }

    /**
     * Adds the type of noun to the end of a word
     *
     * @return formatted word (nouns)
     */

    private String toNoun(String word) {

        if (wordType == Type.nf) {
            return word + " (Feminine noun)";
        }
        if (wordType == Type.nm) {
            return word + " (Masculine noun)";
        }
        return word;
    }

    /**
     * Adds "to" to the front of verbs
     *
     * @return formatted word (verbs)
     */

    private String toVerb(String word) {
        if (wordType == Type.verb) {
            return "to " + word;
        }
        return word;
    }

    /**
     * Initializes a word
     *
     * @param english english translation
     * @param welsh   welsh translation
     * @param type    type of word
     */

    public Word(String english, String welsh, Type type) {
        welsh = welsh.trim();
        english = english.trim();
        this.wordType = type;
        this.displayEnglish = toVerb(english);
        this.displayWelsh = toNoun(welsh);
        this.english = stripChars(english);
        this.welsh = stripChars(welsh);
        this.favorite = false;
    }

    /**
     * Gets the formatted welsh
     *
     * @return formatted welsh (noun type)
     */

    public String getDisplayWelsh() {
        return displayWelsh;
    }

    /**
     * Gets the formatted english
     *
     * @return formatted english (run -> to run)
     */

    public String getDisplayEnglish() {
        return displayEnglish;
    }

    /**
     * Returns the english translation
     *
     * @return english translation
     */

    public String getEnglish() {
        return english;
    }

    /**
     * Returns the welsh translation
     *
     * @return welsh translation
     */

    public String getWelsh() {
        return welsh;
    }

    /**
     * Returns words type
     *
     * @return type of word
     */

    public Type getType() {
        return wordType;
    }


    /**
     * Returns the word object as a string
     *
     * @return word object as a string
     */

    @Override
    public String toString() {
        return "Word{" +
                "displayWelsh='" + displayWelsh + '\'' +
                ", displayEnglish='" + displayEnglish + '\'' +
                ", english='" + english + '\'' +
                ", welsh='" + welsh + '\'' +
                ", wordType=" + wordType +
                '}';
    }

    /**
     * Overrides the hashCode function to limit it to include the welsh, english and wordType attributes
     *
     * @return hash code
     */

    @Override
    public int hashCode() {
        return Objects.hash(english, welsh, wordType);
    }


    /**
     * Overrides the equals function to limit it to include the welsh, english and wordType attributes
     *
     * @param object an object to compare to
     * @return whether this object and another object are equal
     */

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Word word = (Word) object;
        return Objects.equals(displayEnglish, word.displayEnglish) &&
                Objects.equals(displayWelsh, word.displayWelsh) &&
                wordType == word.wordType;
    }


    /**
     * Checks boolean value of word
     *
     * @return boolean value of word
     */

    public boolean isFavorite() {
        return favorite;
    }

    /**
     * Sets boolean value of word
     *
     * @param favorite boolean value of word
     */

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


}
