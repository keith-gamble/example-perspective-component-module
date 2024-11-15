/*
 * Copyright 2024 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package dev.kgamble.perspective.examples.designer;

import javax.swing.Icon;
import java.util.Optional;
import javax.annotation.Nonnull;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.designer.DesignerComponentRegistry;

import dev.kgamble.perspective.examples.common.utilities.DelegatingComponentDescriptor;

/**
 * Utility class for registering components with custom icons in the Ignition Designer.
 * 
 * @author Keith Gamble
 */
public class ComponentUtilities {
    
    private static final LoggerEx logger = LoggerEx.newBuilder().build(ComponentUtilities.class);
    
    /**
     * Registers a component with a custom SVG icon in the Designer component registry.
     * 
     * We use Designer-scoped SVG functions because:
     * 1. The Designer scope already has the SvgIconUtil class available for loading SVG icons.
     * 
     * We use a DelegatingComponentDescriptor because:
     * 1. It allows us to override only the getIcon() method while keeping all other descriptor behaviors intact.
     * 2. This approach avoids the need to implement all methods of ComponentDescriptor, reducing code duplication.
     * 3. It provides a flexible way to customize component representation in the Designer without modifying the original descriptor.
     * 
     * @param registry The DesignerComponentRegistry to register the component with.
     * @param descriptor The original ComponentDescriptor of the component.
     * @param iconFilePath The file path of the SVG icon to be used for the component.
     */
    public static void registerComponentWithIcon(DesignerComponentRegistry registry, ComponentDescriptor descriptor, String iconFilePath) {
        Icon icon = null;
        try {
            icon = IconUtilities.getSvgIcon(iconFilePath);
        } catch (Exception e) {
            logger.error("Failed to load icon from path: " + iconFilePath, e);
        }

        final Icon finalIcon = icon;  // Need a final reference for use in the anonymous class

        registry.registerComponent(new DelegatingComponentDescriptor(descriptor) {
            @Override
            @Nonnull
            public Optional<Icon> getIcon() {
                return Optional.ofNullable(finalIcon);
            }
        });

        if (finalIcon == null) {
            logger.warn("Component " + descriptor.id() + " registered without an icon due to loading failure.");
        } else {
            logger.trace("Component " + descriptor.id() + " registered successfully with custom icon.");
        }
    }
}