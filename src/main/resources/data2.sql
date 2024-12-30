-- Department 데이터 삽입 (완)
INSERT INTO department (department_seq, department_name)
VALUES (1, '교육팀'),
       (2, '영업팀'),
       (3, '총무팀'),
       (4, '인사팀'),
       (5, '개발팀'),
       (6, '마케팅팀')
;

-- Quiz Category 데이터 삽입 (완)
INSERT INTO quiz_category (quiz_category_name, reg_date)
VALUES
    ('공통 교육 퀴즈', NOW()),
    ('직무 교육 퀴즈', NOW());

-- Position 데이터 삽입
INSERT INTO positions (position_seq, position_name)
VALUES
    (1, '팀장'),
    (2, '차장'),
    (3, '과장'),
    (4, '대리'),
    (5, '주임'),
    (6, '사원'),
    (7, '인턴');

-- Employee 데이터 삽입 (완)
INSERT INTO employee (employee_seq, department_seq, position_seq, employee_num, employee_name, employee_phone, employee_email, employee_role, employee_password, reg_date)
VALUES
    (10,4, 3, 'E010', '담당자', '010-1234-1234', 'hrmanager@company.com', 'HR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),

    (11,1, 1, 'E011', '팀장1', '010-0004-0001', 'leader1@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (12, 2, 1, 'E012', '팀장2', '010-0004-0002', 'leader2@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (13, 3, 1, 'E013', '팀장3', '010-0004-0003', 'leader3@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (14, 4, 1, 'E014', '팀장4', '010-0004-0004', 'leader4@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (15, 5, 1, 'E015', '팀장5', '010-0004-0005', 'leader5@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (16, 6, 1, 'E016', '팀장6', '010-0004-0006', 'leader6@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),

    (17, 1, 4, 'E026', '대리1', '010-0003-0001', 'mentor1@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (18, 1, 4, 'E027', '대리2', '010-0003-0002', 'mentor2@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (19, 2, 4, 'E028', '대리3', '010-0003-0003', 'mentor3@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (20, 2, 4, 'E029', '대리4', '010-0003-0004', 'mentor4@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (21, 3, 4, 'E030', '대리5', '010-0003-0005', 'mentor5@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (22, 3, 4, 'E031', '대리6', '010-0003-0006', 'mentor6@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (23, 4, 4, 'E032', '대리7', '010-0003-0007', 'mentor7@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (24, 4, 4, 'E033', '대리8', '010-0003-0008', 'mentor8@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (25, 5, 4, 'E034', '대리9', '010-0003-0009', 'mentor9@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (26, 5, 4, 'E035', '대리10', '010-0003-0010', 'mentor10@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (27, 6, 4, 'E036', '대리11', '010-0003-0011', 'mentor11@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (28, 6, 4, 'E037', '대리12', '010-0003-0012', 'mentor12@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),

    (29, 1, 6, 'E038', '사원1', '010-0002-0001', 'employee1@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (30, 1, 6, 'E039', '사원2', '010-0002-0002', 'employee2@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (31, 2, 6, 'E040', '사원3', '010-0002-0003', 'employee3@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (32, 2, 6, 'E041', '사원4', '010-0002-0004', 'employee4@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (33, 3, 6, 'E042', '사원5', '010-0002-0005', 'employee5@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (34, 3, 6, 'E043', '사원6', '010-0002-0006', 'employee6@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (35, 4, 6, 'E044', '사원7', '010-0002-0007', 'employee7@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (36, 4, 6, 'E045', '사원8', '010-0002-0008', 'employee8@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (37, 5, 6, 'E046', '사원9', '010-0002-0009', 'employee9@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (38, 5, 6, 'E047', '사원10', '010-0002-0010', 'employee10@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (39, 6, 6, 'E048', '사원11', '010-0002-0011', 'employee11@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (40, 6, 6, 'E049', '사원11', '010-0002-0012', 'employee12@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),

    (41, 1, 7, 'E050', '인턴1', '010-0001-0001', 'mentee1@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (42, 1, 7, 'E051', '인턴2', '010-0001-0002', 'mentee2@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (43, 2, 7, 'E052', '인턴3', '010-0001-0003', 'mentee3@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (44, 2, 7, 'E053', '인턴4', '010-0001-0004', 'mentee4@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (45, 3, 7, 'E054', '인턴5', '010-0001-0005', 'mentee5@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (46, 3, 7, 'E055', '인턴6', '010-0001-0006', 'mentee6@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (47, 4, 7, 'E056', '인턴7', '010-0001-0007', 'mentee7@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (48, 4, 7, 'E057', '인턴8', '010-0001-0008', 'mentee8@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (49, 5, 7, 'E058', '인턴9', '010-0001-0009', 'mentee9@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (50, 5, 7, 'E059', '인턴10', '010-0001-0010', 'mentee10@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (51, 6, 7, 'E060', '인턴11', '010-0001-0011', 'mentee11@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (52, 6, 7, 'E061', '인턴12', '010-0001-0012', 'mentee12@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW())
;

-- Mentoring 데이터 삽입
INSERT INTO mentoring (mentoring_seq, mentor_seq, mentee_seq, chat_room_seq, reg_date)
VALUES
    (10,17, 41, 10, NOW()),
    (11,19, 43, 11, NOW()),
    (12,21, 45, 12, NOW()),
    (13,23, 47, 13, NOW()),
    (14,25, 49, 14, NOW()),
    (15,27, 51, 15, NOW())
;

-- Template 추가 데이터 삽입
INSERT INTO template (template_seq, template_type, template_check_required_status, template_training_type, template_title, template_sub, template_detail, template_url_name, template_task_round, template_procedure, reg_date)
VALUES
    -- 직무교육 템플릿
    (3, 'JOB', TRUE, '직무교육', '개발자 기초 교육', '프로그래밍 기초 학습', '개발 기초 교육 과정입니다', 'https://example.com/dev/basic','4주차', 3, NOW()),
    (4, 'JOB', TRUE, '직무교육', 'SQL 심화 과정', 'DB 실무 역량 강화', 'SQL 심화 학습 과정입니다', 'https://example.com/dev/sql','3주차', 4, NOW()),
    (5, 'JOB', TRUE, '직무교육', '영업 실무 교육', '영업 프로세스 이해', '영업팀 실무 교육입니다', 'https://example.com/sales/basic','4주차', 5, NOW()),

    -- 일반 공통교육 템플릿
    (6, 'NORMAL', TRUE, '공통교육', '신입 비즈니스 매너', '비즈니스 매너 학습', '기업 예절 교육입니다', 'https://example.com/common/manner','2주차', 6, NOW()),
    (7, 'NORMAL', FALSE, '공통교육', '정보보안 기초', '보안 의식 강화 교육', '전사 보안 교육입니다', 'https://example.com/common/security','1주차', 7, NOW()),

    -- 휴식/팀빌딩 템플릿
    (8, 'BREAK', FALSE, '공통교육', '팀 커뮤니케이션 데이', '팀 단합을 위한 활동', '팀워크 강화 프로그램입니다', 'https://example.com/break/teamday','1주차', 8, NOW()),
    (9, 'BREAK', FALSE, '공통교육', '부서 문화의 날', '부서별 문화 활동', '부서 단합 프로그램입니다', 'https://example.com/break/culture','1주차', 9, NOW()),

    -- 체크리스트 템플릿
    (10, 'CHECKLIST', TRUE, '공통교육', '신입사원 온보딩 체크리스트', '입사 후 필수 확인사항', '온보딩 필수 체크리스트입니다', 'https://example.com/checklist/onboarding','1주차', 10, NOW()),
    (11, 'CHECKLIST', TRUE, '직무교육', '개발환경 셋업 체크리스트', '개발환경 구성 가이드', '개발환경 설정 체크리스트입니다', 'https://example.com/checklist/devsetup','1주차', 11, NOW()),

    -- 동영상 교육 템플릿
    (12, 'VIDEO', TRUE, '공통교육', '회사 소개 동영상', '회사 역사와 문화', '회사 소개 영상 교육입니다', 'https://example.com/video/company','1주차', 12, NOW()),
    (13, 'VIDEO', TRUE, '직무교육', '신기술 동향 강의', 'IT 트렌드 교육', '최신 기술 동향 교육입니다', 'https://example.com/video/tech','2주차', 13, NOW()),

    -- 퀴즈 템플릿
    (14, 'QUIZ', TRUE, '공통교육', '정보보안 퀴즈', '보안 정책 이해도 테스트', '보안 교육 평가입니다', 'https://example.com/quiz/security','1주차', 14, NOW()),
    (15, 'QUIZ', TRUE, '직무교육', '프로그래밍 기초 퀴즈', '개발 지식 테스트', '개발 기초 평가입니다', 'https://example.com/quiz/programming','2주차', 15, NOW());

-- Checklist 데이터 수정 (CHECKLIST 타입 템플릿에만 연결)
INSERT INTO checklist (checklist_seq, checklist_content, template_seq)
VALUES
    -- 신입사원 온보딩 체크리스트 (template_seq: 10)
    (4, '사내 시스템 계정 생성 완료', 10),
    (5, '복리후생 안내 확인', 10),
    (6, '사내 규정 확인', 10),
    (7, '부서 소개 미팅 참석', 10),
    (8, '필수 교육 이수 확인', 10),

    -- 개발환경 셋업 체크리스트 (template_seq: 11)
    (9, 'IDE 설치 완료', 11),
    (10, '형상관리 도구 설정', 11),
    (11, '로컬 개발환경 구성', 11),
    (12, '프로젝트 빌드 테스트', 11),
    (13, '코드 컨벤션 문서 확인', 11),
    (14, '협업 도구 설정 완료', 11);

-- Task 데이터 수정 (직무/공통 구분 명확히)
INSERT INTO task (task_seq, department_seq, template_seq, task_type, task_title, task_content, reg_date)
VALUES
    (3, 5, 3, 'GROUP', '개발팀 자바 프로그래밍 과제', '기초 알고리즘 구현하기', NOW()),
    (4, 5, 4, 'GROUP', '개발팀 DB 설계 과제', 'ERD 설계 및 쿼리 작성', NOW()),
    (5, 2, 5, 'GROUP', '영업팀 제안서 작성', '제품 제안서 작성 실습', NOW()),
    (6, 1, 6, 'GROUP', '비즈니스 매너 팀 프로젝트', '비즈니스 커뮤니케이션 실습', NOW()),
    (7, 1, 7, 'GROUP', '정보보안 팀 프로젝트', '보안 정책 수립 실습', NOW()),
    (8, 1, 8, 'GROUP', '팀 빌딩 프로젝트', '크로스펑셔널 팀 운영 실습', NOW());

-- Task Group 데이터 수정
INSERT INTO task_group (task_group_seq, task_seq, task_group_active_status, task_group_num, chat_room_seq, reg_date)
VALUES
    -- 직무 교육 과제 그룹
    (3, 3, TRUE, 3, 16, NOW()), -- 개발팀 자바 과제 그룹
    (4, 4, TRUE, 4, 17, NOW()), -- 개발팀 DB 과제 그룹
    (5, 5, TRUE, 5, 18, NOW()), -- 영업팀 제안서 과제 그룹
    -- 공통 교육 과제 그룹
    (6, 6, TRUE, 6, 19, NOW()), -- 비즈니스 매너 공통 그룹
    (7, 7, TRUE, 7, 20, NOW()), -- 정보보안 공통 그룹
    (8, 8, TRUE, 8, 21, NOW()); -- 팀 빌딩 공통 그룹

-- Group Member 데이터 수정
INSERT INTO group_member (group_member_seq, task_group_seq, employee_seq)
VALUES
    -- 개발팀 자바 과제 그룹 (같은 부서)
    (5, 3, 37), -- 개발팀 사원9
    (6, 3, 38), -- 개발팀 사원10
    (7, 3, 49), -- 개발팀 인턴9
    (8, 3, 50), -- 개발팀 인턴10

    -- 개발팀 DB 과제 그룹 (같은 부서)
    (9, 4, 25),  -- 개발팀 대리9
    (10, 4, 26), -- 개발팀 대리10
    (11, 4, 37), -- 개발팀 사원9
    (12, 4, 38), -- 개발팀 사원10

    -- 영업팀 제안서 과제 그룹 (같은 부서)
    (13, 5, 19), -- 영업팀 대리3
    (14, 5, 20), -- 영업팀 대리4
    (15, 5, 31), -- 영업팀 사원3
    (16, 5, 32), -- 영업팀 사원4

    -- 비즈니스 매너 공통 그룹 (다른 부서)
    (17, 6, 29), -- 교육팀 사원1
    (18, 6, 31), -- 영업팀 사원3
    (19, 6, 33), -- 총무팀 사원5
    (20, 6, 35), -- 인사팀 사원7

    -- 정보보안 공통 그룹 (다른 부서)
    (21, 7, 37), -- 개발팀 사원9
    (22, 7, 39), -- 마케팅팀 사원11
    (23, 7, 41), -- 교육팀 인턴1
    (24, 7, 43), -- 영업팀 인턴3

    -- 팀 빌딩 공통 그룹 (다른 부서)
    (25, 8, 45), -- 총무팀 인턴5
    (26, 8, 47), -- 인사팀 인턴7
    (27, 8, 49), -- 개발팀 인턴9
    (28, 8, 51); -- 마케팅팀 인턴11

-- Quiz 데이터 - 변경 없음 (문제 자체는 유지)
INSERT INTO quiz (quiz_seq, quiz_category_seq, quiz_question, quiz_answer, quiz_explanation, reg_date)
VALUES
    -- 공통 교육 퀴즈
    (5, 1, '회사 내부에서 개인 USB 사용이 허용되나요?', FALSE, '보안을 위해 승인된 저장장치만 사용 가능합니다.', NOW()),
    (6, 1, '사내 메신저로 업무 자료 공유시 암호화가 필요한가요?', TRUE, '기밀문서는 반드시 암호화가 필요합니다.', NOW()),
    (7, 1, '재택근무 시 공용 Wi-Fi 사용이 가능한가요?', FALSE, '보안상의 이유로 공용 Wi-Fi 사용은 금지됩니다.', NOW()),
    (8, 1, '회사 계정의 비밀번호는 분기별로 변경해야 하나요?', TRUE, '정보보안 정책에 따라 분기별 변경이 필수입니다.', NOW()),
    (9, 1, '외부 미팅 시 노트북 보안케이블 사용은 필수인가요?', TRUE, '도난 방지를 위해 보안케이블 사용은 필수입니다.', NOW()),
    -- 직무 교육 퀴즈
    (10, 2, 'Java의 기본 데이터 타입 중 정수형은 byte, short, int, long이다.', TRUE, '자바의 정수형 기본 타입은 크기별로 4가지가 있습니다.', NOW()),
    (11, 2, 'Spring Framework에서 @Autowired는 생성자 주입만 지원한다.', FALSE, '@Autowired는 생성자, 필드, 세터 주입을 모두 지원합니다.', NOW()),
    (12, 2, 'REST API에서 PUT 메서드는 멱등성을 가진다.', TRUE, 'PUT은 같은 요청을 여러 번 보내도 결과가 동일합니다.', NOW()),
    (13, 2, 'JPA의 즉시로딩(EAGER)이 항상 성능상 유리하다.', FALSE, '상황에 따라 지연로딩(LAZY)이 더 유리할 수 있습니다.', NOW()),
    (14, 2, 'Docker 컨테이너는 반드시 하나의 프로세스만 실행해야 한다.', FALSE, '여러 프로세스 실행이 가능하나, 하나를 권장합니다.', NOW());

-- Quiz Result 데이터 (멘토링 지정된 mentee만)
INSERT INTO quiz_result (quiz_seq, employee_seq, quiz_correct_status, reg_date)
VALUES
    -- mentee 41 (교육팀 인턴1)
    (5, 41, TRUE, NOW()),
    (6, 41, TRUE, NOW()),
    (7, 41, FALSE, NOW()),
    (8, 41, TRUE, NOW()),
    -- mentee 43 (영업팀 인턴3)
    (5, 43, TRUE, NOW()),
    (6, 43, TRUE, NOW()),
    (7, 43, TRUE, NOW()),
    (8, 43, FALSE, NOW()),
    -- mentee 45 (총무팀 인턴5)
    (5, 45, TRUE, NOW()),
    (6, 45, FALSE, NOW()),
    (7, 45, TRUE, NOW()),
    -- mentee 47 (인사팀 인턴7)
    (5, 47, TRUE, NOW()),
    (6, 47, TRUE, NOW()),
    (7, 47, FALSE, NOW()),
    -- mentee 49 (개발팀 인턴9)
    (10, 49, TRUE, NOW()),
    (11, 49, TRUE, NOW()),
    (12, 49, FALSE, NOW()),
    -- mentee 51 (마케팅팀 인턴11)
    (5, 51, TRUE, NOW()),
    (6, 51, FALSE, NOW()),
    (7, 51, TRUE, NOW());

-- Task Submit 데이터 (멘토링 관계 있는 사원만)
INSERT INTO task_submit (task_submit_seq, task_seq, employee_seq, task_submit_content, reg_date)
VALUES
    (3, 3, 49, '알고리즘 구현 과제 제출합니다. 피드백 부탁드립니다.', NOW()),
    (5, 4, 25, 'DB 설계 ERD 및 쿼리문 제출합니다.', NOW()),
    (7, 5, 19, '제품 제안서 초안 제출합니다.', NOW()),
    (9, 6, 41, '비즈니스 매너 실습 결과 제출합니다.', NOW());

-- Eval Ind (평가 지표) - 변경 없음
INSERT INTO eval_ind (eval_ind_seq, eval_ind_content, eval_ind_score, reg_date)
VALUES
    (1, '과제 이해도', 30, NOW()),
    (2, '기술 숙련도', 40, NOW()),
    (3, '커뮤니케이션', 30, NOW());

-- Eval List (평가 항목) - 변경 없음
INSERT INTO eval_list (eval_list_seq, task_seq, eval_ind_seq, eval_list_content, eval_list_score, reg_date)
VALUES
    (1, 3, 1, '알고리즘 이해도', 30, NOW()),
    (2, 3, 2, '코드 품질', 40, NOW()),
    (3, 3, 3, '팀 협업도', 30, NOW());

-- Task Eval 데이터 (멘토링 관계 있는 사원의 과제만)
INSERT INTO task_eval (task_eval_seq, eval_list_seq, task_submit_seq, task_score, reg_date)
VALUES
    (4, 1, 3, 28, NOW()),
    (5, 2, 3, 32, NOW()),
    (6, 3, 3, 27, NOW()),
    (7, 1, 5, 29, NOW()),
    (8, 2, 5, 37, NOW()),
    (9, 3, 5, 30, NOW());

-- Final Eval Ind - 변경 없음
INSERT INTO final_eval_ind (final_eval_ind_seq, final_eval_ind_name, final_eval_ind_weight)
VALUES
    (1, '업무 성과', 40),
    (2, '역량 개발', 30),
    (3, '태도', 30);

-- Final Eval 데이터 (멘토링 있는 mentee만)
INSERT INTO final_eval (final_eval_seq, employee_seq, final_eval_ind_seq, employee_score, reg_date)
VALUES
    -- mentee 41 평가
    (1, 41, 1, 85.5, NOW()),
    (2, 41, 2, 88.0, NOW()),
    (3, 41, 3, 90.0, NOW()),
    -- mentee 43 평가
    (4, 43, 1, 87.5, NOW()),
    (5, 43, 2, 84.0, NOW()),
    (6, 43, 3, 86.0, NOW());

-- Peer Review List - 변경 없음
INSERT INTO peer_review_list (peer_review_list_seq, peer_review_list_content, peer_review_list_score, reg_date)
VALUES
    (1, '팀워크 및 협업 능력', 5, NOW()),
    (2, '의사소통 능력', 5, NOW()),
    (3, '업무 처리 능력', 5, NOW());

-- Peer Review 데이터 (멘토링 관계 있는 사원들 간의 평가만)
INSERT INTO peer_review (peer_review_seq, peer_review_list_seq, reviewer_seq, reviewee_seq, peer_review_score, reg_date)
VALUES
    -- 개발팀 mentor-mentee 간 평가
    (1, 1, 25, 49, 4, NOW()),
    (2, 2, 25, 49, 5, NOW()),
    (3, 3, 25, 49, 4, NOW()),
    -- 영업팀 mentor-mentee 간 평가
    (4, 1, 19, 43, 5, NOW()),
    (5, 2, 19, 43, 4, NOW()),
    (6, 3, 19, 43, 5, NOW());

-- Wiki 데이터 - 변경 없음
INSERT INTO wiki (wiki_seq, wiki_title, wiki_current_ver, reg_date)
VALUES
    (3, '프로젝트 코딩 컨벤션', 1, NOW()),
    (4, '배포 프로세스 가이드', 1, NOW()),
    (5, '신규 입사자 업무 가이드', 2, NOW()),
    (6, '재택근무 보안 가이드', 1, NOW());

-- Wiki Snapshot 데이터 - 변경 없음
INSERT INTO wiki_snapshot (wiki_snapshot_seq, wiki_seq, wiki_snapshot_ver, wiki_snapshot_content, reg_date)
VALUES
    (3, 3, 1, '프로젝트의 코딩 스타일 가이드입니다.', NOW()),
    (4, 4, 1, '서비스 배포 절차를 안내합니다.', NOW()),
    (5, 5, 1, '신규 입사자를 위한 기본 업무 가이드 버전 1', NOW()),
    (6, 5, 2, '신규 입사자를 위한 기본 업무 가이드 버전 2 - 업데이트', NOW()),
    (7, 6, 1, '재택근무 시 보안 유의사항 안내', NOW());

-- Wiki Mod Content 데이터 (멘토 위주로 수정)
INSERT INTO wiki_mod_content (wiki_mod_content_seq, wiki_seq, employee_seq, wiki_snapshot_seq, mod_content, reg_date)
VALUES
    (3, 3, 25, 3, '{"type": "create", "content": "코딩 컨벤션 최초 작성"}', NOW()),
    (4, 4, 25, 4, '{"type": "create", "content": "배포 프로세스 가이드 작성"}', NOW()),
    (5, 5, 17, 5, '{"type": "create", "content": "신규 입사자 가이드 최초 작성"}', NOW()),
    (6, 5, 17, 6, '{"type": "update", "content": "신규 입사자 가이드 개정"}', NOW()),
    (7, 6, 19, 7, '{"type": "create", "content": "재택근무 보안 가이드 작성"}', NOW());
