package ddr.ddr.scania;

import ddr.ddr.scania.utils.SimpleCsvWriter;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.HashMap;

public class ScaniaProcessor {

    private final static String[] selectedFields = new String[]{
            "orderNo", "partNo", "lineNo", "readyToRickUp", "quantity"
    };
    private final static HashMap<String, String> headerNames = new HashMap<>(6);

    static {
        headerNames.put("orderNo", "Zamówienie");
        headerNames.put("partNo", "Detal");
        headerNames.put("lineNo", "Linia");
        headerNames.put("readyToRickUp", "Termin");
        headerNames.put("quantity", "Ilość");
    }

    private Parser m_parser;

    public ScaniaProcessor() {
        m_parser = new PDFParser();
    }

    public void process(String input, String output) throws IOException, TikaException, SAXException {
        DeliveryEntry[] entries = parseInput(readInput(input));
        writeResults(entries, output);
    }

    private String readInput(String input) throws IOException, TikaException, SAXException {
        InputStream stream = new FileInputStream(new File(input));

        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        BodyContentHandler handler = new BodyContentHandler();

        try {
            m_parser.parse(stream, handler, metadata, context);
        } finally {
            stream.close();
        }

        return handler.toString();
    }

    private DeliveryEntry[] parseInput(String input) {
        return new ScaniaParser().parse(input);
    }

    private void writeResults(DeliveryEntry[] entries, String output) throws IOException {
        SimpleCsvWriter writer = new SimpleCsvWriter(output);


        String[] headers = new String[selectedFields.length];
        for (int i = 0; i < selectedFields.length; i++)
            headers[i] = headerNames.get(selectedFields[i]);
        writer.writeHeaders(headers);

        for (DeliveryEntry entry : entries) {
            writer.writeLine(selectFields(entry));
        }
        writer.close();
    }

    private String[] selectFields(DeliveryEntry entry) {
        HashMap<String, String> dict = entry.asDict();

        String[] fields = new String[selectedFields.length];
        for (int i = 0; i < selectedFields.length; i++)
            fields[i] = dict.get(selectedFields[i]);

        return fields;
    }

}
