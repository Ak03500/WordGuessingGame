package aoa.Choosers;

import java.util.List;
import java.util.TreeMap;
import java.util.*;

import edu.princeton.cs.algs4.StdRandom;
import aoa.Util.FileUtils;

public class EvilChooser implements aoa.Choosers.Chooser {
    private String pattern;
    private List<String> wordPool;


    public EvilChooser(int wordLength, String dictionaryFile) {
        //make default patterns string everytime new object is created
        if (wordLength < 1) {
            throw new IllegalArgumentException("wordLength must be greater than 1");
        }
        List<String> words = FileUtils.readWords(dictionaryFile);
        int counter = 0;
        ;
        for (String s : words) {
            if (s.length() == wordLength) {
                counter++;
            }
        }
        if (counter == 0) {
            throw new IllegalStateException("no words found of wordLength");

        }

        //intialize empty pattern string with "-"'s
        //everytime new object is created
        char[] patterns = new char[wordLength];
        for (int h = 0; h < patterns.length; h++) {
            patterns[h] = '-';
        }
        String temp = new String(patterns);
        pattern = temp;
        wordPool = FileUtils.readWordsOfLength(dictionaryFile,wordLength);;

    }

    //returns a pattern based on guessed letter and the words in list of words
    //Need to accomodate words of varying length
    public String updatedPattern(char guessedLetter, String chosenWord) {
        List<Integer> indices = new ArrayList<>();
        int occurrences = 0;
        //count num of occurrences of guessedLetter in chosenWord
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == guessedLetter) {
                occurrences += 1;
                indices.add(i);
            }
        }
        int AnyWordsAtAll = 0;
        char[] R = new char[pattern.length()];
        for (int W = 0; W < R.length; W++) {
            R[W] = pattern.charAt(W);
        }
        String Q = new String(R); //make new clone of pattern
        //now see if the currentWord and current patten have any characters in common
        for (int Y = 0; Y < Q.length(); Y++) {
            for (int E = 0; E < chosenWord.length(); E++) {
                if (Q.charAt(Y) == chosenWord.charAt(E)) {
                    AnyWordsAtAll += 1;
                }
            }
        }

        //wrong Guess or "----" type pattern
        if (occurrences == 0 && AnyWordsAtAll == 0) {
            char[] patterns = new char[chosenWord.length()];
            for (int h = 0; h < patterns.length; h++) {
                patterns[h] = '-';
            }
            String temp = new String(patterns);
            return temp;

        } else if (occurrences == 0 && AnyWordsAtAll != 0) {//wrong Guess return same pattern
            return Q;

        } else {//develop pattern based on word and guessedLetter
            int wordLen = chosenWord.length();
            char[] tmp = new char[wordLen];
            if (wordLen != pattern.length()) {
                for (int o = 0; o < tmp.length; o++) {
                    tmp[o] = '-';
                }
            } else {
                for (int F = 0; F < pattern.length(); F++) {
                    tmp[F] = pattern.charAt(F);
                }
            }
            for (int e = 0; e < indices.size(); e++) {
                int index = indices.get(e);
                tmp[index] = guessedLetter;

            }

            String t = new String(tmp);
            return t;


        }

    }

    //returns list of patterns based on guessed letter and words in wordPool
    public List<String> matchPattern(char letter) {
        List<String> f = new ArrayList<>(wordPool);
        String temp = pattern;
        List<String> pats = new ArrayList<>();
        for (String ss : f) {
            temp = updatedPattern(letter, ss);
            pats.add(temp);
        }
        //now to remove the duplicates from the list
        List<String> tempsss = new ArrayList<>();
        for (String p : pats) {
            if (!tempsss.contains(p)) {
                tempsss.add(p);
            }
        }

        return tempsss;

    }

    public int countLettersinPattern(String pattern) {
        int counter = 0;
        List<Character> count = new ArrayList<>();
        //add individual characters from pattern to List
        for (int i = 0; i < pattern.length(); i++) {
            count.add(pattern.charAt(i));
        }
        //now iterate through List and count how many real letters
        for (int h = 0; h < count.size(); h++) {
            if (count.get(h) != '-') {
                counter += 1;
            }
        }
        return counter;

    }

    public Map<Character, Integer> FreqMapforPattern(String Pattern) {
        Set<Character> letters = new TreeSet<>();
        Map<Character, Integer> frequencyPattern = new TreeMap<>();
        int size = Pattern.length();
        int counter = 0;
        int i = 0;
        //add all letters to set
        for (i = 0; i < size; i++) {
            char s = Pattern.charAt(i);
            if (s != '-') {
                letters.add(s);
            }
        }
        List<Character> temp = new ArrayList<>(letters);
        for (int j = 0; j < temp.size(); j++) {
            counter = 0;
            char compare = temp.get(j);
            //iterate through string
            for (int k = 0; k < Pattern.length(); k++) {
                if (compare == Pattern.charAt(k)) {
                    counter += 1;
                }
            }
            frequencyPattern.put(compare, counter);
        }

        return frequencyPattern;
    }
    public int AnyWordsinCommon(String chosenWord,int size,String pattern){
        int anyWordsAtAll = 0;
        char[] R = new char[pattern.length()];
        for (int W = 0; W < R.length; W++) {
            R[W] = pattern.charAt(W);
        }
        String Q = new String(R); //make new clone of pattern
        //now see if the currentWord and current patten have any characters in common
        for (int Y = 0; Y < Q.length(); Y++) {
            for (int E = 0; E < size; E++) {
                for(int u = 0; u < chosenWord.length(); u++){
                    if (Q.charAt(Y) == chosenWord.charAt(u)) {
                        anyWordsAtAll += 1;
                    }
                }
            }
        }
        return anyWordsAtAll;
    }
    //return list of words that obey current pattern
    public List<String> obeysPattern(String pattern, char guessedLetter) {
        List<String> words = new ArrayList<>(wordPool);
        List<String> matches = new ArrayList<>();
        //first get rid of all words that don't match length of pattern
        for (int f = 0; f < words.size(); f++) {
            if (words.get(f).length() == pattern.length()) {
                matches.add(words.get(f));
            }
        }
        //need to count how many letters in "pattern"
        int numOfLetters = countLettersinPattern(pattern);
        int counter = 0;
        //if numOfLetters = 0, then it is a "----" type of pattern or wrong Guess, and we filter out all words in list
        //that don't contain the guessed letter
        if(numOfLetters == 0){
            List<String> allWords = new ArrayList<>(matches);
            List<String> wordsDontWant = new ArrayList<>();
            for(String cs: allWords){
                for(int hgh = 0; hgh < cs.length();hgh++){
                    if(cs.charAt(hgh) == guessedLetter ){
                        wordsDontWant.add(cs);
                    }

                }
            }
            allWords.removeAll(wordsDontWant);
            return allWords;
        }
        //Now iterate through each words in "matches" and add elements that match to a new List
        else {
            List<String> x = new ArrayList<>();
            for (int i = 0; i < matches.size(); i++) {
                String a = matches.get(i);
                counter = 0;
                for (int g = 0; g < a.length(); g++) {
                    if (pattern.charAt(g) == a.charAt(g)) {
                        counter++;
                    }
                }
                if (counter == numOfLetters) {
                    x.add(matches.get(i));
                }


            }
            //now get rid of words that appear to match pattern but don't
            int counter_three = 0;
            String[] finalList = new String[x.size()];
            List<String> f = new ArrayList<>();
            for (int jk = 0; jk < finalList.length; jk++) {
                finalList[jk] = x.get(jk);
            }
            Map<Character, Integer> mapPattern = FreqMapforPattern(pattern);
            Map<Character, Integer> wordMap;
            for (int A = 0; A < x.size(); A++) {
                counter_three = 0;
                String lol = x.get(A);
                for (char key : mapPattern.keySet()) {
                    counter_three = 0;
                    for (int Y = 0; Y < lol.length(); Y++) {
                        if (key == lol.charAt(Y)) {
                            counter_three += 1;
                        }
                    }
                    if (counter_three != mapPattern.get(key)) {
                        finalList[A] = null;
                        //finalList.add(lol);
                    }
                }
            }

            //add elements from array to list
            for (String s : finalList) {
                if (s != null) {
                    f.add(s);
                }
            }
            //check to see if any list is equal to wordPool.size only if guess is wrong
            // if so then, remove the extra element
            int anyWordsAtAll = 0;
            for(String yes: f){
                anyWordsAtAll += AnyWordsinCommon(yes,f.size(),pattern);
            }
            if(f.size() == wordPool.size() && anyWordsAtAll == 0){
                for(int k = f.size()-1; k > 0;k--){
                    f.remove(k);
                }
                return f;
            }
            else{
                return f;
            }


        }


    }

    //returns list of words that obey List of patterns
    public List<List<String>> wordswithPattern(List<String> patterns, char guessedLetter) {
        List<List<String>> match = new ArrayList<>();
        List<String> first = new ArrayList<>();
        for (String fd : patterns) {
            first = obeysPattern(fd, guessedLetter);
            match.add(first);
        }
        //check if there is two words in two categories
        // if so then remove extra word
        //hold one list constant and perform removeALL on each subsequent list
        for(int t = 0; t < match.size() - 1;t++){
            List<String> curr = match.get(t);
            for(int w = t+1; w < match.size();w++){
                List<String> next = match.get(w);
                if(curr.size() > next.size()){
                    curr.removeAll(next);
                }
                else{
                    next.removeAll(curr);
                }
            }
        }
        return match;
    }

    @Override
    public int makeGuess(char letter) {
        //Make map with patterns as keys and the values as the number of words that obey that pattern
        //then go through the map and find which pattern has the highest number of words
        //then also update wordpool and return number of occurrences that guessed letter is in pattern
        Map<String, List<String>> evil = new TreeMap<>();
        List<String> patterns = new ArrayList<>();
        patterns = matchPattern(letter);
        List<List<String>> word = wordswithPattern(patterns, letter);

        //using these two lists: (1) patterns list (2) words from wordpool that match each subsequent pattern
        //make our map: mapping the pattern to corresponding list of words from wordpool
        for (int i = 0; i < patterns.size(); i++) {
            evil.put(patterns.get(i), word.get(i));
        }

        //create new Patterns list and have patterns in alphabetical order
        //based off of TreeMap "Evil";
        List<String> newPatterns = new ArrayList<>();
        for(String key: evil.keySet()){
            newPatterns.add(key);
        }
        //now traverse map and find which key stores the longest list
        int curr; //length of current list in map
        int next; //length of next list in map
        List<String> longestOnes = new ArrayList<>();
        longestOnes = evil.get(newPatterns.get(0));
        int longest = evil.get(newPatterns.get(0)).size();
        for (int j = 0; j < evil.size(); j++) {
            curr = evil.get(newPatterns.get(j)).size();
            if (curr > longestOnes.size()) {
                longestOnes = evil.get(newPatterns.get(j));

            }

        }
        //now eliminate all words from wordpool that don't match longestOnes
        List<String> newWordPool = new ArrayList<>(wordPool);
        List<String> newWordPool2 = new ArrayList<>();

        for (int y = 0; y < longestOnes.size(); y++) {
            String comp = longestOnes.get(y);
            for (int s = 0; s < newWordPool.size(); s++) {
                String comp2 = newWordPool.get(s);
                if (comp == comp2) {
                    newWordPool2.add(comp2);
                }
            }
        }
        wordPool = newWordPool2;
        //now based on the pattern which EvilChoose decided to go with
        //count num of occurrences of guessed letter in that pattern
        //need to find the pattern in our map by iterating through each key in map and seeing which of its
        //values matches with longestOnes List
        String temp = pattern;
        for (String key : evil.keySet()) {
            if (evil.get(key) == longestOnes) {
                temp = key;
            }
        }
        pattern = temp;

        int occurrences = 0;
        for (int lol = 0; lol < temp.length(); lol++) {
            if (temp.charAt(lol) == letter) {
                occurrences += 1;
            }
        }

        return occurrences;
    }

    @Override
    public String getPattern() {

        return pattern;
    }

    @Override
    public String getWord() {
        if (wordPool.size() == 1) {
            return wordPool.get(0);
        }

        int numWords = wordPool.size();
        int random = StdRandom.uniform(numWords);
        return wordPool.get(random);
    }


    public static void main(String[] args) {
        /**
         List<String> test = new ArrayList<>();
         test.add("add");
         test.add("aarvard");
         test.add("cool");
         test.add("catttyy");
         */
        EvilChooser ec = new EvilChooser(6, "data/sorted_scrabble.txt");
        int first = ec.makeGuess('e');
        System.out.println(ec.getPattern());
        System.out.println("***********************************************");
        int second = ec.makeGuess('o');
        System.out.println(ec.getPattern());
        System.out.println("***********************************************");
        int third = ec.makeGuess('a');
        System.out.println(ec.getPattern());
        System.out.println("***********************************************");
        int fourth = ec.makeGuess('i');
        System.out.println(ec.getPattern());
        System.out.println("***********************************************");
        int fifth = ec.makeGuess('u');
        System.out.println(ec.getPattern());
        int sixth = ec.makeGuess('s');
        System.out.println(ec.getPattern());
        int seventh = ec.makeGuess('t');
        int eighth = ec.makeGuess('b');
        int nine = ec.makeGuess('c');
        int ten = ec.makeGuess('d');
        int eleven = ec.makeGuess('f');
        int twelve = ec.makeGuess('g');
        int thirteen = ec.makeGuess('h');
        int fourteen = ec.makeGuess('j');
        int fifteen = ec.makeGuess('k');
        System.out.println(ec.getPattern());
        int sixteen = ec.makeGuess('l');
        System.out.println(ec.getPattern());
        int seventeen = ec.makeGuess('m');
        int eighteen = ec.makeGuess('p');
        System.out.println(eighteen);
    }

}