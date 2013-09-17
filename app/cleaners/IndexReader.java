package cleaners;

import models.CustomerOrder;
import models.Item;
import models.MetaData;
import org.yaml.snakeyaml.Yaml;
import play.db.jpa.GenericModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: daviddale
 * Date: 9/10/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexReader {

    public IndexReader(){

    }

    public List read( File file ) throws IOException {
        Yaml yaml = new Yaml();
        return (List) yaml.load( new FileInputStream( file ) );
    }



}
