package io.github.darealturtywurty.minecraftmadness.data;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.LOGGER;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

/**
 * This is a generic data provider for generating data via the GatherDataEvent.
 * This data provider leverages codecs to handle the java-to-json serialization,
 * making it useable with any type of object that has a codec defined for that
 * type (rather than implementing the serialization manually as with other data
 * providers).
 * 
 * Data providers can be used in the GatherDataEvent mod bus event.
 * 
 * To use this, create an instance of JsonDataProvider in the GatherDataEvent,
 * use {@link JsonDataProvider#act(DirectoryCache)} to add objects to the
 * provider, and then add the data provider to the event's data generator via
 * event.getGenerator().addProvider(dataProvider) and then the data generator
 * will use the data provider to create a json for each object specified.
 * 
 * Data providers can also be nested by adding a different data provider to the
 * data generator and then invoking {@link JsonDataProvider#act(DirectoryCache)}
 * in the parent provider's act method.
 * 
 * Credits: Commoble
 */
public class JsonDataProvider<T> implements IDataProvider {
	/**
	 * Used by the CodecDataProvider to determine whether to save jsons to the
	 * assets or data folder
	 */
	public static enum ResourceType {
		ASSETS("assets"), DATA("data");

		public final String resourceFolder;

		ResourceType(String resourceFolder) {
			this.resourceFolder = resourceFolder;
		}
	}

	private static final Gson DEFAULT_GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

	protected final Gson gson;
	protected final DataGenerator generator;
	protected final ResourceType resourceType;
	protected final String folder;
	protected final Codec<T> codec;
	protected final Map<ResourceLocation, T> objects = new HashMap<>();

	/**
	 * Creates a data provider that uses a codec to generate jsons in a specified
	 * folder. Uses the default gson, which generates tiny jsons with no whitespace.
	 * 
	 * @param generator    The generator instance from the gather data event
	 * @param resourceType Whether to generate data in the assets or data folder
	 * @param folder       The root folder of this data type in a given data domain
	 *                     e.g. to generate data in
	 *                     resources/data/modid/foods/cheeses/, use
	 *                     ResourceType.DATA for the resource type, and
	 *                     "foods/cheeses" for the folder name.
	 * @param codec        The codec that will be used to convert objects to jsons
	 */
	public JsonDataProvider(DataGenerator generator, ResourceType resourceType, String folder, Codec<T> codec) {
		this(DEFAULT_GSON, generator, resourceType, folder, codec);
	}

	/**
	 * As with the other constructor but with a custom gson instance
	 * 
	 * @param gson         The gson instance that will be used to write JsonElements
	 *                     to raw text json files
	 * @param generator    The generator instance from the gather data event
	 * @param resourceType Whether to generate data in the assets or data folder
	 * @param folder       The root folder of this data type in a given data domain
	 *                     e.g. to generate data in
	 *                     resources/data/modid/foods/cheeses/, use
	 *                     ResourceType.DATA for the resource type, and
	 *                     "foods/cheeses" for the folder name.
	 * @param codec        The codec that will be used to convert objects to jsons
	 */
	public JsonDataProvider(Gson gson, DataGenerator generator, ResourceType resourceType, String folder, Codec<T> codec) {
		this.gson = gson;
		this.generator = generator;
		this.resourceType = resourceType;
		this.folder = folder;
		this.codec = codec;
	}

	/**
	 * Adds an object to this provider that will be serialized when act is invoked
	 * 
	 * @param id     The path from this provider's folder to write the json at e.g.
	 *               to write to
	 *               /resources/data/modid/foods/cheeses/cheddars/hard.json, where
	 *               foods/cheeses is this data provider's folder name, use
	 *               "modid:cheddars/hard" as the id here
	 * @param object The object to serialize to json. Must have the same type as
	 *               this provider's codec.
	 * @return this
	 */
	public JsonDataProvider<T> with(ResourceLocation id, T object) {
		this.objects.put(id, object);
		return this;
	}

	/**
	 * Takes all the objects declared by #with calls and writes them to json. If
	 * this provider has been added to the data generator from gatherDataEvent, this
	 * will be automatically called. Alternatively, other data providers can invoke
	 * this in their own act methods if they choose to do so.
	 */
	@Override
	public void act(DirectoryCache cache) throws IOException {
		Path resourcesFolder = this.generator.getOutputFolder();
		this.objects.forEach((id, object) -> {
			Path jsonLocation = this.getPath(resourcesFolder, id);
			this.codec.encodeStart(JsonOps.INSTANCE, object)
					.resultOrPartial(s -> LOGGER.error("Failed to encode {}", jsonLocation, s)).ifPresent(jsonElement -> {
						try {
							IDataProvider.save(this.gson, cache, jsonElement, jsonLocation);
						} catch (IOException e) {
							LOGGER.error("Failed to save {}", jsonLocation, e);
						}
					});

		});
	}

	protected Path getPath(Path resourcesFolder, ResourceLocation id) {
		return resourcesFolder.resolve(
				String.join("/", this.resourceType.resourceFolder, id.getNamespace(), this.folder, id.getPath() + ".json"));
	}

	/**
	 * Gets the name of this data provider. Used by the data generator to log its
	 * root data providers.
	 */
	@Override
	public String getName() {
		return String.format("%s %s provider", this.folder, this.resourceType.resourceFolder);
	}

}