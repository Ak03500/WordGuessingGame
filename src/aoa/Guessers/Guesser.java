package aoa.Guessers;

import java.util.List;

public interface Guesser {
    public char getGuess(String pattern, List<Character> guesses);
}
