# User Guide

## Features 

### View help: `help`
Displays a message containing all the available commands ands their formats.

Format: `help`

### Add a Todo: `todo`
Adds a Todo to the task list

Format: `todo DESCRIPTION`

Examples:
* `todo CS2113T homework`
* `todo clean room`

### Add a Deadline: `deadline`
Adds a Deadline to the task list

Format: `deadline DESCRIPTION /by DATE_TIME<yyyy-mm-dd HH:mm>`

Example:
* `deadline iP final submission /by 2020-10-02 23:59`

### Add an Event: `event`
Adds an Event to the task list

Format: `event DESCRIPTION /at START_DATE_TIME<yyyy-mm-dd HH:mm> /to END_DATE_TIME<yyyy-mm-dd HH:mm>`

Example:
* `event CS2113T exam /at 2020-12-01 13:00 /to 2020-12-01 14:00`

### List all tasks: `list`
Shows all the tasks in the task list

Format: `list`

### List upcoming tasks: `upcoming`
Shows a list of all tasks due/happening within a certain time frame.

Format: `upcoming TIME_FRAME`

Example:
* `upcoming day`
* `upcoming week`
* `upcoming month`

### Filter tasks by name: `find`
Find tasks with a description that contains a given keyword.

Format: `find KEYWORD`

Examples:
* `find homework`
* `find exam`

### Delete a task: `delete`
Delete the task of a given index

Format: `delete INDEX`

Example:
* `delete 9` (Given that the task list has at least 9 tasks)

### Mark a task as done: `done`
Changes a task's status to done

Format: `done INDEX`

* `done 9` (Given that the task list has at least 9 tasks)

### Exit the program: `bye`
Exits the program

Format: `bye`

### Save data into hard drive
Duke saves all data in the task list automatically after any command that modifies the data. There is no need to save manually.

## Command Summary
Command | Format, Examples
-------|-----------------
help|`help`
list|`list`
upcoming|`upcoming day/week/month`
todo|`todo DESCRIPTION` e.g., `todo homework`
deadline|`deadline DESCRIPTION /by DATE_TIME` e.g., `deadline iP final submission /by 2020-10-02 23:59`
event|`event DESCRIPTION /at FROM_DATE_TIME /to TO_DATE_TIME` e.g., `event CS2113T exam /at 2020-12-01 13:00 /to 2020-12-01 14:00`
find|`find KEYWORD` e.g., `find September` `find assignment`
delete|`delete INDEX` e.g., `delete 9`
done|`done INDEX` e.g., `done 9`
bye|`bye`