 DELIMITER //
 DROP PROCEDURE IF EXISTS ModifyStdMxOutNEMonthNEYear;
 CREATE PROCEDURE ModifyStdMxOutNEMonthNEYear()
   BEGIN
 DECLARE done INT DEFAULT 0;
 DECLARE my_day CHAR(2) DEFAULT '01';
 DECLARE my_mxOut TIMESTAMP;
 DECLARE my_sm_id INT;
 DECLARE my_expm CHAR(2);
 DECLARE my_expy CHAR(4);
 DECLARE str_date TIMESTAMP;
 DECLARE cur1_2 CURSOR FOR SELECT sm.id,maxout,expirationMonth,expirationYear FROM student_maxout sm, sponsee s WHERE sm.enrollmentid IN (SELECT id FROM enrollment WHERE STATUS=0 AND renewed='N' AND id NOT IN (120,122,125,128,131,132,133,134,749,778,797,1018,1057,1331,1341,1702,1935)) AND s.enrollmentId=sm.enrollmentId AND s.studentId = sm.studentId AND s.studentId NOT IN (SELECT studentid FROM student_maxout WHERE enrollmentid IN (SELECT id FROM enrollment WHERE STATUS=1 AND renewed='Y')) AND sm.studentid NOT IN (SELECT studentid FROM student_maxout WHERE enrollmentid IN (SELECT id FROM enrollment WHERE STATUS=1 AND renewed='Y')) AND DATE_FORMAT(sm.maxOut, '%m') <> s.expirationMonth AND DATE_FORMAT(sm.maxOut, '%Y') <> s.expirationYear and expirationMonth <> 0 AND flag1 <> 1 GROUP BY sm.studentId ORDER BY sm.enrollmentId;
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

OPEN cur1_2;

read_loop: LOOP
 IF done THEN
 LEAVE read_loop;
 END IF;
 FETCH cur1_2 
        INTO my_sm_id, my_mxOut, my_expm, my_expy;
 SET str_date = STR_TO_DATE(CONCAT(my_expy,'-',my_expm,'-',my_day), "%Y-%m-%d");
  -- UPDATE student_maxout SET a=my_expy, b= my_expm  WHERE id = my_sm_id;
  UPDATE student_maxout SET maxOut = str_date, flag2=2 WHERE id = my_sm_id;
 -- UPDATE student_maxout SET maxOut2 = STR_TO_DATE('2014-05-28 11:30:10','%Y-%m-%d %H:%i:%s') WHERE id = my_sm_id;
 END LOOP;

close cur1_2;
 END;
 //
 DELIMITER ;
 -- CALL ModifyStdMxOutNEMonthNEYear();
 
