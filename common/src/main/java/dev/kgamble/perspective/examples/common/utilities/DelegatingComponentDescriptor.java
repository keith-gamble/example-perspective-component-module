/*
 * Copyright 2024 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package dev.kgamble.perspective.examples.common.utilities;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.swing.Icon;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.inductiveautomation.ignition.common.gson.JsonObject;
import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.BrowserResource;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentEventDescriptor;
import com.inductiveautomation.perspective.common.api.ExtensionFunctionDescriptor;
import com.inductiveautomation.perspective.common.api.PaletteEntry;

/**
 * DelegatingComponentDescriptor is an abstract class that implements the
 * ComponentDescriptor interface.
 * It uses the Delegation pattern to forward most method calls to an underlying
 * ComponentDescriptor instance.
 * This class allows for easy extension and customization of ComponentDescriptor
 * behavior by overriding
 * specific methods while delegating the rest to the original implementation.
 *
 * @author Keith Gamble
 */
public abstract class DelegatingComponentDescriptor implements ComponentDescriptor {
	/** The underlying ComponentDescriptor to which most calls are delegated. */
	private final ComponentDescriptor delegate;

	/**
	 * Constructs a new DelegatingComponentDescriptor.
	 *
	 * @param delegate The ComponentDescriptor to which calls will be delegated.
	 */
	public DelegatingComponentDescriptor(ComponentDescriptor delegate) {
		this.delegate = delegate;
	}

	// The following methods all delegate to the underlying ComponentDescriptor.
	// Each method is annotated with @Override to ensure it correctly implements
	// the ComponentDescriptor interface.

	@Override
	@Nonnull
	public String id() {
		return delegate.id();
	}

	@Override
	public String name() {
		return delegate.name();
	}

	@Override
	public boolean deprecated() {
		return delegate.deprecated();
	}

	@Override
	@Nonnull
	public Collection<PaletteEntry> paletteEntries() {
		return delegate.paletteEntries();
	}

	@Override
	@Nonnull
	public String paletteCategory() {
		return delegate.paletteCategory();
	}

	@Override
	@Nonnull
	public String defaultMetaName() {
		return delegate.defaultMetaName();
	}

	@Override
	@Nonnull
	public String moduleId() {
		return delegate.moduleId();
	}

	@Override
	public JsonObject defaultProperties() {
		return delegate.defaultProperties();
	}

	@Override
	public Optional<JsonObject> childPositionDefaults() {
		return delegate.childPositionDefaults();
	}

	@Override
	@Nonnull
	public Set<BrowserResource> browserResources() {
		return delegate.browserResources();
	}

	@Override
	@Nullable
	public JsonSchema schema() {
		return delegate.schema();
	}

	@Override
	@Nullable
	public JsonSchema childPositionSchema() {
		return delegate.childPositionSchema();
	}

	@Override
	@Nonnull
	public Collection<ComponentEventDescriptor> events() {
		return delegate.events();
	}

	@Override
	@Nonnull
	public Collection<ExtensionFunctionDescriptor> extensionFunctions() {
		return delegate.extensionFunctions();
	}

	@Override
	@Nullable
	public JsonObject getInitialProps(String variantId) {
		return delegate.getInitialProps(variantId);
	}

	@Override
	public Optional<JsonObject> getExampleChildPositionDefaults() {
		return delegate.getExampleChildPositionDefaults();
	}

	@Override
	@Nonnull
	public Optional<Icon> getIcon() {
		return delegate.getIcon();
	}
}