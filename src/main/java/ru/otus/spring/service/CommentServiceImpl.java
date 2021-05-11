package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.daosql.CommentSqlDao;
import ru.otus.spring.domainsql.CommentSql;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentSqlDao commentRepository;

    @Autowired
    public CommentServiceImpl(CommentSqlDao commentDaookSqlDao) {
        this.commentRepository = commentDaookSqlDao;
    }

    @Override
    @Transactional
    public void addComment(CommentSql commentSql) {
        commentRepository.saveAndFlush(commentSql);
    }

    @Override
    @Transactional
    public void deleteComment(CommentSql commentSql) {
        commentRepository.deleteById(commentSql.getId());
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
        commentRepository.flush();
    }

    @Override
    public CommentSql findCommentByID(long id) {
       return commentRepository.findById(id).orElse(null);
    }
}
