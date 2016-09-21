
function createCitizen(name){
  return {
    id: uuid(),
    name: name || "Unkown",
    voteRights:[]
  };
}
