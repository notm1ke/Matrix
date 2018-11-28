package co.m1ke.matrix.util;

import java.util.Scanner;
import java.util.function.Function;

public class TransientReader {

    public static String read(Scanner scanner, String prompt) {
        return read(scanner, prompt, (s) -> s);
    }

    public static <T> T read(Scanner scanner, String prompt, Function<String, T> converter) {
        if(prompt != null) System.out.println(prompt);
        System.out.print("> ");
        try {
            return converter.apply(scanner.nextLine());
        } catch (Exception ex) {
            String message = "Unparseable parameters";
            if(ex.getClass().isAssignableFrom(InvalidInput.class)) {
                final String invalidMessage = ex.getMessage();
                if (!invalidMessage.equals("")) message = invalidMessage;
            }
            System.out.println(message);

            return read(scanner, null, converter);
        }
    }

    public static class InvalidInput extends RuntimeException {

        public InvalidInput() {
            this("");
        }

        public InvalidInput(String message) {
            super(message);
        }

    }

}
