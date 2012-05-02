CREATE TABLESPACE OTASDM
    LOGGING
    DATAFILE 'C:\oracle\product\10.2.0\oradata\orcl\OTASDM.ora' SIZE 10M EXTENT
    MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT  AUTO
;

ALTER DATABASE DATAFILE 'C:\oracle\product\10.2.0\oradata\orcl\OTASDM.ora' AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED;

CREATE
    TEMPORARY TABLESPACE OTASDM_TMP TEMPFILE
    'C:\oracle\product\10.2.0\oradata\orcl\OTASDM_TMP.ora' SIZE 10M EXTENT
    MANAGEMENT LOCAL UNIFORM SIZE 1M
;

ALTER DATABASE TEMPFILE 'C:\oracle\product\10.2.0\oradata\orcl\OTASDM_TMP.ora' AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED;

