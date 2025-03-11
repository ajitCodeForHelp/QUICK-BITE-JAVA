package com.quickBite.scheduler;

import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class ScheduledTask {


    @Value("${scheduler}")
    private String scheduler;

    // https://www.freeformatter.com/cron-expression-generator-quartz.html
    // second, minute, hour, day, month, weekday
    // @Scheduled(cron = "0 50 23 * * *")
    // @Scheduled(cron = "0 20 01 * * *")
    // @Scheduled(cron = "0 0 8-20 ? * * *")
    // @Scheduled(cron = "0 43 21 * * *")

    private static boolean Every1MinuteSchedulerState = false;


    @Scheduled(cron = "0 */1 * * * *")
    public void every1MinuteScheduler() {
        if (scheduler.equalsIgnoreCase("OFF")) {
            return;
        }
        log.info("every1MinuteScheduler() at {}", DateUtils.getFormattedDateTime(new Date()));
        if (Every1MinuteSchedulerState) {
            return;
        }
        Every1MinuteSchedulerState = true;
        try {
//            SpringBeanContext.getBean(PosTrade.class).moveToHistory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Every1MinuteSchedulerState = false;
    }

}