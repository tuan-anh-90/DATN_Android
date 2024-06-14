package com.example.calendar.Model;
import java.time.LocalDateTime;

public class EventCreateParams {
    private LocalDateTime start;
    private LocalDateTime end;
    private String text;
    private String eventLocation;
    private String eventNotes;

    public EventCreateParams(LocalDateTime start, LocalDateTime end, String text, String eventLocation, String eventNotes) {
        this.start = start;
        this.end = end;
        this.text = text;
        this.eventLocation = eventLocation;
        this.eventNotes = eventNotes;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventNotes() {
        return eventNotes;
    }

    public void setEventNotes(String eventNotes) {
        this.eventNotes = eventNotes;
    }
}
