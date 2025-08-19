import './commands'

// Example: ignore uncaught exceptions
Cypress.on('uncaught:exception', (err, runnable) => {
    return false
})
