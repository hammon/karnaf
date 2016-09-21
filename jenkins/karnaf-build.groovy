
def envVars = System.getenv()

def addBatch(def ctx){


    ctx.batchFile("""
IF DEFINED DBSQLFILES (
	cd %BUILDER_HOME%\\tools\\idit-promotion-client\\bin
	dbcp.cli.cmd db.type-names Oracle,DB2,MSSQL db.types.selected ${dbId} db.username %dbUsername% db.password %dbPassword% machine %dbHostname% port %dbPort% dbname %dbName%${dbInst}filename "%dbSqlFiles%"
) ELSE (
	Echo dbSqlFiles not defined. Skipping database update.
)
""")
}


freeStyleJob('karnaf-build'){
    parameters{

    }
    steps{

        shell("""
echo "Hello karnaf-build"
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