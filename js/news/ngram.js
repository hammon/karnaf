

function tokenize(text,separators){
  text = text.toLowerCase();
  var tokens = [];
  var str = "";
  for(var i = 0; i < text.length; i++){
    if(separators.indexOf(text[i]) > -1){
      if(str.trim().length > 0){
        tokens.push(str);
      }
      str = "";
    }
    else{
      str += text[i];
    }
  }
  return tokens;
}

function getTokensCount(tokens){
  var map = new HashMap();
  for(var i = 0; i < tokens.length;i++){
      if(map.containsKey(tokens[i])){
          map.put(tokens[i], map.get(tokens[i]) + 1);
      }
      else{
          map.put(tokens[i],1);
      }
  }
  //log.info("getCharCount map: " + map);
  return map;
}

function getNGramCount(arrTokens,arrTokensCount){
  var map = new HashMap();
  for(var i = 0;i < arrTokensCount.length;i++){

    var key = arrTokensCount[i].key;
    var value = arrTokensCount[i].value;
  //  log.info("getNGramCount key:" + key + " value: " + value);
    if(value > 1){
        var arrKey = key.split(" ");
        var firstToken = arrKey[0] || key;
        var index = arrTokens.indexOf(firstToken);
        while (index > -1) {
          //var str = text.substr(index,key.length + 1);
          var token = arrTokens.slice(index,index + arrKey.length + 1).join(" ");
          if(token.startsWith(key)){
          //  log.info("getNGramCount token: '" + token + "' index: " + index + " arrKey.length: " + arrKey.length);
            if(map.containsKey(token)){
                map.put(token, map.get(token) + 1);
            }
            else{
                map.put(token,1);
            }
          }

          index = arrTokens.indexOf(firstToken,index + 1);
        }
    }
  }
  return map;
}

function createNgramCounts(path){
  var text = readFile(path);

  text = text.toLowerCase();

  var arrTokens = tokenize(text,[" ","\r","\n",".",",","\"","'",":",";","[","]","{","}","(",")"]);

  var mapTokensCount = getTokensCount(arrTokens);

  var arrTokensCount = mapToJsonArray(mapTokensCount);
  writeFile(path + ".ngramCount.json",JSON.stringify(arrTokensCount,null,2));

  while (arrTokensCount.length > 0) {
    log.info("createNgramCounts arr.length: " + arrTokensCount.length + " key.length: " + arrTokensCount[0].key.split(" ").length);
    writeFile(path + "." + arrTokensCount[0].key.split(" ").length + "ngramCount.json",JSON.stringify(arrTokensCount,null,2));

    mapTokensCount = getNGramCount(arrTokens,arrTokensCount);

    arrTokensCount = mapToJsonArray(mapTokensCount);
    arrTokensCount = arrTokensCount.filter(function(obj){
      return obj.value > 1;
    });
  }
}
