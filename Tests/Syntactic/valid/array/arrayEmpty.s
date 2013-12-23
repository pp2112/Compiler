.text

.global main
main:
	PUSH {lr}
	SUB sp, sp, #4
	SUB sp, sp, #4
	MOV r0, #0
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #4]
	ADD sp, sp, #8
	MOV r0, #0
	POP {pc}

