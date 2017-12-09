package com.ikueb.advent17;

final class Day9 {

    private Day9() {
        // empty
    }

    static int getGroupCount(String input) {
        int result = 0;
        int level = 0;
        boolean isGarbageMode = false;
        boolean isNegationMode = false;
        for (char current : input.toCharArray()) {
            if (current == '!' && !isNegationMode) {
                isNegationMode = true;
                continue;
            }
            if (!isNegationMode) {
                if (current == '<') {
                    isGarbageMode = true;
                    continue;
                } else if (current == '>') {
                    isGarbageMode = false;
                    continue;
                }
            }
            if (!isGarbageMode) {
                if (current == '{') {
                    level++;
                } else if (current == '}') {
                    result += level;
                    level--;
                }
            }
            isNegationMode = false;
        }
        return result;
    }
}
