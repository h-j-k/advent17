package com.ikueb.advent17;

final class Day9 {

    private Day9() {
        // empty
    }

    static int getGroupCount(String input) {
        int result = 0;
        int level = 0;
        for (char current : input.toCharArray()) {
            if (current == '{') {
                level++;
            } else if (current == '}') {
                result += level;
                level--;
            }
        }
        return result;
    }
}
