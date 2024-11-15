/*
 * Copyright 2024 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package dev.kgamble.perspective.examples.designer;

import javax.swing.Icon;
import java.io.IOException;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;
import com.inductiveautomation.ignition.client.icons.SvgIconUtil;
import com.inductiveautomation.perspective.designer.DesignerHook;

/**
 * Utility class for handling SVG icons in the Ignition Designer.
 * This class provides methods to load and create Swing Icon objects from SVG
 * files.
 *
 * @author Keith Gamble
 */
public class IconUtilities {

	/**
	 * The XML parser class name used for parsing SVG documents.
	 * This is obtained from Apache Batik's XMLResourceDescriptor.
	 */
	public static String xmlParser = XMLResourceDescriptor.getXMLParserClassName();

	/**
	 * Factory for creating SVG documents from XML input.
	 * This factory is initialized with the XML parser specified above.
	 */
	public static SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(xmlParser);

	/**
	 * Loads an SVG file and converts it into a Swing Icon object.
	 * 
	 * This method performs the following steps:
	 * 1. Loads the SVG file from the specified path using the DesignerHook's class
	 * loader.
	 * 2. Creates an SVG document from the loaded file.
	 * 3. Converts the SVG document into a Swing Icon with dimensions of 16x16
	 * pixels.
	 *
	 * @param filePath The path to the SVG file, relative to the classpath.
	 * @return An Icon object representing the loaded SVG.
	 * @throws RuntimeException If there's an error loading or processing the SVG
	 *                          file.
	 */
	public static Icon getSvgIcon(String filePath) {
		final Icon componentIcon;
		try (var inputStream = DesignerHook.class.getResourceAsStream(filePath)) {
			// Create an SVG document from the input stream
			SVGDocument document = factory.createSVGDocument(filePath, inputStream);
			// Convert the SVG document to a Swing Icon
			componentIcon = new SvgIconUtil.SvgIcon(document, 16, 16);
		} catch (IOException e) {
			// If there's an error loading the icon, throw a RuntimeException
			throw new RuntimeException("Unable to load resource icon", e);
		}

		return componentIcon;
	}
}