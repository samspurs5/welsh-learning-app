package uk.ac.aber.cs221.gp16.tests;


import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp16.java.controllers.CommonController;
import uk.ac.aber.cs221.gp16.java.service.*;

import java.util.ArrayList;

/**
 * JUnit class, tests various components of the program.
 *
 * @author mag95
 * @author cap37
 * @author joh84
 *
 * @version 1.0
 */



public class DictionaryTest {

    /**
     * JUnit test to check the ordering by English, grabs both the first and last words and checks whether
     * the first word starts with a and the last with z.
     */

    //FR-03
    @org.junit.jupiter.api.Test
    public void dictionaryOrderingEnglishTest() {
        CommonController.getUserDao().setLang(Lang.EN);
        Dictionary dictionary = CommonController.getUserDao().getDictionary();
        String firstWord = dictionary.getAsObservable().get(0).getEnglish();
        String lastWord = dictionary.getAsObservable().get(dictionary.getAsObservable().size() - 1).getEnglish();
        Assertions.assertEquals(firstWord.charAt(0), 'a');
        Assertions.assertEquals(lastWord.charAt(0), 'z');
    }

    /**
     * JUnit test to order by Welsh, it compares the value of the first and last word in the ordered dictionary
     * and returns true if comparison is greater than 0.
     */

    //FR-04
    @org.junit.jupiter.api.Test
    public void dictionaryOrderingWelshTest() {
        CommonController.getUserDao().setLang(Lang.CY);
        Dictionary dictionary = CommonController.getUserDao().getDictionary();
        String firstWord = dictionary.getAsObservable().get(0).getWelsh();
        String lastWord = dictionary.getAsObservable().get(dictionary.getAsObservable().size() - 1).getWelsh();
        Assertions.assertEquals(firstWord.charAt(0), 'a');
        int comparison = String.valueOf(lastWord.charAt(0)).compareTo(String.valueOf(firstWord.charAt(0)));
        Assertions.assertTrue(comparison > 0);
    }
    //FR-05

    /**
     * Truncates list by specified letter, checks if the list returned matches the character searched for.
     */

    @org.junit.jupiter.api.Test
    public void searchByLetterTest() {
        boolean validation = true;
        CommonController.getUserDao().setLang(Lang.EN);
        Dictionary dictionary = CommonController.getUserDao().getDictionary();
        ArrayList<Word> afterSearch = dictionary.search("b");
        for (Word word : afterSearch) {
            if (!(word.getEnglish().startsWith("b") || word.getEnglish().startsWith("B"))) {
                validation = false;
                break;
            }
        }
        Assertions.assertTrue(validation);
    }

    /**
     * Searches the dictionary for a welsh word, afterwards checks that the correct word and its translation has been
     * found and in the first word in the list.
     */

    //FR-06
    @org.junit.jupiter.api.Test
    public void lookForWordWelshTest() {
        CommonController.getUserDao().setLang(Lang.CY);
        Dictionary dictionary = CommonController.getUserDao().getDictionary();
        ArrayList<Word> afterSearch = dictionary.search("beic");
        Assertions.assertEquals("beic", afterSearch.get(0).getWelsh());
        Assertions.assertEquals("bicycle", afterSearch.get(0).getEnglish());
    }

    /**
     * Changes language to Welsh, and then searches for a truncated version of a word, the list should then only show
     * words beginning with "ca".
     */

    //FR-07
    @org.junit.jupiter.api.Test
    public void truncatedWelshTest() {
        CommonController.getUserDao().setLang(Lang.CY);
        Dictionary dictionary = CommonController.getUserDao().getDictionary();
        ArrayList<Word> afterSearch = dictionary.search("ca");

        Assertions.assertTrue(afterSearch.get(0).getWelsh().startsWith("ca"));
        Assertions.assertTrue(afterSearch.get(afterSearch.size() - 1).getWelsh().startsWith("ca"));

    }

