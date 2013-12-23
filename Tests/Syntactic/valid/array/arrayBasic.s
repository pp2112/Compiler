.text

.global main
main:
	PUSH {lr}
	SUB sp, sp, #4
	SUB sp, sp, #8
	LDR r0, =0
	STR r0, [sp, #4]
	MOV r0, #1
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #8]
	ADD sp, sp, #12
	MOV r0, #0
	POP {pc}

