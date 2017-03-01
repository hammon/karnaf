# Karnaf

Javascript scripting framework based on [Nashorn](http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html).


Setup
------------------------------
Make sure you have [git](https://git-scm.com/downloads) and [gradle](http://gradle.org/gradle-download/) installed.

Clone the repo and run gradle build
```bash
git clone https://github.com/hammon/karnaf.git
cd karnaf
gradle buildJar

```
 
How to use
------------------------------

##### Dependencies
Java dependencies are defined in the dependencies section of the [build.gradle](build.gradle)

##### Global objects
Javascript global objects can be defined in [java](src/main/java/karnaf/Karnaf.java):


```
JSExecutor jsExec = new JSExecutor();
jsExec.putBinding("http",new HttpModule());
```

or in [Javascript](js/globals.js):

```
var File = java.io.File;
var FileUtils = Packages.org.apache.commons.io.FileUtils;

function readFile(path){
    return FileUtils.readFileToString(new File(path));
}

function writeFile(path,data,append){
    FileUtils.writeStringToFile(new File(path),data,append || false);
}
```

##### Example

Send http request and save respone:
```
var response = http.get("http://example.com");
writeFile("path/to/file/response.json",response);
```

##### Execute


```
./karnaf.sh -f js/example.js
```

Modules
------------------------------

This is work in progress.

Conributions are more then welcome!

##### Files

##### Process

##### Ssh

##### Http

##### DB

##### Git

##### Jenkins

##### Docker

##### AWS

##### ElasticSearch
