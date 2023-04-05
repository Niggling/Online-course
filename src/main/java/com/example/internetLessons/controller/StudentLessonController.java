package com.example.internetLessons.controller;

import com.example.internetLessons.mapper.mainInterface.LessonDataMapper;
import com.example.internetLessons.mapper.mainInterface.StudentLessonMapper;
import com.example.internetLessons.pojo.mainPojo.LessonData;
import com.example.internetLessons.pojo.mainPojo.StudentLesson;
import com.example.internetLessons.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.*;

@CrossOrigin(origins = {"*", "null"})

@RestController
@RequestMapping(value = "/studentLesson")
public class StudentLessonController {

    public StudentLessonMapper getStudentLessonMapper(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        //获取mapper接口对象
        StudentLessonMapper mapper = sqlSession.getMapper(StudentLessonMapper.class);
        return mapper;
    }

//学生选课
    @PostMapping("/insertStudentLesson")
    @ResponseBody
    public void insertStudentLesson(@RequestParam(value = "account") int account,@RequestParam(value = "lessonNumber") int lessonNumber) {

        String s = String.valueOf(account);
        s += " ";
        s += lessonNumber;
        getStudentLessonMapper().insertStudentLesson(s);
    }
//所有课程学习时长
    @PostMapping("/selectTime")
    @ResponseBody
    public int selectTime() {
        List<StudentLesson> studentLesson = getStudentLessonMapper().selectSumTime();
        int sumTime = 0;
        for(StudentLesson s : studentLesson){
            String str = s.getTime();
            String str1 = str.substring(0, str.indexOf("h"));
            Integer a = Integer.valueOf(str1);
            sumTime += a;
        }
        return sumTime;
    }


    //统计所有选了指定一门课的学生人数
    @PostMapping("/accountAmountOfOneLesson")
    @ResponseBody
    public int accountAmountOfOneLesson(@RequestParam(value = "lessonNumber") int lessonNumber)
    {
        List<StudentLesson> studentLesson = getStudentLessonMapper().selectAll();
        int amount = 0;
        for(StudentLesson s : studentLesson){
            String str = s.getStudentLessonNumber();
            //截取" "之后字符串
            String str1 = str.substring(0, str.indexOf(" "));//str1即为学生账号
            String str2 = str.substring(str1.length()+1);//str2即为课程号

            if(str2.equals(lessonNumber+"")){amount++;};//累加
        }
        return amount;

    }
    //统计一门课的通过人数
    @PostMapping("/passedAmountOfOneLesson")
    @ResponseBody
    public int passedAmountOfOneLesson(@RequestParam(value = "lessonNumber") int lessonNumber)
    {
        List<StudentLesson> studentLesson = getStudentLessonMapper().selectAll();
        int amount = 0;
        for(StudentLesson s : studentLesson){
            String str = s.getStudentLessonNumber();
            //截取" "之后字符串
            String str1 = str.substring(0, str.indexOf(" "));//str1即为学生账号
            String str2 = str.substring(str1.length()+1);//str2即为课程号

            if(str2.equals(lessonNumber+"")&&s.isLessonPassed()){amount++;};//累加
        }
        return amount;

    }


    public List<Integer> fiveMax(Map<String,Integer> map){
        List<Integer> value = new ArrayList<>();
        for(String key:map.keySet()){//把所有value装进数组value
            value.add(map.get(key));
        }
        return value;//所有人数的数组
    }


    public  List<Integer> max5(List<Integer> lst)
    {
        if(lst.size()<=5) return lst;

        int a = lst.remove(0);  // 填空
        List<Integer> b = max5(lst); //递归调用至lst小于等于五后

        for(int i=0; i<b.size(); i++)
        {
            int t = b.get(i);
            if(a>t)
            {
                lst.set(i, a);  // 填空
                a = t;
            }
        }

        return b;
    }
//选课人数最多的五门课的查询-课程号
    @PostMapping("/selectPopular")
    @ResponseBody
    public int[] selectPopular() {
        List<StudentLesson> studentLesson = getStudentLessonMapper().selectAll();
//        int[][] array = new int[studentLesson.size()][2];
        Map<String,Integer> map = new HashMap<>();
        for(StudentLesson s : studentLesson){
            String str = s.getStudentLessonNumber();
            //截取" "之后字符串
            String str1 = str.substring(0, str.indexOf(" "));//选这门课学生的学号
            String str2 = str.substring(str1.length()+1);//str2即为课程号
            if(map.containsKey(str2)){//统计所有课程相应的人数
                map.put(str2,map.get(str2) + 1);//累加
            }else{
                map.put(str2,1);//没有就新建一对键值
            }
        }
            List<Integer> list = max5(fiveMax(map));
            int[] result = new int[5];
            int i = 0;
            for (Integer integer : list){//获取integer对应value（可能需要转类型）对应key
                int value = integer;
                for (String key:map.keySet()){
                    if(value == map.get(key)){
                        int temp = Integer.parseInt(key);
                        result[i] = temp;
                        map.remove(key);
                        break;
                    }
                }
                i++;
            }


        return result;
    }
}




