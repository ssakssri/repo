package com.ssakssri.scp.myhrext.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DateUtils {

	private static final int SECOND = 1000;

	private static final int MINUTE = 60 * SECOND;

	private static final int HOUR = 60 * MINUTE;

	/**
	 * <code>DateUtils.getCurrentDate(DateUtils.yyyyMMdd,"")</code> �� ����
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		return getCurrentDate(yyyyMMdd, "");
	}

	public static String convertUnixTimeToDateFormat(String input) {
		Pattern pattern = Pattern.compile("/Date\\((\\d+)\\)/");
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			long unixTime = Long.parseLong(matcher.group(1));
			Date date = new Date(unixTime);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format(date);
		}

		throw new IllegalArgumentException("Invalid Unix time format");
	}

	/**
	 * <code>DateUtils.getCurrentDate(int type,"")</code> �� ����
	 * 
	 * @param type
	 * @return String
	 */
	public static String getCurrentDate(int type) {
		return getCurrentDate(type, "");
	}

	/**
	 * ���� ��¥�� ���Ѵ�.
	 * 
	 * @param type ��¥ Ÿ�� (yyyy, MM, yyyyMM , yyyyMMdd)
	 * @return String ���� ��¥
	 */
	public static String getCurrentDate(int type, String delimiter) {

		Calendar now = Calendar.getInstance();

		String curYear = String.valueOf(now.get(Calendar.YEAR));

		int month = now.get(Calendar.MONTH) + 1;
		String curMonth = month < 10 ? "0" + month : String.valueOf(month);

		int day = now.get(Calendar.DATE);
		String curDay = day < 10 ? "0" + day : String.valueOf(day);

		StringBuffer rtnDate = new StringBuffer(16);
		switch (type) {
		case yyyy:
			rtnDate.append(curYear);
			break;
		case MM:
			rtnDate.append(curMonth);
			break;
		case yyyyMM:
			rtnDate.append(curYear);
			rtnDate.append(delimiter);
			rtnDate.append(curMonth);
			break;
		case yyyyMMdd:
			rtnDate.append(curYear);
			rtnDate.append(delimiter);
			rtnDate.append(curMonth);
			rtnDate.append(delimiter);
			rtnDate.append(curDay);
			break;
		case MMdd:
			rtnDate.append(curMonth);
			rtnDate.append(delimiter);
			rtnDate.append(curDay);
			break;
		case dd:
			rtnDate.append(curDay);
			break;
		}

		return rtnDate.toString();
	}

	/**
	 * Ư�� ���Ͽ� �°� ����ð��� ����Ѵ�. <br>
	 * ����) <code> DateUtils.getCurrentDate("yyyy�� MM�� dd��") </code>
	 * 
	 * @param pattern
	 * 
	 * @return String
	 * @see java.text.SimpleDateFormat
	 */
	public static String getCurrentDate(String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * ������ yyyyMMdd,yyyy-MM-dd, yyyy/MM/dd ���� ������ ��ȣ ��������ش�. yyyyMM,
	 * yyyy-MM, yyyy/MM ���ĵ� ��ȣ ����ȴ�.
	 * 
	 * @param dateString ��¥�� ǥ���� ���ڿ�
	 * @return yyyyMMdd ���� ������ ����� ���ڿ��� ���ؼ��� �� ��Ʈ���� ��ȯ
	 */
	public static String formatDate(String dateString, String delimiter) {
		if (dateString == null)
			return "";
		String output = dateString.replaceAll("[^0-9]", "");
		if (output.length() == 8) {
			return (output.substring(0, 4) + delimiter + output.substring(4, 6) + delimiter + output.substring(6, 8));
		} else if (output.length() == 6) {
			return (output.substring(0, 4) + delimiter + output.substring(4, 6));
		} else {
			return "";
		}
	}

	/**
	 * ������ yyyyMMdd,yyyy-MM-dd, yyyy/MM/dd ���� ������ ��ȣ ��������ش�. yyyyMM,
	 * yyyy-MM, yyyy/MM ���ĵ� ��ȣ ����ȴ�.
	 * 
	 * @param dateString ��¥�� ǥ���� ���ڿ�
	 * @return yyyyMMdd ���� ������ ����� ���ڿ��� ���ؼ��� �� ��Ʈ���� ��ȯ
	 */
	public static String decorateDate(String dateString, String dateFormat) {
		if (dateString == null)
			return "";

		String output = dateString.replaceAll("\\p{Punct}", "");
		if (output.length() != 8)
			return dateString;

		Date tDate = parseDate(output);
		return new SimpleDateFormat(dateFormat).format(tDate);
	}

	/**
	 * "Date" ���� "yyyyMMdd"�������� "String"���� ��ȯ
	 * 
	 * @return �Էµ����Ͱ� Null�ϰ�� �� ��Ʈ������ ��ȯ.
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_PATTERN);
		return formatter.format(date);
	}

	/**
	 * Date ���� ���ϴ� ����(Pattern)���� ��Ʈ������ ��ȯ
	 * 
	 * @return �Էµ����Ͱ� Null�ϰ�� �� ��Ʈ������ ��ȯ.
	 * @see java.text.SimpleDateFormat
	 */
	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	/**
	 * String ���� format ������ Date ������ Parseing �Ѵ�.
	 */
	public static Date parseDate(String strDate, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * String ���� format Type�� Date ������ Parseing �Ѵ�
	 */
	public static Date parseDate(String strDate) {
		return parseDate(strDate, DEFAULT_PATTERN);
	}

	/**
	 * String ���� format Type�� Date ������ Parseing �Ѵ�
	 */
	public static Calendar parseCalendar(String strDate) {
		return parseCalendar(strDate, DEFAULT_PATTERN);
	}

	/**
	 * String ���� format ������ Date ������ Parseing �Ѵ�.
	 */
	public static Calendar parseCalendar(String strDate, String pattern) {
		Date date = parseDate(strDate, pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * ���ڿ� ������ ���ڸ� ���� ���� �����Ҷ� ��� <br>
	 * �ǹ̻��� �޼��� ����Ѵ�. ��, 2005/12/31 + 2 Month �� ������� 2006/03/03 ���� �ƴ�
	 * 2006/02/28 �� �ȴ�. <br>
	 * 
	 * @param dateString ���ڿ� ��¥ "yyyyMMdd"�����̾�� ��
	 * @param value      ���� ������ �� ��
	 * @return "yyyyMMdd"�������� return
	 */
	public static String addMonth(String dateString, int value) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		ParsePosition pos = new ParsePosition(0);
		Date date = format.parse(dateString.substring(0, 6), pos);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int day, daysOfMonth, suffix;
		cal.add(Calendar.MONTH, value);
		day = Integer.parseInt(dateString.substring(6, 8));
		daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		StringBuffer result = new StringBuffer(format.format(cal.getTime()).toString());
		suffix = Math.min(day, daysOfMonth);
		if (suffix >= 10) {
			result.append(suffix);
		} else {
			result.append("0");
			result.append(suffix);
		}

		return result.toString();
	}

	/**
	 * ���ڿ� ������ ���ڸ� ���� ��¥�� �����Ҷ� ���,
	 * 
	 * @param dateString ���ڿ� ��¥ "yyyyMMdd"�����̾�� ��
	 * @param value      ��¥�� ������ �� ��
	 * @return "yyyyMMdd"�������� return
	 */
	public static String addDay(String dateString, int value) {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_PATTERN);
		ParsePosition pos = new ParsePosition(0);
		Date date = format.parse(dateString, pos);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, value);
		return format.format(cal.getTime()).toString();
	}

	/**
	 * ��¥������ ������ ���Ѵ�.
	 * 
	 * @param fromDateStr 'yyyyMMdd' ����
	 * @param toDateStr   'yyyyMMdd' ����
	 * @return int
	 */
	public static int getDaysBetween(String fromDateStr, String toDateStr) {
		Date fromDate = parseDate(fromDateStr);
		Date toDate = parseDate(toDateStr);
		long gap = toDate.getTime() - fromDate.getTime();
		return (int) (gap / (24 * 60 * 60 * 1000));

	}

	/**
	 * ������ ������� ��ȯ�Ѵ�.
	 * 
	 * @param TranseDay   ������('yyyyMMdd')
	 * @param isLeapMonth ���� ����
	 * @return String ó����� ����� ��ƼƼ
	 * @throws java.lang.Exception
	 */
	public static String toSolarDate(String TranseDay, boolean isLeapMonth) {
		int lyear = Integer.parseInt(TranseDay.substring(0, 4));
		int lmonth = Integer.parseInt(TranseDay.substring(4, 6));
		int lday = Integer.parseInt(TranseDay.substring(6, 8));

		if (!isLeapMonth && !verifyDate(lyear, lmonth, lday, "solar-")) {
			return "";
		}
		if (isLeapMonth && !verifyDate(lyear, lmonth, lday, "solar+")) {
			return "";

		}
		int m1 = -1;
		long td = 0L;
		if (lyear != 1881) {
			m1 = lyear - 1882;
			for (int i = 0; i <= m1; i++) {
				for (int j = 0; j < 13; j++) {
					td = td + lunarFactor[i * 13 + j];
				}

				if (lunarFactor[i * 13 + 12] == 0) {
					td = td + 336L;
				} else {
					td = td + 362L;
				}
			}

		}
		m1++;
		int n2 = lmonth - 1;
		int m2 = -1;
		do {
			m2++;
			if (lunarFactor[m1 * 13 + m2] > 2) {
				td = td + 26L + lunarFactor[m1 * 13 + m2];
				n2++;
				continue;
			}
			if (m2 == n2) {
				break;
			}
			td = td + 28L + lunarFactor[m1 * 13 + m2];
		} while (true);
		if (isLeapMonth) {
			td = td + 28L + lunarFactor[m1 * 13 + m2];
		}
		td = td + lday + 29L;
		m1 = 1880;
		do {
			m1++;
			boolean leap = m1 % 400 == 0 || m1 % 100 != 0 && m1 % 4 == 0;
			if (leap) {
				m2 = 366;
			} else {
				m2 = 365;
			}
			if (td < m2) {
				break;
			}
			td = td - m2;
		} while (true);
		int syear = m1;
		daysOfMonth[1] = m2 - 337;
		m1 = 0;
		do {
			m1++;
			if (td <= daysOfMonth[m1 - 1]) {
				break;
			}
			td = td - daysOfMonth[m1 - 1];
		} while (true);
		int smonth = m1;
		int sday = (int) td;
		long y = syear - 1L;
		td = ((y * 365L + y / 4L) - y / 100L) + y / 400L;
		boolean leap = syear % 400 == 0 || syear % 100 != 0 && syear % 4 == 0;
		if (leap) {
			daysOfMonth[1] = 29;
		} else {
			daysOfMonth[1] = 28;
		}
		for (int i = 0; i < smonth - 1; i++) {
			td = td + daysOfMonth[i];
		}

		td = td + sday;
		int i = (int) (td % 10L);
		i = (i + 4) % 10;
		int j = (int) (td % 12L);
		j = (j + 2) % 12;

		String sValue = String.valueOf(syear);

		if (smonth < 10) {
			sValue += "0";
		}
		sValue += String.valueOf(smonth);
		if (sday < 10) {
			sValue += "0";
		}
		sValue += String.valueOf(sday);

		return sValue;
	}

	/**
	 * ��/���� ��ȯ�� �������� �����Ѵ�.
	 * 
	 * @param year  �⵵
	 * @param month ��
	 * @param day   ��
	 * @param type  �����Ϸ��� ����.
	 * @return
	 */
	private static boolean verifyDate(int year, int month, int day, String type) {
		if (year < 1881 || year > 2043 || month < 1 || month > 12)
			return false;
		if (type.equals("lunar") && day > daysOfMonth[month - 1]) {
			return false;
		}
		if (type.equals("solar+")) {
			if (lunarFactor[(year - 1881) * 13 + 12] < 1) {
				return false;
			}
			if (lunarFactor[(year - 1881) * 13 + month] < 3) {
				return false;
			}
			if (lunarFactor[(year - 1881) * 13 + month] + 26 < day) {
				return false;
			}
		}
		if (type.equals("solar-")) {
			int j = month - 1;
			for (int i = 1; i <= 12; i++) {
				if (lunarFactor[((year - 1881) * 13 + i) - 1] > 2) {
					j++;
				}
			}

			if (day > lunarFactor[(year - 1881) * 13 + j] + 28)
				return false;
		}
		return true;
	}

	public static Timestamp getCurrentTimeStamp() {
		Calendar now = Calendar.getInstance();
		return new Timestamp(now.getTimeInMillis());
	}

	public static Timestamp minusOneSecond(Timestamp now) {
		return new Timestamp(now.getTime() - 1000);
	}

	public static String dateToString(Timestamp now, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(now);
	}

	public static Timestamp stringToDate(String now, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		java.util.Date dt = null;

		try {
			dt = sf.parse(now);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Timestamp(dt.getTime());
	}

	public static Timestamp addHour(Timestamp now, int hour) {
		return new Timestamp(now.getTime() + (hour * HOUR));
	}

	public static Timestamp addMinute(Timestamp now, int minute) {
		return new Timestamp(now.getTime() + (minute * MINUTE));
	}

	public static Timestamp addSecond(Timestamp now, int second) {
		return new Timestamp(now.getTime() + (second * SECOND));
	}

	public static Timestamp convertDateToTimestamp(java.util.Date dt) {
		return new Timestamp(dt.getTime());
	}

	public static java.util.Date convertTimestampToDate(Timestamp ts) {
		return new java.util.Date(ts.getTime());
	}

	/**
	 * Date ���ڿ��� ǥ���� ���Խ�
	 */
	// private static Pattern datePattern = Pattern
	// .compile("^\\d{4}[-.\\/]?\\d{2}[-.\\/]?\\d{2}$");
	public final static int yyyy = 1;

	public final static int MM = 2;

	public final static int yyyyMM = 3;

	public final static int yyyyMMdd = 4;

	public final static int MMdd = 5;

	public final static int dd = 6;

	/**
	 * �⺻���� DateFormat�� ������ "yyyyMMdd" �����̴�.
	 */
	public final static String DEFAULT_PATTERN = "yyyyMMdd";

	private static final int lunarFactor[] = { 1, 2, 1, 2, 1, 2, 2, 3, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2,
			2, 0, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 0, 2, 1, 1, 2, 1, 3, 2, 1, 2, 2, 1, 2, 2, 2, 1, 1, 2, 1, 1, 2, 1,
			2, 1, 2, 2, 0, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0, 2, 2, 1, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1,
			2, 1, 1, 2, 1, 2, 1, 0, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0, 1, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2,
			1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0, 1, 1, 2, 1, 1, 2, 3, 2, 2, 1, 2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2,
			0, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0, 2, 1, 2, 1, 2, 3, 1, 2, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 1,
			2, 1, 2, 0, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0, 2, 1, 2, 3, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1,
			2, 2, 1, 2, 1, 2, 0, 1, 2, 1, 1, 2, 1, 2, 2, 3, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0, 2, 1, 2,
			1, 1, 2, 1, 2, 1, 2, 2, 2, 0, 1, 2, 1, 2, 1, 3, 2, 1, 1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 0,
			2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 0, 1, 2, 2, 1, 4, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1,
			2, 1, 0, 2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 0, 1, 2, 3, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2,
			1, 2, 2, 2, 1, 0, 2, 1, 2, 1, 1, 2, 3, 1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 0, 2, 2, 1, 2,
			1, 1, 2, 1, 1, 2, 1, 2, 0, 2, 2, 1, 2, 2, 3, 1, 2, 1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0, 1,
			2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0, 2, 1, 3, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1,
			2, 0, 1, 2, 1, 1, 2, 1, 2, 3, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 0, 2, 1, 2, 1, 1, 2, 1, 1,
			2, 1, 2, 2, 0, 2, 1, 2, 2, 1, 3, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 0, 2, 1, 2, 1, 2,
			2, 1, 2, 1, 2, 1, 1, 0, 2, 1, 2, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0, 2, 1,
			1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0, 1, 2, 3, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2,
			0, 1, 2, 2, 1, 1, 2, 3, 1, 2, 1, 2, 2, 1, 2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 0, 2, 2, 2, 1, 2, 1, 2, 1, 1,
			2, 1, 2, 0, 1, 2, 2, 1, 2, 4, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 0, 1, 1, 2, 1, 2, 1,
			2, 2, 1, 2, 2, 1, 0, 2, 1, 1, 4, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 0, 2, 2, 1,
			1, 2, 1, 1, 4, 1, 2, 2, 1, 2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0,
			2, 2, 1, 2, 2, 1, 4, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 0, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2,
			1, 2, 0, 1, 1, 2, 1, 4, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 0, 2, 1, 1, 2, 1, 1, 2,
			1, 2, 2, 1, 2, 0, 2, 2, 3, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0, 2, 2, 1, 2,
			1, 2, 1, 3, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0, 1,
			2, 1, 2, 1, 4, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2, 0, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2,
			2, 0, 2, 1, 1, 4, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0, 2, 1, 2, 1, 2, 1, 1, 2,
			3, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0, 2, 1, 2, 1, 2,
			2, 3, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 0, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0, 2, 1,
			2, 1, 3, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1,
			0, 2, 2, 2, 3, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 0, 1, 2, 2, 1, 2, 1, 2, 3, 2,
			1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0, 2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 0, 1, 2, 1, 1, 2, 3,
			2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 1, 0, 2, 2, 1,
			2, 3, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 0, 2, 2, 1, 2, 1, 2, 1, 2, 3, 2, 1, 1, 2,
			2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 0, 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0, 2, 1, 1, 2, 1, 2, 4, 1, 2, 2,
			1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 0, 2, 1, 2, 1, 3, 2, 1,
			1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 0, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 3, 2, 2, 1, 2, 2, 1,
			2, 1, 2, 1, 1, 2, 1, 2, 0, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 0, 2, 1, 2, 2, 1, 2, 3, 2, 2, 1, 2, 1, 2, 1,
			1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0, 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0, 1, 2, 1, 1, 2, 3, 1, 2, 1, 2, 2,
			2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 0, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 0, 1, 2, 2, 3, 2, 1, 2, 1,
			1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 0, 1, 2, 2, 1, 2, 2, 1, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2, 2,
			1, 2, 1, 2, 2, 1, 2, 0, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0, 2, 1, 1, 2, 1, 3, 2, 2, 1, 2, 2, 2, 1, 2, 1,
			1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 0, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 0, 2, 2, 2, 1, 3, 2, 1, 1, 2, 1, 2, 1,
			2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 0, 1, 2, 3, 2, 2, 1, 2, 1, 2,
			1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1, 0, 2, 1, 2, 1, 2, 1, 2, 3, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2,
			1, 2, 2, 2, 1, 2, 0, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 0, 2, 2, 1, 1, 2, 3, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2,
			1, 2, 1, 1, 2, 1, 2, 1, 2, 0, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0, 2, 1, 2, 4, 2, 1, 2, 1, 1, 2, 1, 2, 1,
			2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0, 1, 2, 1, 2, 1, 2, 1, 2, 2, 3, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1,
			2, 2, 0, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 0, 2, 1, 1, 2, 1, 3, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2,
			1, 2, 1, 2, 2, 0, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0, 2, 1, 2, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1,
			2, 1, 2, 1, 2, 1, 2, 1, 0, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 0, 1, 2, 3, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1,
			2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0, 2, 1, 2, 1, 1, 2, 3, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2,
			2, 0, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 0, 1, 2, 2, 1, 2, 3, 1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2, 1, 1, 2,
			1, 1, 2, 2, 0, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 0, 2, 1, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1,
			2, 2, 1, 2, 2, 1, 2, 0, 1, 2, 1, 1, 2, 1, 2, 3, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0, 2, 1,
			2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 0, 2, 2, 1, 2, 1, 1, 4, 1, 1, 2, 1, 2, 2, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2,
			0, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 0, 2, 2, 1, 2, 2, 3, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2,
			1, 2, 1, 0, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0, 1, 2, 3, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1,
			1, 2, 2, 1, 2, 2, 0, };

	private static final int daysOfMonth[] = { 31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

}
