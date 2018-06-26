package org.yseasony.sqlgenerator;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class Util {

    public static final String LF = System.getProperty("line.separator");

    private Util() {
    }


    public static String appendColumns1(List<String> columns, String separator, String prefix,
        String suffix, boolean withName) {
        if (columns.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = columns.size(); i < size; i++) {
            builder.append(" ");
            if (i != 0) {
                builder.append(separator);
            }
            builder.append(columns.get(i))
                .append(" = ")
                .append(prefix)
                .append(withName ? convertCamelCase(columns.get(i)) : "")
                .append(suffix)
                .append(" ");
        }
        return builder.toString();
    }

    public static String appendColumns(List<String> columns, String separator, String prefix,
        String suffix, boolean withColumnName, boolean withParamName) {
        if (columns.isEmpty()) {
            return "";
        }
        return columns.stream()
            .map(column -> (withColumnName ? column + "=" : "") +
                (withParamName ? (prefix + Util.convertCamelCase(column) + suffix) : suffix))
            .collect(Collectors.joining(separator));
    }

    public static void main(String[] args) {
        final ArrayList<String> columns = new ArrayList<>();
        columns.add("aa_aa1");
        columns.add("aa_aa2");
        columns.add("aa_aa3");
        System.out.println(
            appendColumns(columns, ",", ":", "", true,
                true));
        System.out.println(
            appendColumns(columns, ",", "#{", "}", true,
                true));
        System.out.println(
            appendColumns(columns, ",", "#{", "}", false,
                true));
        System.out.println(
            appendColumns(columns, ",", "#{", "?", true,
                false));
    }

    public static String appendNamedParams(List<String> columns, String separator, String prefix,
        String suffix) {
        return appendColumns(columns, separator, prefix, suffix, true, true);
    }

    private static String makeNamesWhereClause(List<String> columns, String prefix, String suffix) {
        return "WHERE" + appendNamedParams(columns, "AND", prefix, suffix);
    }

    /**
     * column=?
     */
    public static String makeNormalWhereClause(List<String> columns) {
        return appendColumns(columns, "AND", "", "?", true, false);
    }


    /**
     * column= prefix+param+suffix
     */
    public static String makeNamedPlaceholderWhereClause(List<String> columns, String prefix,
        String suffix) {
        return makeNamesWhereClause(columns, prefix, suffix);
    }

    public static String convertCamelCase(String value) {
        String[] split = value.split("[_-]");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (i == 0) {
                ret.append(s.toLowerCase(Locale.ENGLISH));
            } else {
                ret.append(Character.toUpperCase(s.charAt(0)));
                if (s.length() > 1) {
                    ret.append(s.substring(1).toLowerCase(Locale.ENGLISH));
                }
            }
        }
        return ret.toString();
    }
}
