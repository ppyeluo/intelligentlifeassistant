package com.sspu.intelligentlifeassistant.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sspu.intelligentlifeassistant.models.ScheduleItem;

import java.util.List;

@Dao
public interface ScheduleDao {
    /**
     * 插入新的日程项
     *
     * @param scheduleItem 要插入的日程项
     */
    @Insert
    void insert(ScheduleItem scheduleItem);

    /**
     * 更新现有的日程项
     *
     * @param scheduleItem 要更新的日程项
     */
    @Update
    void update(ScheduleItem scheduleItem);

    /**
     * 删除指定的日程项
     *
     * @param scheduleItem 要删除的日程项
     */
    @Delete
    void delete(ScheduleItem scheduleItem);

    /**
     * 获取所有的日程项,按照年、月、日、上午/下午、小时、分钟的顺序排序
     *
     * @return 所有日程项的列表
     */
    @Query("SELECT * FROM schedule_item ORDER BY year, month, day, isAM, hour, minute")
    List<ScheduleItem> getAll();

    /**
     * 根据年、月、日获取日程项,按照上午/下午、小时、分钟的顺序排序
     *
     * @param year  日程项的年份
     * @param month 日程项的月份
     * @param day   日程项的日期
     * @return 指定日期的日程项列表
     */
    @Query("SELECT * FROM schedule_item WHERE year = :year AND month = :month AND day = :day ORDER BY isAM, hour, minute")
    List<ScheduleItem> getByDate(int year, int month, int day);

    /**
     * 根据日程类型获取日程项,按照年、月、日、上午/下午、小时、分钟的顺序排序
     *
     * @param type 要查询的日程类型
     * @return 指定类型的日程项列表
     */
    @Query("SELECT * FROM schedule_item WHERE type = :type ORDER BY year, month, day, isAM, hour, minute")
    List<ScheduleItem> getByType(int type);

    /**
     * 根据日程优先级获取日程项,按照年、月、日、上午/下午、小时、分钟的顺序排序
     *
     * @param priority 要查询的日程优先级
     * @return 指定优先级的日程项列表
     */
    @Query("SELECT * FROM schedule_item WHERE priority = :priority ORDER BY year, month, day, isAM, hour, minute")
    List<ScheduleItem> getByPriority(int priority);

    /**
     * 根据时间(小时、分钟、上午/下午)获取日程项,按照年、月、日的顺序排序
     *
     * @param hour   要查询的小时
     * @param minute 要查询的分钟
     * @param isAM   是否为上午
     * @return 指定时间的日程项列表
     */
    @Query("SELECT * FROM schedule_item WHERE hour = :hour AND minute = :minute AND isAM = :isAM ORDER BY year, month, day")
    List<ScheduleItem> getByTime(int hour, int minute, boolean isAM);
}

