DELIMITER //
DROP PROCEDURE IF EXISTS StudentSeqDiff;
CREATE PROCEDURE StudentSeqDiff(stCode INT, prjId INT)
 BEGIN
   DECLARE done INT DEFAULT 0;
   DECLARE student_code INT;
   DECLARE student_id INT;
   DECLARE new_count INT DEFAULT 0;
   DECLARE stud_curs CURSOR FOR SELECT id,studentCode FROM student WHERE studentCode > stCode AND projectid=prjId;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

        OPEN stud_curs;
        student_loop:LOOP
          IF  done THEN
            LEAVE student_loop;
          END IF;
          FETCH stud_curs INTO  student_id, student_code;
         SET new_count = new_count + 1;
         UPDATE student SET studentCode = stCode+new_count WHERE id = student_id;
        END LOOP student_loop;
        CLOSE stud_curs;
    END;
//
 DELIMITER ;