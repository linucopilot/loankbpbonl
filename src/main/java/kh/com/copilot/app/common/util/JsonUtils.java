package kh.com.copilot.app.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
	private JsonUtils() {
	}

	private static final ObjectMapper mapper = createNewObjectMapper(true);
	private static final ObjectMapper mapperNonEmpty = createNewObjectMapper(false);

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}

	public static ObjectMapper getObjectMapperNonEmpty() {
		return mapperNonEmpty;
	}

	public static ObjectMapper createNewObjectMapper(boolean includeType) {
		final ObjectMapper mapper = new ObjectMapper();
		if (includeType) {
			mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		} else {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		}
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		return mapper;
	}

	public static <T> T convertValue(Object object, Class<T> clazz) {
		if (clazz == null) {
			throw new JsonException("Unable to convert JSON value: target type is required");
		}
		try {
			if (object == null) {
				return null;
			} else {
				return mapper.convertValue(object, clazz);
			}
		} catch (IllegalArgumentException e) {
			throw new JsonException("Unable to convert JSON value to " + clazz.getSimpleName(), e);
		}
	}

	public static <T> T readJson(final String jsonString, final Class<T> clazz) {
		if (jsonString == null || jsonString.isBlank() || clazz == null) {
			throw new JsonException("Unable to read JSON: input and target type are required");
		}
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			throw new JsonException("Unable to read JSON object as " + clazz.getSimpleName(), e);
		}
	}

	public static <T> T readJson(final String jsonString, final TypeReference<T> typeReference) {
		if (jsonString == null || jsonString.isBlank() || typeReference == null) {
			throw new JsonException("Unable to read JSON: input and target type are required");
		}
		try {
			return mapper.readValue(jsonString, typeReference);
		} catch (IOException e) {
			throw new JsonException("Unable to read JSON object as " + typeReference.getType(), e);
		}
	}

	public static JsonNode readJson(final String jsonString) {
		if (jsonString == null || jsonString.isBlank()) {
			throw new JsonException("Unable to read JSON: input is required");
		}
		try {
			return mapper.readTree(jsonString);
		} catch (IOException e) {
			throw new JsonException("Unable to read JSON node from input", e);
		}
	}

	public static Map<String, Object> readJsonToMap(final String jsonString) {
		return readJson(jsonString, new TypeReference<HashMap<String, Object>>() {
		});
	}

	public static List<Map<String, Object>> readJsonToList(final String jsonString) {
		return readJson(jsonString, new TypeReference<List<Map<String, Object>>>() {
		});
	}

	public static <T> String toJson(final T object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (final JsonProcessingException e) {
			throw new JsonException("Unable to write JSON object", e);
		}
	}

	public static <T> JsonNode toJsonNode(final T object) {
		final String jsonString = toJson(object);
		return readJson(jsonString);
	}

	public static <T> String toJson(final T object, ObjectMapper mapper) {
		if (mapper == null) {
			throw new JsonException("Unable to write JSON object: mapper is required");
		}
		try {
			return mapper.writeValueAsString(object);
		} catch (final JsonProcessingException e) {
			throw new JsonException("Unable to write JSON object", e);
		}
	}

	public static <T> T readJsonFromFile(final String resourcePath, final Class<T> clazz) {
		if (clazz == null) {
			throw new JsonException("Unable to read JSON file: target type is required");
		}
		try {
			String result = java.nio.file.Files.readString(toPath(resourcePath), StandardCharsets.UTF_8);
			return mapper.readValue(result, clazz);
		} catch (IOException | InvalidPathException e) {
			throw new JsonException("Unable to read JSON object from file", e);
		}
	}

	public static JsonNode readJsonNodeFromFile(final String resourcePath) {
		try (var bufferedReader = java.nio.file.Files.newBufferedReader(toPath(resourcePath), StandardCharsets.UTF_8)) {
			return mapper.readTree(bufferedReader);
		} catch (IOException | InvalidPathException e) {
			throw new JsonException("Unable to read JSON node from file", e);
		}
	}

	public static void writeJsonToFile(final String resourcePath, final String jsonString) {
		if (jsonString == null) {
			throw new JsonException("Unable to write JSON file: input is required");
		}
		try {
			java.nio.file.Files.writeString(toPath(resourcePath), jsonString, StandardCharsets.UTF_8);
		} catch (IOException | InvalidPathException e) {
			throw new JsonException("Unable to write JSON file", e);
		}
	}

	public static String unprettify(final String jsonString) {
		return toJson(readJson(jsonString));
	}

	public static String prettify(final String jsonString) {
		try {
			JsonNode node = readJson(jsonString);
			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
			return writer.writeValueAsString(node);
		} catch (JsonProcessingException e) {
			throw new JsonException("Unable to format JSON input", e);
		}
	}

	public static String prettify(final Object object) {
		try {
			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
			return writer.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new JsonException("Unable to format JSON object", e);
		}
	}

	private static Path toPath(String resourcePath) {
		if (resourcePath == null || resourcePath.isBlank()) {
			throw new JsonException("A file path is required");
		}
		return Path.of(resourcePath);
	}
}
