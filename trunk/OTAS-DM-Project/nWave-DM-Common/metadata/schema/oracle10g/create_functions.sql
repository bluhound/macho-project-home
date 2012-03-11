CREATE OR REPLACE FUNCTION GETZHONGWEN(P_NAME IN VARCHAR2) RETURN VARCHAR2 AS
  V_COMPARE VARCHAR2(100);
  V_RETURN VARCHAR2(4000);
  v_name varchar2(20);
  FUNCTION F_NLSSORT(P_WORD IN VARCHAR2) RETURN VARCHAR2 AS
   BEGIN
     RETURN NLSSORT(P_WORD, 'NLS_SORT=SCHINESE_PINYIN_M');
   END;

BEGIN
	v_name := substr(P_NAME,0,1);
	if (v_name >= 'a' and v_name <='z') or (v_name >= 'A' and v_name <='Z') then
		RETURN NULL;
	else
  FOR I IN 1..NVL(LENGTH(P_NAME), 0) LOOP
   V_COMPARE := F_NLSSORT(SUBSTR(P_NAME, I, 1));
  IF V_COMPARE >= F_NLSSORT('߹') AND V_COMPARE <= F_NLSSORT('�') THEN
   V_RETURN := V_RETURN || 'A';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('��') THEN
   V_RETURN := V_RETURN || 'B';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�e') THEN
   V_RETURN := V_RETURN || 'C';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�z') THEN
   V_RETURN := V_RETURN || 'D';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('��') THEN
   V_RETURN := V_RETURN || 'E';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�g') THEN
   V_RETURN := V_RETURN || 'F';
  ELSIF V_COMPARE >= F_NLSSORT('�') AND V_COMPARE <= F_NLSSORT('�B') THEN
   V_RETURN := V_RETURN || 'G';
  ELSIF V_COMPARE >= F_NLSSORT('�o') AND V_COMPARE <= F_NLSSORT('��') THEN
   V_RETURN := V_RETURN || 'H';
  ELSIF V_COMPARE >= F_NLSSORT('آ') AND V_COMPARE <= F_NLSSORT('�h') THEN
   V_RETURN := V_RETURN || 'J';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�i') THEN
   V_RETURN := V_RETURN || 'K';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�^') THEN
   V_RETURN := V_RETURN || 'L';
  ELSIF V_COMPARE >= F_NLSSORT('�`') AND V_COMPARE <= F_NLSSORT('��') THEN
   V_RETURN := V_RETURN || 'M';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('��') THEN
   V_RETURN := V_RETURN || 'N';
  ELSIF V_COMPARE >= F_NLSSORT('�p') AND V_COMPARE <= F_NLSSORT('�a') THEN
   V_RETURN := V_RETURN || 'O';
  ELSIF V_COMPARE >= F_NLSSORT('�r') AND V_COMPARE <= F_NLSSORT('��') THEN
   V_RETURN := V_RETURN || 'P';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�d') THEN
   V_RETURN := V_RETURN || 'Q';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�U') THEN
   V_RETURN := V_RETURN || 'R';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�R') THEN
   V_RETURN := V_RETURN || 'S';
  ELSIF V_COMPARE >= F_NLSSORT('�@') AND V_COMPARE <= F_NLSSORT('�X') THEN
   V_RETURN := V_RETURN || 'T';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('�F') THEN
   V_RETURN := V_RETURN || 'W';
  ELSIF V_COMPARE >= F_NLSSORT('Ϧ') AND V_COMPARE <= F_NLSSORT('�R') THEN
   V_RETURN := V_RETURN || 'X';
  ELSIF V_COMPARE >= F_NLSSORT('Ѿ') AND V_COMPARE <= F_NLSSORT('�') THEN
   V_RETURN := V_RETURN || 'Y';
  ELSIF V_COMPARE >= F_NLSSORT('��') AND V_COMPARE <= F_NLSSORT('��') THEN
   V_RETURN := V_RETURN || 'Z';
  END IF;
 END LOOP;
 RETURN V_RETURN;
 end if;
END;
/
