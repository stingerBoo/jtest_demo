# Containerized Static Code Analysis (Parasoft Jtest)

This repository configures a **GitHub Actions** CI/CD workflow (**`Container Jtest with Gradle`**) that executes static code analysis inside a Docker container using **Parasoft Jtest 2026.2** and uploads the findings to GitHub Code Scanning.

## ⚙️ Analysis Job Overview (`Analyze project with Jtest`)

Triggered on **push** or **pull request** events targeting the `container` branch (as well as manual dispatch), the job runs on a **Windows self-hosted runner** with Docker Desktop and executes three core steps:

1. **Repository Checkout (`actions/checkout@v6`)**
   - Clones the `container` branch with full commit history (`fetch-depth: 0`).

2. **Containerized Jtest Execution (`Run Container`)**
   - Spins up an ephemeral container (`--rm`) from the `parasoft/jtest:2026.2` image (named `c_jtest2026.2`).
   - Mounts the repository workspace to `/home/parasoft/example` and injects the local `jtestcli.properties` configuration file into `/opt/parasoft/jtest/jtestcli.properties`.
   - Executes `jtestcli` against the project build data (`/home/parasoft/example/demo.data.json`) using the built-in **`Recommended Rules`** configuration.

3. **SARIF Report Upload (`Upload results (SARIF)`)**
   - Runs unconditionally (`if: always()`) via `github/codeql-action/upload-sarif@v4`.
   - Publishes `${{ github.workspace }}/reports/report.sarif` directly to the repository's **Security -> Code scanning alerts** tab.

---

## 🖥️ Runner Prerequisites

- **OS & Runtime**: Windows self-hosted runner (`shell: cmd`) with **Docker Desktop** running.
- **Docker Image**: `parasoft/jtest:2026.2` available locally on the host.
- **Required Files in Workspace**: Pre-generated `demo.data.json` and `jtestcli.properties` configuration file.
- **Permissions**: `security-events: write` enabled for the workflow.
