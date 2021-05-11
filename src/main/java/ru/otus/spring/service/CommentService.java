package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

public interface CommentService {

    void addComment(Comment comment);

    void deleteComment(Comment comment);

    void deleteById(long id);

    Comment findCommentByID(long id);

}
