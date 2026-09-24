# CodeMetrics – Student Code Quality Analyzer

> **B.Tech Computer Science & Engineering (CSE) Semester Project**  
> **Phase 1 Evaluation (50 Marks)**  
> **Official Technology Stack:** HTML5, CSS3, Vanilla JavaScript, Core Java (SE 17+), JDBC, MySQL.  
> **Strict Architectural Policy:** Pure Vanilla Stack — Zero React, Node.js, Spring Boot, or external frameworks.

---

## 1. Project Title & Overview

**CodeMetrics – Student Code Quality Analyzer** is an automated academic software engineering tool engineered to evaluate Java source code written by computer science students. It assesses structural metrics, measures control flow cyclomatic complexity, monitors bracket-level nesting depth, detects clean code defects, and computes an objective quality score from 0 to 100.

The application bridges the gap between student lab assignments and professional software industry standards (e.g., SonarQube / PMD) without relying on heavy commercial frameworks.

---

## 2. Problem Statement

In undergraduate programming laboratories and coursework:
1. **Manual Inspection Bottlenecks:** Faculty and teaching assistants spend excessive hours manually verifying code styling, indentation, and structure rather than logical correctness.
2. **Delayed Student Feedback:** Students receive assignment feedback days or weeks after submission, missing immediate opportunities to refine their coding habits.
3. **Bad Code Smells & Antipatterns:** Novice programmers frequently develop detrimental habits: monolithic methods (>50 lines), deeply nested loops/conditionals (>4 levels), ambiguous single-letter variable names, and lack of modular decomposition.
4. **Subjective Grading:** Evaluating code quality without quantifiable metrics leads to inconsistent assessment across different evaluators.

---

## 3. Project Objectives

- **Automated Metric Extraction:** Compute 10 foundational software engineering metrics from submitted Java files in real-time.
- **Complexity Assessment:** Calculate McCabe's Cyclomatic Complexity to determine independent execution pathways and testing risk.
- **Clean Code Issue Detection:** Automatically flag poor naming conventions, bloated methods, missing documentation, and excessive nesting depth.
- **Relational Persistence:** Store user credentials, submission source codes, parsed metrics, and defect lists in a normalized MySQL relational database using Java Database Connectivity (JDBC).
- **Interactive Student Dashboard:** Provide clear visual feedback, radial score gauges, and highlighted code viewers to assist student self-learning.

---

## 4. Scope of the System

- **In Scope (Phase 1):**
  - Parsing single and multi-class Java source code files.
  - Calculation of: LOC, Class count, Method count, Variable declarations, Loop constructs (`for`, `while`, `do-while`), Conditional branches (`if`, `else if`, `switch`, `case`), Comment lines, Average method length, Maximum nesting depth, and Cyclomatic complexity.
  - Multi-tier issue ranking (`HIGH`, `MEDIUM`, `LOW`) with line numbers and corrective suggestions.
  - Complete MySQL relational database schema with referential integrity (`ON DELETE CASCADE`).
  - Pure JDBC data access objects (DAO pattern) with parameterized `PreparedStatement` queries.
  - Responsive, framework-free web interface with live client-side preview engine and demo presets.

- **Future Scope (Phase 2 & Phase 3):**
  - Static AST token streaming with complete syntax tree visualization.
  - Plagiarism detection across peer student submissions using cosine similarity.
  - Faculty grading portal with bulk assignment export and automated rubric scoring.

---

## 5. Target Users

1. **Undergraduate CS/IT Students:** Submitting lab assignments and receiving immediate feedback on code readability, complexity, and cleanliness.
2. **Laboratory Instructors / TAs:** Automating initial code quality screening before grading functional tests.
3. **Course Coordinators:** Monitoring semester-long student coding habits and quality progression over time.

---

## 6. Official Technology Stack

| Layer | Technology | Justification |
| :--- | :--- | :--- |
| **Frontend UI** | HTML5, CSS3, Vanilla JavaScript | Compliant with semester lab syllabus; zero external bundle dependencies. |
| **Backend Core** | Java SE (JDK 17+) | Native Java string tokenization, regex lexical scanning, and bracket stack algorithms. |
| **Persistence** | JDBC (`java.sql.*`) | Standard relational database connectivity using `PreparedStatement` to prevent SQL injection. |
| **Database** | MySQL 8.0+ | ACID-compliant relational storage with InnoDB foreign keys and cascaded deletions. |

> **Prohibited Frameworks Note:** In strict accordance with evaluation guidelines, this project contains **NO Spring Boot, NO Node.js/Express, NO React/Vue/Angular, NO Tailwind CSS, and NO ORMs (Hibernate/JPA)**.

---

## 7. System Architecture (3-Tier Model)

The application follows the classic, enterprise-standard **Three-Tier Architecture**:

