# Security guidance

This local coursework app is not a hardened HR/payroll service. No security audit, supported-release programme or response deadline is established here.

## Credentials and personal data

DatabaseHelper.java contains hard-coded database credentials. Treat them as exposed if used anywhere real, rotate affected credentials and review history/shared exports. Their values are not included in this overlay. Source cleanup and configuration externalisation require separate changes.

Use an isolated development database with synthetic data. The application stores names, addresses and pay-related values without an application authentication or authorisation layer. Do not use real staff records merely because PostgreSQL persistence is present.

## Database boundaries

Startup creates/changes schema and sequence state. Review permissions and recovery before connecting to an existing database. No backup or rollback is provided by this documentation.

StaffDAO binds record values, but concatenates the update column. The controller currently supplies fixed literals; keep that boundary explicit and do not accept arbitrary column names in future callers.

Input parsing does not enforce business ranges or finite decimal values. JDBC resources are manually closed, and SQL errors are shown to the console. These are source observations, not executed exploits or a complete vulnerability inventory.

## Reporting

Use synthetic reproductions and redact credentials, records and local paths. For sensitive concerns, use a verified private maintainer channel if available, or request one without publishing the details. No dedicated security mailbox or enabled private-reporting feature is confirmed by the supplied repository.

This documentation records risks; it does not remediate them.
