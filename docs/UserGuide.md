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
1. Starter -- Active
2. PPL
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

### 5. Editing a Programme

Edits a given Programme based on command and argument flags.

Command: `prog edit [/p PROG_INDEX] [/d DAY_INDEX] [command] [args]`

Parameters:

- **/p PROG_INDEX** _(optional)_: Specifies the Programme index. Defaults to the current active Programme if omitted.  
  **Alias**: `/programme` – Either `/p` or `/programme` can be used to specify the programme index.

- **/d DAY_INDEX**: Specifies the day index within the Programme.  
  **Alias**: `/day` – Either `/d` or `/day` can be used to specify the day index.

- **command**: Determines the action type on the exercise or day:

  - `/a` : **Add a New Exercise** to the specified day.  
    **Alias**: `/addExercise` – Either `/ae` or `/addExercise` can be used to add an exercise.
  - `/u EXERICSE_INDEX` : **Update an Existing Exercise** within the specified day.  
    **Alias**: `/updateExercise` – Either `/ue` or `/updateExercise` can be used to update an exercise.
  - `/x EXERICSE_INDEX` : **Remove an Existing Exercise** from the specified day.  
    **Alias**: `/removeExercise` – Either `/xe` or `/removeExercise` can be used to delete an exercise.
  - `/ad`: **Create a New Day** in the Programme.  
    **Alias**: `/addDay` – Either `/ad` or `/addDay` can be used to add a day.
  - `/xd`: **Remove an Existing Day** from the Programme.  
    **Alias**: `/removeDay` – Either `/xd` or `/removeDay` can be used to delete a day.

- **args**: These arguments set specific attributes for edit actions:
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

Example Commands:

**Create a New Exercise**:

```plaintext
prog edit /p 1 /d 1 /a "Push-Up /w 30 /r 15 /s 3 /c 100"
```

Adds a "Push-Up" exercise on Day 1 of Programme 1.

**Update an Existing Exercise**:

```plaintext
prog edit /p 1 /d 1 /u 1 /w 30 /r 12
```

Updates Exercise 1 on Day 1 of Programme 1, setting the weight to 30 and resp to 12.

**Delete an Exercise**:

```plaintext
prog edit /p 1 /d 1 /x 1
```

Deletes Exercise 1 on Day 1 of Programme 1.

**Create a New Day**:

```plaintext
prog edit /p 1 /ad "Cardio Day"
```

Creates a new empty Day named "Cardio Day" in Programme 1.

_Note_: Advanced users can directly create days with exercises using the syntax found in `Create Programme`

```plaintext
prog edit /p 1 /ad "Pull Day" /e /n "Push-Up /w 30 /r 15 /s 3 /c 100 /e ...
```

**Remove an Existing Day**:

```plaintext
prog edit /p 1 /xd 1
```

Removes Day 1 of Programme 1.

---

### 6. Set Program as Active

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

### 7. Log a Workout

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

### 8. Add a Meal

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

### 9. View Meals

Displays all meals recorded for a specific date.

Command: `meal view /t DATE`

Parameters:

- `/t DATE`: Date in the format `dd-MM-yyyy`.

**Example**: `meal view 30-10-2024`

```
Meals for 30-10-2024:

1. Chicken Breast | 250 kcal
2. Scrambled Eggs | 150 kcal
```

---

### 10. Delete a Meal

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

### 11. Add a Water Log

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

### 12. View Water Logs

Displays all water logs recorded for a specific date.

Command: `water view /t DATE`

Parameters:

- `/t DATE`: Date in the format `dd-MM-yyyy`.

**Example**: `water view 30-10-2024`

```
Water intake for 30-10-2024:

1: 100.0
2. 200.2
```

---

### 13. Delete a Water Log

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

### 14. View All History

Displays a comprehensive record of workouts, meals, and water intake for each logged day.

Command: `history`

**Example**: `history`

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

## Command Summary

| Command              | Description                                                                               | Format                                                                                      | Example                                                                |
| -------------------- | ----------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------- |
| **Add Programme**    | Creates a new workout Programme                                                           | `prog create PROG_NAME /d DAY_NAME /e /n EXERCISE_NAME /s SET /r REP /w WEIGHT /c CALORIES` | `prog create Starter /d ONE /e /n Bench_Press /s 3 /r 12 /w 30 /c 100` |
| **View Programme**   | Displays the detailed workout routine of a specific Programme                             | `prog view [INDEX]`                                                                         | `prog view 1`                                                          |
| **List Programmes**  | Lists all workout Programmes with their index and name                                    | `prog list`                                                                                 | `prog list`                                                            |
| **Delete Programme** | Deletes a Programme by its index                                                          | `prog delete INDEX`                                                                         | `prog delete 1`                                                        |
| **Edit Programme**   | Edits exercises or days within a Programme                                                | `prog edit /p PROG_INDEX /d DAY [command] [args]`                                           | `prog edit /p 1 /d 1 /u 1 /w 30`                                       |
| **Set Active**       | Sets a Programme as the active one                                                        | `prog start INDEX`                                                                          | `prog start 1`                                                         |
| **Log Workout**      | Logs a workout for a specific day                                                         | `prog log /p PROGRAMME_INDEX /d DAY_INDEX /t DATE`                                          | `log /p 1 /d 1 /t 12/10/2024`                                          |
| **Add Meal**         | Adds a meal to a daily record                                                             | `meal add /n MEAL_NAME /c CALORIES /t DATE`                                                 | `meal add /n Chicken_Breast /c 250 /t 30-10-2024`                      |
| **View Meals**       | Displays all meals for a specific date                                                    | `meal view /t DATE`                                                                         | `meal view 30-10-2024`                                                 |
| **Delete Meal**      | Deletes a meal from a daily record                                                        | `meal delete /m MEAL_INDEX /t DATE`                                                         | `meal delete /m 1 /t 30-10-2024`                                       |
| **Add Water**        | Adds a water to a daily record                                                            | `water add /n MEAL_NAME /c CALORIES /t DATE`                                                | `water add /v 200.2 /t 30-10-2024`                                     |
| **View Water**       | Displays all water for a specific date                                                    | `water view /t DATE`                                                                        | `water view 30-10-2024`                                                |
| **Delete Water**     | Deletes a water from a daily record                                                       | `water delete /m MEAL_INDEX /t DATE`                                                        | `water delete /w 1 /t 30-10-2024`                                      |
| **View History**     | Displays a comprehensive record of workouts, meals, and water intake for each logged day. | `history`                                                                                   | `history`                                                              |
