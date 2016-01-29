package fr.treeptik.examples;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class CommentDAO {
	
	@Autowired private SessionFactory sessionFactory;
	
	@Transactional
	public List<Comment> findAll() {
		Session session = sessionFactory.getCurrentSession();
		List comments = session.createQuery("from Comment").list();
		return comments;
	}



}
