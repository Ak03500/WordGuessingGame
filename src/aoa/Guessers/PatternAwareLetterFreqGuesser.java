package aoa.Guessers;

import aoa.Util.FileUtils;

import java.util.*;

public class PatternAwareLetterFreqGuesser implements aoa.Guessers.Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {

        words = FileUtils.readWords(dictionaryFile);
    }


    public int countLettersinPattern(String pattern){
        int counter = 0;
        List<Character> count = new ArrayList<>();
        //add individual characters from pattern to List
        for(int i = 0; i < pattern.length();i++){
            count.add(pattern.charAt(i));
        }
        //now iterate through List and count how many real letters
        for(int h = 0; h < count.size(); h++){
            if(count.get(h) != '-'){
                counter += 1;
            }
        }
        return counter;

    }
    //return list of words that match the "pattern"
    public List<String> WordsMatchPattern(String pattern){
        List<String> words = this.words;
        List<String> matches = new ArrayList<>();
        //first get rid of all words that don't match length of pattern
        for(int f =0; f < words.size(); f++){
            if(words.get(f).length() == pattern.length()){
                matches.add(words.get(f));
            }
        }

        //need to count how many letters in "pattern"
        int numOfLetters = countLettersinPattern(pattern);
        int counter = 0;
        //Now iterate through each words in "matches" and add elements that match to a new List
        List<String> x = new ArrayList<>();
        for(int i = 0; i < matches.size(); i++){
            String a = matches.get(i);
            counter = 0;
            for(int g = 0; g < a.length();g++){
                if(pattern.charAt(g) == a.charAt(g)){
                    counter ++;
                }
            }
            if(counter == numOfLetters){
                x.add(matches.get(i));
            }


        }

        return x;
    }

    //returns frequency map that matches the "pattern"
    public Map<Character,Integer> FreqMapMatchesPattern(List<String> MatchPattern){
        Set<Character> letters = new TreeSet<>();
        Map<Character,Integer> frequency = new TreeMap<>();
        int size = MatchPattern.size();
        int counter = 0;
        int i = 0;
        //add all letters to set
        for(i = 0; i < size; i++){
            String s = MatchPattern.get(i);
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
                String a = MatchPattern.get(g);
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

    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    @Override
    public char getGuess(String pattern, List<Character> guesses) {
        int patternLength = pattern.length();
        //gives us map of frequency of words that match pattern
        Map<Character,Integer> freq = FreqMapMatchesPattern(WordsMatchPattern(pattern));
        //now simply find next most common character. If tie then pick first alphabetically
        if(freq.isEmpty()){return '?';}
        int max = 0;
        //remove the guesses from the map
        for(int i = 0; i < guesses.size(); i++){
            freq.remove(guesses.get(i));
        }
        //now put all the frequencies in arraylist
        List<Integer> sorted = new ArrayList<>();
        List<Character> sortedLetters = new ArrayList<>();
        for(char key: freq.keySet()){
            sorted.add(freq.get(key));
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
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));

    }
}


