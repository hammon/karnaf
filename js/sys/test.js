
(function ()
{
   var keys=Object.keys( this );
   for (var i in keys)
   {
     // if (typeof this[keys[i]] != 'function')
     //+ this[keys[i]] + " - "
      log.info(keys[i] + " - "  + typeof this[keys[i]]);
   }
})();


function testSsh(){
    ssh.connect("127.0.0.1","michael","");

    log.info("hostname: " + ssh.exec("hostname"));

    log.info("pwd: " + ssh.exec("pwd"));

    log.info("ls: " + ssh.exec("ls -l"));

    ssh.disconnect();
}