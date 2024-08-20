package dev.bwdesigngroup.perspective.examples.designer;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.perspective.designer.DesignerComponentRegistry;
import com.inductiveautomation.perspective.designer.api.PerspectiveDesignerInterface;

import dev.bwdesigngroup.perspective.examples.common.components.input.Button;

/**
 * Designer module hook for the Example Component Library.
 * This class is responsible for initializing and managing the module's
 * lifecycle in the Ignition Designer.
 */
public class ExampleComponentLibraryDesignerHook extends AbstractDesignerModuleHook {
	private static final LoggerEx log = LoggerEx.newBuilder().build(ExampleComponentLibraryDesignerHook.class);

	private DesignerContext context;
	private DesignerComponentRegistry registry;

	/**
	 * Initializes the module in the Designer scope.
	 * 
	 * @param context         The DesignerContext for this module.
	 * @param activationState The current license state.
	 */
	@Override
	public void startup(DesignerContext context, LicenseState activationState) {
		log.trace("Starting up Example Component Library Designer Hook");
		this.context = context;
		init();
	}

	/**
	 * Initializes the component registry and registers components.
	 */
	private void init() {
		PerspectiveDesignerInterface pdi = PerspectiveDesignerInterface.get(context);

		registry = pdi.getDesignerComponentRegistry();

		// Each component must be registered, with an optional icon, to the component registry.
		ComponentUtilities.registerComponentWithIcon(registry, Button.DESCRIPTOR, "/images/button-click.svg");
	}

	/**
	 * Shuts down the module and unregisters components.
	 */
	@Override
	public void shutdown() {
		log.trace("Shutting down Example Component Library Designer Hook");
		removeComponents();
	}

	/**
	 * Removes registered components from the registry.
	 */
	private void removeComponents() {
		registry.removeComponent(Button.COMPONENT_ID);
	}
}