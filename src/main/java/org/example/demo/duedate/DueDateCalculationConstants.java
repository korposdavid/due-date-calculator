package org.example.demo.duedate;

import java.time.DayOfWeek;
import java.util.List;

class DueDateCalculationConstants {
    static final int WORKING_HOURS_START = 9;
    static final int WORKING_HOURS_END = 17;
    static final int WORKING_HOURS_PER_DAY = WORKING_HOURS_END - WORKING_HOURS_START;
    static final int LENGTH_OF_WORKING_WEEK_IN_DAYS = 5;
    static final int LENGTH_OF_WEEK_IN_DAYS = 7;
    static final List<DayOfWeek> DAYS_OF_WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
}
