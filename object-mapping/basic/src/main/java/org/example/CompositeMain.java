package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import model.composite.Blog;
import model.composite.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@Slf4j
public class CompositeMain {
    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(CompositeMain.class);

        ObjectMapper objectMapper = new ObjectMapper();

        Blog blog = new Blog("USER_NAME", "TITLE", "CONTENT");
        String blogJsonFromObj = objectMapper.writeValueAsString(blog);
        logger.debug(blogJsonFromObj);
        Comment comment = new Comment(blog.getBlogId(), "COMMENT_CONTENT");
        blog.addComment(comment);
        logger.debug(blog.toString());

        // convert Object to Json
        String blogWithCommentFromObj = objectMapper.writeValueAsString(blog);
        logger.debug(blogWithCommentFromObj);

        // File
        File blogFile = new File("src/main/java/model/composite/json/blog.json");
        File commentFile = new File("src/main/java/model/composite/json/comment.json");

        // convert json to Obj
        Blog blogFromJson = objectMapper.readValue(blogFile, Blog.class);
        logger.debug(blogFromJson.toString());

        // Comment 추가하기
        Comment commentFromJson = objectMapper.readValue(commentFile, Comment.class);
        logger.debug("Comment ID: {}", commentFromJson.getCommentId());
        blog.addComment(commentFromJson);
        logger.debug(blog.toString());
    }
}
