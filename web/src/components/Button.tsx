/**
 * Example of a component which displays an Button, given a URL.
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

export const COMPONENT_TYPE = "examples.input.button";

export interface ButtonProps {
	text?: string;
	enabled?: boolean;
}

export class Button extends Component<ComponentProps<ButtonProps>, any> {

	onActionPerformed = () => {
        this.props.componentEvents.fireComponentEvent("onActionPerformed", {});
    }

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

export class ButtonMeta implements ComponentMeta {

	getComponentType(): string {
		return COMPONENT_TYPE;
	}

	getViewComponent(): PComponent {
		return Button;
	}

	getDefaultSize(): SizeObject {
		return ({
			width: 80,
			height: 34
		});
	}

	getPropsReducer(tree: PropertyTree): ButtonProps {
		return {
			text: tree.readString("text", ""),
			enabled: tree.readBoolean("enabled", true)
		};
	}
}
