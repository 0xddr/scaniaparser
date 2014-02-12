package ddr.ddr.scania;


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
public class ParseHeaderTest {

    private String desc;
    private String partNo;
    private String header;

    public ParseHeaderTest(String header, String partNo, String desc) {
        this.partNo = partNo;
        this.desc = desc;
        this.header = header;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0480479 BRACKET PCS", "0480479", "BRACKET"},
                {"1493665 BRACKET/ PCS", "1493665", "BRACKET/"},
                {"1784016 FLOOR SUPPORT PCS", "1784016", "FLOOR SUPPORT"},
                {"1858648 BRACKET WINDOW \nPANEL\n\nPCS", "1858648", "BRACKET WINDOW PANEL"}

        });
    }

    @Test
    public void parseHeader_test() {
        HeaderInfo info = ScaniaParser.parseHeader(header);


        Assert.assertEquals(partNo,  info.partNo);
        Assert.assertEquals(desc, info.description);
    }
}



