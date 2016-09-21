importFile("js/globals.js");

importFile("js/ci/config.js");


var procRes = JSON.parse(proc.exec("docker ps"))


if(procRes && procRes.output){
    log.info("procRes.output:" + procRes.output);
    var lines = procRes.output.split("\n");
    if(lines && lines.length > 1){
        for(var i = 1; i < lines.length;i++){

            log.info("docker: " + lines[i]);
        }

    }
}
else{
    log.error("procRes error" + JSON.stringify(procRes));
}


