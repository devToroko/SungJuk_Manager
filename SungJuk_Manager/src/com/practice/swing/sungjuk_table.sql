
-- 혹시라도 사용중이신 테이블이 sungjuk이시고 중요한 테이블이면 해당 테이블의 이름을 바꾸시는 게 좋을 겁니다.(트리거도 마찬가지)
drop table sungjuk;
drop trigger sungjuk_trg;

--성적 테이블 생성
create table sungjuk (
    ID varchar(6),
    NAME varchar(12) not null,
    GRADE number(1,0) not null,
    LANGUAGE NUMBER(3,0) default 0,
    ENGLISH NUMBER(3,0) default 0,
    MATH NUMBER(3,0) default 0,
    TOTAL NUMBER(3,0),
    AVG NUMBER(5,2),
    CONSTRAINT sungjuk_pk PRIMARY KEY(ID),
    CONSTRAINT grade_limit check(GRADE between 1 and 3),
    CONSTRAINT language_limit check(LANGUAGE between 0 and 100),
    CONSTRAINT english_limit check(ENGLISH between 0 and 100),
    CONSTRAINT math_limit check(MATH between 0 and 100)
);


--트리거 생성 ( 성적과 관련된 입력이나 수정이 일어날 때 다음과 같은 트리거가 발생합니다 ) 
create or replace trigger sungjuk_trg AFTER insert or update 
of language,math,english on sungjuk
BEGIN
UPDATE sungjuk set total = language+math+english, avg = (language+math+english)/3;
END;
/

--기본 데이터 입력
insert into sungjuk(id,name,grade,language,english,math) values('A0001','박모군',1,100,100,100);
insert into sungjuk(id,name,grade,language,english,math) values('A0002','김모군',1,100,100,100);
insert into sungjuk(id,name,grade,language,english,math) values('A0003','호로로',1,100,100,100);
insert into sungjuk(id,name,grade,language,english,math) values('A0004','고로케',3,97,90,100);
select * from sungjuk order by id;

--입력된 데이터 영구적으로 반영을 위한 commit
commit;
