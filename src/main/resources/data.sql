INSERT INTO author(id, name) VALUES(1,'Frank Herbert');
INSERT INTO lendable_material(book_genre, movie_genre, is_available, author_id, director_id, id, screenwriter_id, material_type, title)
VALUES('SCIFI', null, true, 1, null, 1, null, 'Book', 'Dune');
INSERT INTO author(id, name) VALUES(2,'Aaron Sorkin');
INSERT INTO lendable_material(book_genre, movie_genre, is_available, author_id, director_id, id, screenwriter_id, material_type, title)
VALUES(null,'DRAMA', true, null, null, 2, 2, 'Movie', 'A Few Good Men');