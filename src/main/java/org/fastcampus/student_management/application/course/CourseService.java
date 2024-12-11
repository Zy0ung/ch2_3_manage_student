package org.fastcampus.student_management.application.course;

import java.util.List;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.course.interfaces.CourseCommandRepository;
import org.fastcampus.student_management.application.course.interfaces.CourseQueryRepository;
import org.fastcampus.student_management.domain.Course;
import org.fastcampus.student_management.domain.CourseList;
import org.fastcampus.student_management.domain.DayOfWeek;
import org.fastcampus.student_management.domain.Student;
import org.fastcampus.student_management.repo.CourseCommandRepositoryImpl;
import org.fastcampus.student_management.repo.StudentRepository;

public class CourseService {
  private final CourseCommandRepository courseCommandRepository;
  private final CourseQueryRepository courseQueryRepository;
  private final StudentRepository studentRepository;

  public CourseService(CourseCommandRepositoryImpl courseCommandRepository, CourseQueryRepository courseQueryRepository,
          StudentRepository studentRepository) {
    this.courseCommandRepository = courseCommandRepository;
      this.courseQueryRepository = courseQueryRepository;
      this.studentRepository = studentRepository;
  }

  public void registerCourse(CourseInfoDto courseInfoDto) {
    Student student = studentRepository.getStudent(courseInfoDto.getStudentName());
    Course course = new Course(student, courseInfoDto);
    courseCommandRepository.save(course);
  }

  public List<CourseInfoDto> getCourseDayOfWeek(DayOfWeek dayOfWeek) {
    List<Course> courses = courseQueryRepository.getCourseDayOfWeek(dayOfWeek);
    //람다식을 이용해 DTO로 변환 시켜 반환
    return courses.stream().map(CourseInfoDto::new).toList();
  }

  public void changeFee(String studentName, int fee) {
    List<Course> courses = courseQueryRepository.getCourseListByStudent(studentName);
    CourseList courseList = new CourseList(courses);
    courseList.changeAllCoursesFee(fee);
  }
}