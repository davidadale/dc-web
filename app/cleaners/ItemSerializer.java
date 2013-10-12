package cleaners;

import com.google.gson.*;
import models.Item;
import models.MetaData;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: daviddale
 * Date: 9/23/13
 * Time: 7:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class ItemSerializer implements JsonSerializer<Item> {
    @Override
    public JsonElement serialize(Item item, Type type, JsonSerializationContext jsonSerializationContext) {

        final JsonObject json = new JsonObject();
        json.addProperty("filename", item.getFileName() );

        final JsonArray data = new JsonArray();
        json.add("data", data);

        for( MetaData meta: item.data ){
              data.add( jsonSerializationContext.serialize( meta  ) );
        }

        return json;

    }
}
