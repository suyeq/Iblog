INSERT INTO user (id, username, password, name, email) VALUES (1, 'admin', '123', '溯夜', '473721601@qq.com');
INSERT INTO user (id, username, password, name, email)  VALUES (2, '刘继强', '１２３', '机枪学长', 'zzzz@qq.com');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
