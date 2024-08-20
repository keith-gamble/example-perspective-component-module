/*
 * Copyright 2022 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package dev.bwdesigngroup.perspective.examples.common.utilities;

import org.apache.poi.ss.formula.functions.T;

import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentEventDescriptor;

/**
 *
 * @author Keith Gamble
 */
public class ComponentUtilities {
	public static JsonSchema getSchemaFromFilePath(String resourcePath) {
		// If there is already a / in the resourcePath, then don't add another one.

		if (!resourcePath.startsWith("/")) {
			resourcePath = "/" + resourcePath;
		}

		return JsonSchema.parse(ComponentUtilities.class.getResourceAsStream(resourcePath));
	}

	private static class DynamicEventDescriptor extends ComponentEventDescriptor {
		public DynamicEventDescriptor(String filePath, String eventName, String description) {
			super(eventName, description, ComponentUtilities.getSchemaFromFilePath(filePath));
		}
	}

    public static DynamicEventDescriptor getEventDescriptor(String filePath, String eventName, String description) {
        return new DynamicEventDescriptor(filePath, eventName, description);
    }
}
