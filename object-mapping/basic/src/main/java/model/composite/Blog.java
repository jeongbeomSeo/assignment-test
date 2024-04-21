package model.composite;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class Blog {
    private static Long BLOG_ID = 1L;
    Long blogId;
    String user;
    String title;
    String content;
    Date createAt;
    List<Comment> commentList;

    public Blog() {
        blogId = BLOG_ID++;
        this.createAt = new Date();
    }

    public Blog(String user, String title, String content) {
        this();
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment) {
        if (commentList == null) {
            this.commentList = new ArrayList<>();
        }
        commentList.add(comment);
    }
}
