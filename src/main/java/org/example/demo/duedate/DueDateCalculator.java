package org.example.demo.duedate;

import java.time.LocalDateTime;

import static org.example.demo.duedate.DueDateCalculationConstants.*;

public class DueDateCalculator {
    public LocalDateTime calculateDueDate(LocalDateTime submitDateTime, int turnaroundHours)
            throws DueDateCalculationException {
        validateTurnaroundHours(turnaroundHours);
        validateSubmitDateTime(submitDateTime);

        LocalDateTime dueDateResult = getDateOfDueDate(submitDateTime, turnaroundHours);
        dueDateResult = getTimeOfDueDate(dueDateResult, turnaroundHours);

        return dueDateResult;
    }

    /**
     * Adds the day difference (excl. hour difference) to the input LocalDateTime,
     * calculated from turnaroundHours input.
     */
    private LocalDateTime getDateOfDueDate(LocalDateTime dueDateTime, int turnaroundHours) {
        int totalWorkDays = turnaroundHours / WORKING_HOURS_PER_DAY;
        int totalWeeks = totalWorkDays / LENGTH_OF_WORKING_WEEK_IN_DAYS;
        int remainingWorkDays = totalWorkDays % LENGTH_OF_WORKING_WEEK_IN_DAYS;
        int totalDaysToAdd = totalWeeks * LENGTH_OF_WEEK_IN_DAYS + remainingWorkDays;

        dueDateTime = dueDateTime.plusDays(totalDaysToAdd);
        dueDateTime = dueDateTime.plusDays(isDayOnWeekend(dueDateTime) ?
                LENGTH_OF_WEEK_IN_DAYS - LENGTH_OF_WORKING_WEEK_IN_DAYS : 0);
        return dueDateTime;
    }

    /**
     * Adds the hour difference (excl. day difference) to the input LocalDateTime,
     * calculated from turnaroundHours input.
     */
    private LocalDateTime getTimeOfDueDate(LocalDateTime dueDateTime, int turnaroundHours) {
        int remainingHours = turnaroundHours % WORKING_HOURS_PER_DAY;
        LocalDateTime dueDateResult = dueDateTime.plusHours(remainingHours);
        if (dueDateResult.getHour() >= WORKING_HOURS_END) {
            LocalDateTime nextDay = dueDateResult.plusDays(1);
            int daysToNextWorkingDay = isDayOnWeekend(nextDay) ?
                    LENGTH_OF_WEEK_IN_DAYS - LENGTH_OF_WORKING_WEEK_IN_DAYS + 1 : 1;
            dueDateResult = dueDateResult.minusHours(WORKING_HOURS_PER_DAY).plusDays(daysToNextWorkingDay);
        }
        return dueDateResult;
    }

    private void validateSubmitDateTime(LocalDateTime submitDateTime) throws DueDateCalculationException {
        if (isDayOnWeekend(submitDateTime)) {
            throw new DueDateCalculationException("Submission date must be a weekday");
        }
        if (submitDateTime.getHour() < WORKING_HOURS_START
                || submitDateTime.getHour() >= WORKING_HOURS_END) {
            throw new DueDateCalculationException("Submission time must be during working hours");
        }
    }

    private void validateTurnaroundHours(int turnaroundHours) throws DueDateCalculationException {
        if (turnaroundHours <= 0) {
            throw new DueDateCalculationException("Turnaround time must be more than 0");
        }
    }

    private boolean isDayOnWeekend(LocalDateTime submitDateTime) {
        return DAYS_OF_WEEKEND.contains(submitDateTime.getDayOfWeek());
    }
}
