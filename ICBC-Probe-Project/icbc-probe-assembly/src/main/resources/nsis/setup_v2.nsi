# Auto-generated by EclipseNSIS Script Wizard
# 2011-9-1 22:55:18

Name "ICBC HTTP DNS Probe V2.0"

# General Symbol Definitions
!define REGKEY "SOFTWARE\$(^Name)"

# MUI Symbol Definitions
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install-colorful.ico"
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_STARTMENUPAGE_REGISTRY_ROOT HKLM
!define MUI_STARTMENUPAGE_REGISTRY_KEY ${REGKEY}
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME StartMenuGroup
!define MUI_STARTMENUPAGE_DEFAULTFOLDER "ICBC HTTP DNS Probe V2.0"
#!define MUI_FINISHPAGE_RUN "regedit /s c:\icbc-probe\bin\install.reg"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall-colorful.ico"
!define MUI_UNFINISHPAGE_NOAUTOCLOSE

# Define customer page
!define MUI_PAGE_CUSTOMFUNCTION_SHOW MyFinishShow
!define MUI_PAGE_CUSTOMFUNCTION_LEAVE MyFinishLeave


# Included files
!include Sections.nsh
!include MUI.nsh
!include nsDialogs.nsh
!include LogicLib.nsh

# Variables
Var StartMenuGroup

Var Dialog
Var Label
Var RadioButton1
Var RadioButton2
Var RadioButton3
Var RadioButton4
Var RadioButton5
Var RadioButton6
Var networkid

# Installer pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_STARTMENU Application $StartMenuGroup
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

# Installer languages
!insertmacro MUI_LANGUAGE SimpChinese

# Installer attributes
OutFile E:\Temp\icbc_probe_setup_v2.0.exe
InstallDir c:\icbc-probe
CRCCheck on
XPStyle on
ShowInstDetails show
InstallDirRegKey HKLM "${REGKEY}" Path
ShowUninstDetails show


# Installer sections
Section -Main SEC0000
    # ICBC
    SetRebootFlag true

    SetOutPath c:\icbc-probe
    SetOverwrite on
    
    # ICBC: Move old version
    Exec 'net stop Tomcat6'
    Exec 'net stop Tomcat6'
    DetailPrint "Waiting service stop ..."
    Sleep 15000
    DetailPrint "Rename old version directory files ..."
    RmDir /r C:\icbc-probe.uninstalled
    Rename C:\icbc-probe C:\icbc-probe.uninstalled
    RmDir /r c:\icbc-probe
    
    # Copy File
    File /r C:\Users\IBM_ADMIN\workspace\ICBC-Probe-Project\icbc-probe-assembly\target\icbc-probe\*
    DetailPrint "Write registry for uninstall ..."
    WriteRegStr HKLM "${REGKEY}\Components" Main 1
SectionEnd

Section -post SEC0001
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
    SetOutPath $INSTDIR
    WriteUninstaller $INSTDIR\uninstall.exe
    !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\Uninstall $(^Name).lnk" $INSTDIR\uninstall.exe
    !insertmacro MUI_STARTMENU_WRITE_END
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayName "$(^Name)"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" Publisher "${COMPANY}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" URLInfoAbout "${URL}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayIcon $INSTDIR\uninstall.exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" UninstallString $INSTDIR\uninstall.exe
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoModify 1
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoRepair 1
    
    # ICBC
    DetailPrint "Write registry for Windows service ..."
    Exec 'regedit /s c:\icbc-probe\bin\install.reg'
    
    # copy conf file based on networkid
    CopyFiles c:\icbc-probe\conf\probe.xml.$networkid c:\icbc-probe\conf\probe.xml.ok
    
SectionEnd

# Macro for selecting uninstaller sections
!macro SELECT_UNSECTION SECTION_NAME UNSECTION_ID
    Push $R0
    ReadRegStr $R0 HKLM "${REGKEY}\Components" "${SECTION_NAME}"
    StrCmp $R0 1 0 next${UNSECTION_ID}
    !insertmacro SelectSection "${UNSECTION_ID}"
    GoTo done${UNSECTION_ID}
next${UNSECTION_ID}:
    !insertmacro UnselectSection "${UNSECTION_ID}"
