package ddr.ddr.scania.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ddr on 2/11/14.
 */
public class SimpleCsvWriter {

    private static final char SEPARATOR = ',';
    private State m_state;
    private PrintWriter m_writer;

    public SimpleCsvWriter(String filename) throws IOException {
        m_writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        m_state = State.BEFORE;
    }

    public void writeLine(String[] fields) {
        if (m_state == State.CLOSED)
            throw new IllegalStateException("Writer has been closed already");

        String line = stringifyFields(fields);
        m_writer.println(line);

        m_state = State.LINE_ADDED;
    }

    private String stringifyFields(String[] fields) {
        StringBuilder sb = new StringBuilder(150);

        for (int i = 0; i < fields.length; i++) {
            if (i > 0)
                sb.append(SEPARATOR);

            if (fields[i] != null) {
                sb.append('"');
                sb.append(fields[i]);
                sb.append('"');
            }
        }

        return sb.toString();
    }

    public void writeHeaders(String[] fields) {
        if (m_state != State.BEFORE)
            throw new IllegalStateException("Headers have to be written first!");

        writeLine(fields);
    }

    public void close() {
        m_writer.close();
        m_state = State.CLOSED;
    }

    private enum State {
        BEFORE, LINE_ADDED, CLOSED
    }

}
