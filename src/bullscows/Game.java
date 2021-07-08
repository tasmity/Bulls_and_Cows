package bullscows;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Game extends Generator {
    Scanner scanner = new Scanner(System.in);
    private final char[] secret;
    private int bull;
    private int cow;
    private int turn = 1;
    private int secretLength;
    private int secretSymbol;

    Game() {
        newSecret();
        this.secret = randomSecret(this.secretLength, this.secretSymbol).toCharArray();
        initialization();
    }

    private void newSecret() {
        System.out.println("Input the length of the secret code:");
        this.secretLength = checkError(scanner.nextLine());
        if (this.secretLength > 36 || this.secretLength < 1) {
            System.out.printf("Error: can't generate a secret number with a length of %d because" +
                    "there aren't enough unique digits.%n", secretLength);
            System.exit(-1);
        }
        System.out.println("Input the number of possible symbols in the code:");
        this.secretSymbol = checkError(scanner.nextLine());
        if (secretSymbol < secretLength) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n",
                    secretLength, secretSymbol);
            System.exit(-1);
        } else if (secretSymbol > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(-1);
        }
    }

    private void initialization() {
        System.out.print("The secret is prepared: ");
        IntStream.range(0, secretLength).forEach(i -> System.out.print("*"));
        if (secretSymbol <=10)
            System.out.printf(" (0-%c).%n", symbol.charAt(secretSymbol - 1));
        else if (secretSymbol == 11)
            System.out.println(" (0-9, a)");
        else
            System.out.printf(" (0-9, a-%c).%n", symbol.charAt(secretSymbol - 1));
        System.out.println("Okay, let's start a game!");
        while (this.bull < this.secret.length) {
            this.bull = 0;
            this.cow = 0;
            System.out.printf("Turn %d:%n", turn);
            check(scanner.next().toCharArray());
            turn++;
        }
    }

    private void check(char[] array) {
        for (var i = 0; i < this.secret.length; i++) {
            for (var j = 0; j < array.length; j++) {
                if (this.secret[i] == array[j]){
                    if (i == j) {
                        bull++;
                    } else {
                        cow++;
                    }
                }
            }
        }
        printInfo();
    }

    private void printInfo() {
        System.out.print("Grade: ");
        String bulls = bull > 1 ? "bulls" : "bull";
        String cows = cow > 1 ? "cows" : "cow";
        if (bull == 0 && cow == 0)
            System.out.println("None");
        else if (bull != 0 && cow != 0)
            System.out.printf("%d %s and %d %s%n", bull, bulls, cow, cows);
        else
            System.out.println(bull != 0 ? bull + " " + bulls : cow + " " + cows);
        if (bull == secret.length) {
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }

    private int checkError(String strNum) {
        try {
            return Integer.parseInt(strNum);
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.%n", strNum);
            System.exit(-1);
            return - 1;
        }
    }
}