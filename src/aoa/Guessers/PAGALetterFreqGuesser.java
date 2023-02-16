package aoa.Guessers;

import aoa.Util.FileUtils;
import java.util.List;
import java.util.Map;
import java.util.*;

public class PAGALetterFreqGuesser implements aoa.Guessers.Guesser { private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    public int countLetters_Pattern(String pattern){
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
    public Map<Character,Integer> FreqMapforPattern(String Pattern){
        Set<Character> letters = new TreeSet<>();
        Map<Character,Integer> frequencyPattern = new TreeMap<>();
        int size = Pattern.length();
        int counter = 0;
        int i = 0;
        //add all letters to set
        for(i = 0; i < size; i++){
            char s = Pattern.charAt(i);
            if(s != '-'){
                letters.add(s);
            }
        }
        List<Character> temp = new ArrayList<>(letters);
        for(int j = 0; j < temp.size(); j++){
            counter = 0;
            char compare = temp.get(j);
            //iterate through string
            for(int k = 0; k < Pattern.length();k++){
                if(compare == Pattern.charAt(k) ){
                    counter += 1;
                }
            }
            frequencyPattern.put(compare,counter);
        }

        return frequencyPattern;
    }
    public Map<Character,Integer> wordMap (String word,Map<Character,Integer> patternMap){
        Set<Character> letters = new TreeSet<>();
        Map<Character,Integer> frequencyPattern = new TreeMap<>();
        int size = word.length();
        int counter = 0;
        int i = 0;
        //add all letters to set
        for(i = 0; i < size; i++){
            char s = word.charAt(i);
            if(s != '-'){
                letters.add(s);
            }
        }
        List<Character> temp = new ArrayList<>(letters);
        for(int j = 0; j < temp.size(); j++){
            counter = 0;
            char compare = temp.get(j);
            //iterate through string
            for(int k = 0; k < word.length();k++){
                if(compare == word.charAt(k) ){
                    counter += 1;
                }
            }
            frequencyPattern.put(compare,counter);
        }
        //now compare key values and make sure patternMap
        // and wordMap have same letters in them

        return frequencyPattern;

    }
    public List<Character> filterGuessesBasedonPattern(List<Character> currentGuesses,String Pattern){
        char[] g = new char[currentGuesses.size()];
        for(int u = 0; u < g.length;u++){
            g[u] = currentGuesses.get(u);
        }
        //List<Integer> indices = new ArrayList<>();
        List<Character> patternCharacters = new ArrayList<>();
        for(char p: Pattern.toCharArray()){
            if(p != '-') {
                patternCharacters.add(p);
            }
        }

        /**
         for(int i = 0; i < Pattern.length();i++){
         char curr = Pattern.charAt(i);
         if(curr == '-'){
         continue;
         }
         else {
         for (int j = 0; j < currentGuesses.size(); j++) {
         if(currentGuesses.get(j) != curr){
         indices.add(j);
         }
         }
         }
         }
         */
        /**
         List<Character> newGuesses = new ArrayList<>();
         for(int index:indices){
         newGuesses.add(g[index]);
         }
         //now remove any duplicate values by creating a new list:
         List<Character> finalGuesses = new ArrayList<>();
         for(char ch: newGuesses){
         if(!finalGuesses.contains(ch)){
         finalGuesses.add(ch);
         }
         }
         */
        currentGuesses.removeAll(patternCharacters);
        return currentGuesses;
    }
    public List<String> WordsMatch_Pattern(int length, String pattern,List<Character> Guesses){
        List<String> words = this.words;
        List<String> matches = new ArrayList<>();
        List<Character> newGuesses = new ArrayList<>();
        for(char R: Guesses){
            newGuesses.add(R);
        }
        int numofLetters = countLetters_Pattern(pattern);
        //first get rid of all words that don't match length of pattern
        for(int f =0; f < words.size(); f++){
            if(words.get(f).length() == pattern.length()){
                matches.add(words.get(f));
            }
        }
        //now to get rid of words that don't match the pattern
        List<String> x = new ArrayList<>();
        int counter  = 0;
        for(int i = 0; i < matches.size(); i++){
            String a = matches.get(i);
            counter = 0;
            for(int g = 0; g < a.length();g++){
                if(pattern.charAt(g) == a.charAt(g)){
                    counter ++;
                }
            }
            if(counter == numofLetters){
                x.add(matches.get(i));
            }
        }
        //filter out newGuesses so it only contains the guesses not in pattern
        newGuesses = filterGuessesBasedonPattern(newGuesses,pattern);
        //now get rid of all words in "words" that had characters that were guessed but
        // were not in pattern
        List<String> newMatches = new ArrayList<>();
        int counter_two  = 0;
        if(!newGuesses.isEmpty()){
            for(int i = 0; i < x.size(); i++){
                for(char guess: newGuesses){
                    String a = x.get(i);
                    counter_two = 0;
                    //iterate through each word in "words"
                    for(int j = 0; j < a.length(); j++){
                        if(guess == a.charAt(j)){
                            counter_two += 1;
                        }
                    }
                    if(counter_two <= 0){
                        newMatches.add(x.get(i));
                    }
                }
            }
            x = newMatches;
        }

        //Now get rid of all words that appear to match pattern but don't
        //need to make map if each remaining word in "words"
        // and check if it matches with the characters in patternMap
        //else if(numofLetters != 0 && !newGuesses.isEmpty()){
        int counter_three = 0;
        String[] finalList = new String[x.size()];
        List<String> f = new ArrayList<>();
        for(int jk = 0; jk < finalList.length;jk++){
            finalList[jk] = x.get(jk);
        }
        Map<Character,Integer> mapPattern = FreqMapforPattern(pattern);
        Map<Character,Integer> wordMap;
        for(int A = 0; A < x.size(); A++){
            counter_three = 0;
            String lol = x.get(A);
            for(char key: mapPattern.keySet()){
                counter_three = 0;
                for(int Y = 0; Y < lol.length() ; Y++){
                    if(key == lol.charAt(Y)){
                        counter_three += 1;
                    }
                }
                if(counter_three != mapPattern.get(key)){
                    finalList[A] = null;
                    //finalList.add(lol);
                }
            }
        }
        //add elements from array to list
        for(String s: finalList){
            if(s != null){
                f.add(s);
            }
        }
        return f;


/**
 else{
 return x;
 }
 */
    }

    public Map<Character,Integer> FreqMapMatches_Pattern(List<String> Match_Pattern){
        Set<Character> letters = new TreeSet<>();
        Map<Character,Integer> frequency = new TreeMap<>();
        int size = Match_Pattern.size();
        int counter = 0;
        int i = 0;
        //add all letters to set
        for(i = 0; i < size; i++){
            String s = Match_Pattern.get(i);
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
                String a = Match_Pattern.get(g);
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

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        List<String> WordsMatch = WordsMatch_Pattern(pattern.length(),pattern,guesses);
        Map<Character,Integer> freq = FreqMapMatches_Pattern(WordsMatch);
        //System.out.println(WordsMatch);
        //System.out.println(freq);
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
        PAGALetterFreqGuesser palfg = new PAGALetterFreqGuesser("data/sorted_scrabble.txt");
        char guess = palfg.getGuess("-o--a-", List.of('o', 'a', 's'));
        System.out.println(guess);
    }

}
