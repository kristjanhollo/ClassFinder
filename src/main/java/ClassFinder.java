
import util.FullName;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.stream.Stream;

import static java.lang.Character.isUpperCase;

public class ClassFinder {
    public static void main(String... args) {

        try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {

            String finalWildCard = addAddWildCards(args[1]);

            stream.filter(e -> matchList(finalWildCard, e)).
                    map(FullName::new).sorted().
                    forEach(System.out::println);


        } catch (Exception e) {
            System.out.println("Wrong input: " + e.getMessage());
        }
    }

    static boolean matchList(String finalPattern, String fileInput) {
        if (finalPattern.endsWith(" ") && matchingPatternAndLastWord(finalPattern, fileInput)) {
            return true;
        }
        return matchingPattern(finalPattern, fileInput);
    }

    static boolean matchingPatternAndLastWord(String finalPattern, String inputWord) {
        Pattern pattern = Pattern.compile(finalPattern.trim());
        Matcher matcher = pattern.matcher(inputWord);
        return matcher.find() && checkIfLastWordInMatch(finalPattern, inputWord);
    }

    static boolean matchingPattern(String finalPattern, String inputWord) {
        Pattern pattern = Pattern.compile(finalPattern);
        Matcher matcher = pattern.matcher(inputWord);
        return matcher.find();
    }

    static String[] splitPatternByCapitalLetters(String patternInput) {
        String[] list = patternInput.toUpperCase().split("");
        if (!isLowerCase(patternInput)) {
            return patternInput.split("(?<=.)(?=\\p{Lu})"); //regex for splitting with capital letters
        }                                                         //FooBar -> ["Foo", "Bar"]
        return list;
    }

    static String addAddWildCards(String pattern) {
        String placeHolder = pattern.replaceAll("[*]", ".");
        StringBuilder stringBuilder = new StringBuilder();
        String[] placeHolderArray = splitPatternByCapitalLetters(placeHolder);
        for (int i = 0; i < placeHolderArray.length; i++) {
            if (i != placeHolderArray.length - 1) {
                stringBuilder.append(placeHolderArray[i]).append(".*");
            } else {
                stringBuilder.append(placeHolderArray[i]);
            }
        }
        return stringBuilder.toString();
    }

    static String fetchLastWordInPattern(String template) {
        StringBuilder sb = new StringBuilder(template.trim());
        String reverseStr = sb.reverse().toString();
        for (char ch : reverseStr.toCharArray()) {
            if (isUpperCase(ch)) {
                return new StringBuilder(reverseStr.substring(0, reverseStr.indexOf(ch) + 1))
                        .reverse().toString();
            }
        }
        return template;
    }

    static boolean isLowerCase(String template) {
        for (char ch : template.toCharArray()) {
            if (!Character.isLowerCase(ch)) {
                return false;
            }
        }
        return true;
    }

    static boolean checkIfLastWordInMatch(String finalRegex, String input) {
        String lastWord = fetchLastWordInPattern(input);
        String[] splitPattern = splitPatternByCapitalLetters(finalRegex.trim());
        return lastWord.contains(splitPattern[splitPattern.length - 1]);
    }
}

