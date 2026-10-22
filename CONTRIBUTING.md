# Contributing

Keep changes focused on the coursework's MVC, inheritance and JDBC learning goals. Read the [README](README.md) and [implementation notes](docs/implementation-notes.md).

Use synthetic records and an isolated development database. Do not commit credentials, real staff names/addresses/pay data, generated class files or database dumps.

## Verification

No automated test suite is supplied. Report the actual JDK, JAR versions, commands and database scenarios exercised. Compilation does not establish successful initialisation or persistence.

Useful regression cases include a fresh empty database, restart with existing rows, all three pay formulas, type-specific updates, missing IDs, deletion, failed connections and invalid input. Check negative/non-finite numbers and overlong/empty text before claiming validation is complete.

Keep SQL column selection restricted to known fields. Parameter binding for values does not protect a concatenated identifier. Review sequence changes and existing-data compatibility before modifying startup logic.

For non-trivial changes, add a focused regression check where practical. Do not add a framework solely to make a small fix look more sophisticated.

## Pull requests

Describe the problem, focused change, checks run, database effects and remaining gaps. Keep unrelated formatting, IDE changes and dependency upgrades separate. Follow [SECURITY.md](SECURITY.md) for sensitive reports.
