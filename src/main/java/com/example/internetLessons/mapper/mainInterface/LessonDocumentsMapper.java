package com.example.internetLessons.mapper.mainInterface;

import com.example.internetLessons.pojo.mainPojo.LessonData;
import com.example.internetLessons.pojo.mainPojo.LessonDocuments;

import java.util.List;

public interface LessonDocumentsMapper {
    String selectLessonChapterNumber(int lessonChapterNumber);//查询章节文件位置

    LessonDocuments selectChapterPositionByChapterNumberName(int lessonChapterNumber);

    void addLessonDocuments(LessonDocuments lessonDocuments);

}
