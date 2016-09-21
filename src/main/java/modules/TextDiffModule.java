package modules;

import org.json.JSONArray;
import org.json.JSONObject;
import text.diff_match_patch;

import java.util.LinkedList;

/**
 * Created by michael on 7/2/16.
 */
public class TextDiffModule {
    diff_match_patch _diff = new diff_match_patch();

    public String diff(String a, String b){
        LinkedList<diff_match_patch.Diff> deltas = _diff.diff_main(a, b);
        _diff.diff_cleanupSemantic(deltas);

        JSONArray arrAdded = new JSONArray();
        JSONArray arrDeleted = new JSONArray();

        for(diff_match_patch.Diff d: deltas){
            if(d.operation==diff_match_patch.Operation.DELETE)
                arrDeleted.put(d.text);
            else if(d.operation==diff_match_patch.Operation.INSERT)
                arrAdded.put(d.text);
            else
            {
//                            text1 += d.text;
//                            text2 += d.text;
            }
        }

        JSONObject objRet = new JSONObject();
        objRet.put("added",arrAdded);
        objRet.put("deleted",arrDeleted);
        return objRet.toString();
    }
}
