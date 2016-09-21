package modules;

import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.DepthWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import text.diff_match_patch;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//import org.webjars.fraser.neil.plaintext.diff_match_patch;
//import org.webjars.fraser.neil.plaintext.diff_match_patch.Diff;


/**
 * Created by michael on 6/27/16.
 */
public class GitModule {

    final static Logger log = LoggerFactory.getLogger(GitModule.class);

//    Repository _repo = null;
    Git _git = null;

    public static void main(String[] args)  {
        try {
            karnaf.Karnaf.configureLogger();
            Repository repository = new FileRepository(new File("/home/michael/.news/.git"));

            GitModule gitModule = new GitModule(repository);
            // the diff works on TreeIterators, we prepare two for the two branches
            AbstractTreeIterator oldTreeParser = gitModule.prepareTreeParser("667cfb7ca4d956a9b4e44f705d067b8e11b876ba");
            AbstractTreeIterator newTreeParser = gitModule.prepareTreeParser("93c0ef404d427b611aabe81797287987ae12eac7");

            //ObjectReader reader = repository.newObjectReader();
            // then the procelain diff-command returns a list of diff entries
            try (Git git = new Git(repository)) {
                List<DiffEntry> diff = git.diff().
                        setOldTree(oldTreeParser).
                        setNewTree(newTreeParser).
//                        setPathFilter(PathFilter.create("README.md")).
                        call();
                for (DiffEntry entry : diff) {
                    System.out.println("Entry: " + entry + ", from: " + entry.getOldId() + ", to: " + entry.getNewId());
//                    try (DiffFormatter formatter = new DiffFormatter(System.out)) {
//                        formatter.setRepository(repository);
//                        formatter.format(entry);
//                    }


                    String oldContent = gitModule.getObjectContent(repository, entry.getOldId().toObjectId());
                    String newContent = gitModule.getObjectContent(repository, entry.getNewId().toObjectId());


                    text.diff_match_patch difference = new text.diff_match_patch();

                    LinkedList<diff_match_patch.Diff> deltas = difference.diff_main(oldContent, newContent);
                    difference.diff_cleanupSemantic(deltas);

                    String text1 = "";
                    String text2 = "";
                    for(diff_match_patch.Diff d: deltas){

                        if(d.operation==diff_match_patch.Operation.DELETE)
                            text1 += d.text;
                        else if(d.operation==diff_match_patch.Operation.INSERT)
                            text2 += d.text;
                        else
                        {
//                            text1 += d.text;
//                            text2 += d.text;
                        }

                    }
                    System.out.println("text1: " + text1 );
                    System.out.println("text2: " + text2 );
                }
            }
        }
        catch (Exception e){
            log.error("Error",e);
        }
    }

    public GitModule(String repoPath){
        try {
            _git = new Git(new FileRepository(repoPath));
        } catch (IOException e) {
            log.error("Failed to init repo: " + repoPath,e);
        }
    }

    public GitModule(Repository repo){
        _git = new Git(repo);
    }

    public GitModule(Git git){
        _git = git;
    }

    public void setRepo(Repository repo){
        _git = new Git(repo);
    }

    public void setRepo(String repoPath){
        try {
            setRepo(new FileRepository(repoPath));
        } catch (IOException e) {
            log.error("setRepo failed.",e);
        }
    }

    public Repository getRepo(){
        return _git.getRepository();
    }

    public Git getGit(){
        return _git;
    }

    public void commit(String repoPath,String userName, String userMail, String message){
        try {
            _git = new Git(new FileRepository(repoPath));
            commit(_git.getRepository(),userName,userMail,message);
        } catch (Exception e) {
            log.error("Failed to commit to git repo: " + repoPath,e);
        }
    }

    public void commit(Repository repo, String userName, String userMail, String message){
        _git = new Git(repo);
        commit(userName,userMail,message);
    }

    public void commit(String userName, String userMail, String message){
        try {
            _git.add().addFilepattern(".").call();
            _git.commit().setAuthor(userName, userMail).setMessage(message).call();

        } catch (Exception e) {
            log.error("Failed to commit to git repo: " + _git.getRepository().getDirectory().getAbsolutePath(),e);
        }
    }

    public String getCommits(String tag){
        JSONArray arrCommints = new JSONArray();

        try {
            Iterator<RevCommit> commits = _git.log().add(_git.getRepository().resolve(tag)).call().iterator();
            while (commits.hasNext()){
                RevCommit commit = commits.next();

                JSONObject objCommit = new JSONObject();
                objCommit.put("name",commit.getName());
                objCommit.put("message",commit.getFullMessage());
                objCommit.put("userName",commit.getAuthorIdent().getName());
                objCommit.put("commitTime",new Date(commit.getCommitTime() * 1000).toString());

                arrCommints.put(objCommit);
            }
        } catch (Exception e) {
            log.error("getCommits failed.",e);
        }
        return arrCommints.toString();
    }

    public String getCommits(Repository repo, String tag){
        _git = new Git(repo);
       return getCommits(_git.getRepository(),tag);
    }

    public String getCommits(String repoPath, String tag){
        try {
            _git = new Git(new FileRepository(repoPath));
            return  getCommits(_git.getRepository(),tag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getObjectContent(String objId){
        return getObjectContent(ObjectId.fromString(objId));
    }

    public String getObjectContent(ObjectId objId) {
        return getObjectContent(_git.getRepository(),objId);
    }

    public String getObjectContent(Repository repo, ObjectId objId) {
        _git = new Git(repo);
        ObjectLoader loader = null;
        try {
            loader = _git.getRepository().open(objId.toObjectId());
            StringWriter writer = new StringWriter();
            IOUtils.copy(loader.openStream(), writer);
            return writer.toString();
        } catch (IOException e) {
            log.error("getObjectContent failed.",e);
        }

        return "";
    }

    public AbstractTreeIterator prepareTreeParser(String objectId) {
        return prepareTreeParser(_git.getRepository(),objectId);
    }

    public AbstractTreeIterator prepareTreeParser(Repository repo, String objectId) {

//        log.info("prepareTreeParser objectId:" + objectId);

        try  {
            _git = new Git(repo);
            RevWalk walk = new RevWalk(repo);
            RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
            try (ObjectReader oldReader = _git.getRepository().newObjectReader()) {
                oldTreeParser.reset(oldReader, tree.getId());
            }
            walk.dispose();
            return oldTreeParser;
        }
        catch (Exception e){
            log.error("prepareTreeParser failed.",e);
        }
        return null;
    }

    public String diff(String idA,String idB){
        JSONArray arrDiffs = new JSONArray();
        AbstractTreeIterator treeParserA = prepareTreeParser(idA);
        AbstractTreeIterator treeParserB = prepareTreeParser(idB);

        List<DiffEntry> diff = null;
        try {
            diff = _git.diff().
                    setOldTree(treeParserA).
                    setNewTree(treeParserB).
    //                        setPathFilter(PathFilter.create("README.md")).
            call();
        } catch (Exception e) {
            log.error("diff failed.",e);
            return "";
        }

        for (DiffEntry entry : diff) {
//            log.info("diff getOldId:" + entry.getOldId().toObjectId().getName());
//            log.info("diff getNewId:" + entry.getNewId().toObjectId().getName());
            JSONObject objDiff = new JSONObject();
            objDiff.put("oldId",entry.getOldId().toObjectId().getName());
            objDiff.put("newId",entry.getNewId().toObjectId().getName());
            arrDiffs.put(objDiff);
        }
        return arrDiffs.toString();
    }
}
