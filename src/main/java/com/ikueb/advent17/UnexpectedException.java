package com.ikueb.advent17;

final class UnexpectedException extends RuntimeException {

    UnexpectedException(String message) {
        super("Expecting a " + message + ".");
    }
}
