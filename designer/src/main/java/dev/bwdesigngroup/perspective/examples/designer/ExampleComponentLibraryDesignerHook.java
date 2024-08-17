package dev.bwdesigngroup.perspective.examples.designer;

import java.io.IOException;

import javax.swing.Icon;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

import com.inductiveautomation.ignition.client.icons.SvgIconUtil;
import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.perspective.designer.DesignerComponentRegistry;
import com.inductiveautomation.perspective.designer.DesignerHook;
import com.inductiveautomation.perspective.designer.api.PerspectiveDesignerInterface;

import dev.bwdesigngroup.perspective.examples.common.DelegatingComponentDescriptor;
import dev.bwdesigngroup.perspective.examples.common.component.input.Button;
import dev.bwdesigngroup.perspective.examples.designer.IconUtilities;

/**
 * This is the Designer-scope module hook. The minimal implementation contains a
 * startup method.
 */
public class ExampleComponentLibraryDesignerHook extends AbstractDesignerModuleHook {
	private static final LoggerEx log = LoggerEx.newBuilder().build(ExampleComponentLibraryDesignerHook.class);

	private DesignerContext context;
	private DesignerComponentRegistry registry;

	static {
		// This bundle is a direct reference to src/main/resources/example-components.properties
		BundleUtil.get().addBundle("example-components", ExampleComponentLibraryDesignerHook.class.getClassLoader(),
				"example-components");
	}

	public ExampleComponentLibraryDesignerHook() {
		log.trace("Registering Example Components in Designer!");
	}

	@Override
	public void startup(DesignerContext context, LicenseState activationState) {
		this.context = context;
		init();
	}

	private void init() {
		PerspectiveDesignerInterface pdi = PerspectiveDesignerInterface.get(context);

		registry = pdi.getDesignerComponentRegistry();

		// Component icons are stored as svgs in `src/main/resources/`
		final Icon componentIcon = IconUtilities.getSvgIcon("/img/button-click.svg");
		
		// In the common scope where our button is defined, we dont have access to the save `SvgIconUtil` library
		// so we get the icon here through an override on the Delegate, since we have client scope.
		registry.registerComponent(new DelegatingComponentDescriptor(Button.DESCRIPTOR) {
			@Override
			@Nonnull
			public Optional<Icon> getIcon() {
				return Optional.of(componentIcon);
			}
		});
	}

	@Override
	public void shutdown() {
		removeComponents();
	}

	private void removeComponents() {
		registry.removeComponent(Button.COMPONENT_ID);
	}
}
