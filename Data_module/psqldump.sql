
CREATE TABLE IF NOT EXISTS "auth_table" (
  "id" SERIAL PRIMARY KEY,
  "confirm_password" varchar(255) DEFAULT NULL,
  "createdon" varchar(255) DEFAULT NULL,
  "modified" varchar(255) DEFAULT NULL,
  "password" varchar(255) DEFAULT NULL,
  "roles" varchar(255) DEFAULT NULL,
  "username" varchar(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS "userdetails" (
  "detid" SERIAL PRIMARY KEY,
  "country" varchar(255) DEFAULT NULL,
  "createdon" timestamp(6) DEFAULT NULL,
  "designation" varchar(255) DEFAULT NULL,
  "email" varchar(255) DEFAULT NULL,
  "modified" timestamp(6) DEFAULT NULL,
  "state" varchar(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS "users" (
  "id" SERIAL PRIMARY KEY,
  "address" varchar(255) DEFAULT NULL,
  "age" varchar(255) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "createdon" timestamp(6) DEFAULT NULL,
  "modified" timestamp(6) DEFAULT NULL,
  "auid" int DEFAULT NULL,
  "detid" int DEFAULT NULL,
  "imgurl" varchar(255) DEFAULT NULL,
  UNIQUE ("auid"),
  UNIQUE ("detid"),
  CONSTRAINT "FKa2a6e5qmq5eop5ycqryl7ews1" FOREIGN KEY ("detid") REFERENCES "userdetails" ("detid"),
      CONSTRAINT "FKf7lpdrsl7m45bfb0wrtg2eif1" FOREIGN KEY ("auid") REFERENCES "auth_table" ("id")
  );


