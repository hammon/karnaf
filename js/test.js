
writeFile("workdir.txt","Hello workdir data.");

log.info("readFile: " + readFile("workdir.txt"));

importFile("js/odd/topic.js");

log.info(JSON.stringify(topicCreate("Test Topic")));

importFile("js/odd/citizen.js");

log.info(JSON.stringify(createCitizen("Test Citizen")));

log.info("wp get: -- " + http.get("http://10.0.0.12/wp-json/wp/v2/posts"));



function wpPost(){
  var basicAuth = base64.encodeBase64(new java.lang.String("wordpressUser:wordpressPass").trim().getBytes());
  log.info("wp post: -- " +
      http.post(
          "http://10.0.0.12/wp-json/wp/v2/posts",
          JSON.stringify(
              {
                  'title':'Hello api v2 !!',
                  'content':'Hello content api v2 !!'
              }
          ),
          JSON.stringify(
              {
                  "Authorization":"Basic " + basicAuth,
                  "Content-Type":"application/json",
                  "Accept":"application/json",
                  "charset":"UTF-8"
              }
          )
      )
  );
}
