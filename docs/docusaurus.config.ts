import { themes as prismThemes } from 'prism-react-renderer';
import type { Config } from '@docusaurus/types';

const config: Config = {
  title: 'Example Perspective Component Module',
  tagline: 'An Example Module for Creating Perspective Components',
  favicon: 'img/favicon.ico',
  url: 'https://keith-gamble.github.io',
  baseUrl: '/example-perspective-component-module/',
  organizationName: 'keith-gamble',
  projectName: 'example-perspective-component-module',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  presets: [
    [
      'classic',
      {
        docs: {
          sidebarPath: './sidebars.ts',
          routeBasePath: '/', // Serve docs at the site's root
          editUrl:
            'https://github.com/keith-gamble/example-perspective-component-module/tree/master/docs',
        },
        blog: false, // Disable the blog plugin if not used
      },
    ],
  ],

  markdown: {
    mermaid: true
  },

  themes: ['@docusaurus/theme-mermaid'],

  themeConfig: {
    navbar: {
      title: 'Example Perspective Component Module',
      logo: {
        alt: 'Ignition Logo',
        src: 'img/Logo-Ignition-Check.svg',
      },
      items: [
        {
          href: 'https://github.com/keith-gamble/example-perspective-component-module',
          label: 'GitHub',
          position: 'right',
        },
      ],
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.dracula,
    },
    mermaid: {
      theme: { light: 'neutral', dark: 'dark' }
    },
  },
};

export default config;
