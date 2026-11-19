#### Containerized Static Code Analysis (Parasoft Jtest)

This repository configures a **GitHub Actions** CI/CD workflow (**Container Jtest with Gradle**) that executes static code analysis inside a Docker container using **Parasoft Jtest 2026.2** and uploads the findings to GitHub Code Scanning. 

#### ⚙️ Analysis Job Overview (Analyze project with Jtest container)

Triggered on **push** or **pull request** events targeting the container branch (as well as manual dispatch), the job runs on a **Windows self-hosted runner** with Docker Desktop and executes three core steps: 

1. **Repository Checkout (actions/checkout@v6)** 

  * Clones the container branch with full commit history (fetch-depth: 0).
2. **Containerized Jtest Execution (Run Container)** 

  * Reads the DTP_HOST repository secret and maps it inside the container to the host gateway via --add-host="%DTP_HOST%:host-gateway", allowing the containerized tool to communicate with the Parasoft DTP server.
  * Spins up an ephemeral container (--rm) from the parasoft/jtest:2026.2 image (named c_jtest2026.2).
  * Mounts the repository workspace to /home/parasoft/example and injects the local jtestcli.properties configuration file into /opt/parasoft/jtest/jtestcli.properties.
  * Executes jtestcli against the project build data (/home/parasoft/example/demo.data.json) using the built-in **Recommended Rules** configuration and outputs reports to /home/parasoft/example/reports.
3. **SARIF Report Upload (Upload results (SARIF))** 

  * Runs unconditionally (if: always()) via github/codeql-action/upload-sarif@v4 with wait-for-processing: true.
  * Publishes ${{ github.workspace }}/reports/report.sarif directly to the repository's **Security -> Code scanning alerts** tab.

#### 🔐 Secrets Configuration (DTP_HOST)

The workflow requires the DTP_HOST secret to resolve the Parasoft DTP hostname inside the Docker container: 

1. Navigate to your GitHub repository **Settings -> Secrets and variables -> Actions**.
2. Click **New repository secret**.
3. Set **Name** to DTP_HOST.
4. Set **Secret** to your full DTP hostname (e.g., fullHost.com.name).
5. Click **Add secret**.
<img width="1231" height="316" alt="image" src="https://github.com/user-attachments/assets/ad66f0a7-715d-4ec0-ac4b-db3aa9c1a89d" />



#### 🖥️ Runner Prerequisites

* **OS & Runtime**: Windows self-hosted runner (shell: cmd) with **Docker Desktop** running.
* **Docker Image**: parasoft/jtest:2026.2 available locally on the host.
* **Required Files in Workspace**: Pre-generated demo.data.json and jtestcli.properties configuration file.
* **Secrets & Permissions**: DTP_HOST repository secret configured and security-events: write enabled for the workflow.
