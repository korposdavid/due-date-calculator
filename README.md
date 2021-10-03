## Project Description

This application is written in Java using JUnit for testing and Maven for dependency management. 

## Task

A solution that implements a due date calculator in an issue
tracking system. Your task is to implement the CalculateDueDate method:

|||
|---|---|
| Input  | Takes the submit date/time and turnaround time. |
| Output | Returns the date/time when the issue is resolved. |

## Rules

* Working hours are from 9AM to 5PM on every working day, Monday to Friday.
* Holidays should be ignored (e.g. A holiday on a Thursday is considered as a
working day. A working Saturday counts as a non-working day.).
* The turnaround time is defined in working hours (e.g. 2 days equal 16 hours).
If a problem was reported at 2:12PM on Tuesday and the turnaround time is
16 hours, then it is due by 2:12PM on Thursday.
* A problem can only be reported during working hours. (e.g. All submit date
values are set between 9AM to 5PM.)
* Do not use any third-party libraries for date/time calculations (e.g. Moment.js,
Carbon, Joda, etc.) or hidden functionalities of the built-in methods.

## Test

```bash
# command to run unit tests
$ mvn test
```