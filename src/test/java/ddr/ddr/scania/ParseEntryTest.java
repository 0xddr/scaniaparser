package ddr.ddr.scania;


import ddr.ddr.scania.model.EntryInfo;
import ddr.ddr.scania.model.HeaderInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by ddr on 2/12/14.
 */
@RunWith(Parameterized.class)
public class ParseEntryTest {

    private String qty;
    private String orderNo;
    private String lineNo;
    private String readyToPickUp;
    private String header;

    public ParseEntryTest(String header, String qty, String orderNo, String lineNo, String readyToPickUp) {
        this.qty = qty;
        this.orderNo = orderNo;
        this.lineNo = lineNo;
        this.readyToPickUp = readyToPickUp;
        this.header = header;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {" 402000077030 000030 2011-10-26 NEW!", "40", "2000077030", "000030", "2011-10-26"},
                {" 1201000059691 000010 2011-10-28 NEW!", "120", "1000059691", "000010", "2011-10-28"},
                {" 3,6001000059314 000010 2011-10-07 NEW!", "3,600", "1000059314", "000010", "2011-10-07"},
        });
    }

    @Test
    public void parseHeader_test() {
        EntryInfo info = ScaniaParser.parserEntry(header);

        Assert.assertEquals(qty, info.qty);
        Assert.assertEquals(lineNo, info.lineNo);
        Assert.assertEquals(orderNo, info.orderNo);
        Assert.assertEquals(readyToPickUp, info.readyToPickUp);
    }
}



