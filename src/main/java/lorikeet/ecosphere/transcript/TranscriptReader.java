package lorikeet.ecosphere.transcript;

import lorikeet.Err;

public class TranscriptReader {

    public void parseLine(String line) {

    }

    Extract<TranscriptNode> parseNode(String line) {
        final Extract<String> className = extractNextIdentifier(line, jumpWhitesapce(line, 0));
        if (className.getError() != null) {
            return new Extract<>(className.getError());
        }
        while (true) {
            final int start = jumpWhitesapce(line, className.getContinuation());
            final char character = line.charAt(start);
            if (character == '>') {
                return null; // done
            }
            if (isAllowedAtStartOfName(character)) {
                return null; // error
            }
            final Extract<String> identifier = extractNextIdentifier(line, start);
            if (identifier.getError() != null) {
                return new Extract<>(identifier.getError());
            }
            final int equalsPos = identifier.getContinuation();
            if (line.charAt(equalsPos) != '=') {
                return null; // error
            }
            // extract value
        }
    }

    public static Extract<String> extractNextIdentifier(String line, int start) {
        StringBuilder className = new StringBuilder();
        for (int i = start; i < line.length(); i++) {
            final char character = line.charAt(i);
            if (isAllowedInName(character)) {
                className.append(character);
                continue;
            }
            return validateName(className.toString(), i);
        }
        return validateName(className.toString(), line.length());
    }

    static boolean isAllowedInName(char character) {
        return Character.isLetterOrDigit(character) || character == '.' || character == '_';
    }

    static boolean isAllowedAtStartOfName(char character) {
        return Character.isLetter(character) || character == '_';
    }

    static Extract<String> validateName(String className, int currentIndex) {
        if (className.trim().isEmpty()) {
            return new Extract<>(new RuntimeException());
        }
        if (className.startsWith(".") || className.endsWith(".")) {
            return new Extract<>(new RuntimeException());
        }
        return new Extract<>(className, currentIndex);
    }

    static int jumpWhitesapce(String line, int start) {
        for (int i = start; i < line.length(); i++) {
            final char character = line.charAt(i);
            if (!Character.isWhitespace(character)) {
                return i;
            }
        }
        return line.length();
    }

}
