SELECT isbn as ISNB, books.name as Name, author as Author, book_categories.name as 'Category', year as Year
FROM books left join book_categories
ON books.book_category_id = book_categories.id
WHERE books.name like '%Harry Potter%'
ORDER BY ISNB DESC ;

SELECT * FROM book_borrow;

SELECT status FROM users where full_name = 'Test Librarian 50';

SELECT COUNT(*) FROM book_borrow WHERE is_returned = 0;

SELECT book_categories.name, COUNT(*)
FROM books JOIN book_categories
ON books.book_category_id = book_categories.id
JOIN book_borrow ON books.id = book_borrow.book_id
GROUP BY 1
ORDER BY 2 DESC;

SELECT name FROM book_categories;

SELECT books.name as Name, isbn, year, author, book_categories.name from books join book_categories on books.book_category_id = book_categories.id where books.name = 'Hello, World!';

SELECT book_categories.name, COUNT(*)
FROM books JOIN book_categories ON books.book_category_id = book_categories.id
    JOIN book_borrow ON books.id = book_borrow.book_id
    GROUP BY 1
    ORDER BY 2 DESC;