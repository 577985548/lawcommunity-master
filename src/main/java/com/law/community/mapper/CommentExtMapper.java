package com.law.community.mapper;

import com.law.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}