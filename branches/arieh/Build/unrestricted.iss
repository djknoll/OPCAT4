; -- Opcat.iss --
; Copies Opcat2.jar and OpcatII.mdb files and creating an icon.

; SEE THE DOCUMENTATION FOR DETAILS ON CREATING .ISS SCRIPT FILES!

[Setup]
AppName=OPCAT
AppVerName=OPCAT 4.0
AppCopyright=Copyright � 1999-2007 Opcat, Inc.

OutputBaseFilename =unrestricted
DefaultDirName={pf32}\Opcat
DefaultGroupName=Opcat
UninstallDisplayIcon={app}\Opcat.jar
Uninstallable=yes
AlwaysRestart=true
;LicenseFile=RestrictedVersionEULA.txt
;AlwaysCreateUninstallIcon =yes
;ChangesAssociations = yes

; uncomment the following line if you want your installation to run on NT 3.51 too.
; MinVersion=4,3.51
SetupLogging=false


[Tasks]
Name: desktopicon; Description: Create a &desktop icon; GroupDescription: Additional icons:; MinVersion: 4,4
Name: quicklaunchicon; Description: Create a &Quick Launch icon; GroupDescription: Additional icons:; MinVersion: 4,4; Flags: unchecked

[Files]
Source: Opcat2.jar; DestDir: {app}; Flags: ignoreversion
Source: Opcat2_32x32.ico; DestDir: {app}
Source: Opcat2_16x16.ico; DestDir: {app}



; INCLUDE SPLASH IMAGE
;Source: Splash.gif; DestDir: {app}

; INCLUDE GIF WINIMAGES
;Source: images\winimages\*.*; DestDir: {app}\images\winimages
;Source: images\alButtons\*.*; DestDir: {app}\images\alButtons

; INCLUDE GIF JAVAIMAGES AND OPCAT IMAGES IN JAVAIMAGES DIRECTORY
;Source: images\javaimages\*.gif; DestDir: {app}\images\javaimages

; INCLUDE HELP HTML FILES
Source: help\*.html; DestDir: {app}\help
Source: help\*.htm; DestDir: {app}\help
Source: help\OPM\*.htm; DestDir: {app}\help\OPM
Source: help\OPM\*.html; DestDir: {app}\help\OPM
Source: help\OPM\OPLdocs\*.htm; DestDir: {app}\help\OPM\OPLdocs
Source: help\DOC\*.*; DestDir: {app}\help\DOC
Source: help\DOC\images\*.*; DestDir: {app}\help\DOC\images
Source: help\OPL2OPD\*.*; DestDir: {app}\help\OPL2OPD
Source: help\HIO\*.*; DestDir: {app}\help\HIO
Source: help\HIO\images\*.*; DestDir: {app}\help\HIO\images
Source: help\Import\*.*; DestDir: {app}\help\Import
Source: help\Import\images\*.*; DestDir: {app}\help\Import\images
Source: help\Animation\*.*; DestDir: {app}\help\Animation


; INCLUDE HELP IMAGES
Source: help\images\*.*; DestDir: {app}\help\images

; INCLUDE EXAMPLE DATABASES FILES
Source: examples\*.*; DestDir: {app}\Examples

; INCLUDE CODE GENERATION FILES
Source: codeGenerator\*.*; DestDir: {app}\codeGenerator

; INCLUDE JAVA HELP FILES
Source: codeGenerator\opl2Java\opmTypes\*.*; DestDir: {app}\codeGenerator\opl2Java\opmTypes

; INCLUDE Lib FILES
Source: lib\*.*; DestDir: {app}\lib

Source: Opcat.Structure\*.*; DestDir: {app}\Opcat.Structure; Flags: ignoreversion recursesubdirs createallsubdirs
Source: user.bat; DestDir: {app}; Flags: ignoreversion

; INCLUDE Home folder FILES
Source: Opcat2.exe; DestDir: {app}; Flags: ignoreversion
Source: license.lic; DestDir: {app}
Source: doreq; DestDir: {app}
Source: opcat.properties; DestDir: {app}; Flags: onlyifdoesntexist
Source: opcat.bat; DestDir: {app}; Flags: ignoreversion
Source: Dataform.xlt; DestDir: {app}; Flags: ignoreversion overwritereadonly; Attribs: readonly
Source: systems.ops; DestDir: {app}; Attribs: readonly; Flags: overwritereadonly promptifolder
Source: colors.ops; DestDir: {app}; Flags: overwritereadonly promptifolder
Source: adminconsole.jar; DestDir: {app}; Flags: ignoreversion skipifsourcedoesntexist
Source: opcat.properties; DestDir: {app}; DestName: new_opcat.properties
Source: rpgconvertor.jar; DestDir: {app}; Flags: ignoreversion skipifsourcedoesntexist



[Icons]
Name: {group}\Opcat; Filename: {app}\Opcat2.jar; WorkingDir: {app}; IconFilename: {app}\Opcat2_16x16.ico
;Name: {userdesktop}\Opcat; Filename: {app}\Opcat2.exe; WorkingDir: {app}; IconFilename: {app}\Opcat2_32x32.ico; IconIndex: 0
Name: {userdesktop}\Opcat; Filename: javaw; WorkingDir: {app}; IconFilename: {app}\Opcat2_32x32.ico; IconIndex: 0; Parameters: " -Xmx1024m -jar ""{app}\Opcat2.jar"""
;Name: {userappdata}\Microsoft\Internet Explorer\Quick Launch\Opcat; Filename: {app}\Opcat2.exe; WorkingDir: {app}; IconFilename: {app}\Opcat2_32x32.ico; IconIndex: 0
Name: {userappdata}\Microsoft\Internet Explorer\Quick Launch\Opcat; Filename: "javaw "; WorkingDir: {app}; IconFilename: {app}\Opcat2_32x32.ico; IconIndex: 0; Parameters: "-Xmx1024m -jar ""{app}\Opcat2.jar"""; Tasks: 

