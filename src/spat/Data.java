package spat;

public interface Data {
	String MCTLSQL1 = "��������##select formatdatetime(MSGDEALSTIME, 'yyyy-MM-dd HH:00') as ʱ���,count(*) as ��������,PROCERRMESG as ҵ�������� from TABMCTL group by ʱ���,ҵ��������";
	String MCTLSQL2 = "���������͵ı����ܱ���##select formatdatetime(MSGDEALSTIME, 'yyyy-MM-dd HH:00') as ʱ���,MSGTYPE as ��������,count(*) as ��������,PROCERRMESG as ҵ�������� from TABMCTL group by ʱ���,��������,ҵ��������";
	String MCTLSQL3 = "��λʱ�������н�����##select formatdatetime(MSGDEALSTIME, 'yyyy-MM-dd HH:00') as ʱ���,count(distinct(PID)) as ������ from TABMCTL group by ʱ���";
	String MCTLSQL4 = "��λʱ���ڵ����̴����������##select formatdatetime(MSGDEALSTIME, 'yyyy-MM-dd HH:00') as ʱ���,PID as���̺�,count(*) as �������� from TABMCTL group by ʱ���,PID";
	String MCTLSQL5 = "����ƽ���ȴ�����ʱ��##select formatdatetime(MSGDEALSTIME, 'yyyy-MM-dd HH:00') as ʱ���,avg(MSGWAITTIME) as ƽ���ȴ�ʱ�� from TABMCTL group by ʱ���";
	String MCTLSQL6 = "����������ͳ��ƽ���ȴ�����ʱ��##select formatdatetime(MSGDEALSTIME, 'yyyy-MM-dd HH:00') as ʱ���,MSGTYPE as ��������,avg(MSGWAITTIME) as ƽ���ȴ�ʱ�� from TABMCTL group by ʱ���,��������";
	String MCTLSQL7 = "����ƽ������ʱ��##select formatdatetime(MSGDEALSTIME, 'yyyy-MM-dd HH:00') as ʱ���,avg(MSGDEALTIME) as ƽ������ʱ�� from TABMCTL group by ʱ���";
	String MCTLSQL8 = "����������ͳ�Ʊ���ƽ������ʱ��##select formatdatetime(MSGDEALSTIME, 'yyyy-MM-dd HH:00') as ʱ���,MSGTYPE as ��������,avg(MSGDEALTIME) as ƽ������ʱ�� from TABMCTL group by ʱ���,��������";

	String PMTSNPCSQL1 = "��������##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as ʱ���,count(*) as �������� from TABPMTSNPC group by ʱ���";
	String PMTSNPCSQL2 = "���������͵ı����ܱ���##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as ʱ���,MSGTYPE as ��������,count(*) as �������� from TABPMTSNPC group by ʱ���,MSGTYPE";
	String PMTSNPCSQL3 = "��λʱ�������н�����##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as ʱ���,count(distinct(PID)) as ������ from TABPMTSNPC group by ʱ���";
	String PMTSNPCSQL4 = "��λʱ���ڵ����̴����������##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as ʱ���,PID as ���̺�,count(*) as �������� from TABPMTSNPC group by ʱ���,���̺�";
	String PMTSNPCSQL5 = "����ƽ������ʱ��##select TU.MSGTIME,TU.����ƽ������ʱ��,TD.����ƽ������ʱ�� from (select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,avg(MSGFWTIME) as ����ƽ������ʱ�� from TABPMTSNPC where MSGDIRECTION = 'U' group by MSGTIME)TU,(select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,avg(MSGDEALTIME) as ����ƽ������ʱ�� from TABPMTSNPC where MSGDIRECTION = 'D' group by MSGTIME)TD where TU.MSGTIME = TD.MSGTIME";
	String PMTSNPCSQL6 = "���ϱ���ƽ������ʱ��##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,avg(MSGFWTIME) as ����ƽ������ʱ�� from TABPMTSNPC where MSGDIRECTION = 'U' group by MSGTIME";
	String PMTSNPCSQL7 = "���±���ƽ������ʱ��##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,avg(MSGDEALTIME) as ����ƽ������ʱ�� from TABPMTSNPC where MSGDIRECTION = 'D' group by MSGTIME";
	String PMTSNPCSQL8 = "����������ͳ��ƽ������ʱ��##select TU.MSGTIME,TU.MSGTYPE,TU.����ƽ������ʱ��,TD.����ƽ������ʱ�� from (select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,MSGTYPE,avg(MSGFWTIME) as ����ƽ������ʱ�� from TABPMTSNPC where MSGDIRECTION = 'U' group by MSGTIME,MSGTYPE)TU,(select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,MSGTYPE,avg(MSGDEALTIME) as ����ƽ������ʱ�� from TABPMTSNPC where MSGDIRECTION = 'D' group by MSGTIME,MSGTYPE)TD where TU.MSGTIME = TD.MSGTIME and TU.MSGTYPE = TD.MSGTYPE";
	String PMTSNPCSQL9 = "����������ͳ�����ϱ���ƽ������ʱ��##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,MSGTYPE,avg(MSGFWTIME) as ����ƽ������ʱ�� from TABPMTSNPC where MSGDIRECTION = 'U' group by MSGTIME,MSGTYPE";
	String PMTSNPCSQL10 = "����������ͳ�����±���ƽ������ʱ��##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,MSGTYPE,avg(MSGDEALTIME) as ����ƽ������ʱ�� from TABPMTSNPC where MSGDIRECTION = 'D' group by MSGTIME,MSGTYPE";

