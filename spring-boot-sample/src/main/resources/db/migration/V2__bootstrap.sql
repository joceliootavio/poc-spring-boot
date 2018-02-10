CREATE SEQUENCE sq_user INCREMENT 50 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE PUBLIC.USER(
  id bigint NOT NULL,
  login character varying(50) NOT NULL,
  name character varying(50) NOT NULL,
  password character varying(50) NOT NULL,
  email character varying(50) NOT NULL,  
  admin boolean NOT NULL,  
  created_date timestamp without time zone NOT NULL,  
  updated_date timestamp without time zone,
  CONSTRAINT user_pkey PRIMARY KEY (id),
  CONSTRAINT UK_USER_LOGIN UNIQUE (login)
);

INSERT INTO PUBLIC.USER(id, NAME, LOGIN, PASSWORD, EMAIL, ADMIN, CREATED_DATE) VALUES (NEXTVAL('PUBLIC.SQ_USER'), 'jossa araujo', 'jossa', '1234', 'jocelio.otavio@gmail.com', TRUE, NOW());