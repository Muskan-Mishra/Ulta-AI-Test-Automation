# Ulta AI-Powered Test Automation Using GitHub Copilot

## Objective
Automate AC1-AC7 from the supplied Wipro problem statement using Java, Selenium, Cucumber BDD, TestNG, POM and Allure. Use GitHub Copilot for test design/code generation/review and AI-assisted result analysis.

## Prerequisites
- JDK 17 recommended (JDK 11+ strongly recommended for current Selenium/Maven dependencies)
- Maven 3.9+
- IntelliJ IDEA / Eclipse / VS Code
- Chrome or Edge
- GitHub Copilot
- Allure CLI for viewing reports

## Run
```bash
mvn clean test
```

Chrome:
```bash
mvn clean test -Dbrowser=chrome
```

Edge:
```bash
mvn clean test -Dbrowser=edge
```

Headless:
```bash
mvn clean test -Dheadless=true
```

## Reports
- Cucumber HTML: `target/cucumber-report.html`
- Cucumber JSON: `target/cucumber.json`
- Allure raw results: `allure-results/` when Allure integration is enabled by the runtime/plugin
- Failure screenshots: `reports/screenshots/`

Generate an Allure report:
```bash
allure serve allure-results
```

Generate the AI-assisted execution summary:
```bash
mvn -q -DskipTests compile exec:java -Dexec.mainClass=ai.AIResultAnalyzer
```
If the exec plugin is not installed, run the class from IntelliJ, passing `target/cucumber.json reports/ai-summary.md` as program arguments.

## GitHub Copilot evidence
Keep screenshots or exported text of these prompts:
1. Generate test scenarios from AC1-AC7.
2. Convert scenarios to Gherkin.
3. Generate/refactor POM classes.
4. Generate Cucumber step definitions.
5. Generate negative and boundary cases.
6. Review locator stability and synchronization.
7. Analyze failed test output and cluster defects.

## AI analysis design
The included `AIResultAnalyzer` creates a deterministic baseline summary and defect clusters from Cucumber JSON. For the assessment's GenAI demonstration, paste the failed-output section into GitHub Copilot using the root-cause-analysis prompt in this README. This avoids hard-coding an API key in the repository.

## Important live-site note
Ulta is a live production website. DOM, popups, inventory, recommendation modules and checkout behavior can change. The framework intentionally uses resilient/fallback locators and does not place an order. If a locator changes, update the Page Object rather than the step definitions.
