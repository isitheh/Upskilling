import { test, expect } from '@playwright/test';

test.beforeEach(async ({ page }) => { 
  await page.goto('/');
});

test('search', async ({ page }) => {
    await page.getByLabel('Search Wikipedia').click();
    await page.getByLabel('Search Wikipedia').fill('Playwright');
    await page.getByRole('button', { name: 'Search' }).click();

    // Use a more specific selector to match the exact element
    const searchResult = page.locator('h1:has-text("Playwright")');
    await expect(searchResult).toBeVisible();
});