
INSERT INTO opcateam.user 
(`USERID`, `LOGINNAME`, `FIRSTNAME`, `LASTNAME`, `PASSWORD`, `EMAIL`, `LASTLOGINTIME`, `DESCRIPTION`, `ADMINISTRATOR`) VALUES
("root", "root", "System account", "System account", "root", "root@localhost", 1, 0, now(), "System acoount", 0);
INSERT INTO opcateam.user 
(`USERID`, `LOGINNAME`, `FIRSTNAME`, `LASTNAME`, `PASSWORD`, `EMAIL`, `LASTLOGINTIME`, `DESCRIPTION`, `ADMINISTRATOR`) VALUES
("moonwatcher", "moonwatcher", "Lior", "Galanti", "poohbear", "sgalanti@t2.technion.ac.il", now(), "Master Administrator",1);
INSERT INTO opcateam.user
(`USERID`, `LOGINNAME`, `FIRSTNAME`, `LASTNAME`, `PASSWORD`, `EMAIL`, `LASTLOGINTIME`, `DESCRIPTION`, `ADMINISTRATOR`) VALUES
("dizza", "dizza", "Dizza", "Beimel", "DB", "dizza@tx.technion.ac.il", now(), "Dizza's account",1);
INSERT INTO opcateam.user
(`USERID`, `LOGINNAME`, `FIRSTNAME`, `LASTNAME`, `PASSWORD`, `EMAIL`, `LASTLOGINTIME`, `DESCRIPTION`, `ADMINISTRATOR`) VALUES
("eran", "eran", "Eran", "Toch", "ET", "etoch@tx.technion.ac.il", now(), "Eran's account",1);


INSERT INTO opcateam.workgroup
(`WORKGROUPID`, `WORKGROUPNAME`, `CREATIONTIME`, `DESCRIPTION`) values
("workgroupgroup1","workgroupgroup1", now(),"Workgroup number 1");
INSERT INTO opcateam.workgroup
(`WORKGROUPID`, `WORKGROUPNAME`, `CREATIONTIME`, `DESCRIPTION`) values
("workgroupgroup2","workgroupgroup2", now(),"workgroup number 2");


INSERT INTO opcateam.opmmodel
(`OPMMODELID`, `OPMMODELNAME`, `WORKGROUPID`, `CREATIONTIME`, `DESCRIPTION`) values
("opmmmodel1", "opmmmodel1", "workgroupgroup1", now(), "OPM model number 1");
INSERT INTO opcateam.opmmodel
(`OPMMODELID`, `OPMMODELNAME`, `WORKGROUPID`, `CREATIONTIME`, `DESCRIPTION`) values
("opmmmodel2", "opmmmodel2", "workgroupgroup2", now(), "OPM model number 2");

INSERT INTO opcateam.revision
(`REVISIONID`, `OPMMODELID`,  `MAJORREVISION`,`MINORREVISION`, `BUILD`, `DESCRIPTION`, `COMITTERID`,`CREATIONTIME` ) values
("1110", "opmmmodel1", 1, 0, 1, "revision 1.0.1 for OPM model 1", "moonwatcher", now());
INSERT INTO opcateam.revision
(`REVISIONID`, `OPMMODELID`,  `MAJORREVISION`,`MINORREVISION`, `BUILD`, `DESCRIPTION`, `COMITTERID`,`CREATIONTIME` ) values
("1111", "opmmmodel1", 1, 1, 2, "revision 1.1.2 for OPM model 1", "dizza", now());
INSERT INTO opcateam.revision
(`REVISIONID`, `OPMMODELID`,  `MAJORREVISION`,`MINORREVISION`, `BUILD`, `DESCRIPTION`, `COMITTERID`,`CREATIONTIME` ) values
("1112", "opmmmodel1", 1, 2, 3, "revision 1.2.3 for OPM model 1","moonwatcher",now());
INSERT INTO opcateam.revision
(`REVISIONID`, `OPMMODELID`,  `MAJORREVISION`,`MINORREVISION`, `BUILD`, `DESCRIPTION`, `COMITTERID`,`CREATIONTIME` ) values
("1120", "opmmmodel1", 2, 0, 4, "revision 2.0.4 for OPM model 1", "dizza", now());


INSERT INTO opcateam.collaborative_session
(`SESSIONID`, `OPMMODELID`,`REVISIONID`,`SESSIONNAME`,`CREATIONTIME`,`DESCRIPTION`,`LASTUPDATE`,`TOKENHOLDERID`) values
("session1", "opmmmodel1","1110","session1",now(),"session number 1", now(),"null_user");

INSERT INTO opcateam.collaborative_session
(`SESSIONID`, `OPMMODELID`,`REVISIONID`,`SESSIONNAME`,`CREATIONTIME`,`DESCRIPTION`,`LASTUPDATE`,`TOKENHOLDERID`) values
("session2", "opmmmodel1","1111","session2",now(),"session number 2", now(), "null_user");

INSERT INTO opcateam.collaborative_session
(`SESSIONID`, `OPMMODELID`,`REVISIONID`,`SESSIONNAME`,`CREATIONTIME`,`DESCRIPTION`,`LASTUPDATE`,`TOKENHOLDERID`) values
("session3", "opmmmodel2","1112","session3",now(),"session number 3", now(), "null_user");

INSERT INTO opcateam.collaborative_session
(`SESSIONID`, `OPMMODELID`,`REVISIONID`,`SESSIONNAME`,`CREATIONTIME`,`DESCRIPTION`,`LASTUPDATE`,`TOKENHOLDERID`) values
("session4", "opmmmodel2","1120","session4",now(),"session number 4", now(), "null_user");

