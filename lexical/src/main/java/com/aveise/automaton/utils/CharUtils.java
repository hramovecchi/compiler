package com.aveise.automaton.utils;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Character utilities are used to simplify the way to add a set of characters
 * in the automaton. The user can invoke to these methods to add a set of data
 * as characters. For instance, the <i>alphabetics</i> class variable retrieve a
 * collection of all the characters which conform the English alphabet is lower
 * case and upper case. The characters utility class builds the functionality in
 * an efficient way avoiding the call the method more than once by program
 * execution.
 * 
 * @author pmvillafane
 */
public class CharUtils {

    /**
     * Retrieve all the ASCII characters according to the default charset.
     */
    public static Collection<Character> anything = anything();

    /**
     * Retrieve all the numbers from 0 to 9.
     */
    public static Collection<Character> number = number();

    /**
     * Retrieve all the characters which conform the English alphabet in upper
     * case.
     */
    public static Collection<Character> alphabetUpperCase = alphabetUpperCase();

    /**
     * Retrieve all the characters which conform the English alphabet in lower
     * case.
     */
    public static Collection<Character> alphabetLowerCase = alphabetLowerCase();

    /**
     * Retrieve all the characters which conform the English alphabet in lower
     * case and upper case.
     */
    public static Collection<Character> alphabet = alphabet();

    /**
     * Retrieve all the characters which conform the English alphabet and the
     * numbers from 0 to 9 in upper case.
     */
    public static Collection<Character> alphanumericUpperCase =
            alphanumericUpperCase();

    /**
     * Retrieve all the characters which conform the English alphabet and the
     * numbers from 0 to 9 in lower case.
     */
    public static Collection<Character> alphanumericLowerCase =
            alphanumericLowerCase();

    /**
     * Retrieve all the characters which conform the English alphabet and the
     * numbers from 0 to 9 in lower case and upper case.
     */
    public static Collection<Character> alphanumeric = alphanumeric();

    /**
     * Retrieve all the characters that are non numerics.
     */
    public static Collection<Character> nonNumber = nonNumber();

    /**
     * Retrieve all the characters that are non alphabetics in upper case.
     */
    public static Collection<Character> nonAlphabetUpperCase =
            nonAlphabetUpperCase();

    /**
     * Retrieve all the characters that are non alphabetics in lower case.
     */
    public static Collection<Character> nonAlphabetLowerCase =
            nonAlphabetLowerCase();

    /**
     * Retrieve all the characters that are non alphabetics in lower case and
     * upper case.
     */
    public static Collection<Character> nonAlphabet = nonAlphabet();

    /**
     * Retrieve all the characters that are non alphabetics in upper case and
     * non numbers from 0 to 9.
     */
    public static Collection<Character> nonAlphanumericUpperCase =
            nonAlphanumericUpperCase();

    /**
     * Retrieve all the characters that are non alphabetics in lower case and
     * non numbers from 0 to 9.
     */
    public static Collection<Character> nonAlphanumericLowerCase =
            nonAlphanumericLowerCase();

    /**
     * Retrieve all the characters that are non alphabetics in lower case and
     * upper case and non numbers from 0 to 9.
     */
    public static Collection<Character> nonAlphanumeric = nonAlphanumeric();

    /**
     * Retrieve separators characters which are \n, \r, \t and whitespace.
     */
    public static Collection<Character> separator = separator();

    /**
     * Retrieve the comma character.
     */
    public static Collection<Character> comma = comma();

    /**
     * Retrieve the semicolon character.
     */
    public static Collection<Character> semicolon = semicolon();

    /**
     * Retrieve the whitespace character.
     */
    public static Collection<Character> whitespace = whitespace();

    /**
     * Retrieve the newline character.
     */
    public static Collection<Character> newline = newline();

    /**
     * Retrieve the tab character.
     */
    public static Collection<Character> tab = tab();

    /**
     * Retrieve the carriage return.
     */
    public static Collection<Character> carriageReturn = carriageReturn();

    private static Collection<Character> anything() {

        final List<Character> ascii = new ArrayList<Character>();
        final Charset charset = Charset.defaultCharset();
        final CharsetEncoder charsetEncoder = charset.newEncoder();

        for (char c = 0; c < Character.MAX_VALUE; c++) {
            if (charsetEncoder.canEncode(c)) {
                ascii.add(c);
            }
        }

        return ascii;
    }

    private static Collection<Character> number() {
        final List<Character> numbers = new ArrayList<Character>();
        for (char number = '0'; number <= '9'; number++) {
            numbers.add(number);
        }
        return numbers;
    }

