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
	__MSG DB 'Mensaje'
	__convAux DD 0
	@_cdw0 DW 0 
	@_dda DD 0 
	@_ddb DD 0 
	@_sdd1 DB 'termino' 
	@_cdd2 DD 7.2 
	@_aux2 DD 0 
.CODE
_overflow_suma:
	invoke MessageBox, NULL, addr _msjOS, addr _msjOS, MB_OK
	invoke ExitProcess, 0
_division_cero:
	invoke MessageBox, NULL, addr _msjDC, addr _msjDC, MB_OK
	invoke ExitProcess, 0
START:
LABEL0:
	FILD @_cdw0
	FSTP __convAux
	FLD __convAux
	FSTP @_dda
LABEL1:
	FLD @_cdd2
	FSTP @_ddb
LABEL2:
	FLD @_dda
	FTST
	XOR BX, BX
	FSTSW AX 
	SAHF 
	JE _division_cero
	FLD @_ddb
	FDIVR 
	FSTP @_aux2
LABEL3:
	FLD @_aux2
	FSTP @_ddb
LABEL4:
	invoke MessageBox, NULL, addr @_sdd1, addr __MSG, MB_OK
LABEL5:
invoke ExitProcess, 0
END START
