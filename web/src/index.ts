import { ComponentMeta, ComponentRegistry } from '@inductiveautomation/perspective-client';
import { Button, ButtonMeta } from './components/Button';

// export so the components are referencable, e.g. `RadComponents['Image']
export { Button };

import './css/styles.css';

// as new components are implemented, import them, and add their meta to this array
const components: Array<ComponentMeta> = [
	new ButtonMeta(),
];

// iterate through our components, registering each one with the registry.  Don't forget to register on the Java side too!
components.forEach((c: ComponentMeta) => ComponentRegistry.register(c));
