package dev.bwdesigngroup.perspective.examples.common;

import java.util.Set;
import com.inductiveautomation.perspective.common.api.BrowserResource;

/**
 * This class defines common properties and resources for all example
 * components.
 * It specifies the category under which these components will appear in the
 * Perspective component palette
 * and declares the necessary browser resources (JS and CSS files) for the
 * components.
 */
public class ExampleComponents {
	/**
	 * The category name for example components in the Perspective component
	 * palette.
	 */
	public static final String COMPONENT_CATEGORY = "Example UI Library";

	/**
	 * A set of BrowserResource objects representing the JavaScript and CSS files
	 * required by the example components on the client side.
	 */
	public static final Set<BrowserResource> BROWSER_RESOURCES = Set.of(
			// JavaScript resource for the example components
			new BrowserResource(
					"example-components-js",
					String.format("/res/%s/ExampleComponents.js", Constants.MODULE_URL_ALIAS),
					BrowserResource.ResourceType.JS),
			// CSS resource for the example components
			new BrowserResource(
					"example-components-css",
					String.format("/res/%s/ExampleComponents.css", Constants.MODULE_URL_ALIAS),
					BrowserResource.ResourceType.CSS));
}