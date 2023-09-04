-- Adminer 4.8.1 PostgreSQL 15.3 (Debian 15.3-1.pgdg120+1) dump

\connect
"clever-bank";

CREATE SEQUENCE account_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."account"
(
    "id"      bigint DEFAULT nextval('account_id_seq') NOT NULL,
    "bank_id" bigint                                   NOT NULL,
    "user_id" bigint                                   NOT NULL,
    "balance" numeric                                  NOT NULL,
    "number"  bigint,
    CONSTRAINT "account_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "account" ("id", "bank_id", "user_id", "balance", "number")
VALUES (1, 1, 1, 777, NULL),
       (2, 1, 2, 555, NULL),
       (3, 3, 1, 111, NULL);

CREATE SEQUENCE bank_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."bank"
(
    "id"   bigint DEFAULT nextval('bank_id_seq') NOT NULL,
    "name" text                                  NOT NULL,
    CONSTRAINT "bank_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "bank" ("id", "name")
VALUES (1, 'bank_1
'),
       (2, 'bank_2'),
       (3, 'bank_3');

CREATE SEQUENCE user_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE TABLE "public"."client"
(
    "id"   integer DEFAULT nextval('user_id_seq') NOT NULL,
    "name" text                                   NOT NULL,
    CONSTRAINT "user_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "client" ("id", "name")
VALUES (1, 'user_1'),
       (2, 'user_2'),
       (3, 'user_3');

CREATE SEQUENCE transaction_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."transaction"
(
    "id"                   bigint DEFAULT nextval('transaction_id_seq') NOT NULL,
    "t_type"                 text                                         NOT NULL,
    "bank_id"              bigint                                       NOT NULL,
    "sender_account_id"    bigint                                       NOT NULL,
    "recipient_account_id" bigint,
    "date_transaction"     date                                         NOT NULL,
    "sum"                  numeric                                      NOT NULL,
    CONSTRAINT "transaction_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "transaction" ("id", "t_type", "bank_id", "sender_account_id", "recipient_account_id", "date_transaction",
                           "sum")
VALUES (1, 'A', 1, 1, NULL, '2023-09-03', 100),
       (4, 'A', 3, 3, NULL, '2023-09-03', 70),
       (3, 'P', 2, 3, 1, '2023-09-03', 50),
       (2, 'R', 1, 2, NULL, '2023-09-03', 200);

-- 2023-09-03 19:41:51.018738+00