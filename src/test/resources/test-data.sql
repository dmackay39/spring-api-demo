INSERT INTO author(id, name) VALUES(10,'Test');
INSERT INTO author(id, name) VALUES(20,'Zero');
INSERT INTO lendable_material(book_genre, movie_genre, is_available, author_id, director_id, id, screenwriter_id, material_type, title)
VALUES('SCIFI', null, true, 10, null, 1, null, 'Book', 'Test book');
INSERT INTO author(id, name) VALUES(30,'Aaron Sorkin');
INSERT INTO lendable_material(book_genre, movie_genre, is_available, author_id, director_id, id, screenwriter_id, material_type, title)
VALUES(null,'DRAMA', true, null, null, 2, 30, 'Movie', 'A Few Good Men');