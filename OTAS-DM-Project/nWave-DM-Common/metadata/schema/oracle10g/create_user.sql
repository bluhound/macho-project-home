CREATE USER otasdm
    IDENTIFIED BY otasdm DEFAULT TABLESPACE OTASDM
    TEMPORARY TABLESPACE OTASDM_TMP
;

GRANT RESOURCE, CONNECT, DBA, CTXAPP TO otasdm;

--����ȫ�ļ���ʱ����Ҫ��otasdm�û�������������Ȩ��

grant execute on sys.dbms_job to otasdm;

grant execute on ctxsys.ctx_ddl to otasdm;


