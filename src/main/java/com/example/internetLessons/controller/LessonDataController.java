package com.example.internetLessons.controller;

import com.example.internetLessons.mapper.mainInterface.LessonDataMapper;
import com.example.internetLessons.pojo.mainPojo.LessonData;

import org.apache.http.entity.ContentType;
import org.apache.ibatis.session.SqlSession;
import com.example.internetLessons.utils.SqlSessionUtils;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

@CrossOrigin(origins = {"*", "null"})
@MapperScan("com.example.internetLessons")

@RestController
@RequestMapping(value = "/lessonData")
public class LessonDataController {


    public LessonDataMapper getLessonDataMapper(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        //获取mapper接口对象
        LessonDataMapper mapper = sqlSession.getMapper(LessonDataMapper.class);
        return mapper;
    }

    @PostMapping("/selectLessonInformation")
    @ResponseBody
    public String selectLessonInformation(@RequestBody LessonData lessonData) {
        LessonData lesson = getLessonDataMapper().selectLessonInformationByLessonNumber(lessonData.getLessonNumber());
        return lesson.getLessonInformation();
    }

    @PostMapping("/selectLessonName")
    @ResponseBody
    public String selectLessonName(@RequestBody LessonData lessonData) {
        LessonData lesson =getLessonDataMapper().selectLessonNameByLessonNumber(lessonData.getLessonNumber());
        return lesson.getLessonName();
    }
//根据课程号查找课程图片位置，并返回图片
//    @PostMapping("/selectCoverImage")
//    @ResponseBody
//    public MultipartFile selectCoverImagePosition(@RequestBody LessonData lessonData) throws IOException {
//        LessonData lesson = getLessonDataMapper().selectCoverImagePositionByLessonNumber(lessonData.getLessonNumber());
//        File pdfFile = new File(lesson.getCoverImagePosition());
//        if (pdfFile.exists()) { //如果文件存在
//
//            FileInputStream fileInputStream = new FileInputStream(pdfFile);
//            MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//
//            return multipartFile;
//        }
//        else {
//            File wrong = new File("D:" + File.separator + "服务器" + File.separator +"default"+File.separator+"noImage.jpg");
//            FileInputStream fileInputStream = new FileInputStream(wrong);
//            return new MockMultipartFile("不存在课程封面图片", fileInputStream);
//        }
//    }

    //根据课程号查找课程图片位置，并返回图片
    @PostMapping("/selectCoverImage")
    @ResponseBody
    public String selectCoverImagePosition(@RequestBody LessonData lessonData) throws IOException {
        LessonData lesson = getLessonDataMapper().selectCoverImagePositionByLessonNumber(lessonData.getLessonNumber());
        File pdfFile = new File(lesson.getCoverImagePosition());
        if (pdfFile.exists()) { //如果文件存在

            return lesson.getCoverImagePosition();
        }
        else {
            return  "D:" + File.separator + "服务器" + File.separator +"default"+File.separator+"noImage.jpg";
        }
    }

    @PostMapping("/selectLessonTeachers")
    @ResponseBody
    public String selectLessonTeachers(@RequestBody LessonData lessonData) {
        LessonData lesson = getLessonDataMapper().selectLessonTeachersByLessonNumber(lessonData.getLessonNumber());
        return lesson.getLessonTeachers();
    }

    @PostMapping("/selectAllLessonNumber")
    @ResponseBody
    public List<LessonData> selectAllLessonNumber() {
        return getLessonDataMapper().selectAllLessonNumber();
    }

    @PostMapping("/reviewLessonVideo")
    @ResponseBody
    public void reviewLessonVideo(@RequestBody LessonData lessonData) {
        getLessonDataMapper().reviewVideo(lessonData);
    }

    @PostMapping("/deleteLesson")
    @ResponseBody
    public void deleteLesson(@RequestBody LessonData lessonData){
        getLessonDataMapper().deleteLesson(lessonData.getLessonNumber());
    }

    @PostMapping("/addLesson")
    @ResponseBody
    public void addLesson(@RequestBody LessonData lessonData){
        getLessonDataMapper().addLesson(lessonData);
    }

}


