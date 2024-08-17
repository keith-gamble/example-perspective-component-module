package dev.bwdesigngroup.perspective.examples.common.component.input;

import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import com.inductiveautomation.perspective.common.api.ComponentEventDescriptor;

import dev.bwdesigngroup.perspective.examples.common.Constants;
import dev.bwdesigngroup.perspective.examples.common.ExampleComponents;

/**
 * Describes the component to the Java registry so the gateway and designer know to look for the front end elements.
 * In a 'common' scope so that it's referenceable by both gateway and designer.
 */
public class Button  {

	public static final ButtonEventDescriptor ACTIONPERFORMED_EVENT_DESCRIPTOR = 
		new ButtonEventDescriptor("onActionPerformed");

	public static JsonSchema getSchema(String resourcePath) {
		return JsonSchema.parse(ExampleComponents.class.getResourceAsStream("/" + resourcePath));
	}

	public static class ButtonEventDescriptor extends ComponentEventDescriptor {
		static String description = "This event is fired when the 'action' of the component occurs.";
		public ButtonEventDescriptor(String name) {
			super(name, description, Button.getSchema("example-button." + name + ".json"));
		}
	}

    // unique ID of the component which perfectly matches that provided in the javascript's ComponentMeta implementation
    public static String COMPONENT_ID = "examples.input.button";

	private static BufferedImage loadThumbnail() {
        try {
            return ImageIO.read(Button.class.getResourceAsStream("/img/button-thumb.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Components register with the Java side ComponentRegistry but providing a ComponentDescriptor.  Here we
     * build the descriptor for this one component. Icons on the component palette are optional.
     */
    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
        .setPaletteCategory(ExampleComponents.COMPONENT_CATEGORY)
        .setId(COMPONENT_ID)
        .setModuleId(Constants.MODULE_ID)
        .setSchema(getSchema("example-button.props.json"))
		.setEvents(List.of(ACTIONPERFORMED_EVENT_DESCRIPTOR))
        .setName("Example Button")
        .addPaletteEntry("", "Example Button", "A better button.", loadThumbnail(), null)
        .setDefaultMetaName("example-button")
        .setResources(ExampleComponents.BROWSER_RESOURCES)
        .build();
}
