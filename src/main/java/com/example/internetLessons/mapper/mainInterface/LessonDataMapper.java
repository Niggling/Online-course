package com.example.internetLessons.mapper.mainInterface;

import com.example.internetLessons.pojo.mainPojo.LessonData;

import java.lang.reflect.Array;
import java.util.List;

public interface LessonDataMapper {


    LessonData selectLessonInformationByLessonNumber(int LessonNumber);//根据Lessonnumber查询Lessondata
    LessonData selectLessonNameByLessonNumber(int LessonNumber);//根据Lessonnumber查询LessonName
    LessonData selectCoverImagePositionByLessonNumber(int LessonNumber);//根据Lessonnumber查询CoverImagePosition
    LessonData selectLessonTeachersByLessonNumber(int LessonNumber);//根据Lessonnumber查询lessonteachers
    List<LessonData> selectAllLessonNumber();//查询所有Lessonnumber
    void reviewVideo(LessonData lessonData);
    void deleteLesson(int LessonNumber);

    void addLesson(LessonData lessonData);


}
