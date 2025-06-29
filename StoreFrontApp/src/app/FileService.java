package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FileService {

    
	public static List<SalableProduct> loadInventory(String jsonFilePath) throws CustomFileException {
	    Gson gson = new GsonBuilder()
	        .registerTypeAdapter(SalableProduct.class, new SalableProductDeserializer())
	        .create();

	    try (FileReader reader = new FileReader(jsonFilePath)) {
	        Type productListType = new com.google.gson.reflect.TypeToken<List<SalableProduct>>(){}.getType();
	        return gson.fromJson(reader, productListType);
	    } catch (IOException e) {
	        throw new CustomFileException("Error reading file: " + e.getMessage());
	    } catch (JsonSyntaxException e) {
	        throw new CustomFileException("Invalid JSON format: " + e.getMessage());
	    }
	}

	public static void saveInventory(String string, List<SalableProduct> inventory) {
		
		
	}

}
