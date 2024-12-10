#USE hihello;
DROP TABLE IF EXISTS `chatbot`;
DROP TABLE IF EXISTS `chatbot_category`;
DROP TABLE IF EXISTS `noti`;
DROP TABLE IF EXISTS `file`;
DROP TABLE IF EXISTS `wiki_mod_content`;
DROP TABLE IF EXISTS `wiki_snapshot`;
DROP TABLE IF EXISTS `wiki`;
DROP TABLE IF EXISTS `peer_review`;
DROP TABLE IF EXISTS `peer_review_list`;
DROP TABLE IF EXISTS `group_member`;
DROP TABLE IF EXISTS `final_eval`;
DROP TABLE IF EXISTS `task_eval`;
DROP TABLE IF EXISTS `task_submit`;
DROP TABLE IF EXISTS `final_eval_ind`;
DROP TABLE IF EXISTS `eval_list`;
DROP TABLE IF EXISTS `eval_ind`;
DROP TABLE IF EXISTS `onboarding_status`;
DROP TABLE IF EXISTS `task_group`;
DROP TABLE IF EXISTS `quiz_result`;
DROP TABLE IF EXISTS `quiz`;
DROP TABLE IF EXISTS `report`;
DROP TABLE IF EXISTS `planning`;
DROP TABLE IF EXISTS `mentoring`;
DROP TABLE IF EXISTS `checklist_status`;
DROP TABLE IF EXISTS `checklist`;
DROP TABLE IF EXISTS `template`;
DROP TABLE IF EXISTS `quiz_category`;
DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `positions`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
                              `department_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                              `department_name`   VARCHAR(20)   NOT NULL,
                              PRIMARY KEY (`department_seq`)
);

CREATE TABLE `task` (
                        `task_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                        `department_seq`   BIGINT   NULL,
                        `template_seq`  BIGINT  NOT NULL,
                        `task_type`   VARCHAR(20)   NOT NULL,
    'task_title'    VARCHAR(100)   NOT NULL,
    `task_content`   TEXT   NOT NULL,
    'task_url'   VARCHAR(255) NULL,
    `reg_date`   DATETIME   NOT NULL,
    `mod_date`   DATETIME   NULL,
    PRIMARY KEY (`task_seq`),
    KEY `FK_department_TO_task_1` (`department_seq`),
    CONSTRAINT `FK_department_TO_task_1` FOREIGN KEY (`department_seq`) REFERENCES `department` (`department_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
    KEY `FK_template_TO_task_1` (`template_seq`),
    CONSTRAINT `FK_template_TO_task_1` FOREIGN KEY (`template_seq`) REFERENCES `template` (`template_seq`) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE `positions` (
                             `position_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                             `position_name`   VARCHAR(20)   NOT NULL,
                             PRIMARY KEY (`position_seq`)
);

CREATE TABLE `employee` (
                            `employee_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                            `department_seq`   BIGINT   NOT NULL,
                            `position_seq`   BIGINT   NOT NULL,
                            `employee_num`   VARCHAR(20)   NOT NULL,
                            `employee_name`   VARCHAR(20)   NOT NULL,
                            `employee_phone`   VARCHAR(20)   NOT NULL,
                            `employee_email`   VARCHAR(30)   NOT NULL,
                            `employee_role`   VARCHAR(20)   NOT NULL    DEFAULT 'STAFF',
                            `employee_password`   VARCHAR(255)   NOT NULL,
                            `employee_deleted_status`   BOOLEAN   NOT NULL   DEFAULT FALSE,
                            `reg_date`   DATETIME   NOT NULL,
                            `mod_date`   DATETIME   NULL,
                            PRIMARY KEY (`employee_seq`),
                            KEY `FK_department_TO_employee_1` (`department_seq`),
                            CONSTRAINT `FK_department_TO_employee_1` FOREIGN KEY (`department_seq`) REFERENCES `department` (`department_seq`) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE `quiz_category` (
                                 `quiz_category_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                                 `quiz_category_name`   VARCHAR(20)   NOT NULL,
                                 `reg_date`   DATETIME   NOT NULL,
                                 `mod_date`   DATETIME   NULL,
                                 PRIMARY KEY(`quiz_category_seq`)
);

