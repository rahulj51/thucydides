package net.thucydides.core.pages;

import org.joda.time.DateTime;
import org.junit.Test;

import static net.thucydides.core.matchers.dates.DateMatchers.isAfter;
import static net.thucydides.core.matchers.dates.DateMatchers.isBefore;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;

public class WhenManagingTheInternalClock {

    @Test
    public void should_pause_for_requested_delay() {
        InternalSystemClock clock = new InternalSystemClock();

        long startTime = System.currentTimeMillis();
        clock.pauseFor(100);
        long pauseLength = System.currentTimeMillis() - startTime;
        assertThat(pauseLength, greaterThanOrEqualTo(100L));
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_runtime_exception_if_something_goes_wrong() {
        InternalSystemClock clock = new InternalSystemClock() {
            @Override
            protected void sleepFor(long timeInMilliseconds) throws InterruptedException {
                throw new InterruptedException();
            }
        };

        clock.pauseFor(50);
    }

    @Test
    public void the_system_date_provider_uses_the_system_clock_to_find_the_current_date() {
        InternalSystemClock clock = new InternalSystemClock();

        DateTime before = new DateTime();
        DateTime systemDate = clock.getCurrentTime();
        DateTime after = new DateTime();

        assertThat(before, not(isAfter(systemDate)));
        assertThat(after, not(isBefore(systemDate)));
    }


}
