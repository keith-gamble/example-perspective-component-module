package dev.bwdesigngroup.perspective.examples.designer;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.perspective.designer.DesignerComponentRegistry;
import com.inductiveautomation.perspective.designer.api.PerspectiveDesignerInterface;

import dev.bwdesigngroup.perspective.examples.common.components.input.Button;

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

	@Override
	public void startup(DesignerContext context, LicenseState activationState) {
		log.trace("Starting up Example Component Library Designer Hook");
		this.context = context;
		init();
	}

	private void init() {
		PerspectiveDesignerInterface pdi = PerspectiveDesignerInterface.get(context);

		registry = pdi.getDesignerComponentRegistry();

		ComponentUtilities.registerComponentWithIcon(registry, Button.DESCRIPTOR, "/images/button-click.svg");
	}

	@Override
	public void shutdown() {
		log.trace("Shutting down Example Component Library Designer Hook");
		removeComponents();
	}

	private void removeComponents() {
		registry.removeComponent(Button.COMPONENT_ID);
	}
}
