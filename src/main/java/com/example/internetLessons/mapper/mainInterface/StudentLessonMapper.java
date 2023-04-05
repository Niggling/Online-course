package com.example.internetLessons.mapper.mainInterface;

import com.example.internetLessons.pojo.mainPojo.LessonData;
import com.example.internetLessons.pojo.mainPojo.StudentLesson;

import java.util.List;

public interface StudentLessonMapper {
    void insertStudentLesson(String studentLessonNumber);//学生课程表插入数据
    List<StudentLesson> selectSumTime();//学习总时长
    List<StudentLesson> selectAll();//查询所有数据
}
