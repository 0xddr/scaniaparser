package ddr.ddr.scania;

import ddr.ddr.scania.model.EntryInfo;
import ddr.ddr.scania.model.HeaderInfo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScaniaParser {

    private final static String header = "(\\d+(\\s+(\\w|\\/)+)+\\s+PCS)";
    private final static String entry = "(.+NEW\\!)";
    private final static Pattern pattern = Pattern.compile(String.format("^((%s|%s))$", header, entry), Pattern.MULTILINE);
    private final static Pattern header_pattern = Pattern.compile(String.format("^%s$", header));
    //private final static Pattern entry_pattern = Pattern.compile(String.format("^%s$", entry));

    public static HeaderInfo parseHeader(String header) {
        String[] tokens = header.split("\\s+");

        HeaderInfo info = new HeaderInfo();
        info.partNo = tokens[0];

        StringBuilder sb = new StringBuilder(100);
        sb.append(tokens[1]);

        for (int i = 2; i < tokens.length - 1; i++) {
            sb.append(' ');
            sb.append(tokens[i]);
        }

        info.description = sb.toString();

        return info;
    }

    public static EntryInfo parserEntry(String entry) {
        String[] tokens = entry.trim().split("\\s+");

        if (tokens.length != 4 || !tokens[3].equals("NEW!"))
            throw new IllegalArgumentException();

        EntryInfo info = new EntryInfo();
        info.readyToPickUp = tokens[2];
        info.lineNo = tokens[1];

        info.qty = tokens[0].substring(0, tokens[0].length() - 10);
        info.orderNo = tokens[0].substring(tokens[0].length() - 10);

        return info;
    }

    public static boolean isHeader(String line) {
        return header_pattern.matcher(line).matches();
    }

    public DeliveryEntry[] parse(String input) {
        String[] extracted = extractLines(input);
        String[][] sections = prepareSections(extracted);
        return parseSections(sections);
    }

    private String[][] prepareSections(String[] lines) {
        ArrayList<String> section = null;
        ArrayList<String[]> sections = new ArrayList<>(100);
        for (String line : lines) {

            if (isHeader(line)) {
                if (section != null)
                    sections.add(section.toArray(new String[section.size()]));
                section = new ArrayList<>(10);
                section.add(line);
            } else {
                section.add(line);
            }
        }
        return sections.toArray(new String[sections.size()][]);
    }

    public static String[] extractLines(String input) {
        ArrayList<String> lines = new ArrayList<>(200);

        Matcher matcher = pattern.matcher(input);
        while(matcher.find()) {
            lines.add(matcher.group());
        }
        return lines.toArray(new String[lines.size()]);
    }

    private ArrayList<DeliveryEntry> createEntries(String[] section) {
        HeaderInfo headerInfo = parseHeader(section[0]);

        ArrayList<DeliveryEntry> entries = new ArrayList<>(5);

        for (int i = 1; i < section.length; i++) {
            EntryInfo entry = parserEntry(section[i]);

            DeliveryEntry deliveryEntry = new DeliveryEntry(headerInfo, entry);
            entries.add(deliveryEntry);
        }

        return entries;
    }

    private DeliveryEntry[] parseSections(String[][] sections) {
        ArrayList<DeliveryEntry> entries = new ArrayList<>(200);

        for (String[] section : sections) {
            ArrayList<DeliveryEntry> newEntries = createEntries(section);

            entries.addAll(newEntries);
        }

        return entries.toArray(new DeliveryEntry[entries.size()]);
    }

}
