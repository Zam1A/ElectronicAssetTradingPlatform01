# Electronic Asset Trading Platform

A modern Java Swing desktop application for electronic asset listings, marketplace purchases, account management, and order tracking.

The project now runs locally without MySQL. Data is stored in simple local TSV files, so the app works immediately after cloning.

## Requirements

- JDK 8 or newer. JDK 26 is supported.
- Windows command prompt or PowerShell.

`build.bat` and `run.bat` automatically look for Java under:

- `JAVA_HOME`
- `C:\Program Files\Java\jdk*`
- the current system `PATH`

## Quick Start

From the project root:

```bat
run.bat
```

This will compile the project and open the modern application:

```text
net.wms.view.ModernTradingPlatform
```

Build only:

```bat
build.bat
```

Run manually after building:

```bat
java -cp bin net.wms.view.ModernTradingPlatform
```

The older fixed-position Swing UI is still available for comparison:

```bat
java -cp bin net.wms.view.Login
```

## Default Accounts

| Username | Password | Type |
| --- | --- | --- |
| admin | 123456 | admin |
| java | 1 | employee |
| d | 123 | employee |

When logging in, make sure the selected account type matches the account.

## Admin Features

Admin users can access these pages:

### Overview

- View total users.
- View active goods listings.
- View completed orders.
- Confirm the signed-in admin account.

### Users

- Search users.
- Add new admin or employee accounts.
- Set a starting balance for new users.
- Change a selected user's password.
- Delete selected users.

The app prevents deleting the account currently in use.

### Goods

- Search all listed assets.
- Review each listing's ID, name, type, price, and seller.
- Remove selected listings.

### Orders

- Search completed orders.
- Review each order's ID, asset, type, price, and buyer.
- Delete selected order records.

### Account

- View the current admin account.
- Change the current admin password.
- See where local data is stored.

## Employee Features

Employee users can access these pages:

### Overview

- View available marketplace assets.
- View personal listing count.
- View personal order count.
- View current balance.

### Marketplace

- Search all available assets.
- Select an asset and buy it.
- The app prevents buying your own listing.
- The app checks that your balance is high enough before purchase.

After purchase:

- The asset is removed from active listings.
- An order record is created.
- The buyer balance is reduced by the asset price.

### My Listings

- Search your active listings.
- Add a new asset listing.
- Edit a selected listing.
- Remove a selected listing.

### Orders

- Search your completed purchases.
- View purchased asset, type, price, and buyer account.

### Account

- View username, role, and balance.
- Change your password.

## Local Data

The app creates this folder on first run:

```text
data/
```

Inside it:

```text
data/users.tsv
data/goods.tsv
data/storage.tsv
```

To reset the app to the default demo data:

1. Close the application.
2. Delete the `data/` folder.
3. Run `run.bat` again.

The original SQL export is still kept as reference:

```text
qqq.sql
```

It is no longer required to run the app.

## Project Structure

```text
src/net/wms/view/ModernTradingPlatform.java   Modern responsive desktop UI
src/net/wms/util/LocalDataStore.java          Local data persistence
src/net/wms/bean/                             Data beans
src/net/wms/dao/                              Compatibility DAO layer
Images/                                      Legacy image assets
build.bat                                    Compile all Java sources
run.bat                                      Build and launch the modern app
```

## Notes

- The old remote MySQL dependency was removed from the runtime path.
- The modern UI uses layout managers, resizable tables, search fields, dialogs, and a full-screen dashboard shell.
- The old Swing windows remain in the repository for compatibility, but `run.bat` launches the modern UI by default.
