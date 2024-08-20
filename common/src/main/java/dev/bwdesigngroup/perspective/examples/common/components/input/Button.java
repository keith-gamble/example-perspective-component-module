package dev.bwdesigngroup.perspective.examples.common.components.input;

import java.util.List;
import java.awt.image.BufferedImage;

import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import com.inductiveautomation.perspective.common.api.ComponentEventDescriptor;

import dev.bwdesigngroup.perspective.examples.common.Constants;
import dev.bwdesigngroup.perspective.examples.common.ExampleComponents;
import dev.bwdesigngroup.perspective.examples.common.utilities.ComponentUtilities;
import dev.bwdesigngroup.perspective.examples.common.utilities.ImageUtilities;

/**
 * This class describes the Button component to the Java registry.
 * It provides the necessary information for both the Gateway and Designer
 * to recognize and utilize the front-end Button component.
 */
public class Button {

	// Unique ID of the component, matching the ID in the JavaScript implementation
	public static String COMPONENT_ID = "examples.input.button";

	// Path to the thumbnail image for the component palette
	private static final String THUMBNAIL_PATH = "/images/button-thumbnail.png";
	private static final int THUMBNAIL_WIDTH = 70;
	private static final int THUMBNAIL_HEIGHT = 35;

	// Path to the JSON schema defining the component's properties
	private static final String PROPS_SCHEMA_PATH = "/props/example-button.props.json";

	// Component metadata
	private static final String COMPONENT_NAME = "Example Button";
	private static final String COMPONENT_DESCRIPTION = "A better button.";
	private static final String COMPONENT_DEFAULT_NAME = "example-button";

	/**
	 * Descriptor for the onActionPerformed event of the Button component.
	 */
	static ComponentEventDescriptor ActionPerformedDescriptor = ComponentUtilities.getEventDescriptor(
			"events/example-button/onActionPerformed.json",
			"onActionPerformed",
			"This event is fired when Better Button is clicked.");

	// Load the thumbnail image for the component palette
	static BufferedImage thumbnail = ImageUtilities.loadThumbnailFromFilePath(THUMBNAIL_PATH, THUMBNAIL_WIDTH,
			THUMBNAIL_HEIGHT);

	/**
	 * The ComponentDescriptor for the Button component.
	 * This descriptor provides all necessary information for the Perspective system
	 * to recognize, display, and utilize the Button component.
	 */
	public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
			.setPaletteCategory(ExampleComponents.COMPONENT_CATEGORY)
			.setId(COMPONENT_ID)
			.setModuleId(Constants.MODULE_ID)
			.setSchema(ComponentUtilities.getSchemaFromFilePath(PROPS_SCHEMA_PATH))
			.setEvents(List.of(ActionPerformedDescriptor))
			.setName(COMPONENT_NAME)
			.addPaletteEntry("", COMPONENT_NAME, COMPONENT_DESCRIPTION, thumbnail, null)
			.setDefaultMetaName(COMPONENT_DEFAULT_NAME)
			.setResources(ExampleComponents.BROWSER_RESOURCES)
			.build();
}