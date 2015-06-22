DROP TABLE version; 
DROP TABLE opds;
DROP TABLE entries; 
DROP TABLE instances; 
DROP TABLE changes; 
drop table links; 
drop table model; 


select * into temp firstVersion from instances where versionid  = 7 ; 
select * into temp seconedVersion from instances where versionid  = 8 ; 

select distinct a.key, a.extid, a.versionid , b.extid  , b.versionid
from firstVersion a  left join seconedVersion b  on a.key = b.key  
	  inner join version c on a.versionid = c.versionid ; 


select distinct a.key, a.extid, a.versionid , b.extid  , b.versionid
from seconedVersion a  left join firstVersion b  on a.key = b.key  
	  inner join version c on a.versionid = c.versionid ; 


drop table firstVersion ; 

drop table seconedVersion ; 
