package com.ikueb.advent17;

final class Day9 {

    private Day9() {
        // empty
    }

    static Result process(String input) {
        Result result = new Result();
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
                    if (isGarbageMode) {
                        result.incrementGarbage();
                    }
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
                    result.addGroup(level);
                    level--;
                }
            }
            if (isGarbageMode && !isNegationMode) {
                result.incrementGarbage();
            }
            isNegationMode = false;
        }
        return result;
    }

    static final class Result {
        private int groupCount = 0;
        private int garbageCount = 0;

        void addGroup(int value) {
            groupCount += value;
        }

        void incrementGarbage() {
            garbageCount++;
        }

        int getGroupCount() {
            return groupCount;
        }

        int getGarbageCount() {
            return garbageCount;
        }
    }
}
