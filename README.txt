# Static Code Analysis (Parasoft Jtest + Gradle)

This repository includes a configured **GitHub Actions** CI/CD pipeline that automates the project build process using **Gradle** and performs static code analysis with **Parasoft Jtest**. Scan results are automatically integrated into the GitHub **Security** tab.

## 📋 Table of Contents
- [How It Works](#how-it-works)
- [Prerequisites (Self-hosted Runner)](#prerequisites-self-hosted-runner)
- [Usage & Triggers](#usage--triggers)
- [Reporting & Results](#reporting--results)

---

## ⚙️ How It Works

The **`Jtest with Gradle`** workflow executes the following steps:

1. **Checkout Repository (`actions/checkout@v6`)**: Clones the full repository history (`fetch-depth: 0`) from the `main` branch and configures the workspace as a safe Git directory.
2. **Git Configuration**: Fetches remote references and ensures the workspace path is registered in Git's global `safe.directory` configuration.
3. **Build & Static Analysis (`gradlew jtest`)**:
   - Cleans and builds the project using the Gradle Wrapper (`.\gradlew clean build`).
   - Runs Parasoft Jtest analysis using the built-in ruleset (`builtin://Recommended Rules`).
   - Outputs analysis reports into the `reports/` directory.
4. **Upload Results to GitHub (`upload-sarif@v4`)**: Uploads the generated `report.sarif` file to GitHub Code Scanning (runs via `if: always()` even if previous steps fail or detect issues).

---

## 🖥️ Prerequisites (Self-hosted Runner)

Because this pipeline runs on a custom environment (`runs-on: self-hosted` with `shell: cmd`), the host machine must meet the following requirements:

- **Operating System**: Windows (required for `cmd` shell execution and `.\gradlew`).
- **Parasoft Jtest**: Must be installed locally on the self-hosted runner.
- **Environment Variable `%JTEST%`**: Must be set on the runner and point to the Jtest installation directory (used to load `%JTEST%/integration/gradle/init.gradle`).
- **Repository Permissions**: The workflow requires `security-events: write` permission to publish SARIF results.

---

## 🚀 Usage & Triggers

The workflow triggers **automatically** on:
- **Push** events to the `main` branch.
- **Pull Request** events targeting the `main` branch.

**Manual Execution:**
1. Navigate to the **Actions** tab in this repository.
2. Select **`Jtest with Gradle`** from the workflows list on the left.
3. Click the **Run workflow** button on the right.

---

## 📊 Reporting & Results

Once the workflow completes, analysis results can be viewed in two places:
- **GitHub Code Scanning**: Go to **Security** -> **Code scanning alerts** (filtered under category `parasoft-jtest-SoftCode4`) to inspect detected rule violations directly in GitHub.
- **Local Reports**: Detailed report files (including `report.sarif`) are saved in the `reports/` directory within the runner's workspace.
