/*
 * Copyright 2024 Keith Gamble
 * [License information]
 */
package dev.kgamble.perspective.examples.common.utilities;

import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentEventDescriptor;

/**
 * Utility class providing helper methods for component-related operations.
 *
 * @author Keith Gamble
 */
public class ComponentUtilities {

	/**
	 * Loads and parses a JSON schema from a given resource path.
	 *
	 * @param resourcePath The path to the JSON schema resource.
	 * @return A JsonSchema object representing the parsed schema.
	 */
	public static JsonSchema getSchemaFromFilePath(String resourcePath) {
		// Ensure the resource path starts with a forward slash
		if (!resourcePath.startsWith("/")) {
			resourcePath = "/" + resourcePath;
		}

		return JsonSchema.parse(ComponentUtilities.class.getResourceAsStream(resourcePath));
	}

	/**
	 * Inner class representing a dynamic ComponentEventDescriptor.
	 * This allows for creation of event descriptors with schemas loaded from files.
	 */
	private static class DynamicEventDescriptor extends ComponentEventDescriptor {
		public DynamicEventDescriptor(String filePath, String eventName, String description) {
			super(eventName, description, ComponentUtilities.getSchemaFromFilePath(filePath));
		}
	}

	/**
	 * Creates a ComponentEventDescriptor with a schema loaded from a file.
	 *
	 * @param filePath    The path to the JSON schema file for the event.
	 * @param eventName   The name of the event.
	 * @param description A description of the event.
	 * @return A ComponentEventDescriptor for the specified event.
	 */
	public static DynamicEventDescriptor getEventDescriptor(String filePath, String eventName, String description) {
		return new DynamicEventDescriptor(filePath, eventName, description);
	}
}