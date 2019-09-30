 DELIMITER //
 DROP PROCEDURE IF EXISTS UpdateSponsorReceipts;
 CREATE PROCEDURE UpdateSponsorReceipts(rid int)
   BEGIN
 DECLARE done INT DEFAULT 0;
 DECLARE receipt_id INT;
 DECLARE sponsor_id INT;
 DECLARE cur1_2 CURSOR FOR SELECT r.receiptId,s.id FROM receipts r, sponsor s WHERE r.firstName = s.firstName AND r.lastName=s.lastName AND r.referenceId = s.parishId AND r.receiptId >=rid GROUP BY r.receiptId;
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

OPEN cur1_2;

read_loop: LOOP
 IF done THEN
 LEAVE read_loop;
 END IF;
 FETCH cur1_2 
        INTO receipt_id, sponsor_id;

  insert into sponsor_receipts (sponsorId,receiptId) VALUES (sponsor_id, receipt_id);
 
 END LOOP;

close cur1_2;
 END;
 //
 DELIMITER ;
 
CALL UpdateSponsorReceipts(1);
 
