package dev.bwdesigngroup.perspective.examples.common;

import java.util.Set;

import com.inductiveautomation.perspective.common.api.BrowserResource;

public class ExampleComponents {
    public static final String COMPONENT_CATEGORY = "Example UI Library";
    public static final Set<BrowserResource> BROWSER_RESOURCES =
        Set.of(
            new BrowserResource(
                "example-components-js",
                String.format("/res/%s/ExampleComponents.js", Constants.MODULE_URL_ALIAS),
                BrowserResource.ResourceType.JS
            ),
            new BrowserResource("example-components-css",
                String.format("/res/%s/ExampleComponents.css",Constants.MODULE_URL_ALIAS),
                BrowserResource.ResourceType.CSS
            )
        );
}
