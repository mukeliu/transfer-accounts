package com.demo.transfer.scheduler;

/**
 * description: 定时调度器 <br>
 * date: 2020/2/10 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public interface Scheduler {

    /**
     * description: 执行定时任务，定时任务具体逻辑在这里实现 <br>
     *
     * @param
     * @return: void
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    void execute();

}
