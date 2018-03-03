CREATE TABLE ers_user_roles
(
    ers_user_role_id NUMBER NOT NULL,
    user_role VARCHAR2(250) NOT NULL,
    CONSTRAINT ers_user_roles_pk PRIMARY KEY (ers_user_role_id)
);

CREATE TABLE ers_reimbursement_type
(
    reimb_type_id NUMBER NOT NULL,
    reimb_type VARCHAR2(250) NOT NULL,
    CONSTRAINT reimb_type_pk PRIMARY KEY (reimb_type_id)
);

CREATE TABLE ers_reimbursement_status
(
    reimb_status_id NUMBER NOT NULL,
    reimb_status VARCHAR2(250) NOT NULL,
    CONSTRAINT reimb_status_pk PRIMARY KEY (reimb_status_id)
);

CREATE TABLE ers_users
(
    ers_users_id NUMBER NOT NULL,
    ers_username VARCHAR2(250) UNIQUE NOT NULL,
    ers_password VARCHAR2(250) NOT NULL,
    user_first_name VARCHAR2(250) NOT NULL,
    user_last_name VARCHAR2(250) NOT NULL,
    user_email VARCHAR2(250) UNIQUE NOT NULL,
    user_role_id NUMBER NOT NULL,
    CONSTRAINT user_roles_fk
        FOREIGN KEY (user_role_id)
        REFERENCES ers_user_roles(ers_user_role_id),
    CONSTRAINT ers_users_pk PRIMARY KEY (ers_users_id)
);

CREATE TABLE ers_reimbursement
(
    reimb_id NUMBER NOT NULL,
    reimb_amount NUMBER NOT NULL,
    reimb_submitted TIMESTAMP NOT NULL,
    reimb_resolved TIMESTAMP,
    reimb_description VARCHAR2(250),
    reimb_receipt BLOB,
    reimb_author NUMBER NOT NULL,
    reimb_resolver NUMBER NOT NULL,
    reimb_status_id NUMBER NOT NULL,
    reimb_type_id NUMBER NOT NULL,
    CONSTRAINT ers_users_fk_auth
        FOREIGN KEY (reimb_author)
        REFERENCES ers_users(ers_users_id),
    CONSTRAINT ers_users_fk_reslvr
        FOREIGN KEY (reimb_resolver)
        REFERENCES ers_users(ers_users_id),
    CONSTRAINT reimb_status_fk
        FOREIGN KEY (reimb_status_id)
        REFERENCES ers_reimbursement_status(reimb_status_id),
    CONSTRAINT reimb_type_fk
        FOREIGN KEY (reimb_type_id)
        REFERENCES ers_reimbursement_type(reimb_type_id),
    CONSTRAINT ers_reimbursement_pk PRIMARY KEY (reimb_id)
);

CREATE SEQUENCE reimb_id_seq;

CREATE OR REPLACE TRIGGER reimb_id_trig
BEFORE INSERT OR UPDATE ON ers_reimbursement
FOR EACH ROW
BEGIN 
    IF INSERTING THEN 
        SELECT reimb_id_seq.nextVal INTO :new.reimb_id FROM dual;
    ELSIF UPDATING THEN
        SELECT :old.reimb_id INTO :new.reimb_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE ers_users_id_seq;

CREATE OR REPLACE TRIGGER ers_users_id_trig
BEFORE INSERT OR UPDATE ON ers_users
FOR EACH ROW
BEGIN 
    IF INSERTING THEN 
        SELECT ers_users_id_seq.nextVal INTO :new.ers_users_id FROM dual;
    ELSIF UPDATING THEN
        SELECT :old.ers_users_id INTO :new.ers_users_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE ers_user_role_id_seq;

CREATE OR REPLACE TRIGGER ers_user_role_id_trig
BEFORE INSERT OR UPDATE ON ers_user_roles
FOR EACH ROW
BEGIN 
    IF INSERTING THEN 
        SELECT ers_user_role_id_seq.nextVal INTO :new.ers_user_role_id FROM dual;
    ELSIF UPDATING THEN
        SELECT :old.ers_user_role_id INTO :new.ers_user_role_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE reimb_type_id_seq;

CREATE OR REPLACE TRIGGER reimb_type_id_trig
BEFORE INSERT OR UPDATE ON ers_reimbursement_type
FOR EACH ROW
BEGIN 
    IF INSERTING THEN 
        SELECT reimb_type_id_seq.nextVal INTO :new.reimb_type_id FROM dual;
    ELSIF UPDATING THEN
        SELECT :old.reimb_type_id INTO :new.reimb_type_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE reimb_status_id_seq;

CREATE OR REPLACE TRIGGER reimb_status_id_trig
BEFORE INSERT OR UPDATE ON ers_reimbursement_status
FOR EACH ROW
BEGIN 
    IF INSERTING THEN 
        SELECT reimb_status_id_seq.nextVal INTO :new.reimb_status_id FROM dual;
    ELSIF UPDATING THEN
        SELECT :old.reimb_status_id INTO :new.reimb_status_id FROM dual;
    END IF;
END;
/

INSERT INTO ers_user_roles(user_role)
    VALUES('Employee');
    
INSERT INTO ers_user_roles(user_role)
    VALUES('Finance Manager');
    
INSERT INTO ers_reimbursement_type(reimb_type)
    VALUES('Lodging');

INSERT INTO ers_reimbursement_type(reimb_type)
    VALUES('Travel');
    
INSERT INTO ers_reimbursement_type(reimb_type)
    VALUES('Food');
    
INSERT INTO ers_reimbursement_type(reimb_type)
    VALUES('Other');
    
INSERT INTO ers_reimbursement_status(reimb_status)
    VALUES('Pending');
    
INSERT INTO ers_reimbursement_status(reimb_status)
    VALUES('Approved');
    
INSERT INTO ers_reimbursement_status(reimb_status)
    VALUES('Denied');
    
COMMIT;