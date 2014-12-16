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
	@_cdw0 DW 1 
	@_sdd1 DB "Empieza programa de prueba", 0 
	@_dwcontador DW 0 
	@_cdw2 DW 5 
	@_sdd3 DB "entro al for", 0 
	@_dwaux4 dw 0 
.CODE
_overflow_suma:
	invoke MessageBox, NULL, addr _msjOS, addr _msjOS, MB_OK
	invoke ExitProcess, 0
_division_cero:
	invoke MessageBox, NULL, addr _msjDC, addr _msjDC, MB_OK
	invoke ExitProcess, 0
START:
LABEL0:
	invoke MessageBox, NULL, addr @_sdd1, addr __MSG, MB_OK
LABEL1:
	MOV BX, @_cdw0
	MOV @_dwcontador, BX
LABEL2:
	MOV CX, @_cdw2
	CMP @_dwcontador, CX
LABEL3:
	JGE LABEL8
LABEL4:
	MOV BX, @_dwcontador
	ADD BX, @_cdw0
	JO _overflow_suma
	MOV @_dwaux4, BX
LABEL5:
	MOV BX, @_dwaux4
	MOV @_dwcontador, BX
LABEL6:
	invoke MessageBox, NULL, addr @_sdd3, addr __MSG, MB_OK
LABEL7:
	JMP LABEL2
LABEL8:
invoke ExitProcess, 0
END START
