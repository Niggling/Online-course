package com.example.internetLessons.pojo.mainPojo;


import lombok.Data;

/**
 * @author 宁立
 * @version 1.0
 */
@Data
public class StudentLesson {
    private String StudentLessonNumber;

    private String grade;

    private String time;

    private boolean isLessonPassed;

}
