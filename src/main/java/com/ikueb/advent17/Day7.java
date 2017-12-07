package com.ikueb.advent17;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class Day7 {

    private Day7() {
        // empty
    }

    static String getBottomProgram(Collection<String> programs) {
        return null;
    }

    private static final class Program {

        private Program parent = null;
        private final int weight;
        private final Set<String> childrenNames;
        private final Set<Program> children = new HashSet<>();

        Program(int weight, Set<String> childrenNames) {
            this.weight = weight;
            this.childrenNames = childrenNames;
        }

        void setParent(Program parent) {
            this.parent = parent;
        }

        void setChildren(Map<String, Program> map) {
            childrenNames.stream().map(map::get).forEach(children::add);
        }
    }
}
