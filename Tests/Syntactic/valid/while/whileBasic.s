.text

.global main
main:
	PUSH {lr}
L0:
	MOV r0, #0
	CMP r0, #0
	BEQ L1
	B L0
L1:
	MOV r0, #0
	POP {pc}

