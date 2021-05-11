package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Comment;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentDao commentRepository;

    @Autowired
    public CommentServiceImpl(CommentDao commentDaookDao) {
        this.commentRepository = commentDaookDao;
    }

    @Override
    @Transactional
    public void addComment(Comment comment) {
        commentRepository.saveAndFlush(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Comment comment) {
        commentRepository.deleteById(comment.getId());
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
        commentRepository.flush();
    }

    @Override
    public Comment findCommentByID(long id) {
       return commentRepository.findById(id).orElse(null);
    }
}
