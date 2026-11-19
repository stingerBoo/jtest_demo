# Incremental Static Code Analysis (Parasoft Jtest Branch Diff)

This repository demonstrates how **Parasoft Jtest** performs static code analysis **exclusively on files modified between selected Git branches**. 
Instead of scanning the entire codebase, Jtest restricts the analysis scope to the diff between the active working branch (`branchDiffScopeFilter`) and a target reference branch (`remotes/origin/main`).

---

## 🎯 Branch Diff Scope Configuration

The custom test configuration **`Recommended Rules scope diff branches.properteis`**(under this branch) filters files under the **Scope -> Time filters** tab using the following settings:
- **Filter Mode**: `Check files modified between the current working branch and...`
- **Reference Target**: `custom reference branch/revision` set to **`remotes/origin/main`**

With this setup, Jtest compares the current branch (`branchDiffScopeFilter`) against `remotes/origin/main` and analyzes only the changed files.

<!-- 👇 PASTE YOUR SCREENSHOT BELOW (in GitHub's editor, place the cursor here and press Ctrl+V) 👇 -->
<img width="975" height="269" alt="image" src="https://github.com/user-attachments/assets/134d3df7-7b66-4ecf-a851-ae7a5527a151" />


---

## 🔧 Source Control Configuration (`jtestsettings.properties`)

To allow Jtest to calculate branch differences via Git, source control integration is configured in `jtestsettings.properties`:

```properties
scope.scontrol=true
scontrol.rep1.type=git
scontrol.rep1.git.url=https\://github.com/your/project
# branch is not needed here regarding the runner
#scontrol.rep1.git.branch=yourBranch
scontrol.rep1.git.workspace=c\:\\runner\\_work\\jtest_demo\\jtest_demo
scontrol.git.exec=C\:\\your\\bin\\git.exe
```

---

## ⚙ Workflow Execution (`Scope diff on branches`)

1. **Full Git History (`actions/checkout@v6`)**: Checks out `branch1` with `fetch-depth: 0` and runs `git fetch --all` so Git has access to `remotes/origin/main` for comparison.
2. **Scope-Filtered Jtest Run**: Executes `.\gradlew clean build jtest` using:
   - `-Djtest.config="user://Recommended Rules scope diff branches"`
   - `-Djtest.settings="\${{ github.workspace }}/jtestsettings.properties"`
3. **Results Reporting**: Outputs findings to `reports/report.sarif` and uploads them to GitHub **Security -> Code scanning** (`parasoft-jtest-SoftCode4`).
