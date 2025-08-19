import React from 'react';
import Dashboard from '../../src/components/Dashboard';


describe('Dashboard component', () => {
    it('renders without crashing', () => {
        cy.mount(<Dashboard />);
        cy.contains('isParent:').should('exist');
    });
});