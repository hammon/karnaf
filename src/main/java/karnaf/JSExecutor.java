package karnaf;

import org.apache.commons.io.FileUtils;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.lib.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by michael on 4/19/15.
 */
public class JSExecutor {
    final static Logger log = LoggerFactory.getLogger(JSExecutor.class);
    ScriptEngine _engine = new ScriptEngineManager().getEngineByName("nashorn");
    Bindings _bindings = _engine.getBindings(ScriptContext.ENGINE_SCOPE);

    public JSExecutor(){
       // _bindings.put("jsExec",this);
        java.util.HashMap map = new java.util.HashMap();
        java.util.TreeMap tmap = new java.util.TreeMap();
       // map.containsKey()
        //map.get()
       // map.keySet().iterator()
       // Git git = Git.open( new F‌ile( "/path/to/repo/.git" ) );
       // FileRepository repo

    }

    public void putBinding(String name, Object value){
        _bindings.put(name,value);
    }

    public Object eval(File file){
        try {
            return eval(FileUtils.readFileToString(file, "UTF-8"));
        } catch (IOException e) {
            log.error("Failed to execute " + file.getAbsolutePath(),e);
        }
        return null;
    }

    public Object eval(String script){
        try {
            return _engine.eval(script,_bindings);
        } catch (ScriptException e) {
            log.error("Failed to eval script",e);
        }
        return null;
    }
}
