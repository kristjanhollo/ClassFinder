
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import util.FullName;

import static java.lang.Character.isUpperCase;

public class ClassFinder {
    public static void main(String... args) {

        if (args.length <= 1 || args[1].startsWith(" ")) {
            System.out.println("Error! '<pattern>' is needed for search");
            System.exit(0);
        }

        printMatches(args);
    }

    static void printMatches(String[] args) {
        try (Stream<String> classNameStream = Files.lines(Paths.get(args[0]))) {

            String patternWithWildCard = replaceWildCards(args[1]);

            classNameStream.filter(className -> isMatch(patternWithWildCard, className))
                    .map(FullName::new)
                    .sorted()
                    .forEach(System.out::println);

        } catch (IOException exception) {
            System.out.println("./class-finder <filename> '<pattern>'");
        }
    }

    static String replaceWildCards(String pattern) {
        StringBuilder stringBuilder = new StringBuilder();
        String patternWithWildCard = pattern.replaceAll("[*]", ".");
        String[] splitPattern = splitByCapitalLetters(patternWithWildCard);

        for (int i = 0; i < splitPattern.length; i++) {
            if (i != splitPattern.length - 1) {
                stringBuilder.append(splitPattern[i]).append(".*");
            } else {
                stringBuilder.append(splitPattern[i]);
            }
        }
        return stringBuilder.toString();
    }

    static String[] splitByCapitalLetters(String pattern) {
        if (isAllInLowerCase(pattern)) {
            return pattern.toUpperCase().split("");
        }
        // regex for splitting with capital letters
        // FooBar -> ["Foo", "Bar"]
        return pattern.split("(?<=.)(?=\\p{Lu})");
    }

    static boolean isMatch(String patternWithWildCard, String className) {
        if (patternWithWildCard.endsWith(" ") && isLastWordMatch(patternWithWildCard, className)) {
            return isMatchingPattern(patternWithWildCard.trim(), className);
        }
        return isMatchingPattern(patternWithWildCard, className);
    }

    static boolean isLastWordMatch(String pattern, String className) {
        String lastWord = fetchLastWordOf(className);
        String[] splitPattern = splitByCapitalLetters(pattern.trim());
        return lastWord.contains(splitPattern[splitPattern.length - 1]);
    }

    static String fetchLastWordOf(String text) {
        StringBuilder stringBuilder = new StringBuilder(text.trim());
        String reverseString = stringBuilder.reverse().toString();
        for (char character : reverseString.toCharArray()) {
            if (isUpperCase(character)) {
                return new StringBuilder(reverseString.substring(0, reverseString.indexOf(character) + 1))
                        .reverse()
                        .toString();
            }
        }
        return text;
    }

    static boolean isMatchingPattern(String finalPattern, String inputWord) {
        Pattern pattern = Pattern.compile(finalPattern);
        Matcher matcher = pattern.matcher(inputWord);

        return matcher.find();
    }

    static boolean isAllInLowerCase(String word) {
        for (char character : word.toCharArray()) {
            if (Character.isUpperCase(character)) {
                return false;
            }
        }
        return true;
    }


}
