package com.dojo.guava.strings;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.google.common.base.CharMatcher;

public class CharMatcherShould {

    @Test
    public void removeWhitespace() {
        CharMatcher whitespace = CharMatcher.WHITESPACE;
        
        String mysentence = "aaa aa aaaa   a ";
        assertThat(whitespace.removeFrom(mysentence)).isEqualTo("aaaaaaaaaa");
    }
    
    @Test
    public void matchesCharacters() {
        CharMatcher charMatcher = CharMatcher.anyOf("@.");
        
        assertThat(charMatcher.matchesAllOf("@.")).isTrue();
        assertThat(charMatcher.matchesAllOf("@@@@")).isTrue();
        assertThat(charMatcher.matchesAllOf("@test@@")).isFalse();
        
        assertThat(charMatcher.matchesAnyOf("test@test")).isTrue();
        assertThat(charMatcher.matchesAnyOf("..@")).isTrue();
        
        assertThat(charMatcher.matchesNoneOf("test@test")).isFalse();
        assertThat(charMatcher.matchesNoneOf("test")).isTrue();
    }
    
    @Test
    public void removeAllLowerCaseFromString () {
        
        String allButLowerCase = CharMatcher.JAVA_LOWER_CASE
                .negate()
                .retainFrom("PoUR l exemple C est cHouette");
        
        assertThat("PUR   C  H").isEqualTo(allButLowerCase);
    }
    
    @Test
    public void trimLeadingSpacesFromString () {
        
        String leftTrimmedString = CharMatcher.WHITESPACE
                .trimLeadingFrom("       Some String       ");
        
        assertThat("Some String       ").isEqualTo(leftTrimmedString);
    }
    
    @Test
    public void trimAllSpacesFromString () {
        
        String trimmedString = CharMatcher.WHITESPACE
                .trimFrom("       Some String       ");
        
        assertThat("Some String").isEqualTo(trimmedString);
    }
    
    @Test
    public void removeAllAsteriskFromString () {
        
        String stringWithoutAstricks = CharMatcher.is('*')
                .removeFrom("(* This is a comment.  The compiler will ignore it. *)");
        
        assertThat("( This is a comment.  The compiler will ignore it. )").isEqualTo(stringWithoutAstricks);
    }
    
    @Test
    public void gradeWithinPassingRange () {
        
        CharMatcher failGradeMatcher = CharMatcher
                .inRange('A', 'C');
        
        assertThat(failGradeMatcher.matches('F')).isFalse();
        assertThat(failGradeMatcher.matches('C')).isTrue();
    }
    
    @Test
    public void obtainDigitsFromTelephoneNumber () {
        
        String telephoneNumber = CharMatcher
                .inRange('0','9')
                .retainFrom("06 44-45-44 46");
        
        assertThat("0644454446").isEqualTo(telephoneNumber);
        
        // worried about performance
        CharMatcher digits = CharMatcher
                .inRange('0','9')
                .precomputed();
        
        String teleNumber = digits.retainFrom("06 44-45-44 46");
        assertThat("0644454446").isEqualTo(teleNumber);
    }
    
    @Test
    public void countNumberOfMatchingChars () {
        
        int numberOfDigits = CharMatcher.DIGIT.countIn("123-Try2Count");
        
        assertThat(4).isEqualTo(numberOfDigits);
    }
    
    @Test
    public void collapseWhitespaceDash () {

        String address = "505 Williams  Street";

        String addressWithDash = CharMatcher.WHITESPACE.collapseFrom(address, '-');
        
        assertThat("505-Williams-Street").isEqualTo(addressWithDash);
    }
}
