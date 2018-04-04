package com.ikueb.advent17;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

final class Day04 {

    private static final Pattern SPLITTER = Pattern.compile("\\s+");

    private Day04() {
        // empty
    }

    static boolean isValid(String passphrase) {
        return isValid(passphrase, UnaryOperator.identity());
    }

    static boolean isValid(String passphrase, UnaryOperator<String> mapper) {
        var seen = new HashSet<String>();
        return SPLITTER.splitAsStream(passphrase).map(mapper).allMatch(seen::add);
    }

    static String reform(String input) {
        var temp = input.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }
}
