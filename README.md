# Team Project

## Team Information

### Team Members
- Matthew He
- Leo Wu
- Haoran Zhao
- Cola He
- Zefeng Xu

## Project Description
### Domain

## User Stories
- As a student, I want to be able to save my timetable as a pdf onto my computer so that I can share it with others,
check it later or print it onto a paper.
- As a student, I want to track my academic process and know what course I need to take in order to complete my degree
and graduate.
- As a student, I want to be able to drag courses from the course list into the timetable to arrange the courses I want
take in the future, and also have a record of what courses I have taken.
- As a student, when I hover over a course name, I want to see both its prerequisites and the courses for which it
serves as a prerequisite.
- As a user, I want to create an account and login so I can have the progress of my timetable to be saved for checking
and editing purpose in the future.

## User Cases
## Use Case 1
**Name**: Export Timetable as PDF

**Primary Actor**: Student

**Goal**: Save a copy of the timetable as a PDF for sharing, viewing later, or printing.

**Preconditions**: The student has a timetable open in the application.

**Trigger**: The student selects Export as PDF.

**Success Outcome**: A PDF containing the student’s timetable is saved to the selected location on their computer.

**Main Success Scenario**:
1. The student selects Export as PDF from the timetable view.
2. The application prompts the student to choose a file name and save location.
3. The student confirms the file name and location.
4. The application generates a PDF of the current timetable and saves it to the selected location.
5. The application confirms that the PDF was saved successfully.

**Alternative Flows**：
- 3a. The student cancels. The save dialog closes and the student returns to the timetable view. No PDF is saved.
- 4a. The application cannot generate or save the PDF. The application displays an error message. The student can retry the export.
## Use Case 2
**Name**: Track Graduation Progress 

**Primary Actor**: Student 

**Preconditions**: The student can access the course planner. 

**Trigger**: The student starts tracking progress toward a target degree. 

**Success Outcome**: The student sees which requirements for the selected degree and for each selected program are
satisfied by completed courses, expected to be satisfied under the current course plan, or still missing. For missing
requirements, the system shows required courses or eligible course options when that information is available. 

**Main Success Scenario**:
1. The student selects a target degree (e.g., H.B.Sc.) and the programs they are pursuing (Specialist, Major, or Minor).
2. The system retrieves the requirements for the selected degree and programs, along with the student's completed and
planned courses.
3. The system evaluates each requirement using completed courses first, then checks whether adding the planned courses
would satisfy any remaining requirements.
4. The system displays graduation progress for the degree and for each program, showing the status of each requirement
and available course details for missing requirements. 

**Extensions**:
- **3a. The course plan is empty**: The system evaluates requirements using completed courses alone and displays which
requirements are satisfied or still missing.
- **2a. Requirements are unavailable for a selected degree or program**: The system identifies the affected degree or
program and marks its progress as unavailable. It continues evaluating selections whose requirements are available.
