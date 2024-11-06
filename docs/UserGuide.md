# User Guide

## Introduction

BuffBuddy is your all-in-one fitness tracking companion, designed to help you streamline and organize your workout routines.
Whether you’re planning custom programs, tracking daily exercises, or logging your workout history, BuffBuddy keeps everything in one place.
Build personalized workout plans, log progress, and stay motivated with an intuitive interface that’s perfect for fitness enthusiasts of all levels.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `BuffBuddy` from [here](https://github.com/AY2425S1-CS2113-W10-3/tp/releases/tag/v1.0).
3. Open a command terminal, cd into the folder you put the jar file in, and use the `java -jar BuffBuddy.jar` command to run the application.

## Terminology:

- **Exercise**: An exercise defined by a name, rep, set, weight and average calories burned.
- **Day**: A ‘workout day’ is a collection of exercises to be done together.
- **Programme**: A programme is a collection of workout days.
- **Daily Record**: A daily record contains a user's workout activity, food intake and water intake for any given day.

---

## Features

> **Important Notes**
>
> When entering a multi-word input for any string argument, i.e. "My Starter Programme", please enter it with a "\_" between the spaces like so: "My_Starter_Programme".

### 1. Add a New Programme

Adds a new empty workout Programme with a given name.

Command: `prog create PROG_NAME`

Example: `prog create Starter`

```
========================================
New programme created:
Starter
========================================
```

_Note_: Advanced users can directly create Programmes with Day and Exercise data like so:

Command: `prog create PROG_NAME /d /e /n EXERCISE_NAME /s SET /r REP /w WEIGHT /c CALORIES /e ...`

Example: `prog create Starter /d ONE /e /n Bench_Press /s 3 /r 12 /w 30 /c 200 /e /n Squat /s 3 /r 12 /w 50 /c 200 /d TWO /e /n Bicep_Curl /s 3 /r 12 /w 10 /c 100`

```
========================================
New programme created:
Starter

Day 1: Name: ONE
1. Bench Press: 3 sets of 12 reps at 30 kg | Burnt 200 calories
2. Squat: 3 sets of 12 reps at 50 kg | Burnt 200 calories

Day 2: Name: TWO
1. Bicep Curl: 3 sets of 12 reps at 10 kg | Burnt 100 calories
========================================
```

---

### 2. View Programme

This command displays the detailed workout routine, separated by days, for the chosen Programme.
Each exercise includes its name, sets, reps, weight and how many calories it would burn.

If no Programme index is provided, it defaults to viewing the currently active Programme.

Command: `prog view [PROG_INDEX]`

Example: `prog view 1`

```
========================================
Viewing programme:
Starter

Day 1: ONE
1. Bench Press: 3 sets of 12 at 30 | Burnt 200 cals
2. Squat: 3 sets of 12 at 50 | Burnt 200 cals

Day 2: TWO
1. Bicep Curl: 3 sets of 12 at 10 | Burnt 100 cals
========================================
```

---

### 3. List All Programmes

Lists all workout Programmes belonging to the user by name and index.

Command: `prog list`

Example: `prog list`

```
Listing programmes:
1. Advanced
2. Starter -- Active
```

---

### 4. Delete Programme

Deletes the programme at the given index.

Command: `prog delete INDEX`

Example: `prog delete 1`

```
==================================================
Deleted programme:
Starter

Day 1: ONE
1. bench: 3 sets of 30 at 30 | Burnt 0 cals
2. Squat: 3 sets of 12 at 50 | Burnt 0 cals

Day 2: TWO
1. Bicep Curl: 3 sets of 12 at 10 | Burnt 0 cals
==================================================
```

---

### 5. Add a New Day in an Existing Programme

Add a new Day in an existing programme

Command: `prog edit [/p PROG_INDEX] /ad DAY_NAME`

Parameters:
- **/p PROG_INDEX** _(optional)_: Specifies the Programme index. Defaults to the current active Programme if omitted.  
  **Alias**: `/programme` – Either `/p` or `/programme` can be used to specify the programme index.
- **/ad DAY_NAME**: DAY_NAME is a string that names the day. 
  **Alias**: `/addDay` – Either `/ad` or `/addDay` can be used to add a day.

Example: `prog edit /p 1 /ad "Cardio Day"`
Creates a new empty Day named "Cardio Day" in Programme 1.

```plaintext
Created new day: Cardio Day
```

_Note_: Advanced users can directly create days with exercises using the syntax found in `Create Programme`

```plaintext
prog edit /p 1 /ad "Pull Day" /e /n "Push-Up /w 30 /r 15 /s 3 /c 100 /e ...
```

---

### 6. Delete a Day in an Existing Programme

Delete a day in an existing programme

Command: `prog edit [/p PORG_INDEX] /xd DAY_INDEX`

Parameters:
- **/p PROG_INDEX** _(optional)_: Specifies the Programme index. Defaults to the current active Programme if omitted.  
  **Alias**: `/programme` – Either `/p` or `/programme` can be used to specify the programme index.
- **/xd DAY_INDEX**: DAY_INDEX is the index of the day in the programme that the user wants to delete.
  **Alias**: `/removeDay` – Either `/xd` or `/removeDay` can be used to delete a day.

Example: `prog edit /p 1 /xd 1`
Deletes day 1 in programme 1

```plaintext
Deleted day:
Cardio Day
```

---

### 7. Add an Exercise of an Existing Day in an Existing Programme

Add an exercise to an existing day in an existing programme

Command: `prog edit [/p PORG_INDEX] /d DAY_INDEX /a /n EXERCISE_NAME /w WEIGHT /r REPS /s SETS /c CALORIES`

Parameters:
- **/p PROG_INDEX** _(optional)_: Specifies the Programme index. Defaults to the current active Programme if omitted.  
  **Alias**: `/programme` – Either `/p` or `/programme` can be used to specify the programme index.
- **/d DAY_INDEX**: Index of day the user wants to add exercise to.
- **/a** : Flag to add exercise.
  **Alias**: `/addExercise` – Either `/ae` or `/addExercise` can be used to add an exercise.
- **/n NAME**: Sets or updates the name of the exercise on update.  
  **Alias**: `/name` – Either `/n` or `/name` can be used to specify the exercise name.
- **/w WEIGHT**: Sets the weight for the exercise on update.  
  **Alias**: `/weight` – Either `/w` or `/weight` can be used to specify weight.
- **/r REPS**: Sets the repetitions on update.  
  **Alias**: `/reps` – Either `/r` or `/reps` can be used to specify repetitions.
- **/s SETS**: Sets the number of sets on update.  
  **Alias**: `/sets` – Either `/s` or `/sets` can be used to specify sets.
- **/c CALORIES**: Sets the calories of the exercise on update.  
  **Alias**: `/calories` – Either `/c` or `/calories` can be used to specify the calorie count.

Example: `prog edit /p 1 /d 1 /a /n Push-Up /w 30 /r 15 /s 3 /c 100`
Add exercise of push up with weight of 30, 15 reps, 3 sets, burning 100 calories in day 1 of programme 1.

```plaintext
Created new exercise:
Push-Up: 3 sets of 15 at 30 | Burnt 100 cals
```

---

### 8. Delete an Exercise of an Existing Day in an Existing Programme

Delete an exercise to an existing day in an existing programme

Command: `prog edit [/p PORG_INDEX] /d DAY_INDEX /x EXERCISE_INDEX`

Parameters:
- **/p PROG_INDEX** _(optional)_: Specifies the Programme index. Defaults to the current active Programme if omitted.  
  **Alias**: `/programme` – Either `/p` or `/programme` can be used to specify the programme index.
- **/d DAY_INDEX**: Index of day the user wants to delete an exercise from.
- **/x EXERCISE_INDEX** : **Remove an Existing Exercise** from the specified day.  
  **Alias**: `/removeExercise` – Either `/xe` or `/removeExercise` can be used to delete an exercise.

Example: `prog edit /p 1 /d 1 /x 1`
Delete exercise indexed 1 in day 1 of programme 1.

```plaintext
Deleted exercise:
Push-Up: 3 sets of 15 at 30 | Burnt 100 cals
```

---

### 9. Update an Exercise of an Existing Day in an Existing Programme

Update an exercise to a day in a programme

Command: `prog edit [/p PORG_INDEX] /d DAY_INDEX /x EXERCISE_INDEX [args]`

Parameters:
- **/p PROG_INDEX** _(optional)_: Specifies the Programme index. Defaults to the current active Programme if omitted.  
  **Alias**: `/programme` – Either `/p` or `/programme` can be used to specify the programme index.
- **/d DAY_INDEX**: Index of day the user wants to edit an exercise from.
- **/u EXERICSE_INDEX** : EXERCISE_INDEX is the index of exercise the user wants to edit.  
  **Alias**: `/updateExercise` – Either `/ue` or `/updateExercise` can be used to update an exercise.

This Edit Command must have at least 1 of the arguments below.
- **args**: These arguments set specific attributes for edit exercise actions:
  - **/w WEIGHT**: Sets the weight for the exercise on update.  
    **Alias**: `/weight` – Either `/w` or `/weight` can be used to specify weight.
  - **/r REPS**: Sets the repetitions on update.  
    **Alias**: `/reps` – Either `/r` or `/reps` can be used to specify repetitions.
  - **/s SETS**: Sets the number of sets on update.  
    **Alias**: `/sets` – Either `/s` or `/sets` can be used to specify sets.
  - **/n NAME**: Sets or updates the name of the exercise on update.  
    **Alias**: `/name` – Either `/n` or `/name` can be used to specify the exercise name.
  - **/c CALORIES**: Sets the calories of the exercise on update.  
    **Alias**: `/calories` – Either `/c` or `/calories` can be used to specify the calorie count.

Example: `prog edit /p 1 /d 1 /u 1 /w 30 /r 12`
Updates Exercise 1 on Day 1 of Programme 1, setting the weight to 30 and resp to 12.

```plaintext
Updated exercise: Push-Up: 3 sets of 15 at 30 | Burnt 100 cals
```

---

### 10. Set Program as Active

Sets given programme as the ‘active programme’, which other commands will default to if `programme_index` is not supplied.

Command: `prog start PROGRAMME_INDEX`

Example: `prog start 1`

```
==================================================
Started programme:
Starter

Day 1: ONE
1. Bench Press: 3 sets of 12 at 30 | Burnt 200 cals
2. Squat: 3 sets of 12 at 50 | Burnt 200 cals

Day 2: TWO
1. Bicep Curl: 3 sets of 12 at 10 | Burnt 100 cals
==================================================
```

---

### 11. Log a Workout

Log the successful completion of a workout for a given day.

Command: `prog log /p PROGRAMME_INDEX /d DAY_INDEX [/t DATE] `

Parameters:

- `/t DATE`: date (in `dd-MM-yyyy` format) for when the workout was completed. Defaults to current day if not provided.
- `/p PROGRAMME_INDEX`: index of the programme that was completed.
- `/d DAY_INDEX`: index of the Day that was completed.

Example: `prog log /p 1 /d 1 /t 12-12-2024`

```
==================================================
Congrats! You've successfully completed:
ONE
1. Bench Press: 3 sets of 12 at 30 | Burnt 200 cals
2. Squat: 3 sets of 12 at 50 | Burnt 200 cals
==================================================
```

---

### 12. Add a Meal

Adds a meal to the daily record of a specific date.

Command: `meal add /n MEAL_NAME /c CALORIES /t DATE`

Parameters:

- `/n MEAL_NAME`: Name of the meal.
- `/c CALORIES`: Number of calories in the meal.
- `/t DATE`: Date in the format `dd-MM-yyyy`.

**Example**: `meal add /n Chicken_Breast /c 250 /t 30-10-2024`

```
Chicken Breast | 250kcal has been added to 30-10-2024.
```

---

### 13. View Meals

Displays all meals recorded for a specific date.

Command: `meal view DATE`

Parameters:

- `DATE`: Date in the format `dd-MM-yyyy`.

**Example**: `meal view 30-10-2024`

```
Meals for 30-10-2024:

1. Chicken Breast | 250 kcal
2. Scrambled Eggs | 150 kcal
```

---

### 14. Delete a Meal

Deletes a meal from the daily record of a specific date.

Command: `meal delete /m MEAL_INDEX /t DATE`

Parameters:

- `/m MEAL_INDEX`: Index of the meal to delete.
- `/t DATE`: Date in the format `dd-MM-yyyy`.

**Example**: `meal delete /m 1 /t 30-10-2024`

```
Chicken Breast | 250kcal has been deleted from 30-10-2024
```

---

### 15. Add a Water Log

Adds a water log to the daily record of a specific date.

Command: `water add /v WATER_VOLUME /t DATE`

Parameters:

- `/v WATER_VOLUME `: Volume of Water. (floating number)
- `/t DATE`: Date in the format `dd-MM-yyyy`.

**Example**: `water add /v 200.2 /t 30-10-2024`

```
200.2 liters of water has been added
```

---

### 16. View Water Logs

Displays all water logs recorded for a specific date.

Command: `water view DATE`

Parameters:

- `DATE`: Date in the format `dd-MM-yyyy`.

**Example**: `water view 30-10-2024`

```
Water intake for 30-10-2024:

1: 100.0
2. 200.2
```

---

### 17. Delete a Water Log

Deletes a water log from the daily record of a specific date.

Command: `water delete /w WATER_INDEX /t DATE`

Parameters:

- `/w WATER_INDEX`: Index of the water to delete.
- `/t DATE`: Date in the format `dd-MM-yyyy`.

**Example**: `water delete /w 1 /t 30-10-2024`

```
100.0 liters of water has been deleted
```

---

### 18. View All History

Displays a comprehensive record of workouts, meals, and water intake for each logged day.

Command: `history view`

**Example**: `history view`

```
Completed On: 30-10-2024

Day:
ONE
1. Bench Press: 3 sets of 12 at 30 | Burnt 220 cals
2. Squat: 3 sets of 12 at 50 | Burnt 300 cals

Total Calories burnt: 520 kcal

Meals:
1: pasta | 100kcal
Total Calories from Meals: 100 kcal

Water Intake:
1: 100.0
Total Water Intake: 100.0 liters

Caloric Balance: -420 kcal

```
---

### View Weekly Summary

Displays a summary of workouts, meals, and water intake for the past week

Command: `history wk`

**Example**: `history wk`

```

Your weekly workout summary: 
Empty Day
Completed On: 30-10-2024

ONE
1. Bench Press: 3 sets of 12 at 30 | Burnt 200 cals
2. Squat: 3 sets of 12 at 50 | Burnt 200 cals
Completed On: 02-11-2024

```

---

### View PB for exercise

Displays Personal Best for specified exercise

Command: `history pb <exercise_name>`

**Example**: `history pb bench_press`

```

Personal best for bench press: Bench Press: 3 sets of 12 at 30

```
---

### View PBs for exercises 

Displays Personal Bests for all exercises

Command: `history pb`

**Example**: `history pb`

```

Personal bests for all exercises:
Bench Press: Bench Press: 3 sets of 12 at 30 
Squat: Squat: 3 sets of 12 at 50 
Bicep Curl: Bicep Curl: 3 sets of 12 at 10 

```
---

## Command Summary

| Command                                        | Description                                                    | Format                                                                                      | Example                                                                |
|------------------------------------------------|----------------------------------------------------------------| ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------- |
| **Add Programme**                              | Creates a new workout Programme                                | `prog create PROG_NAME /d DAY_NAME /e /n EXERCISE_NAME /s SET /r REP /w WEIGHT /c CALORIES` | `prog create Starter /d ONE /e /n Bench_Press /s 3 /r 12 /w 30 /c 100` |
| **View Programme**                             | Displays the detailed workout routine of a specific Programme  | `prog view [INDEX]`                                                                         | `prog view 1`                                                          |
| **List Programmes**                            | Lists all workout Programmes with their index and name         | `prog list`                                                                                 | `prog list`                                                            |
| **Delete Programme**                           | Deletes a Programme by its index                               | `prog delete INDEX`                                                                         | `prog delete 1`                                                        |
| **Add a Day to a Programme**                   | Add a new Day in an existing programme                         | `prog edit [/p PROG_INDEX] /ad DAY_NAME`                                           | `prog edit /p 1 /ad "Cardio Day"`                                     |
| **Delete a Day in a Programme**                | Delete a Day in an existing programme                          | `prog edit [/p PORG_INDEX] /xd DAY_INDEX`                                           | `prog edit /p 1 /xd 1"`                                     |
| **Add an Exercise in a Day in a Programme**    | Add an exercise to an existing day in an existing programme    | `prog edit [/p PORG_INDEX] /d DAY_INDEX /a /n EXERCISE_NAME /w WEIGHT /r REPS /s SETS /c CALORIES`                                           | `prog edit /p 1 /d 1 /a /n Push-Up /w 30 /r 15 /s 3 /c 100`                                     |
| **Delete an Exercise in a Day in a Programme** | Delete an exercise in an existing day in an existing programme | `prog edit [/p PORG_INDEX] /d DAY_INDEX /x EXERCISE_INDEX`                                           | `prog edit /p 1 /d 1 /x 1`                                     |
| **Update an Exercise in a Day in a Programme** | Update an exercise in an existing day in an existing programme | `prog edit [/p PORG_INDEX] /d DAY_INDEX /x EXERCISE_INDEX [args]`                                           | `prog edit /p 1 /d 1 /u 1 /w 30 /r 12`                                     |
| **Set Active**       | Sets a Programme as the active one                                                        | `prog start INDEX`                                                                          | `prog start 1`                                                         |
| **Log Workout**      | Logs a workout for a specific day                                                         | `prog log /p PROGRAMME_INDEX /d DAY_INDEX /t DATE`                                          | `log /p 1 /d 1 /t 12/10/2024`                                          |
| **Add Meal**         | Adds a meal to a daily record                                                             | `meal add /n MEAL_NAME /c CALORIES /t DATE`                                                 | `meal add /n Chicken_Breast /c 250 /t 30-10-2024`                      |
| **View Meals**       | Displays all meals for a specific date                                                    | `meal view /t DATE`                                                                         | `meal view 30-10-2024`                                                 |
| **Delete Meal**      | Deletes a meal from a daily record                                                        | `meal delete /m MEAL_INDEX /t DATE`                                                         | `meal delete /m 1 /t 30-10-2024`                                       |
| **Add Water**        | Adds a water to a daily record                                                            | `water add /n MEAL_NAME /c CALORIES /t DATE`                                                | `water add /v 200.2 /t 30-10-2024`                                     |
| **View Water**       | Displays all water for a specific date                                                    | `water view /t DATE`                                                                        | `water view 30-10-2024`                                                |
| **Delete Water**     | Deletes a water from a daily record                                                       | `water delete /m MEAL_INDEX /t DATE`                                                        | `water delete /w 1 /t 30-10-2024`                                      |
| **View History**     | Displays a comprehensive record of workouts, meals, and water intake for each logged day. | `history`                                                                                   | `history`                                                              |
| **View Weekly Summary**    | Displays a summary of workouts, meals, and water intake for the past week                 | `history wk`                                                                                | `history wk`                                                           |
| **View PB for exercise**   | Displays Personal Best for specified exercise                                             | `history pb <exercise_name>`                                                                | `history pb squat`                                                     |
| **View PBs for exercises** | Displays Personal Bests for all exercises                                                 | `history pb`                                                                                | `history pb`                                                           |
