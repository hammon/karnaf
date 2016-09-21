package karnaf;

import com.beust.jcommander.JCommander;
import modules.*;
//import modules.ProcessModule;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by michael on 6/25/16.
 */
public class Karnaf {
    final static Logger log = LoggerFactory.getLogger(Karnaf.class);

    public static void main(String[] args) {
        System.out.println("Hello karnaf.Karnaf!");

        Karnaf.configureLogger();

        log.info("Starting...");

        JSExecutor jsExec = new JSExecutor();

        jsExec.putBinding("jsExec",jsExec);
        jsExec.putBinding("log",log);

        jsExec.putBinding("proc",new ProcessModule());
        jsExec.putBinding("http",new HttpModule());
//        jsExec.putBinding("elasticSearch",new ElasticSearchModule());


        jsExec.eval(new File("js/globals.js"));


        log.info("Started");

        KarnafCmd cmd = new KarnafCmd();
        new JCommander(cmd,args);

        cmd.parameters.forEach(param -> {
            //System.out.println(param);
            log.info(param);
        });

        if(!cmd.file.isEmpty()){
            try {
                jsExec.eval(new File(cmd.file));
            } catch (Exception e) {
                log.error("Failed to eval file" + cmd.file,e);
            }
        }
        else if(!cmd.eval.isEmpty()){
            try {
                jsExec.eval(cmd.eval);
            } catch (Exception e) {
                log.error("Failed to eval " + cmd.eval,e);
            }

        }
        else{
            log.info("No params");
        }
    }

    public static void configureLogger() {
        Properties p = new Properties();

        try {
            p.load(new FileInputStream("./conf/log4j.properties"));
            PropertyConfigurator.configure(p);
            log.info("Logger configured!");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
