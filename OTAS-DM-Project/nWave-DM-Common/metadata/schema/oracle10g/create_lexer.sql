/*==============================================================*/
-- ɾ��"otas_dm_portal_lexer"�ʷ�������
/*==============================================================*/
exec ctx_ddl.drop_preference ('otas_dm_portal_lexer');
/*==============================================================*/
--ɾ����������
/*==============================================================*/
drop index IDX_FT_SOFT_NAME;

drop index IDX_FT_SOFT_DESC;

drop index IDX_FT_VENDOR_NAME;

drop index IDX_FT_CATEGORY_NAME;

/*==============================================================*/
--����"BASIC_LEXER"ģʽ����Ϊ"otas_dm_portal_lexer"�ʷ�������
/*==============================================================*/

exec ctx_ddl.create_preference ('otas_dm_portal_lexer', 'BASIC_LEXER');

/*==============================================================*/
-- ��"NW_DM_SOFTWARE(NAME)"��"NW_DM_SOFTWARE(DESCRIPTION)"��
-- "NW_DM_SOFTWARE_VENDOR(NAME)"��"NW_DM_SOFTWARE_CATEGORY(NAME)"
-- ���Ͻ�������ʷ���������
/*==============================================================*/

create index IDX_FT_SOFT_NAME on NW_DM_SOFTWARE(NAME) 
       indextype is ctxsys.context parameters('lexer otas_dm_portal_lexer');

create index IDX_FT_SOFT_DESC on NW_DM_SOFTWARE(DESCRIPTION) 
       indextype is ctxsys.context parameters('lexer otas_dm_portal_lexer');

create index IDX_FT_VENDOR_NAME on NW_DM_SOFTWARE_VENDOR(NAME) 
       indextype is ctxsys.context parameters('lexer otas_dm_portal_lexer');

create index IDX_FT_CATEGORY_NAME on NW_DM_SOFTWARE_CATEGORY(NAME) 
       indextype is ctxsys.context parameters('lexer otas_dm_portal_lexer');

/*==============================================================*/
-- ��������������������������ű����insert,update,deleteʱ
-- ȫ�ļ����Ǽ��������²����ļ�¼�ģ�����Ҫ������������
/*==============================================================*/
-- ͬ������
EXEC CTX_DDL.SYNC_INDEX('IDX_FT_SOFT_NAME', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_FT_SOFT_DESC', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_FT_VENDOR_NAME', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_FT_CATEGORY_NAME', '2M');

-- �Ż�����
exec ctx_ddl.optimize_index ('IDX_FT_SOFT_NAME', 'full');

exec ctx_ddl.optimize_index ('IDX_FT_SOFT_DESC', 'full');

exec ctx_ddl.optimize_index ('IDX_FT_VENDOR_NAME', 'full');

exec ctx_ddl.optimize_index ('IDX_FT_CATEGORY_NAME', 'full');

/*==============================================================*/
-- �����������������������Զ�ִ�У�Ҫͨ��oracle��jobs���Զ�ÿ���೤ʱ�����У�
-- �������£�
-- ͨ��jobs�Զ�ִ��ͬ������: 
/*==============================================================*/

-- ����ͬ��job
DECLARE 
job1 number; 

--�ύͬ��job1ÿ��15����ͬ��

BEGIN 

DBMS_JOB.SUBMIT(:job1,'CTX_DDL.SYNC_INDEX(''IDX_FT_SOFT_NAME'', ''2M'');', SYSDATE, 'SYSDATE + (1/24/4)'); 
DBMS_JOB.run(:job1);
DBMS_JOB.SUBMIT(:job1,'CTX_DDL.SYNC_INDEX(''IDX_FT_SOFT_DESC'', ''2M'');', SYSDATE, 'SYSDATE + (1/24/4)'); 
DBMS_JOB.run(:job1);
DBMS_JOB.SUBMIT(:job1,'CTX_DDL.SYNC_INDEX(''IDX_FT_VENDOR_NAME'', ''2M'');', SYSDATE, 'SYSDATE + (1/24/4)'); 
DBMS_JOB.run(:job1);
DBMS_JOB.SUBMIT(:job1,'CTX_DDL.SYNC_INDEX(''IDX_FT_CATEGORY_NAME'', ''2M'');', SYSDATE, 'SYSDATE + (1/24/4)'); 
DBMS_JOB.run(:job1);

commit; 

END; 
/

-- ͨ��jobs�Զ�ִ���Ż�����:  

DECLARE
job2 number; --�����Ż�job

--�ύͬ��job��ÿ��1���Ż�

BEGIN 

DBMS_JOB.SUBMIT(:job2,'ctx_ddl.optimize_index (''IDX_FT_SOFT_NAME'', ''full'');', SYSDATE, 'SYSDATE + 1'); 
DBMS_JOB.run(:job2);
DBMS_JOB.SUBMIT(:job2,'ctx_ddl.optimize_index (''IDX_FT_SOFT_DESC'', ''full'');', SYSDATE, 'SYSDATE + 1'); 
DBMS_JOB.run(:job2);
DBMS_JOB.SUBMIT(:job2,'ctx_ddl.optimize_index (''IDX_FT_VENDOR_NAME'', ''full'');', SYSDATE, 'SYSDATE + 1'); 
DBMS_JOB.run(:job2);
DBMS_JOB.SUBMIT(:job2,'ctx_ddl.optimize_index (''IDX_FT_CATEGORY_NAME'', ''full'');', SYSDATE, 'SYSDATE + 1'); 
DBMS_JOB.run(:job2);

commit; 

END;
/


