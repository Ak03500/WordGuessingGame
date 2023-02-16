package aoa.Choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.Util.FileUtils;
import java.util.*;

public class RandomChooser implements aoa.Choosers.Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        //make default patterns string everytime new object is created
        if(wordLength < 1){
            throw new IllegalArgumentException("wordLength must be greater than 1");
        }
        List<String> words = FileUtils.readWords(dictionaryFile);
        int counter = 0;;
        for(String s: words){
            if(s.length() == wordLength){
                counter++;
            }
        }
        if(counter == 0){
            throw new IllegalStateException("no words found of wordLength");

        }
        char[] patterns = new char[wordLength];
        for(int h = 0; h < patterns.length; h++){
            patterns[h] = '-';
        }
        String temp = new String(patterns);
        pattern = temp;

        List<String> wordsofLength =FileUtils.readWordsOfLength(dictionaryFile,wordLength);
        int numWords = wordsofLength.size();
        int random =  StdRandom.uniform(numWords);
        chosenWord = wordsofLength.get(random);
    }

    @Override
    public int makeGuess(char letter) {
        int occurrences = 0;
        List<Integer> indices = new ArrayList<>();
        //iterate through choseWord and count occurrences of letter in chosenWord
        for(int i = 0; i < chosenWord.length();i++){
            if(chosenWord.charAt(i) == letter){
                occurrences += 1;
                indices.add((i));
            }
        }
        //now update pattern by iterating through the string
        //and adding the "letter" "occurrences" many times into "pattern"
        //based on what index it matched at which is stored in "indices" List
        String temp = pattern;
        char[] test = new char[chosenWord.length()];
        for(int y = 0; y < pattern.length(); y++){
            test[y] = pattern.charAt(y);
        }
        if(occurrences > 0){ //check if the guessed letter actually does appear in secret word
            for(int j = 0; j < indices.size(); j++){
                int index = indices.get(j);
                test[index] = letter;

            }
        }

        String t = new String(test);
        pattern = t;
        return occurrences;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
    public static void main(String[] args) {
        /**
         List<String> test = new ArrayList<>();
         test.add("add");
         test.add("aarvard");
         test.add("cool");
         test.add("catttyy");
         */
        RandomChooser ec = new RandomChooser(6, "data/sorted_scrabble.txt");
        int first = ec.makeGuess('e');
        System.out.println(ec.getPattern());
        int second = ec.makeGuess('o');
        System.out.println(ec.getPattern());
        int third = ec.makeGuess('a');
        System.out.println(ec.getPattern());
        int fourth = ec.makeGuess('i');
        System.out.println(ec.getPattern());
        int fifth = ec.makeGuess('u');
        System.out.println(ec.getPattern());
        int sixth = ec.makeGuess('s');
        System.out.println(ec.getPattern());
        System.out.println(sixth);
    }

}
