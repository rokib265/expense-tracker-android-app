# Trackify - Expense Tracker

Trackify is an offline-first Android application designed to help users track their income and expenses efficiently. 

## Features
- **Dashboard**: View total income, expenses, available balance, and a breakdown of expenses by category (Pie Chart).
- **Transactions**: Add new income or expenses, view history, and delete transactions.
- **Reports**: View detailed category-wise expense summaries.
- **Category Management**: Add custom categories directly from the Add Transaction screen.
- **Offline Storage**: Uses Room Database (SQLite) for secure local data storage.

## Architecture
- **MVVM (Model-View-ViewModel)**: Ensures separation of concerns and data persistence across configuration changes.
- **Room Database**: For efficient local data handling.
- **LiveData**: For real-time UI updates.

## Setup & Build
1. Open the project in Android Studio.
2. Sync Gradle files.
3. Run on an Emulator or Physical Device (API Level 24+).

## Tech Stack
- Java
- Android SDK
- Room Persistence Library
- MPAndroidChart
- Material Design Components

## Developer Notes
- Database is pre-populated with default categories like Food, Transport, Shopping, Salary.
- You can add more categories dynamically.
