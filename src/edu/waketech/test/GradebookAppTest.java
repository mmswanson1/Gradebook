package edu.waketech.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.waketech.academic.Assignment;
import edu.waketech.academic.Course;
import edu.waketech.common.Student;
import edu.waketech.common.StudentBody;
import edu.waketech.GradebookApp;

class GradebookAppTest {


	// add for grading
	@BeforeEach
	void testClearStudentBody() {
		StudentBody.getInstance().clear();
	}

	@Test
	void testCreateAssignment() {
		Course course = new Course(new String("CSC"), 251);
		Student s1 = new Student(new String("last1"), new String("first1"), 2);
		Student s2 = new Student(new String("last2"), new String("first2"), 3);
		StudentBody sb = StudentBody.getInstance();
		sb.add(s1);
		sb.add(s2);
		
		course.addStudent(s1.getId());
		course.addStudent(s2.getId());
		
		GradebookApp.createAssignment(course, new String("lab1"), 50);
		List<Assignment> labList = course.getAssignment(s1.getId(), new String("lab1"));
		Assignment foundLab = labList.get(0);
		assertEquals(new String("lab1"), foundLab.getName());
		assertEquals(50, foundLab.getPossiblePoints());
		assertTrue(foundLab.getScore() >= 35);
	}
	
	@Test
	void testCreateAssignment1() {
		Course course = new Course(new String("CSC"), 251);
		Student s1 = new Student(new String("last1"), new String("first1"), 2);
		Student s2 = new Student(new String("last2"), new String("first2"), 3); 
		StudentBody sb = StudentBody.getInstance();
		sb.add(s1);
		sb.add(s2);
		
		course.addStudent(s1.getId());
		course.addStudent(s2.getId());
		
		GradebookApp.createAssignment(course, new String("lab1"), 50);
		List<Assignment> labList = course.getAssignment(s2.getId(), new String("lab1"));
		Assignment foundLab = labList.get(0);
		assertEquals(new String("lab1"), foundLab.getName());
		assertEquals(50, foundLab.getPossiblePoints());
		assertTrue(foundLab.getScore() >= 35);
	}
	
	
	@Test
	void testGetStudentsTakingEverything() {
		Course course1 = new Course(new String("CSC"), 251);
		Course course2 = new Course(new String("DBA"), 120);
		Course course3 = new Course(new String("CSC"), 151);
		List<Course> courses = new ArrayList<>();
		courses.add(course1);
		courses.add(course2);
		courses.add(course3);
		Student s1 = new Student(new String("last1"), new String("first1"), 2);
		Student s2 = new Student(new String("last2"), new String("first2"), 3);
		Student s3 = new Student(new String("last3"), new String("first3"), 4);
		Student s4 = new Student(new String("last4"), new String("first4"), 5);
		StudentBody sb = StudentBody.getInstance();
		sb.add(s1);
		sb.add(s2);
		sb.add(s3);
		sb.add(s4);
		
		course1.addStudent(s1.getId());
		course1.addStudent(s2.getId());
		course1.addStudent(s4.getId());
		course2.addStudent(s1.getId());
		course2.addStudent(s2.getId());
		course2.addStudent(s4.getId());
		course3.addStudent(s3.getId());
		course3.addStudent(s1.getId());
		course3.addStudent(s4.getId());
		
		List<Integer> expected = new ArrayList<>(Arrays.asList(2, 5));
		List<Integer> result = GradebookApp.getStudentsTakingEverything(courses);
		assertEquals(result, expected);
	}

	
	@Test
	void testCourseAverageForAssignment() {
		Course course1 = new Course(new String("CSC"), 251);
		Student s1 = new Student(new String("last1"), new String("first1"), 2);
		Student s2 = new Student(new String("last2"), new String("first2"), 3);
		Student s3 = new Student(new String("last3"), new String("first3"), 4);
		Assignment lab1 = new Assignment(new String("lab1"), 100, 90);
		StudentBody sb = StudentBody.getInstance();
		sb.add(s1);
		sb.add(s2);
		sb.add(s3);
		
		course1.addAssignment(s1.getId(), lab1);
		course1.addAssignment(s2.getId(), lab1);
		course1.addAssignment(s3.getId(), lab1);

		
		double expected = 90.0;
		double result = GradebookApp.courseAverageForAssignment(course1, "lab1");
		assertEquals(expected, result);
	}
	
	
	@Test
	void testCalculateStudentAverageInOneCourse() {
		Course course1 = new Course(new String("CSC"), 251);
		Student s1 = new Student(new String("last1"), new String("first1"), 1);
		Assignment lab1 = new Assignment(new String("lab1"), 100, 90);
		Assignment lab2 = new Assignment(new String("lab2"), 100, 75);
		Assignment lab3 = new Assignment(new String("lab3"), 100, 50);
		StudentBody sb = StudentBody.getInstance();
		sb.add(s1);
		
		course1.addAssignment(s1.getId(), lab1);
		course1.addAssignment(s1.getId(), lab2);
		course1.addAssignment(s1.getId(), lab3);
		
		double expected = 215/3;
		double result = GradebookApp.calculateStudentAverageInOneCourse(course1, 1);
	}

}
