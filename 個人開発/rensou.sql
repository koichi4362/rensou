CREATE TABLE users(
user_id SERIAL PRIMARY KEY,
user_name VARCHAR NOT NULL,
e_mail VARCHAR UNIQUE NOT NULL,
passwd VARCHAR NOT NULL
);

CREATE TABLE nodes(
node_id SERIAL PRIMARY KEY,
node_name VARCHAR,
parent_id INTEGER NOT NULL
);

CREATE TABLE sheets(
sheet_id SERIAL PRIMARY KEY NOT NULL,
user_id INTEGER NOT NULL,
last_node_id INTEGER,
sheet_name VARCHAR,
public_flag INTEGER,
FOREIGN KEY (user_id) REFERENCES users (user_id),
FOREIGN KEY (last_node_id) REFERENCES nodes (node_id)
);

DROP TABLE sheets;

DROP TABLE good_sheets;

ALTER TABLE sheets
ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE nodes
ADD COLUMN sheet_id INTEGER
REFERENCES sheets(sheet_id);

ALTER TABLE sheets
ADD FOREIGN KEY (last_node_id) REFERENCES nodes (node_id);

ALTER TABLE sheets
ADD COLUMN good_count INTEGER;

ALTER TABLE sheets
ADD COLUMN public_flag INTEGER;

ALTER TABLE sheets
ADD COLUMN sheet_id SERIAL PRIMARY KEY;

CREATE TABLE good_sheets(
good_id SERIAL PRIMARY KEY,
user_id INTEGER NOT NULL REFERENCES users(user_id),
sheet_id INTEGER NOT NULL REFERENCES sheets(sheet_id)
);

CREATE TABLE managers(
m_id SERIAL PRIMARY KEY,
m_name VARCHAR(20) NOT NULL,
m_e_mail VARCHAR(50) UNIQUE NOT NULL,
m_passwd VARCHAR(20) NOT NULL
);

ALTER TABLE good_sheets
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE good_sheets
ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE FUNCTION set_update_time() RETURNS opaque AS '
    BEGIN
        new.updated_at :=''now'';
        RETURN new;
    END;
' LANGUAGE 'plpgsql';

CREATE TRIGGER update_trigger BEFORE UPDATE ON good_sheets FOR EACH ROW
EXECUTE PROCEDURE set_update_time();

ALTER TABLE sheets
ADD COLUMN public_date TIMESTAMP;

CREATE OR REPLACE FUNCTION set_sheet_open_time() RETURNS opaque AS'
    BEGIN
        NEW.public_date :=''now'';
        RETURN NEW;
    END;
' LANGUAGE 'plpgsql';

CREATE TRIGGER sheet_open_trigger BEFORE UPDATE OF public_flag ON sheets FOR EACH ROW
EXECUTE PROCEDURE set_sheet_open_time();

DROP FUNCTION set_sheet_open_time();

DROP TRIGGER sheet_open_trigger ON sheets;

UPDATE users SET user_name = 'ナカムラ' WHERE user_id = 2;

ALTER TABLE users
ADD COLUMN user_role INTEGER;

DROP TABLE sheets;

DELETE FROM users;

DELETE FROM sheets;

DELETE FROM nodes;

ALTER TABLE nodes ALTER COLUMN parent_id DROP NOT NULL;

ALTER TABLE nodes DROP COLUMN public_flag;


INSERT INTO sheets (user_id , sheet_name) VALUES (2, 'シート1');

UPDATE sheets SET public_flag = 1 WHERE user_id = 2;

UPDATE sheets SET sheet_name = 'sheet1' WHERE user_id = 2;

ALTER TABLE sheets ALTER COLUMN public_flag SET NOT NULL;

UPDATE users SET user_role = 1 WHERE user_id = 2;

ALTER TABLE users ALTER COLUMN user_role SET NOT NULL;

ALTER TABLE sheets ALTER COLUMN public_flag SET DEFAULT 0;

CREATE OR REPLACE FUNCTION set_parent_id() RETURNS opaque AS '
    BEGIN
        new.parent_id := (SELECT MAX(node_id)AS node_id FROM nodes);
        RETURN new;
    END;
' LANGUAGE 'plpgsql';

CREATE TRIGGER set_parent_id_trigger BEFORE INSERT ON nodes FOR EACH ROW
    WHEN (NEW.parent_id = 0)
EXECUTE PROCEDURE set_parent_id();
    
DROP TRIGGER set_parent_id_trigger ON nodes;

DROP FUNCTION set_parent_id();

DELETE FROM sheets WHERE sheet_id = 1;


SELECT MAX(sheet_id)AS sheet_id FROM sheets;


---------------------------
CREATE OR REPLACE FUNCTION check_first_node() RETURNS opaque AS'
    BEGIN
        IF EXISTS (SELECT node_id FROM nodes) THEN
        ELSE INSERT INTO nodes(node_name) VALUES('first_node');
        END IF;
    END;
' LANGUAGE 'plpgsql';

CREATE TRIGGER check_first_node_triger AFTER INSERT ON sheets FOR EACH ROW
EXECUTE PROCEDURE check_first_node();

DROP FUNCTION check_first_node();

DROP TRIGGER check_first_node_triger ON sheets;

--------------------
SELECT node_name, node_id, parent_id, sheet_id 
FROM nodes WHERE sheet_id = 21 ORDER BY node_id;

UPDATE sheets SET public_flag = 1 WHERE sheet_id = 21;

INSERT INTO users(user_name,e_mail,passwd,user_role) VALUES (
'管理者',
'rensou@gmail.com',
'rensou0',
9
);
DELETE FROM good_sheets WHERE good_id = 2;

SELECT user_id FROM good_sheets WHERE sheet_id = 21;

