import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClassFinderTest {


    @Test
    void testPrintMatchesNoArgsShouldThrowException() {
        // given
        String[] args = new String[] {};

        // when and then
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> ClassFinder.printMatches(args));
    }

    @Test
    void testReplaceWildCardsNoWildCardPatternShouldReturnPattern() {
        // given
        String pattern = "Pattern";

        // when
        String actualReplaceWildCardsResult = ClassFinder.replaceWildCards(pattern);

        // then
        assertEquals("Pattern", actualReplaceWildCardsResult);
    }

    @Test
    void testReplaceWildCardsAllLowerCasePatternShouldReturnAllUpperCaseWithWildCard() {
        // given
        String pattern = "foo";

        // when
        String actualReplaceWildCardsResult = ClassFinder.replaceWildCards(pattern);

        // then
        assertEquals("F.*O.*O", actualReplaceWildCardsResult);
    }

    @Test
    void testReplaceWildCardsShouldReplaceWildcardWithDots() {
        // given
        String pattern = "B*r";

        // when
        String actualReplaceWildCardsResult = ClassFinder.replaceWildCards(pattern);

        // then
        assertEquals("B.r", actualReplaceWildCardsResult);
    }

    @Test
    void testReplaceWildCardsWithStarOnlyPatternShouldReplaceWildcardWithDots() {
        // given
        String pattern = ".*";

        // when
        String actualReplaceWildCardsResult = ClassFinder.replaceWildCards(pattern);

        // then
        assertEquals("..*.", actualReplaceWildCardsResult);
    }

    @Test
    void testSplitByCapitalLettersShouldReturnSplitWords() {
        // given
        String pattern = "FooBar";

        // when
        String[] actualSplitByCapitalLettersResult = ClassFinder.splitByCapitalLetters(pattern);

        // then
        assertEquals(2, actualSplitByCapitalLettersResult.length);
        assertEquals("Foo", actualSplitByCapitalLettersResult[0]);
        assertEquals("Bar", actualSplitByCapitalLettersResult[1]);
    }

    @Test
    void testSplitByCapitalLettersWithAllLowerCasePatternShouldReturnAllLettersInCapital() {
        // given
        String pattern = "foo";

        // when
        String[] actualSplitByCapitalLettersResult = ClassFinder.splitByCapitalLetters(pattern);

        // then
        assertEquals(3, actualSplitByCapitalLettersResult.length);
        assertEquals("F", actualSplitByCapitalLettersResult[0]);
        assertEquals("O", actualSplitByCapitalLettersResult[1]);
        assertEquals("O", actualSplitByCapitalLettersResult[2]);
    }

    @Test
    void testIsMatchWithNonMatchingPattern() {
        // given
        String patternWithWildCard = "Pattern With Wild Card.";
        String className = "Class Name";

        // when
        boolean actualIsMatchResult = ClassFinder.isMatch(patternWithWildCard, className);

        // then
        assertFalse(actualIsMatchResult);
    }

    @Test
    void testIsMatchWithMatchingPattern() {
        // given
        String patternWithWildCard = "FooB.*B.*";
        String className = "FooBarBaz";

        // when
        boolean actualIsMatchResult = ClassFinder.isMatch(patternWithWildCard, className);

        // then
        assertTrue(actualIsMatchResult);
    }

    @Test
    void testIsMatchWithSpacedPattern() {
        // given
        String patternWithWildCard = "F.*Ba ";
        String className = "FooBar";

        // when
        boolean actualIsMatchResult = ClassFinder.isMatch(patternWithWildCard, className);

        // then
        assertTrue(actualIsMatchResult);
    }

    @Test
    void testIsLastWordMatchShouldPassWithMatchingPattern() {
        // given
        String pattern = "FooB";
        String className = "FooBar";

        // when
        boolean actualIsLastWordMatchResult = ClassFinder.isLastWordMatch(pattern, className);

        // then
        assertTrue(actualIsLastWordMatchResult);
    }

    @Test
    void testIsLastWordMatchShouldFailWithNonMatchingPattern() {
        // given
        String pattern = "FooZoo";
        String className = "FooBar";

        // when
        boolean actualIsLastWordMatchResult = ClassFinder.isLastWordMatch(pattern, className);

        // then
        assertFalse(actualIsLastWordMatchResult);
    }

    @Test
    void testFetchLastWordOf() {
        // given
        String text = "FooBarBaz";

        // when
        String actualFetchLastWordOfResult = ClassFinder.fetchLastWordOf(text);

        // then
        assertEquals("Baz", actualFetchLastWordOfResult);
    }

    @Test
    void testIsMatchingPatternShouldPassWithMatchingPattern() {
        // given
        String finalPattern = "F.*B.*";
        String inputWord = "FooBarBaz";

        // when
        boolean actualIsMatchingPatternResult = ClassFinder.isMatchingPattern(finalPattern, inputWord);

        // then
        assertTrue(actualIsMatchingPatternResult);
    }

    @Test
    void testIsMatchingPatternShouldFailWithNonMatchingPattern() {
        // given
        String finalPattern = "F.*B";
        String inputWord = "BarBaz";

        // when
        boolean actualIsMatchingPatternResult = ClassFinder.isMatchingPattern(finalPattern, inputWord);

        // then
        assertFalse(actualIsMatchingPatternResult);
    }

    @Test
    void testIsAllInLowerCaseShouldPassForAllLowerCasePattern() {
        // given
        String word = "foobar";

        // when
        boolean actualIsAllInLowerCaseResult = ClassFinder.isAllInLowerCase(word);

        // then
        assertTrue(actualIsAllInLowerCaseResult);
    }

    @Test
    void testIsAllInLowerCaseShouldFailForNonLowerCasePattern() {
        // given
        String word = "FooBar";

        // when
        boolean actualIsAllInLowerCaseResult = ClassFinder.isAllInLowerCase(word);

        // then
        assertFalse(actualIsAllInLowerCaseResult);
    }
}
