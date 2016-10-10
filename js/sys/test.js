
ssh.connect("127.0.0.1","michael","");

log.info("hostname: " + ssh.exec("hostname"));

log.info("pwd: " + ssh.exec("pwd"));

log.info("ls: " + ssh.exec("ls -l"));

ssh.disconnect();