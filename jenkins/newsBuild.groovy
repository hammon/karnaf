


freeStyleJob('updateNews'){
    parameters{
        stringParam('Name', 'updateNews', 'name of the job')

        stringParam('newsHomeDir', 'newsDir', 'News home directory')

        textParam('sourcesJson',"""
        [
          {"src":"http://www.linuxinsider.com/","dest":$newsHomeDir + "/linuxinsider.txt"},
          {"src":"http://www.reuters.com/","dest": $newsHomeDir + "/reuters.txt"},
          {"src":"http://www.technewsworld.com/","dest": $newsHomeDir + "/technewsworld.txt"}
        ]
        """,'Json array of sources')
    }
    steps{

        shell("""
echo "Hello updateNews"
pwd

echo "WORKSPACE: $WORKSPACE"

#rm -rf $WORKSPACE/build

ls -l $WORKSPACE

""")

        gradle {
            rootBuildScriptDir(envVars['WORKSPACE'])
            buildFile('build.gradle')
            useWorkspaceAsHome(true)
            tasks('clean')
            tasks('buildJar')
        }

    }
    publishers {

    }
}