```
+-------------------------------------------------------------------------+
|                         PRESENTATION TIER                               |
|        HTML5 Semantic Views + CSS3 Modern Styling + Vanilla JS          |
|  [index.html]   [login.html]   [dashboard.html]   [analyzer.html]       |
+-------------------------------------------------------------------------+
                                    |
                                    | HTTP / REST (JSON)
                                    v
+-------------------------------------------------------------------------+
|                        APPLICATION / LOGIC TIER                         |
|                    Pure Java Standard Edition (SE)                      |
|                                                                         |
|  +------------------------+  +---------------------------------------+  |
|  |     analyzer/          |  |               util/                   |  |
|  |  - CodeAnalyzer        |  |  - BracketStack (Scope Depth)         |  |
|  |  - MetricCalculator    |  |  - TokenCounter (Word Frequency)      |  |
|  |  - ComplexityAnalyzer  |  |  - CodeStructureNode (Parse Tree)     |  |
|  |  - IssueDetector       |  +---------------------------------------+  |
|  |  - QualityScoreCalc    |                                             |
|  +------------------------+  +---------------------------------------+  |
|                              |             controller/               |  |
|                              |  - CodeMetricsServer (Java HTTP)      |  |
|                              |  - Main (CLI Test Runner)             |  |
|                              +---------------------------------------+  |
+-------------------------------------------------------------------------+
                                    |
                                    | JDBC (PreparedStatement API)
                                    v
+-------------------------------------------------------------------------+
|                          PERSISTENCE TIER                               |
|                           MySQL Database                                |
|                                                                         |
|   +-----------+       +---------------+       +---------------------+   |
|   |   USERS   | 1---* |  SUBMISSIONS  | 1---1 |   ANALYSIS_RESULT   |   |
|   +-----------+       +---------------+       +---------------------+   |
|                              | 1                                        |
|                              | *                                        |
|                       +---------------+                                 |
|                       |  CODE_ISSUES  |                                 |
|                       +---------------+                                 |
+-------------------------------------------------------------------------+
```

---

## 8. Data Flow Diagrams (DFD)

### Level 0 DFD (Context Diagram)

```
                       Java Source Code
   +-----------+ ----------------------------> +--------------------+
   |           |                               |                    |
   |  STUDENT  | <---------------------------- |    CodeMetrics     |
   |           |    Score, Metrics & Issues    |   Analyzer System  |
   +-----------+                               +--------------------+
         ^                                               |
         | Account Credentials                           | Read / Write
         v                                               v
   +-----------+                               +--------------------+
   |  Student  |                               |    MySQL 8.0 DB    |
   |  Session  |                               |  (codemetrics_db)  |
   +-----------+                               +--------------------+
```

### Level 1 DFD (Subsystem Level)

```
[Student]
   |
   | (1. Credentials)
   v
[Process 1.0: User Authentication] <=======> (D1: USERS Table)
   |
   | (2. Valid Session)
   v
[Process 2.0: Code Ingestion & Validation]
   |
   | (3. Validated Java Code)
   v
[Process 3.0: Lexical Scanning & Parsing] 
   |
   +---> (Tokens, Lines, Comments)
   |
[Process 4.0: Metric & Complexity Engine] <---> [BracketStack Helper]
   |
   +---> (10 Metrics, Cyclomatic Complexity, Max Nesting)
   |
[Process 5.0: Heuristic Defect Detector]
   |
   +---> (Ranked Issues: High, Medium, Low)
   |
[Process 6.0: Score Formulation & Storage]
   |
   +=====> (D2: SUBMISSIONS Table)
   +=====> (D3: ANALYSIS_RESULT Table)
   +=====> (D4: CODE_ISSUES Table)
   |
   v
[Student UI: Dashboard & Visual Inspection Report]
```

### Level 2 DFD (Code Analysis Deep Dive)

```
(Raw Java Code String)
          |
          v
   +---------------+
   | 3.1 Line-by-  | ----> Identifies comment blocks (/* ... */, //)
   | Line Scanner  | ----> Filters blank lines and measures raw LOC
   +---------------+
          |
          v
   +---------------+
   | 3.2 Tokenizer | ----> RegEx extraction of classes, methods, and variables
   | & Regex Match | ----> Identifies loops (for, while, do) & branching (if, switch)
   +---------------+
          |
          v
   +---------------+
   | 3.3 Bracket   | ----> Pushes '{' onto custom BracketStack
   | Scope Tracker | ----> Pops '}' and tracks max depth observed
   +---------------+ ----> Measures per-method span (EndLine - StartLine)
          |
          v
   +---------------+
   | 3.4 Defect    | ----> Checks naming patterns (e.g., single-letter vars)
   | Classifier    | ----> Flags methods > 30 lines (HIGH severity)
   +---------------+ ----> Flags nesting >= 4 levels (HIGH severity)
          |
          v
   +---------------+
   | 3.5 Weighted  | ----> Formula: 30% Complexity + 35% Readability + 35% Maintainability
   | Score Normal. | ----> Deducts penalties based on defect severity
   +---------------+
          |
          v
(Final Quality Score: 0 - 100)
```

