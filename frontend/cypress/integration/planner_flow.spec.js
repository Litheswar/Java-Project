describe('Trip Planner Flow', () => {
  beforeEach(() => {
    cy.visit('/planner');
  });

  it('should display countries list and map', () => {
    // Check that countries list is displayed
    cy.get('[data-testid="countries-list"]').should('be.visible');
    
    // Check that map is displayed
    cy.get('.leaflet-container').should('be.visible');
    
    // Check that search bar is present
    cy.get('input[placeholder="Search countries..."]').should('be.visible');
  });

  it('should allow searching for countries', () => {
    // Type in search bar
    cy.get('input[placeholder="Search countries..."]').type('France');
    
    // Check that filtered results are displayed
    cy.contains('France').should('be.visible');
  });

  it('should navigate through planner steps', () => {
    // Click next button to go to dates step
    cy.contains('Next').click();
    
    // Verify we're on dates step
    cy.contains('Select your travel dates').should('be.visible');
    
    // Fill in dates
    cy.get('#startDate').type('2023-06-01');
    cy.get('#endDate').type('2023-06-07');
    
    // Click next to go to travelers step
    cy.contains('Next').click();
    
    // Verify we're on travelers step
    cy.contains("Who's traveling?").should('be.visible');
    
    // Select number of travelers
    cy.get('#travelers').clear().type('2');
    
    // Click next to go to budget step
    cy.contains('Next').click();
    
    // Verify we're on budget step
    cy.contains('Set your budget').should('be.visible');
    
    // Enter budget
    cy.get('#budget').clear().type('5000');
    
    // Click next to go to preferences step
    cy.contains('Next').click();
    
    // Verify we're on preferences step
    cy.contains('Travel Preferences').should('be.visible');
    
    // Select meals per day
    cy.contains('3 Meals').click();
    
    // Select transport type
    cy.contains('Mixed').click();
    
    // Select food type
    cy.contains('Mixed').click();
    
    // Click next to go to review step
    cy.contains('Next').click();
    
    // Verify we're on review step
    cy.contains('Review your trip details').should('be.visible');
  });

  it('should show sustainability impact based on selections', () => {
    // Navigate to preferences step
    cy.contains('Next').click().click().click().click();
    
    // Select rail transport for better sustainability
    cy.contains('Rail').click();
    
    // Check that sustainability impact is updated
    cy.contains('Your choices could save').should('be.visible');
  });
});