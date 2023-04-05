package com.example.internetLessons.controller;

import com.example.internetLessons.mapper.mainInterface.LessonDataMapper;
import com.example.internetLessons.mapper.mainInterface.LessonDocumentsMapper;
import com.example.internetLessons.pojo.help.Question;
import com.example.internetLessons.pojo.mainPojo.LessonData;
import com.example.internetLessons.pojo.mainPojo.LessonDocuments;
import com.example.internetLessons.utils.SqlSessionUtils;
import org.apache.http.entity.ContentType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = {"*", "null"})



@RestController
@RequestMapping(value = "/lessonDocuments")
public class LessonDocumentsController {

    @PostMapping("/addLessonDocuments")
    @ResponseBody
    public void addLesson(@RequestBody LessonDocuments lessonDocuments) {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        //获取mapper接口对象
        LessonDocumentsMapper mapper = sqlSession.getMapper(LessonDocumentsMapper.class);
        mapper.addLessonDocuments(lessonDocuments);
    }

    //根据课程章节号号查找课程视频位置，并返回视频
    @PostMapping("/watchVideo")
    @ResponseBody
    public String watchVideo(@RequestBody LessonDocuments lessonDocuments) {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        //获取mapper接口对象
        LessonDocumentsMapper mapper = sqlSession.getMapper(LessonDocumentsMapper.class);


        File pdfFile = new File(mapper.selectLessonChapterNumber(lessonDocuments.getLessonChapterNumber()));
        if (pdfFile.exists()) { //如果文件存在
           return mapper.selectLessonChapterNumber(lessonDocuments.getLessonChapterNumber());
        }
        else {
           return "D:" + File.separator + "服务器" + File.separator +"default"+File.separator+"noImage.jpg";
        }

    }




    private int strStr(String haystack, String needle) {//匹配字符串，前缀表算法，返回匹配项最后一位下标
        if (needle.length() == 0) return 0;
        int[] next = new int[needle.length()];
        getNext(next, needle);

        int j = 0;
        for (int i = 0; i < haystack.length(); i++) {
            while (j > 0 && needle.charAt(j) != haystack.charAt(i))
                j = next[j - 1];
            if (needle.charAt(j) == haystack.charAt(i))
                j++;
            if (j == needle.length())
                return i;
        }
        return -1;

    }

    private void getNext(int[] next, String s) {
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(j) != s.charAt(i))
                j = next[j - 1];
            if (s.charAt(j) == s.charAt(i))
                j++;
            next[i] = j;
        }
    }


    @PostMapping("/readQuession")
    @ResponseBody
    public List<Question> readQuestion(@RequestBody LessonDocuments lessonDocuments) {//读取题目文件
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        //获取mapper接口对象
        LessonDocumentsMapper mapper = sqlSession.getMapper(LessonDocumentsMapper.class);
        LessonDocuments l = mapper.selectChapterPositionByChapterNumberName(lessonDocuments.getLessonChapterNumber());
        String fileName = l.getChapterPosition();
        fileName += "/题目.txt";
        List<Question> questions = new ArrayList<Question>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;//第line行的内容
            int line = 1;//第line行
            // 一次读入一行，直到读入null为文件结束
            tempString = reader.readLine();
            int questionsNumber = Integer.valueOf(tempString);
            for (int i = 0; i < questionsNumber; i++) {
                questions.add(new Question());
            }
            int QNumber = -1;//题号，第几+2题
            while ((tempString = reader.readLine()) != null) {//遍历整个文件
                    if (strStr(tempString, "题目描述#") != -1) {
                        String str = getString(tempString, "#");
                        QNumber++;
                        questions.get(QNumber).setStem(str);
                    } else if (strStr(tempString, "选项A#") != -1) {
                        String str = getString(tempString, "#");
                        questions.get(QNumber).setChoiceA(str);
                    } else if (strStr(tempString, "选项B#") != -1) {
                        String str = getString(tempString, "#");
                        questions.get(QNumber).setChoiceB(str);
                    } else if (strStr(tempString, "选项C#") != -1) {
                        String str = getString(tempString, "C#");
                        questions.get(QNumber).setChoiceC(str);
                    } else if (strStr(tempString, "选项D#") != -1) {
                        String str = getString(tempString, "D#");
                        questions.get(QNumber).setChoiceD(str);
                    } else if (strStr(tempString, "正确选项#") != -1) {
                        String str = getString(tempString, "#");
                        questions.get(QNumber).setAnswer(str);
                    }
                }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;

    }

    private String getString(String tempString,String s){
        String str1 = tempString.substring(0, tempString.indexOf(s));//截取#之后字符串
        String str2 = tempString.substring(str1.length()+1, tempString.length());
        return str2;
    }

}
