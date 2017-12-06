package com.ikueb.advent17;

final class UnexpectedOutcomeException extends RuntimeException {

    UnexpectedOutcomeException() {
        super("Expecting a result");
    }

    UnexpectedOutcomeException(String message) {
        super(message);
    }
}
