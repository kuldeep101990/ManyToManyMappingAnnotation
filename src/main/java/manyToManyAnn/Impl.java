package manyToManyAnn;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Impl {

  public static void main(String[] args) {
  // Load the configuration and build the SessionFactory
  Configuration configuration = HibernateConfig.getConfig();
  configuration.addAnnotatedClass(Student.class);
  configuration.addAnnotatedClass(Course.class);
  
  ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
  		.applySettings(configuration.getProperties())
  		.build();
  SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
  System.out.println("SessionFactory created successfully.");
  // Create a session
  Session session = sessionFactory.openSession();
  Transaction transaction = null;

  
  try {
      transaction = session.beginTransaction();

   // Create Course objects
      Course course1 = new Course();
      course1.setName("Mathematics");

      Course course2 = new Course();
      course2.setName("Science");

      // Create Student objects
      Student student1 = new Student();
      student1.setName("John Doe");

      Student student2 = new Student();
      student2.setName("Jane Smith");

      // Assign courses to students
      Set<Course> coursesForStudent1 = new HashSet();
      coursesForStudent1.add(course1);
      coursesForStudent1.add(course2);
      student1.setCourses(coursesForStudent1);

      Set<Course> coursesForStudent2 = new HashSet();
      coursesForStudent2.add(course2);
      student2.setCourses(coursesForStudent2);

      // Assign students to courses
      Set<Student> studentsForCourse1 = new HashSet();
      studentsForCourse1.add(student1);
      course1.setStudents(studentsForCourse1);

      Set<Student> studentsForCourse2 = new HashSet();
      studentsForCourse2.add(student1);
      studentsForCourse2.add(student2);
      course2.setStudents(studentsForCourse2);

      // Save the students (courses will be saved automatically due to cascade)
      session.persist(student1);
      session.persist(student2);


      transaction.commit();
  } catch (Exception e) {
      if (transaction != null) transaction.rollback();
      e.printStackTrace();
  } finally {
      session.close();
      sessionFactory.close();
  }
}
}