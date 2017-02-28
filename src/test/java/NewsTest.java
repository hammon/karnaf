import karnaf.Karnaf;
import modules.MapUtils;
import modules.ProcessModule;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import text.NGram;

import java.util.Map;

/**
 * Created by michael on 1/13/17.
 */
public class NewsTest {

    private static final Logger log = LoggerFactory.getLogger(NewsTest.class);

    @Test
    public void testNews(){

        Karnaf.configureLogger();

        ProcessModule proc = new ProcessModule();
        String phantomJsPath = "/home/michael/tools/phantomjs/bin/phantomjs";
        String url = "http://cnn.com";

        // Get previus ngrams


        // Get text
        String res = proc.exec(phantomJsPath + " /home/michael/tools/phantomjs/pageInfo.js "
                + url + " --cookies-file=cookies.txt --web-security=no");
        log.info("getPageText: " + res);

        JSONObject resObj = new JSONObject(res);
        JSONObject outputObj = new JSONObject(resObj.getString("output"));
        log.info( outputObj.getString("title"));

        // Get ngrams
        NGram ngram = new NGram(outputObj.getString(("text")));
        Map count1map = ngram.getTokensCount(1);
        log.info(count1map.toString());

        Map<String,Integer> resMap = new MapUtils().sum(count1map,count1map);
        log.info(resMap.toString());

        // Save


        // Git commit


        //Compare with previous ngrams

    }
}
