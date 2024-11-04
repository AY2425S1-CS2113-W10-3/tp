//@@author Bev-Low

package storage;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A custom serializer and deserializer for {@link LocalDate} objects, formatting dates as
 * {@code dd-MM-yyyy} for JSON serialization and deserialization.
 * <p>
 * This class implements {@link JsonSerializer} and {@link JsonDeserializer} to convert
 * {@code LocalDate} objects to and from JSON strings in a specific date format.
 * </p>
 */
public class DateSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Serializes a {@code LocalDate} object into a JSON string using the {@code dd-MM-yyyy} format.
     *
     * @param src the {@code LocalDate} to serialize, must not be {@code null}
     * @param typeOfSrc the type of the source object
     * @param context the serialization context
     * @return a {@link JsonElement} containing the formatted date string
     */
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        assert src != null;
        return new JsonPrimitive(src.format(formatter));  
    }

    /**
     * Deserializes a JSON string into a {@code LocalDate} object using the {@code dd-MM-yyyy} format.
     *
     * @param json the JSON element containing the date string
     * @param typeOfT the type of the object to deserialize
     * @param context the deserialization context
     * @return a {@code LocalDate} parsed from the JSON string
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return LocalDate.parse(json.getAsString(), formatter);
    }
}
