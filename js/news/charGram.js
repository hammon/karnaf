

function getCharCount(str){
    var map = new HashMap();
    for(var i = 0; i < str.length;i++){
        if(map.containsKey(str[i])){
            map.put(str[i], map.get(str[i]) + 1);
        }
        else{
            map.put(str[i],1);
        }
    }
    //log.info("getCharCount map: " + map);
    return map;
}

function getCharGramCount(text,arrCharCount){
  var map = new HashMap();
  for(var i = 0;i < arrCharCount.length;i++){

    var key = arrCharCount[i].key;
    var value = arrCharCount[i].value;
//    log.info("getCharGramCount key:" + key + " value: " + value);
    if(value > 1){
        var index = text.indexOf(key);
        while (index > -1 && index < text.length - 1) {
          var str = text.substr(index,key.length + 1);
        //  log.info("getCharGramCount str:" + str + " index: " + index);
          if(map.containsKey(str)){
              map.put(str, map.get(str) + 1);
          }
          else{
              map.put(str,1);
          }
          index = text.indexOf(key,index + 1);
        }
    }
  }
  return map;
}

function createTextCounts(path){
  var text = readFile(path);

  var map = getCharCount(text);
  var arr = mapToJsonArray(map);

  //writeFile(path + ".charCount.json",JSON.stringify(arr,null,2));

  while (arr.length > 0) {
    writeFile(path + "." + arr[0].key.length + "charCount.json",JSON.stringify(arr,null,2));
    log.info("createTextCounts arr.length: " + arr.length + " key.length: " + arr[0].key.length);
    map = getCharGramCount(text,arr);
    arr = mapToJsonArray(map);
    arr = arr.filter(function(obj){
      return obj.value > 1;
    });
  }
}