    /**
     * Changes language to English, and then searches for a truncated version of a word, the list should then only show
     * words beginning with "bi".
     */

    //FR-08
    @org.junit.jupiter.api.Test
    public void truncatedEnglishTest() {
        CommonController.getUserDao().setLang(Lang.EN);
        Dictionary dictionary = CommonController.getUserDao().getDictionary();
        ArrayList<Word> afterSearch = dictionary.search("bi");
        Assertions.assertTrue(afterSearch.get(0).getEnglish().startsWith("bi"));
        Assertions.assertTrue(afterSearch.get(afterSearch.size() - 1).getEnglish().startsWith("bi"));
    }

    /**
     * Searches the dictionary for a word that does not exist within the list, an empty list should be returned.
     */
    //FR-09
    @org.junit.jupiter.api.Test
    public void searchForWordThatDoesNotExistTest(){
        CommonController.getUserDao().setLang(Lang.EN);
        CommonController.getUserDao().getDictionary().getAsObservable();
        Dictionary dictionary = CommonController.getUserDao().getDictionary();
        ArrayList<Word> afterSearch = dictionary.search("mousepad");

        Assertions.assertEquals(0, afterSearch.size());
    }

    /**
     * Enters illegal characters into the search bar, this search should return nothing, an empty list to the user.
     */
    //FR-10
    @org.junit.jupiter.api.Test
    public void userEntersIllegalCharactersTest(){
        CommonController.getUserDao().setLang(Lang.EN);
        CommonController.getUserDao().getDictionary().getAsObservable();
        Dictionary dictionary = CommonController.getUserDao().getDictionary();
        ArrayList<Word> afterSearch = dictionary.search("&*");

        Assertions.assertEquals(0, afterSearch.size());
    }

    /**
     * Favourites a word to be added to the practice list, then checks the practice list to ensure a word has been
     * added.
     */
    //FR-11
    @org.junit.jupiter.api.Test
    public void addToPracticeListTest() {
        CommonController.getUserDao().setLang(Lang.CY);
        Word firstWord = CommonController.getUserDao().getDictionary().getAsObservable().get(0);
        CommonController.getUserDao().favorite(firstWord);

        Assertions.assertEquals(firstWord, CommonController.getUserDao().getPracticeList().getAsObservable().get(0));
    }

    /**
     * Removes all words from practice list if there are any, and then checks the practice list for a word, this word
     * returned should be equal to NULL because the list is empty.
     */
    //FR-12
    @org.junit.jupiter.api.Test
    public void viewEmptyPracticeListTest() {
        CommonController.getUserDao().setLang(Lang.CY);
        PracticeSet practiceList = CommonController.getUserDao().getPracticeList();
        for (Word word : practiceList.getAsObservable()) {
            practiceList.remove(word);
        }
        Word noWord = practiceList.getRandomElement();

        Assertions.assertNull(noWord);
    }

    /**
     * Removes a word from the practice list, it first adds the first word located in the Welsh dictionary to the
     * practice list, this word is then removed and a check is performed to check this word is no longer
     * inside the practice list.
     */
    //FR-13
    @org.junit.jupiter.api.Test
    public void removeWordFromPractiseList () {
        CommonController.getUserDao().setLang(Lang.CY);
        Word firstWord = CommonController.getUserDao().getDictionary().getAsObservable().get(0);
        CommonController.getUserDao().favorite(firstWord);
        Word testWord = CommonController.getUserDao().getPracticeList().getAsObservable().get(0);

        CommonController.getUserDao().delete(testWord);

        Assertions.assertNotEquals(testWord, CommonController.getUserDao().getPracticeList());
    }

