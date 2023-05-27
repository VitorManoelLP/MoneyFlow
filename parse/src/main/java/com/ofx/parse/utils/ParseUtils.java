package com.ofx.parse.utils;

import com.webcohesion.ofx4j.domain.data.ResponseMessage;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponseTransaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public final class ParseUtils {

	public static final String DATE_BR_PATTERN = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
	public static final String NUMERIC_PATTERN = "^-?\\d+(\\.\\d+)?$";

	private ParseUtils() {}

	public static BankStatementResponseTransaction getBankTransaction (ResponseMessage message) {
		return (BankStatementResponseTransaction) message;
	}

	public static LocalDate ofLocalDate (Instant instant) {
		return LocalDate.ofInstant(instant, ZoneId.systemDefault());
	}

	public static String parseBr(LocalDate date) {
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		return date.format(formatters);
	}

	public static LocalDate parseDefault(String date, String limiter) {
		boolean dataInvalida = matchPattern(date, DATE_BR_PATTERN);

		if (!dataInvalida) {
			throw new IllegalArgumentException("Data inválida");
		}

		String[] dateParts = date.split(limiter);
		return LocalDate.of(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
	}

	public static BigDecimal parseBigDecimal(String value) {

		String valueReplaced = value.replace(",", ".");

		boolean isNumber = matchPattern(valueReplaced, NUMERIC_PATTERN);

		if (!isNumber) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(valueReplaced);
	}

	public static String parseBrInstant(Instant instant) {
		return parseBr(ofLocalDate(instant));
	}

	public static String toUpper (String word) {
		return word.toUpperCase(Locale.ROOT);
	}

	public static Long getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return (long) calendar.get(Calendar.MONTH) + 1L;
	}

	private static boolean matchPattern (String value, String regx) {
		Pattern pattern = Pattern.compile(regx);
		return pattern.matcher(value).matches();
	}

}
