package com.example.heath.ulti;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {
    public final static String VIETNAMESE_DIACRITIC_CHARACTERS
            = "[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W|_]";
    public static DecimalFormat decimalFomatVND = new DecimalFormat("###,###,##0");
    public static DecimalFormat decimalFomatUSD = new DecimalFormat("###,###,##0.00");
    public static DecimalFormat rateFormat = new DecimalFormat("0000000000");
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String CHARACTERS_AND_NUMBERIC = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String dealNull(Object object) {
        String returnstr = "";
        if (object == null)
            returnstr = "";
        else if ("null".equals(object))
            returnstr = "";
        else
            returnstr = (String) object;
        return returnstr.trim();
    }

    public static String[] splitString(String stringList, String delimiter) {
        if (stringList == null)
            return null;
        if (stringList.length() == 0)
            return null;
        StringTokenizer st = new StringTokenizer(stringList, delimiter);
        String result[] = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++)
            result[i] = st.nextToken();

        return result;
    }

    public static String getI18NNamePath(String locale, String pNamePath) {
        return (new StringBuilder("/")).append(locale).append(pNamePath).toString();
    }

    public static String getNamePath(String pI18NNamePath) {
        String ret = "/";
        if (pI18NNamePath != null) {
            int start = pI18NNamePath.indexOf("/home");
            if (start != -1)
                ret = pI18NNamePath.substring(start);
        }
        return ret;
    }

    public static boolean checkFullName(String inputString) {
        String specialCharactersString = "~!@#$%^&*()_+{}[]-|\\;:”<,>.?/";
        for (int i = 0; i < inputString.length(); i++) {
            char ch = inputString.charAt(i);
            if (specialCharactersString.contains(Character.toString(ch))) {
                break;
            } else if (i == inputString.length() - 1)
                return false;

        }
        return true;
    }

    public static boolean checkFullNamePending(String inputString) {
        // ten viet lien -> pending
        if (!inputString.trim().contains(" ")) {
            return true;
        }

        // cac chu nhieu hon 6 ki tu -> pending
        inputString = inputString.replaceAll("\\s+", " ");
        for (String string : inputString.split(" ")) {
            if (string.length() > 7) {
                return true;
            }
        }
        // chua so -> pending
        String specialCharactersString = "0123456789";
        for (int i = 0; i < inputString.length(); i++) {
            char ch = inputString.charAt(i);
            if (specialCharactersString.contains(Character.toString(ch))) {
                return true;
            } else if (i == inputString.length() - 1)
                return false;

        }
        return true;
    }

    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    public static boolean isEmpty(Object str) {
        if (str == null) {
            return true;
        }
        if (str.toString().trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(String str) {
        return isEmpty(str);
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    public static boolean validateSpecialCharacters(String s) {
        try {
            String reg = "^[a-zA-Z0-9]+$";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(s);
            if (m.matches()) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }

    public static String trimEnd(String s) {
        if (s == null || s.length() == 0)
            return s;
        int i = s.length();
        while (i > 0 && Character.isWhitespace(s.charAt(i - 1)))
            i--;
        if (i == s.length())
            return s;
        else
            return s.substring(0, i);
    }

    public static StringBuilder replaceMobile(String mobile) {
        System.out.print(mobile + "\n");
        StringBuilder stringResult = new StringBuilder(mobile);

        for (int i = 0; i < stringResult.length(); i++) {
            if (i > 1 && i < stringResult.length() - 1) {
                stringResult.setCharAt(i, 'X');
            }

        }
        return stringResult;
    }

    public static StringBuilder replaceFullName(String fullName) {
        fullName = fullName.trim().toUpperCase();
        System.out.print(fullName + "\n");
        StringBuilder stringResult = new StringBuilder(fullName);
        for (int i = 1; i < stringResult.length(); i++) {
            if (((stringResult.charAt(i)) != ' ' && stringResult.charAt(i - 1) != ' ')
                    || (i == stringResult.length() - 1 && stringResult.charAt(i - 1) != ' ')) {
                stringResult.setCharAt(i, 'X');
            }
        }
        return stringResult;
    }


    public static boolean compareInListValue(String key, String... values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static boolean notEqualAnyListValue(String key, String... values) {
        return !compareInListValue(key, values);
    }

    public static String[] getFirstAndLast(String name) {
        String lastName = "";
        String firstName = "";
        if (name.split("\\w+").length > 1) {

            lastName = name.substring(name.lastIndexOf(" ") + 1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        } else {
            firstName = name;
        }
        String[] stringArray = {firstName, lastName};
        return stringArray;
    }


    public static StringBuilder replaceTnexAccount(String account) {
        if (isEmpty(account)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(account);
        for (int i = 1; i < sb.length(); i++) {
            if (i >= 5 && i < sb.length() - 3 && sb.charAt(i) != ' ') {

                System.out.println(sb.charAt(i));
                sb.setCharAt(i, 'X');
            }
        }

        return sb;
    }

    public static StringBuilder replaceFundingCard(String account) {
        if (isEmpty(account)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(account);
        for (int i = 1; i < sb.length(); i++) {
            if (i >= 6 && i < sb.length() - 4 && sb.charAt(i) != ' ') {

                System.out.println(sb.charAt(i));
                sb.setCharAt(i, 'X');
            }
        }

        return sb;
    }

    public static String genHTMLCashInNapas(String action, String datakey, String napasKey, String traceNo,
                                            String apiOperation, String src) {
        return "<form id=\"merchant-form\" action=\"" + action + "\"\r\n" + "method=\"POST\">\r\n"
                + "<div id=\"napas-widget-container\"></div>\r\n" + "<script\r\n" + "type=\"text/javascript\"\r\n"
                + "id=\"napas-widget-script\"\r\n" + "src=\"" + src + "\"\r\n" + "merchantId=\"MSBCE\"\r\n"
                + "clientIP=\"192.168.1.1\"\r\n" + "deviceId=\"0123456789\"\r\n" + "environment=\"MobileApp\"\r\n"
                + "cardScheme=\"AtmCard\"\r\n" + "enable3DSecure=\"false\"\r\n" + "apiOperation=\"" + apiOperation
                + "\"\r\n" + "orderReference=\"CASHIN Thanh toan hoa don\"\r\n" + "orderId=\"" + traceNo + "\"\r\n"
                + "channel=\"7399\"\r\n" + "sourceOfFundsType=\"CARD\"\r\n" + "dataKey=\"" + datakey + "\"\r\n"
                + "napasKey=\"" + napasKey + "\" \r\n" + ">\r\n" + "</script>\r\n" + "</form>";

    }

    public static String prefer(String one, String two) {
        if (isNotEmpty(one)) {
            return one;
        }
        return two;
    }


    public static String sqlFormatedList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String i : list) {
            sb.append("'" + i + "',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    public static String sqlFormatedLikeInList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String i : list) {
            sb.append("" + i + "|");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    public static boolean isCheckNumber(String str) {
        String NUMBER_PATTERN = "[0-9]+";
        Pattern pattern;
        if (str == null) {
            return false;
        } else {
            pattern = Pattern.compile(NUMBER_PATTERN);
            if (pattern.matcher(str).matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateSpecial(String s) {
        try {
            String reg = "^[a-zA-Z0-9_]+$";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(s);
            if (m.matches()) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }

    public static boolean validateSpecialChar(String s) {
        try {
            String reg = "^[a-zA-Z0-9_\\s]+$";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(s);
            if (m.matches()) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }

    public static String removeSpecialChar(String data) {
        String reg = "[/!@#$%^&*><{}+-]";
        return data.replaceAll(reg, "");
    }

    public static String genRandomUuid() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }
    public static String normalizeMsisdn(String phoneNumber) {
        String result = phoneNumber;

        try {
            String pattern = "^84";
            result = result.replaceAll(pattern, "");
            pattern = "^0";
            result = result.replaceAll(pattern, "");
        } catch (Exception ex) {
            result = phoneNumber;
        }

        return result;
    }

    public static String randomString(int limitLength) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < limitLength; i++) {
            int index = random.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(index));
        }

        return builder.toString();
    }


    public static String getAlphaNumericString(int n) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int index = random.nextInt(CHARACTERS_AND_NUMBERIC.length());
            builder.append(CHARACTERS_AND_NUMBERIC.charAt(index));
        }

        return builder.toString();
    }
}
