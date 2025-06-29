package app;

import com.google.gson.*;
import java.lang.reflect.Type;

public class SalableProductDeserializer implements JsonDeserializer<SalableProduct> {
    @Override
    public SalableProduct deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("damage")) {
            return context.deserialize(jsonObject, Weapon.class);
        } else if (jsonObject.has("defense")) {
            return context.deserialize(jsonObject, Armor.class);
        } else if (jsonObject.has("healingAmount")) {
            return context.deserialize(jsonObject, Health.class);
        } else {
            throw new JsonParseException("Unknown type of SalableProduct");
        }
    }
}
