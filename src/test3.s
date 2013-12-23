.text

.global main
main:
	PUSH {lr}
	SUB sp, sp, #12
	SUB sp, sp, #16
	LDR r0, =1
	STR r0, [sp, #4]
	LDR r0, =2
	STR r0, [sp, #8]
	LDR r0, =3
	STR r0, [sp, #12]
	MOV r0, #3
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #24]
	SUB sp, sp, #16
	LDR r0, =3
	STR r0, [sp, #4]
	LDR r0, =4
	STR r0, [sp, #8]
	LDR r0, =5
	STR r0, [sp, #12]
	MOV r0, #3
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #36]
	SUB sp, sp, #12
	LDR r0, [sp, #52]
	STR r0, [sp, #4]
	LDR r0, [sp, #48]
	STR r0, [sp, #8]
	MOV r0, #2
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #52]
	ADD sp, sp, #56
	MOV r0, #0
	POP {pc}

