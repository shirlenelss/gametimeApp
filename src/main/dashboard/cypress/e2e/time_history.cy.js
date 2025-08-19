describe('Time History API', () => {
  it('should return approved requests for week range', () => {
    cy.request({
      method: 'GET',
      url: '/api/history?range=WEEK',
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      response.body.forEach(req => {
        expect(req.status).to.eq('APPROVED');
      });
    });
  });

  it('should return rejected requests for day range', () => {
    cy.request({
      method: 'GET',
      url: '/api/history?range=DAY&status=REJECTED',
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      response.body.forEach(req => {
        expect(req.status).to.eq('REJECTED');
      });
    });
  });

  it('should return 4xx for invalid range', () => {
    cy.request({
      method: 'GET',
      url: '/api/history?range=YEAR',
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.be.within(400,499);
    });
  });
});
