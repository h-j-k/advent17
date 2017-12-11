package com.ikueb.advent17;

final class Day9 {

    private Day9() {
        // empty
    }

    static Result process(String input) {
        Result result = new Result();
        int level = 0;
        boolean isGarbageMode = false;
        char current;
        for (int i = 0; i < input.length(); i += (1 + (current == '!' ? 1 : 0))) {
            current = input.charAt(i);
            if (current == '!') {
                continue;
            }
            if (isGarbageMode && current != '>') {
                result.incrementGarbage();
            } else if (current == '<') {
                isGarbageMode = true;
            } else if (current == '>') {
                isGarbageMode = false;
            } else {
                if (current == '{') {
                    level++;
                } else if (current == '}') {
                    result.addGroup(level);
                    level--;
                }
            }
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
