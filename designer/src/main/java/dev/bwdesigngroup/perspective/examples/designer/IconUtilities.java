/*
 * Copyright 2022 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package dev.bwdesigngroup.perspective.examples.designer;
import javax.swing.Icon;
import java.io.IOException;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;
import com.inductiveautomation.ignition.client.icons.SvgIconUtil;
import com.inductiveautomation.perspective.designer.DesignerHook;

/**
 *
 * @author Keith Gamble
 */
public class IconUtilities {
	
	public static String xmlParser = XMLResourceDescriptor.getXMLParserClassName();
	public static SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(xmlParser);
	
	public static Icon getSvgIcon(String filePath) {
		final Icon componentIcon;
		try (var inputStream = DesignerHook.class.getResourceAsStream(filePath)) {
			SVGDocument document = factory.createSVGDocument(filePath, inputStream);
			componentIcon = new SvgIconUtil.SvgIcon(document, 16, 16);
		} catch (IOException e) {
			throw new RuntimeException("Unable to load resource icon", e);
		}

		return componentIcon;
	}	
}
