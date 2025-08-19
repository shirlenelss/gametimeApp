// ***********************************************************
// This example support/component.js is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import { mount } from 'cypress/react'
import { MemoryRouter } from 'react-router-dom'
import React from 'react'

// Override the default mount command
Cypress.Commands.add('mount', (component, options = {}) => {
    // Wrap the component in a MemoryRouter
    const wrapped = <MemoryRouter>{component}</MemoryRouter>

    // Call the original mount function with the wrapped component
    return mount(wrapped, options)
})

// Example use:
// cy.mount(<MyComponent />)
