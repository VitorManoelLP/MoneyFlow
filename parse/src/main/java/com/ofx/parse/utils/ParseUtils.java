package com.ofx.parse.utils;

import com.webcohesion.ofx4j.domain.data.ResponseMessage;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponseTransaction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class ParseUtils {

	private ParseUtils() {}

	public static BankStatementResponseTransaction getBankTransaction (ResponseMessage message) {
		return (BankStatementResponseTransaction) message;
	}

	public static LocalDate ofLocalDate (Instant instant) {
		return LocalDate.ofInstant(instant, ZoneId.systemDefault());
	}

	public static String parseBr(LocalDate date) {
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
		return date.format(formatters);
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

}
