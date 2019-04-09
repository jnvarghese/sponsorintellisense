delimiter //
CREATE PROCEDURE student_code_update()
    BEGIN
      DECLARE  v_student_id INT;
      DECLARE  v_studentCode INT;
      DECLARE  v_last_student INT DEFAULT 0;

      DECLARE student_csr CURSOR FOR
       SELECT id, (studentCode * 1) AS studentCode
         FROM student
        WHERE projectId = 1 and jobid=150
          FOR UPDATE;

      DECLARE CONTINUE HANDLER FOR NOT FOUND SET  v_last_student=1;

      START TRANSACTION;
        OPEN student_csr;
        student_loop:LOOP
          FETCH student_csr INTO  v_student_id, v_studentCode;
          IF  v_last_student THEN
            LEAVE student_loop;
          END IF;
         UPDATE student SET studentCode = v_studentCode-1 WHERE projectId = 1 and jobid=150 and studentCode=v_studentCode;
        END LOOP student_loop;
        CLOSE student_csr;
        SET  v_last_student=0;

      COMMIT;

    END;//