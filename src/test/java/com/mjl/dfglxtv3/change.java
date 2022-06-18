package com.mjl.dfglxtv3;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class change {

        public static void main(String[] args) throws IOException {
        // 遍历C:\Users\31742\Desktop\dfglxt-v3\src\main\resources\templates下的所有深度html文件，获取相对于templates的路径
        File file = new File("C:\\Users\\31742\\Desktop\\dfglxt-v3\\src\\main\\resources\\templates");
        Files.walkFileTree(file.toPath(), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".html")) {
                    String path = file.toString().replace("C:\\Users\\31742\\Desktop\\dfglxt-v3\\src\\main\\resources\\templates", "");
                    // 将左斜杠替换为右斜杠
                    path = path.replace("\\", "/");
                    // 将路径中的.html替换为.js
                    path = path.replace(".html", "");
                    // 提取出path中的单词，并将单词拼接成驼峰式
                    String[] words = path.split("/");
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for (String word : words) {
                        if (word.length() > 0) {
                            if (i == 1) {
                                sb.append(word.substring(0, 1).toLowerCase());
                                sb.append(word.substring(1));
                            } else {
                                sb.append(word.substring(0, 1).toUpperCase());
                                sb.append(word.substring(1));
                            }
                        }
                        ++i;
                    }

                    // 模板字符串,

                    String ga =
                            """
                              @GetMapping(value = "%s")
                                         public String %s(){
                                             return "%s";
                                         }
                            """.formatted(path, sb ,path);
                    System.out.println(ga);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });

        // 2. 新建一个文件，按照指定的格式写入文件
        // 3. 写入文件
        // 4. 关闭文件
        // 5. 删除文件

    }


}