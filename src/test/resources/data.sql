insert into authors (author_id, `author_name`) values (1, 'Tolkien');
insert into authors (author_id, `author_name`) values (2, 'Machiavelli');
insert into authors (author_id, `author_name`) values (3, 'Tolstoy');

insert into genres (genre_id, `genre_name`) values (1, 'Drama');
insert into genres (genre_id, `genre_name`) values (2, 'Comics');
insert into genres (genre_id, `genre_name`) values (3, 'History');

insert into books (book_id, `book_name`, author_id, genre_id) values (1, 'Lord of the Rings', 1, 1);
insert into books (book_id, `book_name`, author_id, genre_id) values (2, 'Istorie Florentine', 2, 3);
insert into books (book_id, `book_name`, author_id, genre_id) values (3, 'Martian', 3, 2);

insert into comments (comment_id, book_id, `text`) values (1, 3, 'MartianComment');
insert into comments (comment_id, book_id, `text`) values (2, 3, 'Martian Comment 2');

