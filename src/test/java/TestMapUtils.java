import karnaf.Karnaf;
import modules.MapUtils;
import org.apache.commons.io.FileSystemUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 2/28/17.
 */
public class TestMapUtils {
    private static final Logger log = LoggerFactory.getLogger(TestMapUtils.class);
    Map<String, Integer> createMap()
    {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("5 five", 5);
        return map;
    }

    @Test
    public void testSaveCsv(){
        Karnaf.configureLogger();

        File testCsv = new File("testSave.csv");

        if(testCsv.exists()){
            testCsv.delete();
        }

        Assert.assertTrue(!testCsv.exists());

        MapUtils mapUtils = new MapUtils();

        log.info("File path: " + testCsv.getAbsolutePath());
        mapUtils.mapToCsv(createMap(),testCsv.getAbsolutePath());

        Assert.assertTrue(testCsv.exists());

        Assert.assertArrayEquals(createMap().keySet().toArray(),mapUtils.csvToMap(testCsv).keySet().toArray());

        log.info("map from file: " + mapUtils.csvToMap(testCsv).toString());
    }

    @Test
    public void testSum(){
        Karnaf.configureLogger();
        MapUtils mapUtils = new MapUtils();

        Map<String,Integer> mapA = createMap();
        mapA.remove("one");
        Map<String,Integer> mapB= createMap();
        mapB.remove("three");

        Map<String,Integer> mapRes =  mapUtils.sum(mapA,mapB);

        log.info("map from file: " + mapRes.toString());

        Assert.assertEquals(1,mapRes.get("one").longValue());
        Assert.assertEquals(4,mapRes.get("two").longValue());
        Assert.assertEquals(3,mapRes.get("three").longValue());
        Assert.assertEquals(8,mapRes.get("four").longValue());
        Assert.assertEquals(10,mapRes.get("5 five").longValue());
    }

    @Test
    public void testSub(){
        Karnaf.configureLogger();
        MapUtils mapUtils = new MapUtils();

        Map<String,Integer> mapA = createMap();
        mapA.remove("one");
        Map<String,Integer> mapB= createMap();
        mapB.remove("three");

        mapA.put("two",22);

        Map<String,Integer> mapRes =  mapUtils.sub(mapA,mapB);

        log.info("map from file: " + mapRes.toString());

        Assert.assertEquals(1,mapRes.get("one").longValue());
        Assert.assertEquals(20,mapRes.get("two").longValue());
        Assert.assertEquals(3,mapRes.get("three").longValue());
        Assert.assertEquals(0,mapRes.get("four").longValue());
        Assert.assertEquals(0,mapRes.get("5 five").longValue());
    }

}


