package com.qb.Commons.Constants;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public enum CommonConstants {

    INSTANCE;

    public final String BLANK_STRING = "";

    public final String HYPHEN_STRING = "-";

    public final String UNDERSCORE_STRING = "_";

    public final String SPACE_STRING = " ";

    public final String DOT_STRING = ".";

    public final String ATTHERATE_STRING = "@";

    public final String OPEN_CURLY_BRACKET_STRING = "{";

    public final String CLOSE_CURLY_BRACKET_STRING = "}";

    public final String COLON_STRING = ":";

    public final String FORWARD_SLASH_STRING = "/";

    public final char FORWARD_SLASH_CHAR = '/';

    public final String DOUBLE_FORWARD_SLASH_STRING = "//";

    public final String PLUS_STRING = "+";

    public final String HASH_STRING = "#";

    public final String DOLLAR_STRING = "$";

    public final String COMMA_STRING = ",";

    public final String DOUBLE_QUOTE_STRING = "\"";

    public final String NEWLINE_STRING = "\n";

    public final String PASSWORD_STRING = "password";

    public final String ACCESS_TOKEN_STRING = "access_token";

    public final String REFRESH_TOKEN_STRING = "refresh_token";

    public final String ID_TOKEN_STRING = "id_token";

    public final String IDTOKEN_STRING = "IdToken";

    public final String AUTHORIZATION_STRING = "Authorization";

    public final String BEARER_STRING = "Bearer ";

    public final String TEMP_FILE_STRING = "TempFile";

    public final String REJECTED_RECORDS_STRING = "Rejected_Records";

    public final String ACCOUNT_LOCK_INFO_STRING = "Account_Lock_Info_Report";

    public final String DEFAULT_STRING = "Default";

    public final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public final String PHONE_NUMBER_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    public final String PHONE_NUMBER_WITHOUT_DIAL_CODE_REGEX = "^[0][1-9]\\d{9}$|^[1-9]\\d{9}$";

    public final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_`*!|.,:;'()?\"/-])(?=\\S+$).{8,}$";

    public final String SERVICE_STRING = "Service";

    public final DateTimeFormatter EEEEDDMMMYYYY_FORMATTER = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

    public final DateTimeFormatter DDMMYYYY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public final DateTimeFormatter DD_MM_YYYY_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_SS");

    public final DateTimeFormatter DDMMYYYY_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:SS");

    public final DateTimeFormatter YYYY_FORMATTER = DateTimeFormatter.ofPattern("yyyy");

    public final String SMS_STRING = "sms";

    public final String REGISTRATION_SUCCESSFUL_STRING = "Registration Successful";

    public final String DIGIT_ONLY_REGEX = "[0-9]+";

    public final String CHARACTERS_ONLY_REGEX = "[^A-Za-z]";

    public final String ALL_STRING = "ALL";

    public final String XLSX_EXTENSION = ".xlsx";

    public final String PDF_EXTENSION = ".pdf";

    public final String TWO_POSITION_SPLIT_REGEX = "(?<=\\G..)";

    public final NumberFormat USA_CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

    public final Integer THREE = 3;

    public final Integer FOUR = 4;

    public final Integer FIVE = 5;

    public final Integer SIX_INTEGER = 6;

    public final Integer SEVEN = 7;

    public final String JPG_STRING = "jpg";

}
