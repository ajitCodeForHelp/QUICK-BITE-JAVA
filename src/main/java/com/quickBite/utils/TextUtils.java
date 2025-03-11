package com.quickBite.utils;

import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class TextUtils {
    public static String generate4DigitOTP() {
        if (true) {
            return "9999";
        }
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        return String.format("%04d", number);
    }

    public static boolean isEmpty(String string) {
        if (null == string) return true;
        return string.length() == 0;
    }

    public static boolean isEmpty(Long value) {
        if (null == value) return true;
        return value <= 0;
    }

    public static boolean isEmpty(Integer value) {
        if (null == value) return true;
        return value <= 0;
    }

    public static boolean isEmpty(Double value) {
        if (null == value) return true;
        return value <= 0;
    }

    public static boolean isEmpty(List list) {
        return (list == null || list.isEmpty());
    }

    public static Long getValidValue(Long oldValue, Long newValue) {
        return TextUtils.isEmpty(newValue) ? oldValue : newValue;
    }

    public static Double getValidValue(Double oldValue, Double newValue) {
        return TextUtils.isEmpty(newValue) ? oldValue : newValue;
    }

    public static String getValidValue(String oldValue, String newValue) {
        return TextUtils.isEmpty(newValue) ? oldValue : newValue;
    }

    public static LocalDate getValidValue(Long newValue, LocalDate oldValue) {
        if (TextUtils.isEmpty(newValue)) {
            return oldValue;
        }
        return DateUtils.getLocalDate(newValue);
    }

    public static boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Long currentSessionId = 0L;

    public static void resetCurrentSessionId() {
        currentSessionId = 0L;
    }

    public static String formatMoneyAmount(double moneyAmount) {
        DecimalFormat format = new DecimalFormat("##.00");
        return format.format(moneyAmount);
    }

    public static String getRandomKeyString(int length) {
        // String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-abcdefghijklmnopqrstuvwxyz_0123456789";
        String alphaNumeric = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
        // I L O / 0 / 1
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int random = (int) ((Math.random() * 1000) % alphaNumeric.length());
            buffer.append(alphaNumeric.charAt(random));
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    public static String getEncodedPassword(String password) {
        if (isEmpty(password)) {
            return null;
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    public static String md5encryption(String text) {
        if (isEmpty(text)) {
            return null;
        }
        String hashtext = null;
        try {
            String plaintext = text;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        } catch (Exception e1) {
        }
        return hashtext;
    }

    public static String get4DigitString(long number) {
        String numberString = Long.toString(number);
        if (numberString.length() > 4) {
            return numberString.substring(0, 4);
        } else if (numberString.length() < 4) {
            return String.format("%04d", number);
        } else {
            return numberString;
        }
    }

    public static String get2DigitString(long number) {
        String numberString = Long.toString(number);
        if (numberString.length() > 2) {
            return numberString.substring(0, 2);
        } else if (numberString.length() < 2) {
            return String.format("%02d", number);
        } else {
            return numberString;
        }
    }

    public static String get10CharRandomCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString.toUpperCase();
    }

    public static String getOrderId(String orderPrefix, Long orderNumber) {
        if (isEmpty(orderNumber)) return null;
        if (isEmpty(orderPrefix)) {
            return "ORD-" + orderNumber;
        } else {
            return orderPrefix.toUpperCase() + "-" + orderNumber;
        }
    }

    public static String getReservationId(String orderPrefix, Long reservationNumber) {
        if (isEmpty(reservationNumber)) return null;
        if (isEmpty(orderPrefix)) {
            return "RES-" + reservationNumber;
        } else {
            return orderPrefix.toUpperCase() + "-" + reservationNumber;
        }
    }

    public static String convertNumberTo10Digit(Long number) {
        return String.format("%010d", number);
    }

    public static String get10DigitTransactionId(Long number) {
        return "TXN-" + convertNumberTo10Digit(number);
    }

    public static String getPaymentRequestTransactionId(Long number) {
        return "TXN-" + convertNumberTo10Digit(number);
    }

    public static String getListToStringCSV(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuffer csv = new StringBuffer();
        for (String image : list) {
            if (csv.length() > 0) {
                csv.append(",");
            }
            csv.append(image);
        }
        return csv.toString();
    }

    public static List<String> getStringCSVToList(String stringCSV) {
        if (isEmpty(stringCSV)) {
            return null;
        }
        return new ArrayList<>(Arrays.asList(stringCSV.split(",")));
    }

    public static String removeSpecialCharacterFromString(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", " ");
    }

    public static String getUniqueKey() {
        return UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString();
    }

    public static String getJsonString(Object obj) {
        return new Gson().toJson(obj);
    }

    public static String generate6DigitRandomNumber() {
        if (true) {
            return "000000";
        }
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static BigDecimal get2DecimalPlaceValue(double value) {
        return new BigDecimal(String.format("%.2f", value));
    }

    public static String getCombinedIdsByDash(List<Long> ids) {
        // Sort If Required.
        ArrayList<Long> idList = new ArrayList<>(ids);
        Collections.sort(idList);
        StringBuffer combinedKey = new StringBuffer("");
        for (Long id : idList) {
            if (combinedKey.length() > 0) {
                combinedKey.append("-");
            }
            combinedKey.append(id);
        }
        return combinedKey.toString();
    }

    public static String validateTitle(String title) {
        if (isEmpty(title)) {
            return null;
        }
        return title.toLowerCase().replaceAll("\\s+", "_");
    }
//    public static String getStringOfLength10(String title) {
//        if (isEmpty(title)) {
//            return null;
//        }
//        title = validateTitle(title);
//        if (title.length() <= 10) {
//            return title;
//        }
//        return title.substring(0, 10);
//    }

    public static String get7CharRandomCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 7;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString.toUpperCase();
    }

    public static String hideCouponCode(String couponCode) {
        String tempCoupon = "";
        if (couponCode.length() == 7) {
            tempCoupon = couponCode.charAt(0) + "*****" + couponCode.charAt(couponCode.length() - 1);
        }
        return tempCoupon;
    }
}