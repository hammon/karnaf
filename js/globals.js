
log.info("Hello globals!");

log.info("Create your namespace @js/globals.js");

var System = java.lang.System;
var Map = java.util.Map;
var HashMap = Java.type('java.util.HashMap');


//var TreeMap = Java.type('java.util.TreeMap<java.lang.String,java.lang.Integer>');

var FileUtils = Packages.org.apache.commons.io.FileUtils;
var Properties = java.util.Properties;

var base64 = Packages.org.apache.commons.codec.binary.Base64;

var df = new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//var Date = java.util.Date;
var File = java.io.File;

var Git = org.eclipse.jgit.api.Git;

var FileRepository = org.eclipse.jgit.internal.storage.file.FileRepository;

function uuid(){
    return java.util.UUID.randomUUID().toString();
}

function importFile(path){
    log.info("importFile: " + path);
    //jsExec.eval(new File(path));

    log.info("jsExec.eval: " + jsExec.eval);

    try{
      jsExec.eval(FileUtils.readFileToString(new File(path)));
    }
    catch(e){
      log.error("Failed to importFile: " + path,e);
    }

}

function readFile(path){
    return FileUtils.readFileToString(new File(path));
}

function writeFile(path,data,append){
    FileUtils.writeStringToFile(new File(path),data,append || false);
}

function exec(cmd){
    return JSON.parse(proc.exec(cmd));
}

function mapToJsonObject(map){
    var obj = {};
    var it = map.keySet().iterator();
    while(it.hasNext()){
        var key = it.next();
        obj[key] = map.get(key);
        //log.info("Key : " + key + " Value : " + map.get(key));
    }
    return obj;
}

function mapToJsonArray(map){
    var arr = [];
    var it = map.keySet().iterator();
    while(it.hasNext()){
        var key = it.next();
        var obj = {};
        obj['key'] = key;
        obj['value'] = map.get(key);
        arr.push(obj);
    }

    arr.sort(function(a,b){
        return  b.value - a.value;
    });
    return arr;
}

function millisToHhMmSs(millis){
    var hours = Math.floor(millis / 36e5).toString(),
    mins = Math.floor((millis % 36e5) / 6e4).toString(),
    secs = Math.floor((millis % 6e4) / 1000).toString();

    if(hours.length === 1){
        hours = "0" + hours;
    }

    if(mins.length === 1){
        mins = "0" + mins;
    }

    if(secs.length === 1){
        secs = "0" + secs;
    }

    return hours + ':' + mins + ':' + secs;
}

log.info("globals loaded. " + uuid());