---

## 9. Database Design & Schema Specifications

The database consists of 4 normalized relational tables in 3rd Normal Form (3NF):

### Table: `USERS`
| Column Name | Data Type | Key / Constraint | Description |
| :--- | :--- | :--- | :--- |
| `user_id` | `INT` | `PRIMARY KEY, AUTO_INCREMENT` | Unique identifier for each student. |
| `name` | `VARCHAR(100)` | `NOT NULL` | Full student name. |
| `email` | `VARCHAR(150)` | `UNIQUE, NOT NULL` | Academic login email address. |
| `password` | `VARCHAR(255)` | `NOT NULL` | Student password credential. |
| `roll_number`| `VARCHAR(50)` | `DEFAULT '21CS001'` | College examination roll number. |
| `created_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Account registration timestamp. |

### Table: `SUBMISSIONS`
| Column Name | Data Type | Key / Constraint | Description |
| :--- | :--- | :--- | :--- |
| `submission_id` | `VARCHAR(50)`| `PRIMARY KEY` | Custom alpha-numeric ID (e.g., `SUB-8491`). |
| `user_id` | `INT` | `FOREIGN KEY -> USERS(user_id)` | Owning student identifier. |
| `title` | `VARCHAR(200)`| `NOT NULL` | Java file or class name. |
| `language` | `VARCHAR(50)` | `DEFAULT 'Java'` | Programming language. |
| `code` | `LONGTEXT` | `NOT NULL` | Raw Java source code text. |
| `score` | `INT` | `CHECK (score BETWEEN 0 AND 100)` | Final computed quality rating. |
| `submitted_at`| `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Date and time of analysis. |

### Table: `ANALYSIS_RESULT`
| Column Name | Data Type | Key / Constraint | Description |
| :--- | :--- | :--- | :--- |
| `result_id` | `INT` | `PRIMARY KEY, AUTO_INCREMENT` | Internal result key. |
| `submission_id` | `VARCHAR(50)`| `UNIQUE, FOREIGN KEY -> SUBMISSIONS`| Target submission reference (1:1). |
| `lines_of_code` | `INT` | `NOT NULL DEFAULT 0` | Effective non-blank, non-comment lines. |
| `classes` | `INT` | `NOT NULL DEFAULT 1` | Declared classes/interfaces. |
| `methods` | `INT` | `NOT NULL DEFAULT 0` | Member methods declared. |
| `variables` | `INT` | `NOT NULL DEFAULT 0` | Declared fields and local variables. |
| `loops` | `INT` | `NOT NULL DEFAULT 0` | Total loop constructs (`for`, `while`, `do`). |
| `conditions` | `INT` | `NOT NULL DEFAULT 0` | Conditional statements (`if`, `switch`). |
| `comments` | `INT` | `NOT NULL DEFAULT 0` | Single-line and block comment lines. |
| `avg_method_length`| `INT` | `NOT NULL DEFAULT 0` | Average span in lines per method. |
| `max_nesting_depth`| `INT` | `NOT NULL DEFAULT 0` | Peak bracket nesting depth. |
| `complexity` | `INT` | `NOT NULL DEFAULT 1` | McCabe Cyclomatic Complexity count. |
| `readability` | `INT` | `NOT NULL DEFAULT 100` | Readability subscore (0–100). |
| `maintainability`| `INT` | `NOT NULL DEFAULT 100` | Maintainability subscore (0–100). |

### Table: `CODE_ISSUES`
| Column Name | Data Type | Key / Constraint | Description |
| :--- | :--- | :--- | :--- |
| `issue_id` | `INT` | `PRIMARY KEY, AUTO_INCREMENT` | Unique issue item ID. |
| `submission_id` | `VARCHAR(50)`| `FOREIGN KEY -> SUBMISSIONS` | Parent submission reference (1:N). |
| `severity` | `ENUM('LOW', 'MEDIUM', 'HIGH')` | `NOT NULL` | Priority/impact classification. |
| `line_number` | `INT` | `NOT NULL` | 1-based source code line where issue occurs. |
| `issue_type` | `VARCHAR(100)`| `NOT NULL` | Category (e.g. Deep Nesting, Long Method). |
| `message` | `VARCHAR(500)`| `NOT NULL` | Explanation of the defect. |
| `suggestion` | `TEXT` | `NOT NULL` | Refactoring guidance for the student. |

---

## 10. Metric Formulas & Algorithms

