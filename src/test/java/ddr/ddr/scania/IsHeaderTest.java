package ddr.ddr.scania;


import ddr.ddr.scania.ScaniaParser;
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
public class IsHeaderTest {

    private boolean isHeader;
    private String header;

    public IsHeaderTest(String header, boolean isHeader) {
        this.isHeader = isHeader;
        this.header = header;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Delivery Schedule Current week:  39 ", false},
                {"ROOLEX ZAKŁAD MECHANICZNY", false},
                {" 452000077030 000020 2011-10-26 NEW!", false},
                {" 202000076293 000020 2011-10-14", false},
                {"0480479 BRACKET PCS", true},
                {"1493665 BRACKET/ PCS", true},
                {"1784016 FLOOR SUPPORT PCS", true},
                {"1858648 BRACKET WINDOW \nPANEL\n\nPCS", true}

        });
    }

    @Test
    public void isHeader_test() {
        Assert.assertEquals(isHeader, ScaniaParser.isHeader(header));
    }
}



