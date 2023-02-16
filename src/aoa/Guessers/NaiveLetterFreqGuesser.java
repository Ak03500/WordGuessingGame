package aoa.Guessers;

import aoa.Util.FileUtils;
import edu.princeton.cs.algs4.In;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

public class NaiveLetterFreqGuesser implements aoa.Guessers.Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);

    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {

        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        Set<Character> letters = new TreeSet<>();
        Map<Character,Integer> frequency = new TreeMap<>();
        int size = this.words.size();
        int counter = 0;
        int i = 0;
        //add all letters to set
        for(i = 0; i < size; i++){
            String s = this.words.get(i);
            for(int j = 0; j < s.length();j++){
                letters.add(s.charAt(j));
            }
        }
        //now to count frequency of each letter
        List<Character> temp = new ArrayList<>(letters);

        for (int k = 0; k < temp.size(); k++) {
            counter = 0;
            char c = temp.get(k);
            //iterate through list of words
            for(int g = 0; g < size; g++){
                String a = this.words.get(g);
                //iterate through each word in words
                for(int r = 0; r < a.length(); r++){
                    if(a.charAt(r) == c){
                        counter += 1;
                    }
                }
            }
            frequency.put(c,counter);
        }

        return frequency;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        Map<Character,Integer> letters = this.getFrequencyMap();
        if(letters.isEmpty()){
            return '?';
        }
        int max = 0;
        //remove the guesses from the map
        for(int i = 0; i < guesses.size(); i++){
            letters.remove(guesses.get(i));
        }
        //now put all the frequencies in arraylist
        List<Integer> sorted = new ArrayList<>();
        List<Character> sortedLetters = new ArrayList<>();
        for(char key: letters.keySet()){
            sorted.add(letters.get(key));
            sortedLetters.add(key);
        }
        //now find the highest number. If tie then return first occurrence
        max = sorted.get(0);
        int index = 0;
        for(int u = 1; u < sorted.size(); u++){
            if(max < sorted.get(u)){
                max = sorted.get(u);
                index = u;
            }

        }

        return sortedLetters.get(index);
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
