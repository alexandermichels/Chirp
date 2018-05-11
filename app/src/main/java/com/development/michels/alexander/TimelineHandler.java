package com.development.michels.alexander;

public interface TimelineHandler
{
    void handleTimelineResponse(Chirp [] timeline);

    void handleTimelineError();
}
