package modules;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.*;

/**
 * Created by michael on 1/11/17.
 */
public class MapUtils {

    private static final Logger log = LoggerFactory.getLogger(MapUtils.class);

    public void mapToCsv(Map<String, Integer> mapCount, String outputFile) {
        Iterator<Map.Entry<String, Integer>> it = mapCount.entrySet().iterator();

        List<String[]> records = new ArrayList<String[]>();
        while (it.hasNext()) {
            Map.Entry<String, Integer> kv = it.next();
            records.add(new String[]{kv.getKey(),kv.getValue().toString()});
        }

        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.EXCEL;
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            csvFilePrinter.printRecords(records);

            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();
        }
        catch (Exception e){
            log.error("Error in mapToCsv",e);
        }
    }

    public HashMap<String,Integer> csvToMap(File file){
        HashMap<String,Integer> mapRes = new HashMap<>();

        if(!file.exists()){
            return mapRes;
        }

        try
        {
            Reader in = new FileReader(file.getAbsolutePath());
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                String str = record.get(0);
                Integer count = Integer.parseInt(record.get(1));
                mapRes.put(str,count);
            }
        }
        catch (Exception e){
            log.error("Error in csvToMap.",e);
        }

        return mapRes;
    }

    public Map<String,Integer> sum(Map<String,Integer> mapA,Map<String,Integer> mapB){
        Map<String,Integer> mapRes = new HashMap<String,Integer>();
        Iterator<Map.Entry<String, Integer>> it = mapA.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Integer> kv = it.next();
            int a = 0;
            int b = 0;
            if(mapA.containsKey(kv.getKey())){
                a = mapA.get(kv.getKey());
            }
            if(mapB.containsKey(kv.getKey())){
                b = mapB.get(kv.getKey());
            }
            mapRes.put(kv.getKey(),a + b);
            mapB.remove(kv.getKey());
        }

        it = mapB.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> kv = it.next();
            mapRes.put(kv.getKey(),kv.getValue());
        }
        return mapRes;
    }

    public Map<String,Integer> sub(Map<String,Integer> mapA,Map<String,Integer> mapB){
        Map<String,Integer> mapRes = new HashMap<String,Integer>();
        Iterator<Map.Entry<String, Integer>> it = mapA.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Integer> kv = it.next();
            int a = 0;
            int b = 0;
            if(mapA.containsKey(kv.getKey())){
                a = mapA.get(kv.getKey());
            }
            if(mapB.containsKey(kv.getKey())){
                b = mapB.get(kv.getKey());
            }
            mapRes.put(kv.getKey(), a - b);
            mapB.remove(kv.getKey());
        }

        it = mapB.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> kv = it.next();
            mapRes.put(kv.getKey(),kv.getValue());
        }
        return mapRes;
    }
}
