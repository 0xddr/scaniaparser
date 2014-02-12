package ddr.ddr.scania;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.PrintStream;

public class App {

    public static void main(String[] args) throws IOException, TikaException, SAXException {
        App app = new App();
        Args args1;

        try {
            args1 = app.parseArgs(args);
        } catch (IllegalArgumentException e) {
            app.printAppUsage(System.out);
            return;
        }

        ScaniaProcessor parser = new ScaniaProcessor();
        parser.process(args1.input_filename, args1.output_filename);
    }

    private void printAppUsage(PrintStream stream) {
        stream.println("tu jakiś opis jak tego użyewać z konsoli");           // TODO
    }

    private Args parseArgs(String[] args) throws IllegalArgumentException {
        Args args1 = new Args();

        args1.input_filename = args[0];
        args1.output_filename = args[1];

        return args1;


        //throw new IllegalArgumentException();
    }

    private class Args {
        String input_filename;
        String output_filename;
    }
}
