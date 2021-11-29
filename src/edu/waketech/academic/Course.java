package edu.waketech.academic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The representation of a Course at Wake Tech.
 * 
 * <p>
 * The general concept of a course is that it has a roster of students. For each
 * of these students, there is a list of Assignmemts. This list of Assignments
 * may have multiple Assignments with identical names, as a student may attempt
 * an Assignment multiple times.
 * </p>
 * <p>
 * In addition to a roster of students with their assignments, a Course has a
 * String department name (such as "CSC" or "DBA") and an int course number
 * (such as 151 or 120).
 * </p>
 * <p>
 * The course roster is a Map&lt;Integer, List&lt;Assignment&gt;&gt;, where the
 * key is the student's id number.
 * </p>
 * 
 * @author parks
 *
 */
public class Course {

	private String department;
	private int courseNumber;

	// Map of <studentId, List of assignments>
	private Map<Integer, List<Assignment>> rosterWithAssignments;

	/**
	 * Create a Course with a given courseNumber and department
	 * 
	 * Note that a course number must be a positive number, and the department name
	 * must be non-null. Otherwise an IllegalArgumentException is thrown.
	 * 
	 * @param courseNumber
	 *            the course number, such as 151 or 251
	 * @param department
	 *            the department offering the course, such as CSC or DBA
	 */
	public Course(int courseNumber, String department) {
		if (courseNumber <= 0) {
			throw new IllegalArgumentException("course number must be greater than zero");
		}
		if (department == null) {
			throw new IllegalArgumentException("department must be non-null");
		}
		this.courseNumber = courseNumber;
		this.department = department.toUpperCase();
		rosterWithAssignments = new HashMap<>();
	}

	/**
	 * See Course(courseNumber, department). Only the parameters have been reversed
	 * 
	 * @param department
	 *            the department offering the course, such as CSC or DBA
	 * @param courseNumber
	 *            the course number, such as 151 or 251
	 */
	public Course(String department, int courseNumber) {
		this(courseNumber, department);
	}

	/**
	 * Add a student to this course roster. Students are identified in courses only
	 * by id.
	 * 
	 * @param studentId
	 *            to be added to this course
	 * 
	 * @return true if the roster of students was changed as part of this method
	 *         call, false otherwise.
	 */
	// TODO supply this method
	public boolean addStudent(int studentId) {
		if (!rosterWithAssignments.containsKey(studentId)) {
			List<Assignment> list = new ArrayList<>();
			rosterWithAssignments.put(studentId, list);
			return true;
		}
		
		return false;
	}

	/**
	 * Add a given assignment for a given student to this course
	 * 
	 * Note that the assignment must be non-null. Otherwise an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param studentId
	 *            identifier of the student related to this assignment
	 * @param assignment
	 *            containing the grade for this student
	 */
	// TODO supply this method
	public void addAssignment(int studentId, Assignment assignment) {
		if (assignment == null) {
			throw new IllegalArgumentException("assignment must be non-null");
		}
	
		if (!getAllStudentIds().contains(studentId)) {
			addStudent(studentId);
		}
		List<Assignment> assignmentList = rosterWithAssignments.get(studentId);
		assignmentList.add(assignment);
	}

	/**
	 * Scan this Course for all assignment related to a given Student
	 * 
	 * @param studentId
	 *            identifier of the student whose assignments are requested
	 * 
	 * @return all the assignments related to the given student, or an empty list.
	 */
	public List<Assignment> getAssignmentsForStudent(int studentId) {
		List<Assignment> retList = rosterWithAssignments.get(studentId);
		if (retList == null)
			retList = new ArrayList<>();
		return retList;
	}

	/**
	 * Find the particular Assignments for a particular Student. It is possible that
	 * a given Assignment has multiple attempts, and each of them will be returned.
	 * 
	 * 
	 * @param studentId
	 *            identifier of the Student
	 * 
	 * @param assignmentName
	 *            the name of the Assignment
	 * 
	 * @return the assignments with the given name and student
	 */
	// TODO supply this method
	public List<Assignment> getAssignment(int studentId, String assignmentName) {
		List<Assignment> thisAssignment = new ArrayList<>();
		List<Assignment> allAssignments = rosterWithAssignments.get(studentId);
		
		for (Assignment a: allAssignments) {
			if (a.getName().equals(assignmentName)) {
				thisAssignment.add(a);
			}
		}
		
		return thisAssignment;
	}

