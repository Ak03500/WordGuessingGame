package aoa.Guessers;

import java.util.Scanner;
import java.util.List;

public class ConsoleGuesser implements aoa.Guessers.Guesser {
    private Scanner console;

    public ConsoleGuesser(Scanner console) {
        this.console = console;
    }

    @Override
    public char getGuess(String pattern, List<Character> guesses) {
        System.out.print("Your guess? ");
        return console.next().charAt(0);
    }

}