done${UNSECTION_ID}:
    Pop $R0
!macroend

# Uninstaller sections
Section /o -un.Main UNSEC0000
    Exec 'net stop Tomcat6'
    DetailPrint "Waiting ..."
    Sleep 15000

    RmDir /r c:\icbc-probe
    DeleteRegValue HKLM "${REGKEY}\Components" Main
SectionEnd

Section -un.post UNSEC0001
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Uninstall $(^Name).lnk"
    Delete /REBOOTOK $INSTDIR\uninstall.exe
    DeleteRegValue HKLM "${REGKEY}" StartMenuGroup
    DeleteRegValue HKLM "${REGKEY}" Path
    DeleteRegKey /IfEmpty HKLM "${REGKEY}\Components"
    DeleteRegKey /IfEmpty HKLM "${REGKEY}"
    RmDir /REBOOTOK $SMPROGRAMS\$StartMenuGroup
    RmDir /REBOOTOK $INSTDIR
    Push $R0
    StrCpy $R0 $StartMenuGroup 1
    StrCmp $R0 ">" no_smgroup
no_smgroup:
    Pop $R0
SectionEnd

# Installer functions
Function .onInit
    InitPluginsDir    
FunctionEnd

# Uninstaller functions
Function un.onInit
    ReadRegStr $INSTDIR HKLM "${REGKEY}" Path
    !insertmacro MUI_STARTMENU_GETFOLDER Application $StartMenuGroup
    !insertmacro SELECT_UNSECTION Main ${UNSEC0000}
FunctionEnd

Function MyFinishShow
  nsDialogs::Create 1018
  Pop $Dialog

  ${If} $Dialog == error
    Abort
  ${EndIf}
  
  ${NSD_CreateLabel} 0 0 100% 12u "��ѡ���������뷽ʽ�������ݲ�ͬ�Ľ��뷽ʽ��ʹ�ò�ͬ�����ݽ��շ�������ַ."
  Pop $Label

  ${NSD_CreateRadioButton} 0 20 100% 12u "������ͨ"
  Pop $RadioButton1
  
  ${NSD_CreateRadioButton} 0 30u 100% 12u "������ͨ2"
  Pop $RadioButton2
 
  ${NSD_CreateRadioButton} 0 45u 100% 12u "��������"
  Pop $RadioButton3
   
  ${NSD_CreateRadioButton} 0 60u 100% 12u "��������2"
  Pop $RadioButton4
  
  ${NSD_CreateRadioButton} 0 75u 100% 12u "�Ϻ�����"
  Pop $RadioButton5
  
  ${NSD_CreateRadioButton} 0 90u 100% 12u "�Ϻ���ͨ"
  Pop $RadioButton6
  
  nsDialogs::Show
  
FunctionEnd

Function MyFinishLeave
# Set default value
strCpy $networkid "bj.unicom"
${NSD_GetState} $RadioButton1 $0
${If} $0 <> 0
    strCpy $networkid "bj.unicom"
    # MessageBox mb_ok "Custom radiobutton#1 was checked..."
${EndIf}
${NSD_GetState} $RadioButton2 $0
${If} $0 <> 0
    strCpy $networkid "bj.unicom2"
    # MessageBox mb_ok "Custom radiobutton#2 was checked..."
${EndIf}
${NSD_GetState} $RadioButton3 $0
${If} $0 <> 0
    strCpy $networkid "bj.telcom"
    # MessageBox mb_ok "Custom radiobutton#3 was checked..."
${EndIf}
${NSD_GetState} $RadioButton4 $0
${If} $0 <> 0
    strCpy $networkid "bj.telcom2"
    # MessageBox mb_ok "Custom radiobutton#4 was checked..."
${EndIf}
${NSD_GetState} $RadioButton5 $0
${If} $0 <> 0
    strCpy $networkid "sh.telcom"
    # MessageBox mb_ok "Custom radiobutton#5 was checked..."
${EndIf}
${NSD_GetState} $RadioButton6 $0
${If} $0 <> 0
    strCpy $networkid "sh.unicom"
    # MessageBox mb_ok "Custom radiobutton#6 was checked..."
${EndIf}
# MessageBox mb_ok $networkid
FunctionEnd