[Dirs]
Name: {app}\backup; Flags: uninsneveruninstall; attribs: hidden
Name: {app}\models; Flags: uninsneveruninstall; Tasks: 
Name: {app}\icons; Flags: uninsneveruninstall


[Run]

[Registry]
Root: HKCR; SubKey: .opx; ValueType: string; ValueData: OPX; Flags: uninsdeletekey
Root: HKCR; SubKey: OPX; ValueType: string; ValueData: OPCAT Model File; Flags: uninsdeletekey
Root: HKCR; SubKey: OPX\Shell\Open\Command; ValueType: string; ValueData: "{app}\Opcat2.exe ""%1"""; Flags: uninsdeletevalue
Root: HKCR; Subkey: OPX\DefaultIcon; ValueType: string; ValueData: {app}\Opcat2_32x32.ico,0; Flags: uninsdeletevalue
Root: HKCR; SubKey: .opz; ValueType: string; ValueData: OPZ; Flags: uninsdeletekey
Root: HKCR; SubKey: OPZ; ValueType: string; ValueData: OPCAT Model Zipped File; Flags: uninsdeletekey
Root: HKCR; SubKey: OPZ\Shell\Open\Command; ValueType: string; ValueData: "{app}\Opcat2.exe ""%1"""; Flags: uninsdeletevalue
Root: HKCR; Subkey: OPZ\DefaultIcon; ValueType: string; ValueData: {app}\Opcat2_32x32.ico,0; Flags: uninsdeletevalue

Root: HKLM; Subkey: SYSTEM\CurrentControlSet\Control\Session Manager\Environment; ValueType: string; ValueName: OPCAT_HOME; ValueData: {app}; Flags: uninsdeletevalue
Root: HKCU; Subkey: Environment; ValueType: none; ValueName: OPCAT_HOME; ValueData: {app}; Flags: deletevalue uninsdeletevalue

[INI]
Filename: {app}\objectprocess.url; Section: InternetShortcut; Key: URL; String: http://www.opcat.com

[UninstallDelete]
Type: files; Name: {app}\objectprocess.url


[Code]
var
  ServersPage , NamesPage: TInputQueryWizardPage;


procedure InitializeWizard;
begin
  ServersPage := CreateInputQueryPage(wpInfoAfter,
    'Servers Information', '',
    'Please specify servers address, database name and MC root name, then click Next.');

  NamesPage := CreateInputQueryPage(ServersPage.ID,
    'Database and MC Information', '',
    'Please specify MC root name and Database name, then click Next.');

  // Add items (False means it's not a password edit)
  ServersPage.Add('DB Server:', False);
  ServersPage.Add('MC Server:', False);
  ServersPage.Add('Vision Server:', False);

  NamesPage.Add('MC root name:', False);
  NamesPage.Add('Database name:', False);


  // Set initial values (optional)
  ServersPage.Values[0] := 'localhost' ; //ExpandConstant('{sysuserinfoname}');
  ServersPage.Values[1] := 'localhost' ; //ExpandConstant('{sysuserinfoorg}');
  ServersPage.Values[2] := 'localhost' ; //ExpandConstant('{sysuserinfoorg}');

  NamesPage.Values[0] := 'Systems' ; //ExpandConstant('{sysuserinfoorg}');
  NamesPage.Values[1] := 'opcat' ; //ExpandConstant('{sysuserinfoorg}');



end ;

procedure CurStepChanged(CurStep: TSetupStep);
var
  FileString : AnsiString ;
  DBServer, MCServer , VisionServer , Path , MCName, DBName: String;
begin

if CurStep=ssDone then
begin
    Path :=  WizardDirValue() ;
    DBServer := ServersPage.Values[0];
    MCServer := ServersPage.Values[1];
    VisionServer := ServersPage.Values[2];

    MCName := NamesPage.Values[0];
    DBName := NamesPage.Values[1];


    if LoadStringFromFile(Path + '\opcat.properties', FileString) then
    begin
      StringChangeEx(Path , '\' , '\\' , True);
      StringChangeEx(FileString, '%OPCAT_HOME%', Path , True);
      StringChangeEx(FileString, '%DB_SERVER%', DBSErver , True);
      StringChangeEx(FileString, '%MC_SERVER%', MCServer , True);
      StringChangeEx(FileString, '%VISION_SERVER%', VisionServer , True);
	  StringChangeEx(FileString, '%MC_NAME%', MCName , True);
      StringChangeEx(FileString, '%DB_NAME%', DBName , True);
      StringChangeEx(Path, '\\', '\' , True);
      SaveStringToFile(Path + '\opcat.properties', FileString, False) ;
    end ;

    if LoadStringFromFile(Path + '\user.bat', FileString) then
    begin
      StringChangeEx(FileString, '%OPCAT_HOME%', Path , True);
      SaveStringToFile(Path + '\user.bat', FileString, False) ;
    end ;

    if LoadStringFromFile(Path + '\opcat.bat', FileString) then
    begin
      StringChangeEx(FileString, '%OPCAT_HOME%', Path , True);
      SaveStringToFile(Path + '\opcat.bat', FileString, False) ;
    end ;

end;

end ;