CREATE TABLE `template` (
                            `template_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                            `quiz_category_seq` BIGINT NULL,
                            `template_type`   VARCHAR(20)   NOT NULL,
                            `template_check_required_status`   BOOLEAN   NOT NULL,
                            `template_training_type`   VARCHAR(20)   NULL,
                            `template_title`   VARCHAR(20)   NOT NULL,
                            `template_sub`   VARCHAR(50)   NULL,
                            `template_detail`   TEXT   NULL,
                            `template_url`   VARCHAR(255)   NULL,
                            `template_quiz_qty`   INT   NULL,
                            `template_task_round` VARCHAR(10) NULL,
                            `template_procedure`   INT   NOT NULL,
                            `template_end_at`   DATETIME   NULL,
                            `reg_date`   DATETIME   NOT NULL,
                            `mod_date`   DATETIME   NULL,
                            PRIMARY KEY (`template_seq`),
                            KEY `FK_quiz_category_TO_template_1` (`quiz_category_seq`),
                            CONSTRAINT `FK_quiz_category_TO_template_1` FOREIGN KEY (`quiz_category_seq`) REFERENCES `quiz_category` (`quiz_category_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `checklist` (
                             `checklist_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                             `checklist_content`   VARCHAR(50)   NOT NULL,
                             `template_seq`   BIGINT   NOT NULL,
                             PRIMARY KEY (`checklist_seq`),
                             KEY `FK_template_TO_checklist_1` (`template_seq`),
                             CONSTRAINT `FK_template_TO_checklist_1` FOREIGN KEY (`template_seq`) REFERENCES `template` (`template_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `checklist_status` (
                                    `checklist_status_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                                    `employee_seq`   BIGINT   NOT NULL,
                                    `checklist_seq`   BIGINT   NOT NULL,
                                    `list_checked_status`   BOOLEAN   NOT NULL   DEFAULT FALSE,
                                    `reg_date`   DATETIME   NOT NULL,
                                    `mod_date`   DATETIME   NULL,
                                    PRIMARY KEY (`checklist_status_seq`),
                                    KEY `FK_employee_TO_checklist_status_1` (`employee_seq`),
                                    CONSTRAINT `FK_employee_TO_checklist_status_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                                    KEY `FK_checklist_TO_checklist_status_1` (`checklist_seq`),
                                    CONSTRAINT `FK_checklist_TO_checklist_status_1` FOREIGN KEY (`checklist_seq`) REFERENCES `checklist` (`checklist_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `mentoring` (
                             `mentoring_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                             `mentor_seq`   BIGINT   NOT NULL,
                             `mentee_seq`   BIGINT   NOT NULL,
                             `mentoring_active_status`  BOOLEAN NOT NULL  DEFAULT TRUE,
                             `reg_date`   DATETIME   NOT NULL,
                             `mod_date`   DATETIME   NULL,
                             PRIMARY KEY (`mentoring_seq`),
                             KEY `FK_employee_TO_mentoring_1` (`mentor_seq`),
                             CONSTRAINT `FK_employee_TO_mentoring_1` FOREIGN KEY (`mentor_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `FK_employee_TO_mentoring_2` (`mentee_seq`),
                             CONSTRAINT `FK_employee_TO_mentoring_2` FOREIGN KEY (`mentee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `planning` (
                            `planning_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                            `employee_seq`   BIGINT   NOT NULL,
                            `planning_name`   VARCHAR(100)   NOT NULL,
                            `planning_content`   TEXT   NOT NULL,
                            `planning_status`   VARCHAR(20)   NOT NULL,
                            `reg_date`   DATETIME   NOT NULL,
                            `mod_date`   DATETIME   NULL,
                            PRIMARY KEY (`planning_seq`),
                            KEY `FK_employee_TO_planning_1` (`employee_seq`),
                            CONSTRAINT `FK_employee_TO_planning_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `report` (
                          `report_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                          `mentoring_seq`   BIGINT   NOT NULL,
                          `report_content`   TEXT   NOT NULL,
                          `report_feeling`   TEXT   NOT NULL,
                          `report_week`   INT   NOT NULL,
                          `report_feedback_content`   TEXT   NULL,
                          `reg_date`   DATETIME   NOT NULL,
                          `mod_date`   DATETIME   NULL,
                          PRIMARY KEY (`report_seq`),
                          KEY `FK_mentoring_TO_report_1` (`mentoring_seq`),
                          CONSTRAINT `FK_mentoring_TO_report_1` FOREIGN KEY (`mentoring_seq`) REFERENCES `mentoring` (`mentoring_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `quiz` (
                        `quiz_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                        `quiz_category_seq`   BIGINT   NOT NULL,
                        `quiz_question`   TEXT   NOT NULL,
                        `quiz_answer`   BOOLEAN   NOT NULL   DEFAULT FALSE,
                        `quiz_explanation`   TEXT   NOT NULL,
                        `reg_date`   DATETIME   NOT NULL,
                        `mod_date`   DATETIME   NULL,
                        PRIMARY KEY (`quiz_seq`),
                        KEY `FK_quiz_category_TO_quiz_1` (`quiz_category_seq`),
                        CONSTRAINT `FK_quiz_category_TO_quiz_1` FOREIGN KEY (`quiz_category_seq`) REFERENCES `quiz_category` (`quiz_category_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `quiz_result` (
                               `quiz_result_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                               `quiz_seq`   BIGINT   NOT NULL,
                               `employee_seq`   BIGINT   NOT NULL,
                               `quiz_correct_status`   BOOLEAN   NOT NULL,
                               `reg_date`   DATETIME   NOT NULL,
                               `mod_date`   DATETIME   NULL,
                               PRIMARY KEY (`quiz_result_seq`),
                               KEY `FK_quiz_TO_quiz_result_1` (`quiz_seq`),
                               CONSTRAINT `FK_quiz_TO_quiz_result_1` FOREIGN KEY (`quiz_seq`) REFERENCES `quiz` (`quiz_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                               KEY `FK_employee_TO_quiz_result_1` (`employee_seq`),
                               CONSTRAINT `FK_employee_TO_quiz_result_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `task_group` (
                              `task_group_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                              `task_seq`   BIGINT   NOT NULL,
                              `task_group_active_status`   BOOLEAN   NOT NULL   DEFAULT TRUE,
                              `reg_date`   DATETIME   NOT NULL,
                              `mod_date`   DATETIME   NULL,
                              PRIMARY KEY (`task_group_seq`),
                              KEY `FK_task_TO_task_group_1` (`task_seq`),
                              CONSTRAINT `FK_task_TO_task_group_1` FOREIGN KEY (`task_seq`) REFERENCES `task` (`task_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `onboarding_status` (
                                     `onboarding_status_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                                     `template_seq`   BIGINT   NOT NULL,
                                     `employee_seq`   BIGINT   NOT NULL,
                                     `onboarding_completed_status`   BOOLEAN   NOT NULL,
                                     `reg_date`   DATETIME   NOT NULL,
                                     `mod_date`   DATETIME   NULL,
                                     PRIMARY KEY (`onboarding_status_seq`),
                                     KEY `FK_template_TO_onboarding_status_1` (`template_seq`),
                                     CONSTRAINT `FK_template_TO_onboarding_status_1` FOREIGN KEY (`template_seq`) REFERENCES `template` (`template_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                                     KEY `FK_employee_TO_onboarding_status_1` (`employee_seq`),
                                     CONSTRAINT `FK_employee_TO_onboarding_status_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `eval_ind` (
                            `eval_ind_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                            `eval_ind_content`   VARCHAR(30)   NOT NULL,
                            `eval_ind_score`   INT   NOT NULL,
                            `reg_date`   DATETIME   NOT NULL,
                            `mod_date`   DATETIME   NULL,
                            PRIMARY KEY (`eval_ind_seq`)
);

CREATE TABLE `eval_list` (
                             `eval_list_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                             `task_seq`   BIGINT   NOT NULL,
                             `eval_ind_seq`   BIGINT   NOT NULL,
                             `eval_list_content`   VARCHAR(200)   NOT NULL,
                             `eval_list_score`   INT   NOT NULL,
                             `reg_date`   DATETIME   NOT NULL,
                             `mod_date`   DATETIME   NULL,
                             PRIMARY KEY (`eval_list_seq`),
                             KEY `FK_task_TO_eval_list_1` (`task_seq`),
                             CONSTRAINT `FK_task_TO_eval_list_1` FOREIGN KEY (`task_seq`) REFERENCES `task` (`task_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `FK_eval_ind_TO_eval_list_1` (`eval_ind_seq`),
                             CONSTRAINT `FK_eval_ind_TO_eval_list_1` FOREIGN KEY (`eval_ind_seq`) REFERENCES `eval_ind` (`eval_ind_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `final_eval_ind` (
                                  `final_eval_ind_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                                  `final_eval_ind_name`   VARCHAR(20)   NOT NULL,
                                  `final_eval_ind_weight`   int   NOT NULL,
                                  PRIMARY KEY (`final_eval_ind_seq`)
);

CREATE TABLE `task_submit` (
                               `task_submit_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                               `task_seq`   BIGINT   NOT NULL,
                               `employee_seq`   BIGINT   NOT NULL,
                               `task_submit_content`   TEXT   NOT NULL,
                               `reg_date`   DATETIME   NOT NULL,
                               `mod_date`   DATETIME   NULL,
                               PRIMARY KEY (`task_submit_seq`),
                               KEY `FK_task_TO_task_submit_1` (`task_seq`),
                               CONSTRAINT `FK_task_TO_task_submit_1` FOREIGN KEY (`task_seq`) REFERENCES `task` (`task_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                               KEY `FK_employee_TO_task_submit_1` (`employee_seq`),
                               CONSTRAINT `FK_employee_TO_task_submit_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `task_eval` (
                             `task_eval_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                             `eval_list_seq`   BIGINT   NOT NULL,
                             `task_submit_seq`   BIGINT   NOT NULL,
                             `task_score`   INT   NOT NULL,
                             `reg_date`   DATETIME   NOT NULL,
                             `mod_date`   DATETIME   NULL,
                             PRIMARY KEY (`task_eval_seq`),
                             KEY `FK_eval_list_TO_task_eval_1` (`eval_list_seq`),
                             CONSTRAINT `FK_eval_list_TO_task_eval_1` FOREIGN KEY (`eval_list_seq`) REFERENCES `eval_list` (`eval_list_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                             KEY `FK_task_submit_TO_task_eval_1` (`task_submit_seq`),
                             CONSTRAINT `FK_task_submit_TO_task_eval_1` FOREIGN KEY (`task_submit_seq`) REFERENCES `task_submit` (`task_submit_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `final_eval` (
                              `final_eval_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                              `employee_seq`   BIGINT   NOT NULL,
                              `eval_ind_seq`   BIGINT   NULL,
                              `final_eval_ind_seq`   BIGINT   NULL,
                              `employee_score`   DOUBLE   NOT NULL,
                              `reg_date`   DATETIME   NOT NULL,
                              `mod_date`   DATETIME   NULL,
                              PRIMARY KEY (`final_eval_seq`),
                              KEY `FK_employee_TO_final_eval_1` (`employee_seq`),
                              CONSTRAINT `FK_employee_TO_final_eval_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                              KEY `FK_eval_ind_TO_final_eval_1` (`eval_ind_seq`),
                              CONSTRAINT `FK_eval_ind_TO_final_eval_1` FOREIGN KEY (`eval_ind_seq`) REFERENCES `eval_ind` (`eval_ind_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                              KEY `FK_final_eval_ind_TO_final_eval_1` (`final_eval_ind_seq`),
                              CONSTRAINT `FK_final_eval_ind_TO_final_eval_1` FOREIGN KEY (`final_eval_ind_seq`) REFERENCES `final_eval_ind` (`final_eval_ind_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `group_member` (
                                `group_member_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                                `task_group_seq`   BIGINT   NOT NULL,
                                `employee_seq`   BIGINT   NOT NULL,
                                PRIMARY KEY (`group_member_seq`),
                                KEY `FK_task_group_TO_group_member_1` (`task_group_seq`),
                                CONSTRAINT `FK_task_group_TO_group_member_1` FOREIGN KEY (`task_group_seq`) REFERENCES `task_group` (`task_group_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                                KEY `FK_employee_TO_group_member_1` (`employee_seq`),
                                CONSTRAINT `FK_employee_TO_group_member_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `peer_review_list` (
                                    `peer_review_list_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                                    `peer_review_list_content`   VARCHAR(200)   NOT NULL,
                                    `peer_review_list_score`   INT   NOT NULL,
                                    `reg_date`   DATETIME   NOT NULL,
                                    `mod_date`   DATETIME   NULL,
                                    PRIMARY KEY (`peer_review_list_seq`)
);

CREATE TABLE `peer_review` (
                               `peer_review_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                               `peer_review_list_seq`   BIGINT   NOT NULL,
                               `reviewer_seq`   BIGINT   NOT NULL,
                               `reviewee_seq`   BIGINT   NOT NULL,
                               `peer_review_score`   INT   NOT NULL,
                               `reg_date`   DATETIME   NOT NULL,
                               `mod_date`   DATETIME   NULL,
                               PRIMARY KEY (`peer_review_seq`),
                               KEY `FK_peer_review_list_TO_peer_review_1` (`peer_review_list_seq`),
                               CONSTRAINT `FK_peer_review_list_TO_peer_review_1` FOREIGN KEY (`peer_review_list_seq`) REFERENCES `peer_review_list` (`peer_review_list_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                               KEY `FK_employee_TO_peer_review_1` (`reviewer_seq`),
                               CONSTRAINT `FK_employee_TO_peer_review_1` FOREIGN KEY (`reviewer_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                               KEY `FK_employee_TO_peer_review_2` (`reviewee_seq`),
                               CONSTRAINT `FK_employee_TO_peer_review_2` FOREIGN KEY (`reviewee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `wiki` (
                        `wiki_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                        `wiki_title`   VARCHAR(100)   NOT NULL,
                        `wiki_current_ver`   INT   NOT NULL,
                        `wiki_deleted_status`   BOOLEAN   NOT NULL   DEFAULT FALSE,
                        `reg_date`   DATETIME      NOT NULL,
                        `mod_date`   DATETIME      NULL,
                        PRIMARY KEY (`wiki_seq`)
);


CREATE TABLE `wiki_snapshot` (
                                 `wiki_snapshot_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                                 `wiki_seq`   BIGINT   NOT NULL,
                                 `wiki_snapshot_ver`   INT   NOT NULL,
                                 `wiki_snapshot_content`   TEXT   NOT NULL,
                                 `reg_date`   DATETIME   NOT NULL,
                                 `mod_date`   DATETIME   NULL,
                                 PRIMARY KEY (`wiki_snapshot_seq`),
                                 KEY `FK_wiki_TO_wiki_snapshot_1` (`wiki_seq`),
                                 CONSTRAINT `FK_wiki_TO_wiki_snapshot_1` FOREIGN KEY (`wiki_seq`) REFERENCES `wiki` (`wiki_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `wiki_mod_content` (
                                    `wiki_mod_content_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                                    `wiki_seq`   BIGINT   NOT NULL,
                                    `employee_seq`   BIGINT   NOT NULL,
                                    `wiki_snapshot_seq`  BIGINT  NOT NULL,
                                    `mod_content`   JSON   NULL,
                                    `reg_date`   DATETIME   NOT NULL,
                                    `mod_date`   DATETIME   NULL,
                                    PRIMARY KEY (`wiki_mod_content_seq`),
                                    KEY `FK_wiki_TO_wiki_mod_content_1` (`wiki_seq`),
                                    CONSTRAINT `FK_wiki_TO_wiki_mod_content_1` FOREIGN KEY (`wiki_seq`) REFERENCES `wiki` (`wiki_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                                    KEY `FK_wiki_snapshot_TO_wiki_mod_content_1` (`wiki_snapshot_seq`),
                                    CONSTRAINT `FK_wiki_snapshot_TO_wiki_mod_content_1` FOREIGN KEY (`wiki_snapshot_seq`)   REFERENCES  `wiki_snapshot` (`wiki_snapshot_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `file` (
                        `file_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                        `planning_seq`   BIGINT NULL,
                        `task_seq`   BIGINT NULL,
                        `task_submit_seq`   BIGINT NULL,
                        `template_seq`   BIGINT NULL,
                        `wiki_mod_content_seq`   BIGINT  NULL,
                        `employee_seq`  BIGINT  NULL,
                        `file_name`   VARCHAR(50)   NOT NULL,
                        `file_url`   TEXT   NOT NULL,
                        `reg_date`   DATETIME   NOT NULL,
                        `mod_date`   DATETIME   NULL,
                        PRIMARY KEY (`file_seq`),
                        KEY `FK_planning_TO_file_1` (`planning_seq`),
                        CONSTRAINT `FK_planning_TO_file_1` FOREIGN KEY (`planning_seq`) REFERENCES `planning` (`planning_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `FK_task_TO_file_1` (`task_seq`),
                        CONSTRAINT `FK_task_TO_file_1` FOREIGN KEY (`task_seq`) REFERENCES `task` (`task_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `FK_task_submit_TO_file_1` (`task_submit_seq`),
                        CONSTRAINT `FK_task_submit_TO_file_1` FOREIGN KEY (`task_submit_seq`) REFERENCES `task_submit` (`task_submit_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `FK_template_TO_file_1` (`template_seq`),
                        CONSTRAINT `FK_template_TO_file_1` FOREIGN KEY (`template_seq`) REFERENCES `template` (`template_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `FK_wiki_mod_content_TO_file_1` (`wiki_mod_content_seq`),
                        CONSTRAINT `FK_wiki_mod_content_TO_file_1` FOREIGN KEY (`wiki_mod_content_seq`) REFERENCES `wiki_mod_content` (`wiki_mod_content_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `FK_employee_TO_file_1` (`employee_seq`),
                        CONSTRAINT `FK_employee_TO_file_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `noti` (
                        `noti_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                        `employee_seq`   BIGINT   NOT NULL,
                        `template_seq`   BIGINT   NOT NULL,
                        `noti_content`   VARCHAR(100)   NOT NULL,
                        `noti_url`   VARCHAR(255)   NULL,
                        `alarm_read_status`   BOOLEAN   NOT NULL   DEFAULT FALSE,
                        `reg_date`   DATETIME   NOT NULL,
                        `mod_date`   DATETIME      NULL,
                        PRIMARY KEY (`noti_seq`),
                        KEY `FK_employee_TO_noti_1` (`employee_seq`),
                        CONSTRAINT `FK_employee_TO_noti_1` FOREIGN KEY (`employee_seq`) REFERENCES `employee` (`employee_seq`) ON DELETE CASCADE ON UPDATE CASCADE,
                        KEY `FK_template_TO_noti_1` (`template_seq`),
                        CONSTRAINT `FK_template_TO_noti_1` FOREIGN KEY (`template_seq`) REFERENCES `template` (`template_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `chatbot_category` (
                                    `chatbot_category_seq`  BIGINT  NOT NULL    AUTO_INCREMENT,
                                    `chatbot_category_content`  VARCHAR(20) NOT NULL,
                                    PRIMARY KEY (`chatbot_category_seq`)
);

CREATE TABLE `chatbot` (
                           `chatbot_seq`   BIGINT   NOT NULL   AUTO_INCREMENT,
                           `chatbot_category_seq`  BIGINT  NOT NULL,
                           `chatbot_data`   VARCHAR(200)   NOT NULL,
                           PRIMARY KEY (`chatbot_seq`),
                           KEY `FK_chatbot_category_TO_chatbot_1`  (`chatbot_category_seq`),
                           CONSTRAINT `FK_chatbot_category_TO_chatbot_1` FOREIGN KEY (`chatbot_category_seq`) REFERENCES `chatbot_category` (`chatbot_category_seq`) ON DELETE CASCADE ON UPDATE CASCADE
);