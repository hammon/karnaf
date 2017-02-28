
importFile("js/news/charGram.js");
importFile("js/news/ngram.js");

var GitModule = Java.type('modules.GitModule');

var TextDiffModule = Java.type('modules.TextDiffModule');
var textDiff = new TextDiffModule();

var phantomJsPath = "/home/michael/tools/phantomjs/bin/phantomjs";

var newsHomeDir = "/home/michael/.news/";

var sources = [
  // {"src":"http://www.linuxinsider.com/","dest":newsHomeDir + "linuxinsider.txt"},
   {"src":"http://cnn.com","dest": newsHomeDir + "cnn.txt"},
   {"src":"http://bbc.com","dest": newsHomeDir + "bbc.txt"},
  // {"src":"http://www.reuters.com/","dest": newsHomeDir + "reuters.txt"},
  {"src":"http://www.technewsworld.com/","dest": newsHomeDir + "technewsworld.txt"}
];

processSources();

var repo = new FileRepository(newsHomeDir + ".git");

var gitModule = new GitModule(repo);


var commits = JSON.parse(gitModule.getCommits("refs/heads/master"));

for(var i = 0;i < commits.length;i++){
  log.info("commit: " + commits[i].name);
}

if(commits.length > 1){
  var diffFiles = JSON.parse(gitModule.diff(commits[4].name,commits[0].name));
  for(var i = 0;i < diffFiles.length;i++){
    log.info("diffFiles: " + diffFiles[i].newId);
  }
}


// var git = new Git(repo);
// //var git = Git.open(new File(newsHomeDir + ".git"));
//
// // Stage all files in the repo including new files
// git.add().addFilepattern(".").call();
//
// // and then commit the changes.
// //git.commit().setMessage("Commit all changes including additions").call();
//
// var timestamp = df.format(Date.now());
//
// System.out.println("timestamp: " + timestamp);
//
// git.commit().setAuthor("ubuntu", "ubuntu@example.com").setMessage("Commit all " + timestamp).call();
//
// var branches = git.branchList().call();
//
// for (var i in branches) {
//     var branchName = branches[i].getName();
//
//     System.out.println("Commits of branch: " + branchName);
//     System.out.println("-------------------------------------");
//
//     var commits = git.log().add(repo.resolve(branchName)).call();
//
//     var it = commits.iterator();
//     while (it.hasNext()) {
//       var commit = it.next();
//       System.out.println(">>>>>>>> " );
//       System.out.println(commit.getName());
//       System.out.println(commit.getAuthorIdent().getName());
//       System.out.println(new Date(commit.getCommitTime() * 1000));
//       System.out.println(commit.getFullMessage());
//     }
// }

// var arrTokens = tokenize(readFile(newsHomeDir + "bbc.txt"),[" ","\r","\n",".",",","\"","'",":",";"]);
//
// var mapTokensCount = getTokensCount(arrTokens);
//
// var arrTokensCount = mapToJsonArray(mapTokensCount);
//
// log.info(JSON.stringify(arrTokensCount,null,2));

//processSources();

function processSources(){
  var allText = "";
  for(var i = 0;i < sources.length;i++){
    var pageInfo = getPageInfo(sources[i].src);

    log.info("<><><><><><><><><sources[i]: " + JSON.stringify(sources[i]));
    log.info("<><><><><><><><><pageInfo: " + JSON.stringify(pageInfo));
    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    writeFile(sources[i].dest,pageInfo.text);

//    for(var j = 0;j < pageInfo.links.length;j++){
      log.info("=================================");
      log.info(JSON.stringify(pageInfo.links));
      log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//    }

//    var oldText = readFile(sources[i].dest);
//    var diffs = JSON.parse( textDiff.diff(text,oldText));
    // for(var i = 0;i < diffs.deleted.length;i++){
    //   log.info("deleted: " + diffs.deleted[i]);
    // }

    // for(var i = 0;i < diffs.added.length;i++){
    //   if(diffs.added[i].trim().length > 15){
    //       log.info("=============================");
    //       log.info("added: " + diffs.added[i].trim());
    //       log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    //   }

//    }
 //   writeFile(sources[i].dest,text);
//    allText += text + "\r\n";


//    createNgramCounts(sources[i].dest);

    var map = getCharCount(pageInfo.text);
    var arr = mapToJsonArray(map);

    writeFile(sources[i].dest + ".charCount.json",JSON.stringify(arr,null,2));
  }
//  writeFile(newsHomeDir + "all.txt",allText);
//  createNgramCounts(newsHomeDir + "all.txt");

  //proc.exec("git add -A",new File(newsHomeDir));
  //proc.exec("git commit -a -m \"commit " + uuid() + "\"",new File(newsHomeDir));


}



//log.info(getPageText("http://bbc.com"));

//log.info(JSON.stringify(mapToJsonArray(getCharCount(getPageText("http://bbc.com"))),null,2));

function getPageText(url){
    var result = exec(phantomJsPath + " /home/michael/tools/phantomjs/pageInfo.js " + url + " --cookies-file=cookies.txt --web-security=no");
    log.info("getPageText: " + JSON.stringify(result));

    result.output = JSON.parse(result.output);

    //log.info(result.output.text);

    return result.output.text;
}

function getPageInfo(url){
    var result = exec(phantomJsPath + " /home/michael/tools/phantomjs/pageInfo.js " + url + " --cookies-file=cookies.txt --web-security=no");
    log.info("getPageInfo: " + JSON.stringify(result));

    result.output = JSON.parse(result.output);

    //log.info(result.output.text);

    return result.output;
}

function generateCsv(filePath){

}



function printMap(map) {
    var it = map.keySet().iterator();
    while(it.hasNext()){
        var key = it.next();
        log.info("Key : " + key + " Value : " + map.get(key));
    }
}
