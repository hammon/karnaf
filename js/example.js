
var response = http.get("http://example.com");

log.info("Reponse: " + response);

writeFile("response.txt",response);