
//var res = JSON.parse(http.get("http://localhost/wp-json/wp/v2/posts"));
/*
log.info("wp get: -- " + JSON.stringify(res));

for(var i = 0;i < res.length; i++){
    log.info(" -- wp post: " + JSON.stringify(res[i]));
    log.info(" -- wp link: " + JSON.stringify(res[i].link));
}
*/


var postRes = JSON.parse(http.post("http://localhost/wp-json/wp/v2/posts","{'title':'My New Title'}","{'Authorization':'Basic ComeHere:ComeHere'}"));
log.info("wp post: -- " + JSON.stringify(postRes));