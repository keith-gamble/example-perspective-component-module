/**
 * This file defines a custom Button component for use in Ignition Perspective.
 */
import * as React from 'react';
import {
	Component,
	ComponentMeta,
	ComponentProps,
	PComponent,
	PropertyTree,
	SizeObject
} from '@inductiveautomation/perspective-client';

/**
 * Unique identifier for the Button component.
 * This should match the COMPONENT_ID in the Java Button class.
 */
export const COMPONENT_TYPE = "examples.input.button";

/**
 * Interface defining the props for the Button component.
 */
export interface ButtonProps {
	text?: string;
	enabled?: boolean;
}

/**
 * Button component class.
 * Extends the base Component class from Perspective, typed with ButtonProps.
 */
export class Button extends Component<ComponentProps<ButtonProps>, any> {

	/**
	 * Handler for the button's action event.
	 * Fires a custom event when the button is clicked.
	 */
	onActionPerformed = () => {
		this.props.componentEvents.fireComponentEvent("onActionPerformed", {});
	}

	/**
	 * Render method for the Button component.
	 * @returns JSX element representing the button
	 */
	render() {
		const { props: { text, enabled }, emit } = this.props;
		return (
			<button
				{...emit({ classes: ['dg-example-button'] })}
				onClick={this.onActionPerformed}
				disabled={!enabled}
			>
				{text}
			</button>
		);
	}
}

/**
 * Metadata class for the Button component.
 * Implements ComponentMeta interface from Perspective.
 */
export class ButtonMeta implements ComponentMeta {

	/**
	 * @returns The unique identifier for this component type.
	 */
	getComponentType(): string {
		return COMPONENT_TYPE;
	}

	/**
	 * @returns The React component class.
	 */
	getViewComponent(): PComponent {
		return Button;
	}

	/**
	 * @returns The default size for the component.
	 */
	getDefaultSize(): SizeObject {
		return ({
			width: 80,
			height: 34
		});
	}

	/**
	 * Reduces the full property tree into the props needed by the component.
	 * @param tree - The full property tree
	 * @returns The props object for the Button component
	 */
	getPropsReducer(tree: PropertyTree): ButtonProps {
		return {
			text: tree.readString("text", ""),
			enabled: tree.readBoolean("enabled", true)
		};
	}
}