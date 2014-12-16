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
	@_cdw0 DW 0 
	@_dwa DW 0 
	@_cdw1 DW 1 
	@_sdd2 DB "entro al sino, a no es 1, a vale 0", 0 
	@_sdd3 DB "error a no es 1 y entro al si", 0 
	@_sdd4 DB "fin de programa", 0 
	@_sdd5 DB "empieza de programa - evalua clausula si-sino", 0 
.CODE
_overflow_suma:
	invoke MessageBox, NULL, addr _msjOS, addr _msjOS, MB_OK
	invoke ExitProcess, 0
_division_cero:
	invoke MessageBox, NULL, addr _msjDC, addr _msjDC, MB_OK
	invoke ExitProcess, 0
START:
LABEL0:
	invoke MessageBox, NULL, addr @_sdd5, addr __MSG, MB_OK
LABEL1:
	MOV BX, @_cdw0
	MOV @_dwa, BX
LABEL2:
	MOV CX, @_cdw1
	CMP @_dwa, CX
LABEL3:
	JE LABEL5
LABEL4:
	invoke MessageBox, NULL, addr @_sdd3, addr __MSG, MB_OK
LABEL5:
	invoke MessageBox, NULL, addr @_sdd2, addr __MSG, MB_OK
LABEL6:
	invoke MessageBox, NULL, addr @_sdd4, addr __MSG, MB_OK
LABEL7:
invoke ExitProcess, 0
END START
