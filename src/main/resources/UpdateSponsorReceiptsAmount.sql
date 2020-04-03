 DELIMITER //
 DROP PROCEDURE IF EXISTS UpdateSponsorReceiptsAmount;
 CREATE PROCEDURE UpdateSponsorReceiptsAmount()
   BEGIN
 DECLARE done INT DEFAULT 0;
 DECLARE receipt_id INT;
 DECLARE r_amount double;
 DECLARE created_date TIMESTAMP;
 DECLARE cur1_2 CURSOR FOR SELECT receiptid,amount,createdDate from receipts WHERE receiptType=2 and receiptId IN (SELECT receiptid FROM sponsor_receipts);
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

OPEN cur1_2;

read_loop: LOOP
 IF done THEN
 LEAVE read_loop;
 END IF;
 FETCH cur1_2 
        INTO receipt_id, r_amount, created_date;
  UPDATE sponsor_receipts SET amount = r_amount, updated='Y', type='I' WHERE receiptId = receipt_id;
 END LOOP;

close cur1_2;
 END;
 //
 DELIMITER ;
CALL UpdateSponsorReceiptsAmount();
 
