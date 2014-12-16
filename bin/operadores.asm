.386
.MODEL flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.STACK 200h
.DATA
	__MIN DD 1.17549435e-38
	__MAX DD 3.40282347e38
	_msjOS DB "Error: Overflow en suma", 0
	_msjDC DB "Error: Division por cero", 0
	__MSG DB "Mensaje", 0 
	__convAux DD 0
	@_dwa DW 0 
	@_sdd0 DB "a=5", 0 
	@_sdd1 DB "a=5 > 4", 0 
	@_sdd2 DB "empieza de programa - evalua operadores", 0 
	@_sdd3 DB "a=5 < 6", 0 
	@_sdd4 DB "a=5 <= 4", 0 
	@_cdw5 DW 4 
	@_sdd6 DB "a=5 >= 5", 0 
	@_cdw7 DW 5 
	@_sdd8 DB "a=5 >= 6", 0 
	@_cdw9 DW 6 
	@_sdd10 DB "a=5 <= 5", 0 
	@_sdd11 DB "fin de programa", 0 
.CODE
_overflow_suma:
	invoke MessageBox, NULL, addr _msjOS, addr _msjOS, MB_OK
	invoke ExitProcess, 0
_division_cero:
	invoke MessageBox, NULL, addr _msjDC, addr _msjDC, MB_OK
	invoke ExitProcess, 0
START:
LABEL0:
	invoke MessageBox, NULL, addr @_sdd2, addr __MSG, MB_OK
LABEL1:
	MOV BX, @_cdw7
	MOV @_dwa, BX
LABEL2:
	MOV CX, @_cdw9
	CMP @_dwa, CX
LABEL3:
	JGE LABEL5
LABEL4:
	invoke MessageBox, NULL, addr @_sdd3, addr __MSG, MB_OK
LABEL5:
	MOV CX, @_cdw5
	CMP @_dwa, CX
LABEL6:
	JLE LABEL8
LABEL7:
	invoke MessageBox, NULL, addr @_sdd1, addr __MSG, MB_OK
LABEL8:
	MOV CX, @_cdw7
	CMP @_dwa, CX
LABEL9:
	JNE LABEL11
LABEL10:
	invoke MessageBox, NULL, addr @_sdd0, addr __MSG, MB_OK
LABEL11:
	MOV CX, @_cdw7
	CMP @_dwa, CX
LABEL12:
	JNE LABEL14
LABEL13:
	invoke MessageBox, NULL, addr @_sdd6, addr __MSG, MB_OK
LABEL14:
	MOV CX, @_cdw9
	CMP @_dwa, CX
LABEL15:
	JNE LABEL17
LABEL16:
	invoke MessageBox, NULL, addr @_sdd8, addr __MSG, MB_OK
LABEL17:
	MOV CX, @_cdw7
	CMP @_dwa, CX
LABEL18:
	JG LABEL20
LABEL19:
	invoke MessageBox, NULL, addr @_sdd10, addr __MSG, MB_OK
LABEL20:
	MOV CX, @_cdw5
	CMP @_dwa, CX
LABEL21:
	JG LABEL23
LABEL22:
	invoke MessageBox, NULL, addr @_sdd4, addr __MSG, MB_OK
LABEL23:
	invoke MessageBox, NULL, addr @_sdd11, addr __MSG, MB_OK
LABEL24:
invoke ExitProcess, 0
END START
