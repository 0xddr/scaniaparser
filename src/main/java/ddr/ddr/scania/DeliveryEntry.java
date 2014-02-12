package ddr.ddr.scania;

import ddr.ddr.scania.model.EntryInfo;
import ddr.ddr.scania.model.HeaderInfo;

import java.util.HashMap;


public class DeliveryEntry {

    public final boolean isNew = true;
    public String partNo;
    public String description;
    public String unit = "PCS";
    public String orderNo;
    public String lineNo;
    public String quantity;
    public String readyToRickUp;

    public DeliveryEntry() {

    }

    public DeliveryEntry(HeaderInfo headerInfo, EntryInfo entryInfo) {
        partNo = headerInfo.partNo;
        description = headerInfo.description;

        orderNo = entryInfo.orderNo;
        lineNo = entryInfo.lineNo;
        quantity = entryInfo.qty;
        readyToRickUp = entryInfo.readyToPickUp;
    }
    
    public HashMap<String, String> asDict() {
        HashMap<String, String> dict = new HashMap<>(6);

        dict.put("partNo", partNo);
        dict.put("description", description);
        dict.put("orderNo", orderNo);
        dict.put("lineNo", lineNo);
        dict.put("quantity", quantity);
        dict.put("readyToRickUp", readyToRickUp);

        return dict;
    }

    
}
