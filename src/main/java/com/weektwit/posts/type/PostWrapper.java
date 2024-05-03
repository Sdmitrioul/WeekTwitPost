package com.weektwit.posts.type;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostWrapper {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String time;
}