	String PMTSCCPCSQL1 = "��������##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as ʱ���,count(*) as �������� from TABPMTSCCPC group by ʱ���";
	String PMTSCCPCSQL2 = "���������͵ı����ܱ���##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as ʱ���,MSGTYPE as ��������,count(*) as �������� from TABPMTSCCPC group by ʱ���,MSGTYPE";
	String PMTSCCPCSQL3 = "��λʱ�������н�����##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as ʱ���,count(distinct(PID)) as ������ from TABPMTSCCPC group by ʱ���";
	String PMTSCCPCSQL4 = "��λʱ���ڵ����̴����������##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as ʱ���,PID as ���̺�,count(*) as �������� from TABPMTSCCPC group by ʱ���,���̺�";
	String PMTSCCPCSQL5 = "����ƽ������ʱ��##select TU.MSGTIME,TU.����ƽ������ʱ��,TD.����ƽ������ʱ�� from (select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,avg(MSGDEALTIME) as ����ƽ������ʱ�� from TABPMTSCCPC where MSGDIRECTION = 'U' group by MSGTIME)TU,(select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,avg(MSGFWTIME) as ����ƽ������ʱ�� from TABPMTSCCPC where MSGDIRECTION = 'D' group by MSGTIME)TD where TU.MSGTIME = TD.MSGTIME";
	String PMTSCCPCSQL6 = "���ϱ���ƽ������ʱ��##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,avg(MSGDEALTIME) as ����ƽ������ʱ�� from TABPMTSCCPC where MSGDIRECTION = 'U' group by MSGTIME";
	String PMTSCCPCSQL7 = "���±���ƽ������ʱ��##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,avg(MSGFWTIME) as ����ƽ������ʱ�� from TABPMTSCCPC where MSGDIRECTION = 'D' group by MSGTIME";
	String PMTSCCPCSQL8 = "����������ͳ��ƽ������ʱ��##select TU.MSGTIME,TU.MSGTYPE,TU.����ƽ������ʱ��,TD.����ƽ������ʱ�� from (select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,MSGTYPE,avg(MSGFWTIME) as ����ƽ������ʱ�� from TABPMTSCCPC where MSGDIRECTION = 'U' group by MSGTIME,MSGTYPE)TU,(select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,MSGTYPE,avg(MSGDEALTIME) as ����ƽ������ʱ�� from TABPMTSCCPC where MSGDIRECTION = 'D' group by MSGTIME,MSGTYPE)TD where TU.MSGTIME = TD.MSGTIME and TU.MSGTYPE = TD.MSGTYPE";
	String PMTSCCPCSQL9 = "����������ͳ�����ϱ���ƽ������ʱ��##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,MSGTYPE,avg(MSGDEALTIME) as ����ƽ������ʱ�� from TABPMTSCCPC where MSGDIRECTION = 'U' group by MSGTIME,MSGTYPE";
	String PMTSCCPCSQL10 = "����������ͳ�����±���ƽ������ʱ��##select formatdatetime(MSGSENDTIME, 'yyyy-MM-dd HH:00') as MSGTIME,MSGTYPE,avg(MSGFWTIME) as ����ƽ������ʱ�� from TABPMTSCCPC where MSGDIRECTION = 'D' group by MSGTIME,MSGTYPE";

