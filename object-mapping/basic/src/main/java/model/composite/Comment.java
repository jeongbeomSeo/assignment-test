package model.composite;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class Comment {
    private static Long COMMENT_ID = 1L;
    private Long commentId;
    private Long blogId;
    private String content;
    private Date createAt;

    public Comment() {
        this.commentId = COMMENT_ID++;
        this.createAt = new Date();
    }

    public Comment(Long blogId, String content) {
        this();
        this.blogId = blogId;
        this.content = content;
    }
}
