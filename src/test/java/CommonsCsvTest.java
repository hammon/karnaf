import modules.MapUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import text.NGram;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by michael on 1/8/17.
 */
public class CommonsCsvTest {

    private static final Logger log = LoggerFactory.getLogger(CommonsCsvTest.class);

    String testString = "Contrary to the GNU Public License (GPL) the Apache Software License does not make any claims over your extensions. By extensions, we mean totally new code that invokes existing log4j classes. You are free to do whatever you wish with your proprietary log4j extensions. In particular, you may choose to never release your extensions to the wider public.\n" +
            "\n" +
            "We are very careful not to change the log4j client API so that newer log4j releases are backward compatible with previous versions. We are a lot less scrupulous with the internal log4j API. Thus, if your extension is designed to work with log4j version n, then when log4j release version n+1 comes out, you will probably need to adapt your proprietary extensions to the new release. ";

    @Test
    public void testWriteCsv(){

        NGram ngram = new NGram(testString);
        Map<String,Integer> mapCount = ngram.getTokensCount(1);

        MapUtils mapUtils = new MapUtils();

        mapUtils.mapToCsv(mapCount,"/home/michael/test.csv");
    }


}
