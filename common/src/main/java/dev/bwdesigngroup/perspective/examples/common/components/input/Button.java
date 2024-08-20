package dev.bwdesigngroup.perspective.examples.common.components.input;

import java.util.List;
import java.awt.image.BufferedImage;

import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import com.inductiveautomation.perspective.common.api.ComponentEventDescriptor;

import dev.bwdesigngroup.perspective.examples.common.Constants;
import dev.bwdesigngroup.perspective.examples.common.ExampleComponents;
import dev.bwdesigngroup.perspective.examples.common.utilities.ComponentUtilities;
import dev.bwdesigngroup.perspective.examples.common.utilities.ImageUtilities;

/**
 * Describes the component to the Java registry so the gateway and designer know
 * to look for the front end elements.
 * In a 'common' scope so that it's referenceable by both gateway and designer.
 */
public class Button {

	// unique ID of the component which perfectly matches that provided in the
	// javascript's ComponentMeta implementation
	public static String COMPONENT_ID = "examples.input.button";
	private static final String THUMBNAIL_PATH = "/images/button-thumbnail.png";
	private static final int THUMBNAIL_WIDTH = 70;
	private static final int THUMBNAIL_HEIGHT = 35;
	private static final String PROPS_SCHEMA_PATH = "/props/example-button.props.json";
	private static final String COMPONENT_NAME = "Example Button";
	private static final String COMPONENT_DESCRIPTION = "A better button.";
	private static final String COMPONENT_DEFAULT_NAME = "example-button";

	/**
	 * Components register with the Java side ComponentRegistry but providing a
	 * ComponentDescriptor. Here we
	 * build the descriptor for this one component. Icons on the component palette
	 * are optional.
	 */

	static ComponentEventDescriptor ActionPerformedDescriptor = ComponentUtilities.getEventDescriptor(
			"events/example-button/onActionPerformed.json", "onActionPerformed",
			"This event is fired when the 'action' of the component occurs.");

	static BufferedImage thumbnail = ImageUtilities.loadThumbnailFromFilePath(THUMBNAIL_PATH, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);

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