    /**
     * JUnit test to add a word to the dictionary, adds the specified test word to the dictionary with its
     * additional attributes, a check then runs to ensure the word has been added to the dictionary list. Lastly this
     * word is removed as to not interfere with other tests.
     */
    //FR-14
    @org.junit.jupiter.api.Test
    public void addingWordToDictionary() {
        CommonController.getUserDao().setLang(Lang.CY);
        Word testWord = new Word("testEN", "testCY", Type.nm);

        CommonController.getUserDao().updateDictionary(testWord);

        Assertions.assertEquals(testWord, CommonController.getUserDao().getPracticeList().getRandomElement());
        Assertions.assertTrue(CommonController.getUserDao().getDictionary().getAsObservable().contains(testWord));
        CommonController.getUserDao().getPracticeList().remove(testWord);
    }

    /**
     * This test checks if the word has already been added to the dictionary, this should catch any duplicate words
     * being added.
     */
    //FR-15
    @Test
    public void wordAlreadyAdded() {
        CommonController.getUserDao().setLang(Lang.CY);
        Word testWord = new Word("bicycle","beic", Type.nm);
        int dictionaryInitialSize = CommonController.getUserDao().getDictionary().getAsObservable().size();
        CommonController.getUserDao().updateDictionary(testWord);
        int dictionaryFinalSize = CommonController.getUserDao().getDictionary().getAsObservable().size();

        Assertions.assertEquals(0, dictionaryInitialSize - dictionaryFinalSize);
    }

    /**
     * This test checks if a word contains Illegal characters and if the characters after them are handled correctly.
     */
    //FR-16
    @org.junit.jupiter.api.Test
    public void userAddsIllegalCharactersTest() {
        CommonController.getUserDao().setLang(Lang.CY);
        Word testWord = new Word("Testword&", "Testword&", Type.nm);
        boolean validation=false;
        CommonController.getUserDao().updateDictionary(testWord);
        ObservableList<Word> dictionary = CommonController.getUserDao().getDictionary().getAsObservable();
        Assertions.assertEquals(testWord, CommonController.getUserDao().getPracticeList().getAsObservable().get(0));
        Assertions.assertFalse(CommonController.getUserDao().getDictionary().getAsObservable().contains("Testword&"));
        for(Word word: dictionary) {
            if (word.getEnglish().equals("Testword")) {
                validation = true;
                break;
            }
        }
        Assertions.assertTrue(validation);

        Word testWord2 = new Word("&", "&", Type.nm);

        CommonController.getUserDao().updateDictionary(testWord2);

        Assertions.assertFalse(CommonController.getUserDao().getDictionary().getAsObservable().contains("&"));

        CommonController.getUserDao().getPracticeList().remove(testWord);

    }

    /**
     * This test checks if the display of a noun shows if it is feminine or masculine.
     */
    //FR-17
    @org.junit.jupiter.api.Test
    public void nounsGenderTest() {

        ObservableList<Word> dictionary = CommonController.getUserDao().getPracticeList().getAsObservable();
        boolean validation = true;

        for (Word word : dictionary) {
            if (word.getType().equals(Type.nm)) {
                if (word.getType().equals(Type.nm)) {
                    if (!word.getDisplayWelsh().contains("(Masculine noun)")) {

                        validation = false;
                    }
                }
            }
            if (word.getType().equals(Type.nf)) {
                if (word.getType().equals(Type.nf)) {
                    if (!word.getDisplayWelsh().contains("(Feminine noun)")) {

                        validation = false;
                    }
                }
            }
        }
        Assertions.assertTrue(validation);
    }

    /**
     * Junit test to ensure that the verbs inside the dictionary have the correct preface "to" in English.
     */

    //FR-18
    @org.junit.jupiter.api.Test
    public void verbsDisplayedToTest() {
        ObservableList<Word> dictionary = CommonController.getUserDao().getDictionary().getAsObservable();
        ArrayList<String> verbs= new ArrayList<>();
        boolean validation=true;
        for(Word word: dictionary){

            if (word.getType().equals(Type.verb))
            {
                verbs.add(word.getDisplayEnglish());
            }
        }
        for (String string: verbs) {
            if (!string.startsWith("to")) {
                validation = false;
                break;
            }
        }
        Assertions.assertTrue(validation);
    }
}