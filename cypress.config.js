const { defineConfig } = require('cypress');
const { devServer } = require('@cypress/webpack-dev-server');
const webpackConfig = require('./webpack.config');

module.exports = defineConfig({
    component: {
        devServer: {
            framework: 'react',
            bundler: 'webpack',
            webpackConfig,
        },
        specPattern: 'cypress/component/**/*.cy.{js,jsx,ts,tsx}',
    },
});