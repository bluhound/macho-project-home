set a=%computername%
set b=%date:~0,4%%date:~5,2%%date:~8,2%
set c=%time%

ECHO start_time > "c:\checkresult.txt" 
hostname >> "c:\checkresult.txt" 
date /t >> "c:\checkresult.txt" 
time /t >> "c:\checkresult.txt" 

ECHO ��һ����:�ռ������������� >> "c:\checkresult.txt" 
ECHO ipconfig /all >> "c:\checkresult.txt" 
ipconfig /all >> "c:\checkresult.txt" 

ECHO �ڶ�����:��������������� >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt"  
ECHO www.icbc.com.cn >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
nslookup www.icbc.com.cn. >> "c:\checkresult.txt" 
nslookup www.icbc.com.cn. >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO mybank.icbc.com.cn >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
nslookup mybank.icbc.com.cn. >> "c:\checkresult.txt" 
nslookup mybank.icbc.com.cn. >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO www.icbc-ltd.com >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
nslookup www.icbc-ltd.com. >> "c:\checkresult.txt" 
nslookup www.icbc-ltd.com. >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO b2c.icbc.com.cn >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
nslookup b2c.icbc.com.cn. >> "c:\checkresult.txt" 
nslookup b2c.icbc.com.cn. >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO vip.icbc.com.cn >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
nslookup vip.icbc.com.cn. >> "c:\checkresult.txt" 
nslookup vip.icbc.com.cn. >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO corporbank.icbc.com.cn >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
nslookup corporbank.icbc.com.cn. >> "c:\checkresult.txt" 
nslookup corporbank.icbc.com.cn. >> "c:\checkresult.txt" 

ECHO ��������:������ʱ������� >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt"  
ECHO ICBC-CNC >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt"  
ping 202.108.88.62 -n 50 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt"  
ECHO ICBC-CHINANET >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt"  
ping 219.142.91.181 -n 50 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-SH-UNI >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ping 211.95.81.60 -n 50 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-SH-TEL >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ping 61.129.61.181 -n 50 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-BJ-TEL2 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ping 60.247.99.181 -n 50 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-BJ-CNC2 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ping 123.127.121.180 -n 50 >> "c:\checkresult.txt" 

ECHO ���Ĳ���:����·�ɲ��� >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-CNC >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
tracert -d 202.108.88.62 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-CHINANET >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
tracert -d 219.142.91.181 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-SH-UNI >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
tracert -d 211.95.81.60 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-SH-TEL >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
tracert -d 61.129.61.181 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-BJ-TEL2 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
tracert -d 60.247.99.181 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ICBC-BJ-CNC2 >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
tracert -d 123.127.121.180 >> "c:\checkresult.txt" 

ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO end_time >> "c:\checkresult.txt"
hostname >> "c:\checkresult.txt"  
date /t >> "c:\checkresult.txt" 
time /t >> "c:\checkresult.txt" 

ECHO ---------------------- >> "c:\checkresult.txt" 
ECHO ---------------------- >> "c:\checkresult.txt" 