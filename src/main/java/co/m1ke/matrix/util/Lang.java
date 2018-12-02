package co.m1ke.matrix.util;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lang {

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BACKGROUND_BLACK	= "\u001B[40m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String BACKGROUND_GREEN	= "\u001B[42m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";
    public static final String BACKGROUND_BLUE = "\u001B[44m";
    public static final String BACKGROUND_MAGENTA = "\u001B[45m";
    public static final String BACKGROUND_CYAN = "\u001B[46m";
    public static final String BACKGROUND_WHITE = "\u001B[47m";

    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[5m";
    public static final String RAPID_BLINK = "\u001B[6m";
    public static final String REVERSE_VIDEO = "\u001B[7m";
    public static final String INVISIBLE_TEXT = "\u001B[8m";

    public static final String SANE	= "\u001B[0m";

    public static final String HIGH_INTENSITY = "\u001B[1m";
    public static final String LOW_INTENSITY = "\u001B[2m";

    public static final String MODULE = RESET + "%s";
    public static final String HELP = RESET + "Invalid usage: %s";

    public static final String DIVIDER = Lang.WHITE + "-------------------------------------------------" + Lang.RESET;

    public static String generate(String head, String body) {
        return String.format(MODULE, body);
    }

    public static String usage(String head, String body) {
        return String.format(HELP, body);
    }

    public static String getColorForElaspedTime(long ms) {
        if (ms >= 0 && ms <= 200) {
            return GREEN;
        }
        if (ms >= 201 && ms <= 400) {
            return YELLOW;
        }
        if (ms >= 401) {
            return RED;
        }
        return PURPLE;
    }

    public static String generateSharedKey(int length) {
        Random ran = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i<length - 1; i++) {
            ran.setSeed(System.currentTimeMillis());
            builder.append(ran.nextInt(9));
        }
        return CipherUtils.base64Encode(builder.toString());
    }

    public static String capitalizeFirst(String original) {
        if (original == null) {
            return null;
        }
        String n = original.toLowerCase();
        if (n.length() == 0) {
            return original;
        }
        return n.substring(0, 1).toUpperCase() + n.substring(1);
    }

    public static String stripLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public static String stripChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }

    public static int countWords(String input) {
        int wordCount = 0;

        boolean word = false;
        int endOfLine = input.length() - 1;

        for (int i = 0; i < input.length(); i++) {
            if (Character.isLetter(input.charAt(i)) && i != endOfLine) {
                word = true;
            } else if (!Character.isLetter(input.charAt(i)) && word) {
                wordCount++;
                word = false;
            } else if (Character.isLetter(input.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    public static String removeLastDecimal(String s) {
        return s.substring(0, s.lastIndexOf("."));
    }

    /**
     * Converts times such as "10m5s" to 605
     * @param input
     * @return
     */
    public static long formatTime(final String input) {
        if (input == null || input.isEmpty()) {
            return -1L;
        }
        long result = 0L;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                final String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static long convert(final int value, final char unit) {
        switch (unit) {
            case 'y': {
                return value * TimeUnit.DAYS.toSeconds(365L);
            }
            case 'M': {
                return value * TimeUnit.DAYS.toSeconds(30L);
            }
            case 'd': {
                return value * TimeUnit.DAYS.toSeconds(1L);
            }
            case 'h': {
                return value * TimeUnit.HOURS.toSeconds(1L);
            }
            case 'm': {
                return value * TimeUnit.MINUTES.toSeconds(1L);
            }
            case 's': {
                return value * TimeUnit.SECONDS.toSeconds(1L);
            }
            default: {
                return -1L;
            }
        }
    }

    /**
     * Converts times such as "10m5s" to 605
     * Same thing as stringToTime()?
     * @param time
     * @return Long
     */
    public static Long formatStringToLong(String time) {
        Map<String, Long> times = new HashMap<>();
        times.put("y", 365L*24*60*60); // Or 366 days?
        times.put("m", 31L*24*60*60); // Or 30 days?
        times.put("w", 7L*24*60*60);
        times.put("d", 24L*60*60);
        times.put("h", 60L*60);

        long sum = 0L;

        Matcher m = Pattern.compile("(\\d+)([A-Za-z]+)").matcher(time);
        while (m.find()) {
            String type = m.group(2);
            String multiplier = m.group(1);
            sum += times.get(type) * Integer.parseInt(multiplier);
        }
        return sum;
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<Long, String>() {
        {
            put(1_000L, "k");
            put(1_000_000L, "M");
            put(1_000_000_000L, "G");
            put(1_000_000_000_000L, "T");
            put(1_000_000_000_000_000L, "P");
            put(1_000_000_000_000_000_000L, "E");
        }
    };

    public static String formatNumber(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatNumber(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatNumber(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

}
