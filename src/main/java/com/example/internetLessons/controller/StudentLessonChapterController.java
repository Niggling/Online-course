package com.example.internetLessons.controller;

import com.example.internetLessons.mapper.mainInterface.StudentLessonMapper;
import com.example.internetLessons.pojo.mainPojo.StudentLesson;
import com.example.internetLessons.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = {"*", "null"})


@RestController
public class StudentLessonChapterController {

    /*public class BigFileWriteDemo {
        // 1MB
        private static final int FILE_SIZE = 1 * 1024 * 1024;
        // 文件结束标识
        private static final int EOF = -1;

        public  void main(String[] args) throws IOException {

            String path = BigFileWriteDemo.class.getResource("/").getPath();
            System.out.println(path);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path + "big.data"));
            int available = bis.available();
            System.out.println(available);

            System.out.println(String.format("%.2fMB", available * 1.0 / FILE_SIZE));

            // 拆分成每个为50Mb大小的文件
            int saveSize = 50 * FILE_SIZE;
            byte[] bytes = new byte[saveSize];

            int length = EOF;
            // 子文件下标
            int filenameExt = 1000;
            while ( (length = bis.read(bytes)) > EOF ) {
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + "sub-big-" + (filenameExt++) + ".data"));) {
                    bos.write(bytes, 0, length);
                }
            }
            bis.close();
        }
    }

    public class BigFileReadDemo {

        // 1MB
        private static final int FILE_SIZE = 1 * 1024 * 1024;
        // 文件结束标识
        private static final int EOF = -1;

        public void main(String[] args) throws IOException {

            String path = BigFileReadDemo.class.getResource("/").getPath();
            // 这里zzz为通用占位符、匹配拆分文件时下标数字
            String filename = "sub-big-zzz.data".replace("zzz", "\\d+");

            File file = new File(path);
            if (!file.isDirectory()) {
                return;
            }

            String name = file.getName();
            System.out.println(name);
            String[] list = file.list();
            Stream<String> stream = Arrays.stream(list);

            List<String> subFileNames = stream.filter(s->s.matches(filename))
                    .sorted()
                    .collect(Collectors.toList());

            if (subFileNames == null || subFileNames.size() < 1) {
                return ;
            }

            // 组装文件
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(path + "composite-big.data"));
            for (String subFilename : subFileNames) {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path + subFilename));) {
                    int available = bis.available();
                    System.out.println(available);
                    // 每次读取1Mb大小的文件
                    byte[] bytes = new byte[FILE_SIZE];
                    int length = EOF;
                    while ((length = bis.read(bytes)) > EOF) {
                        bos.write(bytes, 0, length);
                    }
                    bos.flush();
                }
            }
            bos.close();
        }
    }
//断点传续功能
@RestController
@RequestMapping("/breakpoint")
    public class BreakPointController {
        //断点续传
        public void downLoadByBreakpoint(File file, long start, long end, HttpServletResponse response){
            OutputStream stream = null;
            RandomAccessFile fif = null;
            try {
                if (end <= 0) {
                    end = file.length() - 1;
                }
                stream = response.getOutputStream();
                response.reset();
                response.setStatus(206);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
                response.setHeader("Content-Length", String.valueOf(end - start + 1));
                response.setHeader("file-size", String.valueOf(file.length()));
                response.setHeader("Accept-Ranges", "bytes");
                response.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, file.length()));
                fif = new RandomAccessFile(file, "r");
                fif.seek(start);
                long index = start;
                int d;
                byte[] buf = new byte[10240];
                while (index <= end && (d = fif.read(buf)) != -1) {
                    if (index + d > end) {
                        d = (int)(end - index + 1);
                    }
                    index += d;
                    stream.write(buf, 0, d);
                }
                stream.flush();
            } catch (Exception e) {
                try {
                    if (stream != null)
                        stream.close();
                    if (fif != null)
                        fif.close();
                } catch (Exception e11) {
                }
            }
        }

        //全量下载
        public void downLoadAll(File file, HttpServletResponse response){
            OutputStream stream = null;
            BufferedInputStream fif = null;
            try {
                stream = response.getOutputStream();
                response.reset();
                response.setContentType("application/octet-stream");
                response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
                response.setHeader("Content-Length", String.valueOf(file.length()));
                fif = new BufferedInputStream(new FileInputStream(file));
                int d;
                byte[] buf = new byte[10240];
                while ((d = fif.read(buf)) != -1) {
                    stream.write(buf, 0, d);
                }
                stream.flush();
            } catch (Exception e) {
                try {
                    if (stream != null)
                        stream.close();
                    if (fif != null)
                        fif.close();
                } catch (Exception e11) {
                }
            }
        }
    }
*/
}
