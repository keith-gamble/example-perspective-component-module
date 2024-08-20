import { ComponentMeta, ComponentRegistry } from '@inductiveautomation/perspective-client';
import { Button, ButtonMeta } from './components/Button';

// Export components for external reference
export { Button };

// Import component styles
import './css/styles.css';

// Array of component metadata
const components: Array<ComponentMeta> = [
	new ButtonMeta(),
];

// Register each component with the Perspective ComponentRegistry
components.forEach((c: ComponentMeta) => ComponentRegistry.register(c));