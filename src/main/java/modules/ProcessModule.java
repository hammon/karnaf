package modules;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by malexan on 23/11/2014.
 */
public class ProcessModule {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(ProcessModule.class);

    public String exec(String cmd){
        return exec(cmd,new File(System.getProperty("user.dir")));
    }

    public String exec(String[] cmdArr){
        return exec(cmdArr,new File(System.getProperty("user.dir")));
    }

    public String exec(String[] cmdArr,File workDir){
        String res = "";
        JSONObject jsonRes = new JSONObject();
        try {
            jsonRes.put("exitCode",123);
        } catch (JSONException e) {
            log.error("exec error: " + e.getMessage(), e);
        }

        log.info("cmd arr: " + cmdArr);

        try {


            java.lang.Process p = Runtime.getRuntime().exec(cmdArr,null,workDir);

            res = getOutput(p);
            int exitCode = p.waitFor();
            jsonRes.put("exitCode",exitCode);
            jsonRes.put("output",res);
            log.info(cmdArr[0] + " exitCode: " + exitCode);
        }
        catch (Exception err) {
            //err.printStackTrace();
            log.error(err.getMessage(),err);
        }

        return jsonRes.toString();
    }

    public String exec(String cmd,File workDir){
        String res = "";
        JSONObject jsonRes = new JSONObject();
        try {
            jsonRes.put("exitCode",123);
        } catch (JSONException e) {
            log.error(e.getMessage(), e);
        }

        log.info("cmd str: " + cmd);

        try {
            java.lang.Process p = Runtime.getRuntime().exec(cmd,null,workDir);

            res = getOutput(p);

            int exitCode = p.waitFor();
            jsonRes.put("exitCode",exitCode);
            jsonRes.put("output",res);
            log.info(cmd + " exitCode: " + exitCode);
        }
        catch (Exception err) {
            //err.printStackTrace();
            log.error(err.getMessage(),err);
        }

        return jsonRes.toString();
    }

    public String exec(String cmd,boolean bWaitForExit){
        return exec(cmd,new File(System.getProperty("user.dir")),bWaitForExit);
    }

    public String exec(final String cmd,final File workDir,boolean bWaitForExit){
        String res = "";

        log.info("cmd: " + cmd + " bWaitForExit: " + bWaitForExit);

        try {
            if(bWaitForExit){
                java.lang.Process p = Runtime.getRuntime().exec(cmd,null,workDir);
                res = getOutput(p);

                int exitCode = p.waitFor();
                log.info(cmd + " exitCode: " + exitCode);
            }
            else{
                new Thread(new Runnable() {
                    public void run() {
                        java.lang.Process p = null;
                        try {
                            p = Runtime.getRuntime().exec(cmd,null,workDir);

                            getOutput(p);

                            int exitCode = p.waitFor();
                            log.info(cmd + " exitCode: " + exitCode);
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                log.info("Started.");
            }

        }
        catch (Exception err) {
            //err.printStackTrace();
            log.error(err.getMessage(),err);
        }


        return res;
    }

    private String getOutput( java.lang.Process p) {
        String res = "";
        String line;
        try {
            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((line = bri.readLine()) != null) {
                res += line + "\n";
                log.debug(line);
            }

            bri.close();
            while ((line = bre.readLine()) != null) {
                res += line + "\n";
                log.debug(line);
            }
            bre.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }
}
