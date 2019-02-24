package com.learn.task;

import com.learn.service.DataFromAppService;
import com.learn.service.DataFromPathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ClearTask {

    @Autowired
    private DataFromPathService dataFromPathService;
    @Autowired
    private DataFromAppService dataFromAppService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 删除一周前的数据
     */
    @Scheduled(cron = "1 0 0 * * ?")
    protected void execute() {
        try {
            Long now = System.currentTimeMillis()/1000;
            dataFromPathService.delete(now-7*24*60*60);
            dataFromAppService.delete(now-7*24*60*60);
            logger.info("clearTask execute success!");
        } catch (Exception e) {
            logger.error("clearTask error!", e);
        }
    }
}
