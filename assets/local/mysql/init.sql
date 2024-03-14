CREATE TABLE IF NOT EXISTS `user` (
  id int(11) NOT NULL,
  username varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `follower_followed` (
    id_follower int(11) NOT NULL,
    id_followed int(11) NOT NULL,
    PRIMARY KEY (id_follower, id_followed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- insert 3 rows to users table if not exists (for testing)
INSERT IGNORE INTO `user` (id, username, email) VALUES
(1, 'John_Doe', 'john.doe@gmail.com'),
(2, 'Jane_Doe', 'jane.doe@gmail.com'),
(3, 'John_Smith', 'john.smith@gmail.com');

