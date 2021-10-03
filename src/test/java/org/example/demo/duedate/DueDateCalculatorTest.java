package org.example.demo.duedate;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class DueDateCalculatorTest {

    DueDateCalculator calculator;

    @Before
    public void setUp() {
        calculator = new DueDateCalculator();
    }

    @Test
    public void shouldReturnAccurateDueDateWhenTurnaroundIsOneHour() throws DueDateCalculationException {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 4, 10, 10);
        int turnaroundHours = 1;

        LocalDateTime dueDateTime = calculator.calculateDueDate(submitDateTime, turnaroundHours);

        LocalDateTime expected = LocalDateTime.of(2021, Month.OCTOBER, 4, 11, 10);
        assertEquals(expected, dueDateTime);
    }

    @Test
    public void shouldReturnAccurateDueDateWhenTurnaroundIsOneDay() throws DueDateCalculationException {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 4, 10, 10);
        int turnaroundHours = 8;

        LocalDateTime dueDateTime = calculator.calculateDueDate(submitDateTime, turnaroundHours);

        LocalDateTime expected = LocalDateTime.of(2021, Month.OCTOBER, 5, 10, 10);
        assertEquals(expected, dueDateTime);
    }

    @Test
    public void shouldReturnNextDayWhenTurnaroundIsAfterWorkingHours() throws DueDateCalculationException {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 4, 16, 10);
        int turnaroundHours = 1;

        LocalDateTime dueDateTime = calculator.calculateDueDate(submitDateTime, turnaroundHours);

        LocalDateTime expected = LocalDateTime.of(2021, Month.OCTOBER, 5, 9, 10);
        assertEquals(expected, dueDateTime);
    }

    @Test
    public void shouldReturnNextMondayWhenTurnaroundIsAfterWorkingHoursOnFriday() throws DueDateCalculationException {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 8, 16, 30);
        int turnaroundHours = 2;

        LocalDateTime dueDateTime = calculator.calculateDueDate(submitDateTime, turnaroundHours);

        LocalDateTime expected = LocalDateTime.of(2021, Month.OCTOBER, 11, 10, 30);
        assertEquals(expected, dueDateTime);
    }

    @Test
    public void shouldReturnDueDateInNextWeekWhenTurnaroundIsAWeek() throws DueDateCalculationException {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 4, 10, 10);
        int turnaroundHours = 40;

        LocalDateTime dueDateTime = calculator.calculateDueDate(submitDateTime, turnaroundHours);

        LocalDateTime expected = LocalDateTime.of(2021, Month.OCTOBER, 11, 10, 10);
        assertEquals(expected, dueDateTime);
    }

    @Test
    public void shouldReturnDueDateInNextMonthWhenTurnaroundExceedsActualMonth() throws DueDateCalculationException {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.SEPTEMBER, 30, 10, 10);
        int turnaroundHours = 8;

        LocalDateTime dueDateTime = calculator.calculateDueDate(submitDateTime, turnaroundHours);

        LocalDateTime expected = LocalDateTime.of(2021, Month.OCTOBER, 1, 10, 10);
        assertEquals(expected, dueDateTime);
    }

    @Test
    public void shouldReturnDueDateInNextYearWhenTurnaroundExceedsActualYear() throws DueDateCalculationException {
        LocalDateTime submitDateTime = LocalDateTime.of(2020, Month.DECEMBER, 31, 10, 10);
        int turnaroundHours = 8;

        LocalDateTime dueDateTime = calculator.calculateDueDate(submitDateTime, turnaroundHours);

        LocalDateTime expected = LocalDateTime.of(2021, Month.JANUARY, 1, 10, 10);
        assertEquals(expected, dueDateTime);
    }

    @Test
    public void shouldReturnNextWeekWhenDueDateIsWeekend() throws DueDateCalculationException {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 8, 10, 10);
        int turnaroundHours = 8;

        LocalDateTime dueDateTime = calculator.calculateDueDate(submitDateTime, turnaroundHours);

        LocalDateTime expected = LocalDateTime.of(2021, Month.OCTOBER, 11, 10, 10);
        assertEquals(expected, dueDateTime);
    }

    @Test
    public void shouldThrowExceptionWhenSubmitDateTimeIsOnWeekend() {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 3, 10, 10);
        int turnaround = 2;

        assertThrows(DueDateCalculationException.class, () -> calculator.calculateDueDate(submitDateTime, turnaround));
    }

    @Test
    public void shouldThrowExceptionWhenSubmitDateTimeIsBeforeWorkingHours() {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 4, 6, 10);
        int turnaround = 1;

        assertThrows(DueDateCalculationException.class, () -> calculator.calculateDueDate(submitDateTime, turnaround));
    }

    @Test
    public void shouldThrowExceptionWhenTurnaroundTimeIsNegative() {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 4, 10, 10);
        int turnaround = -1;

        assertThrows(DueDateCalculationException.class, () -> calculator.calculateDueDate(submitDateTime, turnaround));
    }

    @Test
    public void shouldThrowExceptionWhenTurnaroundTimeIsZero() {
        LocalDateTime submitDateTime = LocalDateTime.of(2021, Month.OCTOBER, 4, 10, 10);
        int turnaround = 0;

        assertThrows(DueDateCalculationException.class, () -> calculator.calculateDueDate(submitDateTime, turnaround));
    }
}