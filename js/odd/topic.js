
function topicCreate(name){
  return {
    id: uuid(),
    name: name || "Unkown",
    solved: false,
    votes: [],
    categories: [],
    solutions: []
  };
}

//function topicSaveToFile(topic){
//
//}
//
//function topicReadFromFile(){
//
//}
