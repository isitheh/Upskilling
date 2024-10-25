import { test as setup, expect } from '@playwright/test';
import { STORAGE_STATE } from '../playwright.config';
import path from 'path';
import dotenv from 'dotenv';

// Load environment variables from .env file
dotenv.config({ path: path.resolve(__dirname, '.env') });

setup('test', async ({ page }) => {
  await page.goto('/');
  await page.getByRole('link', { name: 'Log in' }).click();
  await page.getByPlaceholder('Enter your username').fill('isitheh');
  await page.getByPlaceholder('Enter your username').press('Tab');
  await page.getByPlaceholder('Enter your password').fill(process.env.PASSWORD!);
  await page.getByLabel('Keep me logged in (for up to').check();
  await page.getByRole('button', { name: 'Log in' }).click();
  await page.goto('/');

  // Add assertion to ensure that the page contains the text "isitheh" and is visible.
  await expect (page.getByRole('link', { name: 'isitheh' })).toBeVisible();

  await page.context().storageState({path: STORAGE_STATE});
});