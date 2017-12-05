package com.ikueb.advent17;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

class Day4 {

    private static final Pattern SPLITTER = Pattern.compile("\\s+");

    static boolean isValid(String passphrase) {
        return isValid(passphrase, UnaryOperator.identity());
    }

    static boolean isValid(String passphrase, UnaryOperator<String> mapper) {
        Set<String> seen = new HashSet<>();
        return SPLITTER.splitAsStream(passphrase).map(mapper).allMatch(seen::add);
    }

    static String reform(String input) {
        char[] temp = input.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }
}
