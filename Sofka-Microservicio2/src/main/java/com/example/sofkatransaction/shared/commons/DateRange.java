package com.example.sofkatransaction.shared.commons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DateRange {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;


    public DateRange(String value) {
        String[] parts = value.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("DateRange must be in format start,end");
        }

        this.startDateTime = parseToDateTime(parts[0].trim(), true);
        this.endDateTime = parseToDateTime(parts[1].trim(), false);

        if (this.endDateTime.isBefore(this.startDateTime)) {
            throw new IllegalArgumentException("End date must not be before start date");
        }
    }

    private LocalDateTime parseToDateTime(String input, boolean isStart) {
        try {
            // Caso 1: intenta parsear como LocalDateTime (ej: 2025-08-01T15:30:00)
            return LocalDateTime.parse(input);
        } catch (DateTimeParseException e1) {
            try {
                // Caso 2: intenta parsear como LocalDate (ej: 2025-08-01)
                LocalDate date = LocalDate.parse(input);
                return isStart ? date.atStartOfDay()
                        : date.atTime(23, 59, 59, 999_999_999);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Invalid date format: " + input);
            }
        }
    }

    public LocalDateTime getStartDate() {
        return startDateTime;
    }

    public LocalDateTime getEndDate() {
        return endDateTime;
    }
}
