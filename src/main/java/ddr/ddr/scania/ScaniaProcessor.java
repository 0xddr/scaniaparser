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

/**
 * Created by ddr on 2/11/14.
 */
public class ScaniaProcessor {

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
        System.out.println(input);

        return new ScaniaParser().parse(input);
    }

    private void writeResults(DeliveryEntry[] entries, String output) throws IOException {
        SimpleCsvWriter writer = new SimpleCsvWriter(output);

        // writer.writeHeaders();
        for (DeliveryEntry entry : entries) {
            writer.writeLine(selectFields(entry));
        }
    }

    private String[] selectFields(DeliveryEntry entry) {
        return null;
    }


}
