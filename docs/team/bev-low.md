# Project Portfolio: Low Beverly

## Project: BuffBuddy
BuffBuddy is a fitness tracker that tracks your programmes, workouts, meals and water intake. The user interacts wtih
it using a CLI, and it has a GUI created with JavaFX. It is written in Java. 

## Summary of Contributions

### Code Contributed
- **Code Link**: [Click here to view my code on the tP Code Dashboard](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=bev-low&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-09-20&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=Bev-low&tabRepo=AY2425S1-CS2113-W10-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented

- **Save/Load Feature**: Designed and wrote the code to save and load files into JSON Objects using the Gson Library.

    - **What it does**: This feature allows the application to save and load data seamlessly, storing it in JSON format to ensure data persistence between sessions. The feature uses the Gson library to serialize and deserialize data into JSON objects, enabling a structured and readable file format.

    - **Justification**: The save/load feature is essential for users to maintain their progress and data across sessions, making the application more reliable and user-friendly. Without this feature, users would risk losing their data upon closing the application.

    - **Highlights**: This feature required careful design and implementation to integrate with the application's data model. Converting complex data into JSON format required precise management of data structures and file handling to ensure accuracy and consistency. The implementation also included error handling to manage file I/O exceptions and ensure robust file-saving capabilities.

    - **Credits**: The Gson library was used extensively to handle JSON serialization and deserialization, making it easier to manage structured data within the application.

- **Water Intake Features**: Brief description of additional features or optimizations you contributed.

### Contributions to the User Guide (UG)
- Added/edited the following sections:
    - **Added documentation for water related features**: Description of the section or feature.
    - **Section 2**: Another section contributed to, with any additional context if needed.

### Contributions to the Developer Guide (DG)
- **Sections Contributed**: Storage component, Save/Load implementation, Daily Record Class
- **UML Diagrams**:
    - **Storage Class**: Description of the diagram or any updates made.
    - **FileManager Class**: Additional diagrams contributed, if any.
    - **DailyRecord Class**: Additional diagrams contributed, if any.
    - **Save/Load Feature**: Additional diagrams contributed, if any.

### Contributions to Team-Based Tasks
- Participated in team meetings.
- Assisted with [mention any collaborative tasks, like integrating code from different features or team-based debugging sessions].

### Review/Mentoring Contributions
- **Pull Request Reviews**:
    - [PR #178 - Add documentation and testing for edit programme](https://github.com/AY2425S1-CS2113-W10-3/tp/pull/178)
    - [PR #165 - Add delete log functionality](https://github.com/AY2425S1-CS2113-W10-3/tp/pull/165)
    - [PR #164 - Add calorie information to Exercise objects](https://github.com/AY2425S1-CS2113-W10-3/tp/pull/164)
    - [PR #162 - Refactor EditCommand](https://github.com/AY2425S1-CS2113-W10-3/tp/pull/162)
    - [PR #151 - Added WaterCommandFactory and ViewWaterCommand classes](https://github.com/AY2425S1-CS2113-W10-3/tp/pull/151)
- Mentored team members by providing code feedback and debugging assistance.

### Contributions Beyond the Project Team
