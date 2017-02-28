package modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by michael on 1/15/17.
 */
public class NewsServices {
    private static final Logger log = LoggerFactory.getLogger(NewsServices.class);

    public String getPageInfoJsonString(String url){
        ProcessModule proc = new ProcessModule();
        String phantomJsPath = "/home/michael/tools/phantomjs/bin/phantomjs";
        String res = proc.exec(phantomJsPath + " /home/michael/tools/phantomjs/pageInfo.js "
                + url + " --cookies-file=cookies.txt --web-security=no");
        log.debug("getPageInfoJsonString: " + res);
        return res;
    }
}
