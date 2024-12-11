package org.fastcampus.student_management;

import org.fastcampus.student_management.application.course.CourseService;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.course.interfaces.CourseQueryRepository;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.application.student.dto.StudentInfoDto;
import org.fastcampus.student_management.repo.CourseCommandRepositoryImpl;
import org.fastcampus.student_management.repo.CourseJdbcCommandRepository;
import org.fastcampus.student_management.repo.StudentRepository;
import org.fastcampus.student_management.ui.course.CourseController;
import org.fastcampus.student_management.ui.course.CoursePresenter;
import org.fastcampus.student_management.ui.student.StudentController;
import org.fastcampus.student_management.ui.student.StudentPresenter;
import org.fastcampus.student_management.ui.UserInputType;

public class Main {

  public static void main(String[] args) {
    StudentRepository studentRepository = new StudentRepository();
    CourseCommandRepositoryImpl courseRepository = new CourseCommandRepositoryImpl();
    CourseJdbcCommandRepository jdbcCommandRepository = new CourseJdbcCommandRepository();

    StudentService studentService = new StudentService(studentRepository);
    CourseService courseService = new CourseService(courseRepository, jdbcCommandRepository, studentRepository);

    CoursePresenter coursePresenter = new CoursePresenter();
    StudentPresenter studentPresenter = new StudentPresenter();

    CourseController courseController = new CourseController(coursePresenter, courseService, studentPresenter);
    StudentController studentController = new StudentController(studentPresenter, studentService);

    // 기본 Defalut 세팅 값 추가
    StudentInfoDto studentInfo1 = new StudentInfoDto("송아들", 4, "경기도 평택시");
    StudentInfoDto studentInfo2 = new StudentInfoDto("송혜미", 29, "경기도 평택시");
    studentService.saveStudent(studentInfo1);
    studentService.saveStudent(studentInfo2);

    CourseInfoDto courseInfo1 = new CourseInfoDto("삑삑이 던지기", 50000, "MONDAY", "송아들", 1717299008L);
    CourseInfoDto courseInfo2 = new CourseInfoDto("케이크 만들기", 60000, "TUESDAY", "송혜미", 1717299008L);
    courseService.registerCourse(courseInfo1);
    courseService.registerCourse(courseInfo2);

    studentPresenter.showMenu();

    UserInputType userInputType = studentController.getUserInput();
    while (userInputType != UserInputType.EXIT) {
      switch (userInputType) {
        case NEW_STUDENT:
          studentController.registerStudent();
          break;
        case NEW_COURSE:
          courseController.registerCourse();
          break;
        case SHOW_COURSE_DAY_OF_WEEK:
          courseController.showCourseDayOfWeek();
          break;
        case ACTIVATE_STUDENT:
          studentController.activateStudent();
          break;
        case DEACTIVATE_STUDENT:
          studentController.deactivateStudent();
          break;
        case CHANGE_FEE:
          courseController.changeFee();
          break;
        default:
          studentPresenter.showErrorMessage();
          break;
      }
      studentPresenter.showMenu();
      userInputType = studentController.getUserInput();
    }
  }
}