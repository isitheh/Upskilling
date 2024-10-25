import { test, expect } from '@playwright/test';

/*
  * Test 1:
  * we should import and navigate to the playwright.dev page and 
  * expect the title of the page to contain the word or substring "Playwright".
*/
test('has title', async ({ page }) => {
  
  await page.goto('https://playwright.dev/');

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/Playwright/);
});

/*
  * Test 2:
  * we should import and navigate to the playwright.dev page,
  * click on the "Get started" link and 
  * expect the next page to have a heading with the name of "Installation".
*/
test('get started link', async ({ page }) => {
  await page.goto('https://playwright.dev/');

  // Click the get started link.
  await page.getByRole('link', { name: 'Get started' }).click();

  // Expects page to have a heading with the name of Installation.
  await expect(page.getByRole('heading', { name: 'Installation' })).toBeVisible();
});

/*
  * Test 3:
  * We should import and navigate to the playwright.dev page,
  * Ensure that the home page contains a button with the text "Get started"
*/
test('home page', async ({ page }) => {
  await page.goto('https://playwright.dev/');

  // Ensure that the home page contains a button with the text "Get started".
  await expect (page.getByRole('link', { name: 'Get started' })).toBeVisible();
});