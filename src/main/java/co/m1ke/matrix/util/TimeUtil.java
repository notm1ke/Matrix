package co.m1ke.matrix.util;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("M/d/Y HH:mm:ss a");
	private static DateFormat DATE_DAY_FORMAT = new SimpleDateFormat("MMM d, YYYY");

	public static final long DAY = 24 * 60 * 60 * 1000;

	public static String format(DateFormat format, Long input) {
		return format.format(input);
	}

	public static String format(Long input) {
		return DATE_FORMAT.format(input);
	}

	public static String formatDayDate(Long input) {
		return DATE_DAY_FORMAT.format(input);
	}

	public static String getTime(long time) {
		int seconds = (int)(time / 1000L) % 60;
		int minutes = (int)(time / 60000L % 60L);
		int hours = (int)(time / 3600000L % 24L);
		int day = (int)(time / 86400000L % 30.4368D);
		int month = (int)(time / 2.6297424E9D % 12.0D);
		int year = (int)(time / 3.15569088E10D);

		String y = year + " year" + numberEnding(year);
		String mo = month  + " month" + numberEnding(month);
		String d = day + " day" + numberEnding(day);
		String h = hours + " hour" + numberEnding(hours);
		String m = minutes + " minute" + numberEnding(minutes);
		String s = seconds + " second" + numberEnding(seconds);

		return y + ", " + mo + ", " + d + ", " + h + ", " + m + ", " + s;
	}

	public static String numberEnding(int i) {
		return i == 1 ? "":"s";
	}

	public static String fancyConvert(long time, String colorCode) {
		int second = (int)(time / 1000L) % 60;
		int minutes = (int)(time / 60000L % 60L);
		int hours = (int)(time / 3600000L % 24L);
		int days = (int)(time / 86400000L % 30.4368D);
		int month = (int)(time / 2.6297424E9D % 12.0D);
		int year = (int)(time / 3.15569088E10D);

		return year + " year(s), " + month + " month(s), " + days + " day(s), \n " + "&" + colorCode + hours + " hour(s), " + minutes
				+ " minute(s), \n " + "&" + colorCode + second + " second(s)";
	}

	public static String getLatestTimeValue(long time) {
		int second = (int)(time / 1000L) % 60;
		int minutes = (int)(time / 60000L % 60L);
		int hours = (int)(time / 3600000L % 24L);
		int days = (int)(time / 86400000L % 30.4368D);
		int month = (int)(time / 2.6297424E9D % 12.0D);
		int year = (int)(time / 3.15569088E10D);

		String y = year + " year" + numberEnding(year);
		String mo = month + " month" + numberEnding(month);
		String d = days + " day" + numberEnding(days);
		String h = hours + " hour" + numberEnding(hours);
		String m = minutes + " minute" + numberEnding(minutes);
		String s = second + " second" + numberEnding(second);

		StringBuilder sb = new StringBuilder();
		if (year != 0) {
			sb.append(y + ", ");
		}
		if (month != 0) {
			sb.append(mo + ", ");
		}
		if (days != 0) {
			sb.append(d + ", ");
		}
		if (hours != 0) {
			sb.append(h + ", ");
		}
		if (minutes != 0) {
			sb.append(m + ", ");
		}
		sb = new StringBuilder(sb.substring(0, Math.max(0, sb.length() - 2)));
		if ((year != 0 || month != 0 || days != 0 || minutes != 0 || hours != 0) && second != 0) {
			sb.append(", " + s);
		}
		if (year == 0 && month == 0 && days == 0 && minutes == 0 && hours == 0) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static String getShortenedTimeValue(long time) {
		int second = (int)(time / 1000L) % 60;
		int minutes = (int)(time / 60000L % 60L);
		int hours = (int)(time / 3600000L % 24L);
		int days = (int)(time / 86400000L % 30.4368D);
		int month = (int)(time / 2.6297424E9D % 12.0D);
		int year = (int)(time / 3.15569088E10D);

		String y = year + "y";
		String mo = month + "mo";
		String d = days + "d";
		String h = hours + "h";
		String m = minutes + "m";
		String s = second + "s";

		StringBuilder sb = new StringBuilder();
		if (year != 0) {
			sb.append(y + ", ");
		}
		if (month != 0) {
			sb.append(mo + ", ");
		}
		if (days != 0) {
			sb.append(d + ", ");
		}
		if (hours != 0) {
			sb.append(h + ", ");
		}
		if (minutes != 0) {
			sb.append(m + ", ");
		}
		sb = new StringBuilder(sb.substring(0, Math.max(0, sb.length() - 2)));
		if ((year != 0 || month != 0 || days != 0 || minutes != 0 || hours != 0) && second != 0) {
			sb.append(", " + s);
		}
		if (year == 0 && month == 0 && days == 0 && minutes == 0 && hours == 0) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static DecimalFormat getDecimalFormat() {
		return new DecimalFormat("0.0");
	}

	public static long parse(String input) {
		if (input == null || input.isEmpty()) {
			return -1L;
		}

		long result = 0L;
		StringBuilder number = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isDigit(c)) {
				number.append(c);
			} else {
				String str;
				if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
					result += convert(Integer.parseInt(str), c);
					number = new StringBuilder();
				}
			}
		}
		return result;
	}

	private static long convert(int value, char unit) {
		switch (unit) {
			case 'y':
				return value * TimeUnit.DAYS.toMillis(365L);
			case 'M':
				return value * TimeUnit.DAYS.toMillis(30L);
			case 'd':
				return value * TimeUnit.DAYS.toMillis(1L);
			case 'h':
				return value * TimeUnit.HOURS.toMillis(1L);
			case 'm':
				return value * TimeUnit.MINUTES.toMillis(1L);
			case 's':
				return value * TimeUnit.SECONDS.toMillis(1L);
		}
		return -1L;
	}

	public static long getTimeUntilMidnight() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return (cal.getTimeInMillis() - System.currentTimeMillis());
	}

	public static long getMidnightMillis() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	public static long getTimeUntilNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return (cal.getTimeInMillis() - System.currentTimeMillis());
	}

	public static long getNextMonthMillis() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	public static boolean isDate(int day, int month) {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH) == day
				&& cal.get(Calendar.MONTH) == month - 1;
	}


	public static DateFormat getDateFormat() {
		return DATE_FORMAT;
	}

	public static DateFormat getDayDateFormat() {
		return DATE_DAY_FORMAT;
	}

	public static boolean occurredInLastDay(long l1) {
		return l1 > System.currentTimeMillis() - DAY;
	}

	public static class IntegerCountdown {
		public static String setFormat(Integer value) {
			int remainder = value * 1000;

			int seconds = remainder / 1000 % 60;
			int minutes = remainder / 60000 % 60;
			int hours = remainder / 3600000 % 24;

			return (hours > 0 ? String.format("%02d" + ":", new Object[] { hours }) : "")
					+ String.format("%02d:%02d", new Object[] { minutes, seconds });
		}
	}

	public static class LongCountdown {
		public static String setFormat(Long value) {
			if (value < TimeUnit.MINUTES.toMillis(1L)) {
				return getDecimalFormat().format(value / 1000.0D) + "s";
			}
			// HH'h' || mm'm' ss's'
			return DurationFormatUtils.formatDuration(value,
					(value >= TimeUnit.HOURS.toMillis(1L) ? "HH:" : "") + "mm:ss");
		}
	}

}