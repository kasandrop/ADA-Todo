DROP TABLE IF EXISTS TASK;
DROP TABLE IF EXISTS LABEL CASCADE;

CREATE TABLE Label
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);

CREATE TABLE Task
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    NAME        VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255) NOT NULL,
    COMPLETION  BOOLEAN      NOT NULL,
    LABEL_ID    INT,
    FOREIGN KEY (LABEL_ID) REFERENCES Label (ID)
);

CREATE INDEX IF NOT EXISTS INX_NAME ON TASK (NAME);