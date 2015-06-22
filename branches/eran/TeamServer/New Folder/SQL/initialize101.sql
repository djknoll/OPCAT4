INSERT INTO opcateam.user
(`USERID` , `LOGINNAME`, `FIRSTNAME`, `LASTNAME`, `PASSWORD`, `EMAIL`, `LASTLOGINTIME`, `DESCRIPTION`) VALUES
("null_user", "nullUser", "Null", "User", "password", "root@localhost", now(), "null user instance");
INSERT INTO opcateam.workgroup
(`WORKGROUPID`,`WORKGROUPNAME`, `CREATIONTIME`, `DESCRIPTION`) values
("null_workgroup", "nullWorkgroup", now(), "null workgroup instance");
INSERT INTO opcateam.opmmodel
(`OPMMODELID`,`OPMMODELNAME`, `WORKGROUPID`, `CREATIONTIME`, `DESCRIPTION`) values
("null_opmmodel", "nullOpmModel", "null_workgroup", now(),"null workgroup instance");
INSERT INTO opcateam.revision
(`REVISIONID`,`OPMMODELID`,`OPMMODELFILE`,`MAJORREVISION`,`MINORREVISION`,`BUILD`,`CREATIONTIME`,`COMITTERID`,`DESCRIPTION`) VALUES
("null_revision", "null_opmmodel","",0,0,0,now(), "null_user", "Null Revision Object");