### 1. McCabe Cyclomatic Complexity ($V(G)$)
The system calculates cyclomatic complexity using the predicate node formula:
$$V(G) = P + 1$$
Where $P$ is the total count of decision points:
- Loop decision branches: `for`, `while`, `do-while`
- Conditional decision branches: `if`, `else if`, `case`
- Logical short-circuit operators: `&&`, `||`

**Risk Classifications:**
- $1 \le V(G) \le 5$: Low risk, simple and easily testable.
- $6 \le V(G) \le 10$: Moderate risk, requires structured test cases.
- $11 \le V(G) \le 20$: High risk, refactoring strongly recommended.
- $V(G) > 20$: Untestable monolithic flow.

### 2. Nesting Depth Tracking (Custom BracketStack)
A custom academic linked stack (`util.BracketStack`) tracks open braces:
1. When encountering `{`: Pushes character onto stack. Updates `maxDepth = max(maxDepth, currentSize)`.
2. When encountering `}`: Pops top element. If stack size was matching method brace depth, records method length (`currentLine - startLine`).
3. If `currentSize >= 4`, flags a **HIGH** severity **Deep Nesting** issue.

### 3. Overall Quality Score Formulation ($Q$)
$$Q = \left( 0.30 \times S_{\text{complexity}} + 0.35 \times S_{\text{readability}} + 0.35 \times S_{\text{maintainability}} \right) - \text{Penalties}$$
Where:
- $S_{\text{complexity}} = \max(20, \min(100, 100 - (V(G) \times 4)))$
- $S_{\text{readability}} = \max(20, \min(100, 95 - (\text{maxDepth} \times 10) + \text{CommentBonus}))$
- $S_{\text{maintainability}} = \max(20, \min(100, 100 - \max(0, (\text{avgMethodLen} - 25) \times 2)))$
- $\text{Penalties} = \frac{1}{2} \left( 8 \times N_{\text{HIGH}} + 4 \times N_{\text{MEDIUM}} + 2 \times N_{\text{LOW}} \right)$
- Clamped within $15 \le Q \le 100$.

---

## 11. Setup & Installation Guide

### Step 1: MySQL Database Initialization
1. Ensure MySQL Server is running locally on port `3306`.
2. Open terminal or MySQL Workbench and run:
```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/sample_data.sql
```
3. Verify tables created:
```sql
USE codemetrics_db;
SHOW TABLES;
SELECT * FROM USERS;
```

### Step 2: Compiling & Running Java Backend
1. Ensure Java SE JDK 17 or higher is installed:
```bash
javac -version
java -version
```
2. Navigate to `backend/src` and compile the classes:
```bash
cd backend/src
javac model/*.java util/*.java dao/*.java analyzer/*.java service/*.java controller/*.java Main.java
```
3. Run the standalone CLI demonstration runner:
```bash
java Main
```
4. Or launch the pure Java HTTP server on port 8080:
```bash
java controller.CodeMetricsServer
```

### Step 3: Launching the Frontend Application
- The web interface is written in pure semantic HTML5, CSS3, and Vanilla JavaScript.
- Simply open `index.html` in any modern web browser (Google Chrome, Firefox, Safari, Edge).
- Or run using any static file server on port 3000.

---

## 12. Viva Voce / Examination Questions & Answers

**Q1: Why was JDBC PreparedStatement chosen over Statement?**  
*Answer:* `PreparedStatement` pre-compiles SQL queries on the database server. It guarantees protection against SQL Injection attacks because query arguments are bound as data literals rather than concatenated string fragments. Additionally, it enables batch updates (`addBatch()`) for inserting multiple code issues efficiently.

**Q2: What is McCabe's Cyclomatic Complexity and why is it important?**  
*Answer:* Developed by Thomas McCabe in 1976, Cyclomatic Complexity measures the number of linearly independent paths through a program's source code. It dictates the minimum number of test cases required to achieve branch coverage. High complexity correlates directly with elevated defect density and maintenance difficulty.

**Q3: How does the system handle scope nesting without a full compiler frontend?**  
*Answer:* The system implements a custom `BracketStack` data structure. It pushes '{' and pops '}' while ignoring string literals and comments. The peak size of the stack during method traversal gives the exact maximum lexical nesting depth.

**Q4: Why use a 3-tier architecture instead of embedding SQL in the presentation tier?**  
*Answer:* The 3-tier model enforces separation of concerns. The presentation layer remains lightweight and decoupled; the application layer houses the analytical algorithms, and the persistence layer encapsulates database-specific SQL logic within Data Access Objects (DAOs).

**Q5: What are the main code smells detected in Phase 1?**  
*Answer:* (1) Long Methods (>30 lines), (2) Deep Nesting (>=4 levels), (3) Poor Variable Naming (single letters or trailing digits), (4) Methods starting with uppercase letters, (5) Missing Comments/Javadoc, and (6) Lines exceeding 100 characters.
