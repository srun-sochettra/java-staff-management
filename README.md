# Java Staff Management

**A console CRUD project exploring inheritance, MVC and PostgreSQL persistence.**

Created for Homework 03, this application stores staff records, displays calculated pay and supports type-specific updates through a terminal menu. An abstract `StaffMember` model provides a shared interface for three staff types.

This is coursework, not a production HR or payroll system. It does not implement payroll processing, tax rules, authentication or employee performance assessment.

## Features

- Insert volunteers, salaried employees and hourly employees.
- Display stored records in ID order with formatted terminal tables.
- Update the fields applicable to a staff type without editing its ID.
- Remove a record by ID.
- Persist records through JDBC to PostgreSQL.
- Attempt table and sequence initialisation on startup.

The display loads all rows into a list and renders one table. **Pagination is not implemented**, despite the previous README's claim.

## Polymorphic pay examples

| Model | Implemented `pay()` calculation |
| --- | --- |
| `Volunteer` | Stored salary value |
| `SalariedEmployee` | Salary plus bonus |
| `HourlySalaryEmployee` | Hours worked multiplied by rate |

These are the actual coursework formulas, including the salary field on Volunteer. Values use `double` and are displayed to one decimal place; this is not an exact-money or statutory payroll implementation.

## Setup boundaries

The inventory lists PostgreSQL JDBC 42.7.10 and text-table-formatter 1.1.2 JARs under `lib/`. Their binary contents are excluded from the text export and were not tested. Use the original checkout and confirm the files exist.

The IntelliJ module inherits its JDK. No build-tool configuration or verified minimum JDK is supplied; the old Java 8+ claim has not been established for the complete dependency set. Select a compatible JDK and verify locally.

### Database

`src/controller/DatabaseHelper.java` targets a local PostgreSQL database named `staff_db` and contains hard-coded credentials. **Rotate the password if it has been used anywhere real.** Its value is deliberately not copied here. This documentation does not change the connection code or remove historical copies.

Prepare an isolated development database separately and review the helper's connection settings before running. The application attempts to create `staff_members` and adjust its ID sequence; it does not create the database itself. The account needs permissions for those operations.

**Fresh-database startup needs verification:** the sequence-reset statement passes zero when the table is empty. Do not assume automatic initialisation succeeds. Inspect this path before relying on a clean installation; no database test or fix was performed here.

There is no environment-variable loader in the current helper. Creating an `.env` file alone will not configure it.

### Suggested Windows PowerShell commands

From the repository root, after reviewing database configuration and JAR availability:

```powershell
javac -encoding UTF-8 -cp "lib/*" -d out src/Main.java src/controller/*.java src/model/*.java src/view/*.java
java -cp "out;lib/*" Main
```

For a POSIX shell, use `"out:lib/*"` as the runtime classpath instead. These are proposed commands, not commands executed during this refresh. Compile from source rather than relying on the `.class` files listed alongside it.

## Menu

| Option | Action |
| --- | --- |
| `1` | Insert a staff record; select its type |
| `2` | Display all staff |
| `3` | Update an existing record's permitted fields |
| `4` | Remove a record |
| `5` | Exit and close the shared connection |

Updates offer name/address and the relevant salary, bonus, rate or hours fields. Changing record type is not offered. Removal has no confirmation or undo.

## Architecture

```text
src/Main.java                 Startup and MVC wiring
src/model/                    Abstract staff model and concrete types
src/controller/DatabaseHelper.java  Shared JDBC connection and schema setup
src/controller/StaffDAO.java        Queries and row-to-model mapping
src/controller/StaffController.java Menu routing and operations
src/view/StaffView.java             Console input and table output
```

## Known limitations

- Numeric parsing retries malformed input, but does not enforce positive amounts/hours or finite decimal values. Text input can be empty.
- Database string lengths can reject values the console accepts.
- DAO values are parameter-bound, but the update column is concatenated. Current controller calls supply fixed column names; the DAO does not independently enforce an allowlist.
- Sequence allocation occurs before record input/insertion, so IDs need not be contiguous.
- Startup changes the sequence on each run; concurrent instances and existing data need separate review.
- JDBC resources are closed manually; exceptions can bypass some cleanup. Raw SQL error messages are displayed.
- No automated test suite, migration framework, backup or recovery workflow is supplied.

See [implementation notes](docs/implementation-notes.md), [CONTRIBUTING.md](CONTRIBUTING.md) and [SECURITY.md](SECURITY.md).

## Verification and licence

This refresh inspected source and documentation only. No Java compilation, database initialisation, CRUD operation or behavioural test was executed.

No project licence file is included in the export. This refresh assigns no licence and does not establish redistribution rights for the assignment PDF or bundled JARs.