	String HELPINFO0 = "��־�������ͣ�֧��������־��MCTL��־��PMTSNPC��־��PMTSCCPC��־\n\n";
	String HELPINFO1 = "���ݿⱣ�淽ʽ��MEMORY:���ݿⱣ�����ڴ��У�DISK:���ݿⱣ���ڴ�����\n\n";
	String HELPINFO2 = "���ݿ������������ݿ�����Ĭ��Ϊtest�������ݿⱣ�淽ʽΪdisk�����ݿ��������������ݿ��ļ�������\n\n";
	String HELPINFO3 = "�������ݿ�Ŀ¼�����ñ������ݿ��ļ����ڵ�Ŀ¼�������ļ�ѡ����ѡ�񱾵����ݿ��ļ����ڵ�Ŀ¼��Ҳ����ֱ��ѡ�񱾵����ݿ��ļ�\n\n";
	String HELPINFO4 = "�������ݿ⣺����ֱ�����ӱ����ڵĴ����ϵı������ݿ⣬ִ��sql��ѯ���\n\n";
	String HELPINFO5 = "��־Ŀ¼��ѡ����־���ڵ�Ŀ¼\n\n";
	String HELPINFO6 = "�ļ��ؼ��֣�������־�ļ����Ĺ��˻��ƣ������Լ��༭\n\n";
	String HELPINFO7 = "������������ʼ������־��д���������ݿ���\n\n";
	String HELPINFO8 = "SQL��䣺����sql��䣬�����Լ��༭\n\n";
	String HELPINFO9 = "ִ��SQL��ִ��ǰ���sql���\n\n";
	
	
	String[] MCTLFILTER = { "NETS_", "IBPS_", "BEPS_", "HVPS_", "SAPS" };
	String[] PMTSNPCFILTER = { "success." };
	String[] PMTSCCPCFILTER = { "success." };
	String[] MCTLSQL = { MCTLSQL1, MCTLSQL2, MCTLSQL3, MCTLSQL4, MCTLSQL5, MCTLSQL6, MCTLSQL7, MCTLSQL8 };
	String[] PMTSNPCSQL = { PMTSNPCSQL1, PMTSNPCSQL2, PMTSNPCSQL3, PMTSNPCSQL4, PMTSNPCSQL5, PMTSNPCSQL6,
			PMTSNPCSQL7, PMTSNPCSQL8, PMTSNPCSQL9, PMTSNPCSQL10 };
	String[] PMTSCCPCSQL = { PMTSCCPCSQL1, PMTSCCPCSQL2, PMTSCCPCSQL3, PMTSCCPCSQL4, PMTSCCPCSQL5, PMTSCCPCSQL6,
			PMTSCCPCSQL7, PMTSCCPCSQL8, PMTSCCPCSQL9, PMTSCCPCSQL10 };
	String HELPINFO = HELPINFO0 + HELPINFO1 + HELPINFO2 + HELPINFO3 + HELPINFO4 + HELPINFO5 + HELPINFO6
			+ HELPINFO7 + HELPINFO8 + HELPINFO9;
	

	String MCTLMSGSTART = "Level 0 : "; // ��־�鿪ʼ
	String MCTLMSGEND = "] **"; // ��־���β
	String MCTLMQPUT = "���ķ���ʱ��";
	String MCTLMQRECV = "���Ķ�ȡʱ��";
	String MCTLMSGDEAL = "������ʱ��";
	String MCTLPROC = "������:";
	String PMTSMSGSTARTFLAG = "Level 0 PMTSMSGHDL:"; // PMTS��־��ʼ��־
	String MSGBODYFINSHEDFLAG = "</Document>"; // �������Ľ�����־
	String PMTSPRENODETIMEFLAG = "ǰһ�ڵ㷢��ʱ��"; // ǰһ�ڵ㷢��ʱ��
	String PMTSUHEADFLAG = "{U:"; // PMTS-Uͷ��־��Ϣ
	String PMTSUENDFLAG = "}"; // PMTS-Uͷ��β��־��Ϣ
	String IBPSMSGHEAD = "{H:01"; // ��������ͷ
	String CNAPSMSGHEAD = "{H:02"; // ��������ͷ
	String CNCCMSGHEAD = "{N:01"; // CNCC����ͷ
}