	/**
	 * Given an Assignment, list the Student attempts. See getAssignment for how a
	 * given assigment may have multiple attempts
	 * 
	 * @param assignmentName
	 *            the name of the assignment whose attempts are to be returned
	 * 
	 * 
	 * @return the students attempting the given Assignment, with their resulting
	 *         results
	 *         
	 */
	// TODO supply this method
	public Map<Integer, List<Assignment>> getStudentsForAssignment(String assignmentName) {
		
		Map<Integer, List<Assignment>> rv = new HashMap<Integer, List<Assignment>>();
		
		for (Integer id: getAllStudentIds()) {
			
			List<Assignment> matchAssignments = getAssignment(id, assignmentName);
			if (matchAssignments.size() > 0)
				rv.put(id, matchAssignments);
		}
		
		return rv;
	}

	/**
	 * An unordered list of student ids in this course
	 * 
	 * @return an unordered list of student ids in this course
	 */
	public List<Integer> getAllStudentIds() {
		return new ArrayList<Integer>(rosterWithAssignments.keySet());
	}

	/**
	 * Return, in alphabetical order, all assignment names known to this course.
	 * Sorting should be by last name, then first name, then student id. 
	 * <br>
	 * Hint: Use a data structure that will automatically sort and remove duplicate
	 * entries
	 * 
	 * @return sorted list of all assignment names known to this course
	 */
	// TODO complete this method
	public List<String> getAllAssignmentNames() {
		
		Set<String> assignmentNames = new TreeSet<String>();
		
		for (Integer id : getAllStudentIds()) {
			for (Assignment a: getAssignmentsForStudent(id)) {
				assignmentNames.add(a.getName());
			}
		}
		
		List<String> names = new ArrayList<>();
		for (String s: assignmentNames) {
			names.add(s);
		}

		return names;
	}

	/**
	 * Filter the roster based on a student filter AND an assignment filter. 
	 * <br>
	 * Note that this method is optional. If you choose not to provide it, simply
	 * return 
	 * <em> 
	 * new HashMap&lt;Integer, List&lt;Assignment&gt;&gt;(); 
	 * </em>
	 * 
	 * @param studentFilter
	 *            identifies the Students to be considered for this filter
	 * 
	 * @param assignmentFilter
	 *            identifies the Assignment to be considered for this filter
	 * 
	 * @return the Students with the Assignments that pass both the Student and
	 *         Assignment filters.
	 */
	// TODO optional method - worth 10 bonus points
	public Map<Integer, List<Assignment>> filterRoster(Predicate<Integer> studentFilter,
			Predicate<Assignment> assignmentFilter) {
		Map<Integer, List<Assignment>> retVals = new HashMap<>();

		return retVals;
	}

	/**
	 * Simple getter for department
	 * 
	 * @return this course's department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Setter for department. 
	 * <br>
	 * A null parameter value will cause an IllegalArgumentException to be thrown
	 * 
	 * @param department
	 *            new department for this course
	 */
	public void setDepartment(String department) {
		if (department == null) {
			throw new IllegalArgumentException("department must be non-null");
		}
		this.department = department;
	}

	/**
	 * Getter for this course number
	 * 
	 * @return this course's number
	 */
	public int getCourseNumber() {
		return courseNumber;
	}

	/**
	 * Setter for this course number. 
	 * <br>
	 * Note that course number must be positive. A non-positive parameter value will
	 * cause an IllegalArgumentException to be thrown
	 * 
	 * @param courseNumber
	 *            new course number
	 */
	public void setCourseNumber(int courseNumber) {
		if (courseNumber <= 0) {
			throw new IllegalArgumentException("course number must be greater than zero");
		}
		this.courseNumber = courseNumber;
	}

	/**
	 * Getter for this course's roster of students with each student's assignment
	 * scores
	 * 
	 * @return A Map keyed by student id. For each student, the list of assignments
	 *         with their scores
	 */
	public Map<Integer, List<Assignment>> getRoster() {
		return rosterWithAssignments;
	}

	// haven't covered hashCode
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 * 
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + courseNumber; result = prime * result +
	 * ((department == null) ? 0 : department.hashCode()); return result; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (courseNumber != other.courseNumber)
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (rosterWithAssignments == null) {
			if (other.rosterWithAssignments != null)
				return false;
		} else if (!rosterWithAssignments.equals(other.rosterWithAssignments))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Course [department=" + department + ", courseNumber=" + courseNumber + ", roster="
				+ rosterWithAssignments + "]";
	}
}
