import { test, expect } from '@playwright/test';

test.beforeEach(async ({ page }) => { 
  await page.goto('/');
});

test('menu', async ({ page }) => {
  // test the menu items of the wikipedia page
  await page.getByRole('link', { name: 'isitheh' }).click();
  await expect(page.getByRole('heading', { name: /isitheh/i })).toBeVisible();
  await page.getByRole('link', { name: /alerts/i }).click();
  await expect(page.getByRole('heading', { name: 'Notifications' })).toBeVisible();
});

test('logout', async ({ page }) => {  
  // test the logout functionality
  await page.getByRole('button', { name: 'Personal tools' }).check();
  await page.getByRole('link', { name: 'Log out' }).click();
  await expect(page.getByRole('heading', { name: 'Log out' })).toBeVisible();
});
