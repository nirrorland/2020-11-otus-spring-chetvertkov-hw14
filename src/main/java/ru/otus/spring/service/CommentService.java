package ru.otus.spring.service;


import ru.otus.spring.domainsql.CommentSql;

public interface CommentService {

    void addComment(CommentSql commentSql);

    void deleteComment(CommentSql commentSql);

    void deleteById(long id);

    CommentSql findCommentByID(long id);

}
