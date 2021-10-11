
import util.FullName;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.stream.Stream;

import static java.lang.Character.isUpperCase;

public class ClassFinder {
    public static void main(String... args) {

        if(args[1].startsWith(" ") || args[1].length() == 0) {
            System.out.println("./class-finder <filename> '<pattern>'");
            System.exit(0);
        }

        printMatches(args);
    }

    static void printMatches(String[] args) {
        try (Stream<String> classNameStream = Files.lines(Paths.get(args[0]))) {

            String finalWildCard = addWildCards(args[1]);

            classNameStream.filter(e -> isMatch(finalWildCard, e))
                    .map(FullName::new)
                    .sorted()
                    .forEach(System.out::println);

        } catch (Exception exception) {
            System.out.println("./class-finder <filename> '<pattern>'");
        }
    }

    static boolean isMatch(String finalPattern, String word) {
        if (finalPattern.endsWith(" ") && checkIfLastWordIsMatch(finalPattern, word)) {
            return true;
        }
        return isMatchingPattern(finalPattern, word);
    }


    static boolean isMatchingPattern(String finalPattern, String inputWord) {
        Pattern pattern = Pattern.compile(finalPattern);
        Matcher matcher = pattern.matcher(inputWord);

        return matcher.find();
    }

    static String[] splitPatternByCapitalLetters(String pattern) {
        String[] splitPatterns = pattern.toUpperCase().split("");
        if (!isLowerCase(pattern)) {
            return pattern.split("(?<=.)(?=\\p{Lu})"); //regex for splitting with capital letters
        }                                                         //FooBar -> ["Foo", "Bar"]
        return splitPatterns;
    }

    static String addWildCards(String pattern) {
        String replacedWildCard = pattern.replaceAll("[*]", ".");
        StringBuilder stringBuilder = new StringBuilder();
        String[] placeHolderArray = splitPatternByCapitalLetters(replacedWildCard);

        for (int i = 0; i < placeHolderArray.length; i++) {
            if (i != placeHolderArray.length - 1) {
                stringBuilder.append(placeHolderArray[i]).append(".*");
            } else {
                stringBuilder.append(placeHolderArray[i]);
            }
        }
        return stringBuilder.toString();
    }

    static String fetchLastWordInPattern(String word) {
        StringBuilder stringBuilder = new StringBuilder(word.trim());
        String reverseString = stringBuilder.reverse().toString();
        for (char character : reverseString.toCharArray()) {
            if (isUpperCase(character)) {
                return new StringBuilder(reverseString.substring(0, reverseString.indexOf(character) + 1))
                        .reverse()
                        .toString();
            }
        }
        return word;
    }

    static boolean isLowerCase(String word) {
        for (char character : word.toCharArray()) {
            if (!Character.isLowerCase(character)) {
                return false;
            }
        }
        return true;
    }

    static boolean checkIfLastWordIsMatch(String finalPattern, String input) {
        String lastWord = fetchLastWordInPattern(input);
        String[] splitPattern = splitPatternByCapitalLetters(finalPattern.trim());
        return lastWord.contains(splitPattern[splitPattern.length - 1]);
    }
}

