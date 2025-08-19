const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: "http://localhost:3000",  // your CRA dev server
    setupNodeEvents(on, config) {},
  },
  component: {
    devServer: {
      framework: "react",
      bundler: "webpack",
      specPattern: 'cypress/component/**/*.cy.{js,jsx,ts,tsx}'
    },
  },
});