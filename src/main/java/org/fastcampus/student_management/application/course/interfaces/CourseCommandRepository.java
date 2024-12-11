package org.fastcampus.student_management.application.course.interfaces;

import org.fastcampus.student_management.domain.Course;

/**
 * @author jiyoung
 */
public interface CourseCommandRepository {

    void save(Course course);
}
