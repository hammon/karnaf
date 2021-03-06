package karnaf;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import java.util.List;

/**
 * Created by michael on 4/10/15.
 */

//https://github.com/cbeust/jcommander
public class KarnafCmd {
    @Parameter
    public List<String> parameters = Lists.newArrayList();

    @Parameter(names = { "-f", "--file" }, description = "js file")
    public String file = "";

    @Parameter(names = { "-e", "--eval" }, description = "js script")
    public String eval = "";

}