    private static Collection<Character> alphabetUpperCase() {
        final List<Character> alphabet = new ArrayList<Character>();
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            alphabet.add(letter);
        }
        return alphabet;
    }

    private static Collection<Character> alphabetLowerCase() {
        final List<Character> alphabet = new ArrayList<Character>();
        for (char letter = 'a'; letter <= 'z'; letter++) {
            alphabet.add(letter);
        }
        return alphabet;
    }

    private static Collection<Character> alphabet() {
        final List<Character> alphabet = new ArrayList<Character>();
        alphabet.addAll(alphabetUpperCase);
        alphabet.addAll(alphabetLowerCase);
        return alphabet;
    }

    private static Collection<Character> alphanumericUpperCase() {
        final List<Character> alphanumeric = new ArrayList<Character>();
        alphanumeric.addAll(number);
        alphanumeric.addAll(alphabetUpperCase);
        return alphanumeric;
    }

    private static Collection<Character> alphanumericLowerCase() {
        final List<Character> alphanumeric = new ArrayList<Character>();
        alphanumeric.addAll(number);
        alphanumeric.addAll(alphabetLowerCase);
        return alphanumeric;
    }

    private static Collection<Character> alphanumeric() {
        final List<Character> alphanumeric = new ArrayList<Character>();
        alphanumeric.addAll(number);
        alphanumeric.addAll(alphabet);
        return alphanumeric;
    }

    private static Collection<Character> nonNumber() {
        final List<Character> nonNumbers = new ArrayList<Character>();
        for (Character ch : anything) {
            if (!Character.isDigit(ch)) {
                nonNumbers.add(ch);
            }
        }
        return nonNumbers;
    }

    private static Collection<Character> nonAlphabetUpperCase() {
        final List<Character> nonAlphabeticUC = new ArrayList<Character>();
        for (Character ch : anything) {
            if (!Character.isAlphabetic(ch) || Character.isLowerCase(ch)) {
                nonAlphabeticUC.add(ch);
            }
        }
        return nonAlphabeticUC;
    }

    private static Collection<Character> nonAlphabetLowerCase() {
        final List<Character> nonAlphabeticLC = new ArrayList<Character>();
        for (Character ch : anything) {
            if (!Character.isAlphabetic(ch) || Character.isUpperCase(ch)) {
                nonAlphabeticLC.add(ch);
            }
        }
        return nonAlphabeticLC;
    }

    private static Collection<Character> nonAlphabet() {
        final List<Character> nonAlphabetic = new ArrayList<Character>();
        for (Character ch : anything) {
            if (!Character.isAlphabetic(ch)) {
                nonAlphabetic.add(ch);
            }
        }
        return nonAlphabetic;
    }

    private static Collection<Character> nonAlphanumericUpperCase() {
        final List<Character> nonAlphanumericUC = new ArrayList<Character>();
        for (Character ch : anything) {
            if ((!Character.isAlphabetic(ch) || Character.isLowerCase(ch))
                    && !Character.isDigit(ch)) {
                nonAlphanumericUC.add(ch);
            }
        }
        return nonAlphanumericUC;
    }

    private static Collection<Character> nonAlphanumericLowerCase() {
        final List<Character> nonAlphanumericLC = new ArrayList<Character>();
        for (Character ch : anything) {
            if ((!Character.isAlphabetic(ch) || Character.isUpperCase(ch))
                    && !Character.isDigit(ch)) {
                nonAlphanumericLC.add(ch);
            }
        }
        return nonAlphanumericLC;
    }

    private static Collection<Character> nonAlphanumeric() {
        final List<Character> nonAlphanumeric = new ArrayList<Character>();
        for (Character ch : anything) {
            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) {
                nonAlphanumeric.add(ch);
            }
        }
        return nonAlphanumeric;
    }

    private static Collection<Character> separator() {
        return Arrays.asList(' ', '\t', '\n', '\r');
    }

    private static Collection<Character> comma() {
        return Arrays.asList(',');
    }

    private static Collection<Character> semicolon() {
        return Arrays.asList(';');
    }

    private static Collection<Character> whitespace() {
        return Arrays.asList(' ');
    }

    private static Collection<Character> newline() {
        return Arrays.asList('\n');
    }

    private static Collection<Character> tab() {
        return Arrays.asList('\t');
    }

    private static Collection<Character> carriageReturn() {
        return Arrays.asList('\r');
    }

    public static Collection<Character> inputs(final Character... characters) {
        return Arrays.asList(characters);
    }

}
