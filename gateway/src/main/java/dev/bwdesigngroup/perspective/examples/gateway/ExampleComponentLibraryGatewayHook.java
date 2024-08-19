package dev.bwdesigngroup.perspective.examples.gateway;

import java.util.Optional;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.perspective.common.api.ComponentRegistry;
import com.inductiveautomation.perspective.gateway.api.PerspectiveContext;

import dev.bwdesigngroup.perspective.examples.common.Constants;
import dev.bwdesigngroup.perspective.examples.common.component.input.Button;

/**
 * Class which is instantiated by the Ignition platform when the module is
 * loaded in the gateway scope.
 */
public class ExampleComponentLibraryGatewayHook extends AbstractGatewayModuleHook {

	private static final LoggerEx log = LoggerEx.newBuilder().build(ExampleComponentLibraryGatewayHook.class);

	private GatewayContext gatewayContext;
	private PerspectiveContext perspectiveContext;
	private ComponentRegistry componentRegistry;

	/**
	 * Called to before startup. This is the chance for the module to add its
	 * extension points and update persistent
	 * records and schemas. None of the managers will be started up at this point,
	 * but the extension point managers will
	 * accept extension point types.
	 */
	@Override
	public void setup(GatewayContext context) {
		this.gatewayContext = context;
	}

	/**
	 * Called to initialize the module. Will only be called once. Persistence
	 * interface is available, but only in
	 * read-only mode.
	 */
	@Override
	public void startup(LicenseState activationState) {
		this.perspectiveContext = PerspectiveContext.get(this.gatewayContext);
		this.componentRegistry = this.perspectiveContext.getComponentRegistry();

		if (this.componentRegistry != null) {
			this.componentRegistry.registerComponent(Button.DESCRIPTOR);
		} else {
			log.error("Reference to component registry not found, Example Components will fail to function!");
		}
	}

	/**
	 * Called to shutdown this module. Note that this instance will never be started
	 * back up - a new one will be created
	 * if a restart is desired
	 */
	@Override
	public void shutdown() {
		log.info("Shutting down Example module and removing registered components.");
		if (this.componentRegistry != null) {
			this.componentRegistry.removeComponent(Button.COMPONENT_ID);
		} else {
			log.warn("Component registry was null, could not unregister Example Components.");
		}
	}

	/**
	 * @return the path to a folder in one of the module's gateway jar files that
	 *         should be mounted at
	 *         /res/module-id/foldername
	 */
	@Override
	public Optional<String> getMountedResourceFolder() {
		return Optional.of("mounted");
	}

	/**
	 * Used by the mounting underneath /res/module-id/* and /main/data/module-id/*
	 * as an alternate mounting path instead
	 * of your module id, if present.
	 */
	@Override
	public Optional<String> getMountPathAlias() {
		return Optional.of(Constants.MODULE_URL_ALIAS);
	}

	/**
	 * @return {@code true} if this is a "free" module, i.e. it does not participate
	 *         in the licensing system. This is
	 *         equivalent to the now defunct FreeModule attribute that could be
	 *         specified in module.xml.
	 */
	@Override
	public boolean isFreeModule() {
		return true;
	}
}
