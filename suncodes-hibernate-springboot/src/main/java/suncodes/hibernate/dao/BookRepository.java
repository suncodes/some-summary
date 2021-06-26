package suncodes.hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suncodes.hibernate.pojo.po.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}