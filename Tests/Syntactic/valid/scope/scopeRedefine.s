.data

msg_0:
	.word 3
	.ascii	"%d\0"
msg_1:
	.word 5
	.ascii	"true\0"
msg_2:
	.word 6
	.ascii	"false\0"
msg_3:
	.word 1
	.ascii	"\0"

.text

.global main
main:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, =1
	STR r0, [sp]
	SUB sp, sp, #1
	LDR r0, =2
	STR r0, [sp, #1]
	MOV r0, #1
	STRB r0, [sp]
	LDRSB r0, [sp]
	BL p_print_bool
	BL p_print_ln
	ADD sp, sp, #1
	LDR r0, [sp]
	BL p_print_int
	BL p_print_ln
	ADD sp, sp, #4
	MOV r0, #0
	POP {pc}

p_print_int:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_0
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_bool:
	PUSH {lr}
	CMP r0, #0
	LDRNE r0, =msg_1
	LDREQ r0, =msg_2
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_ln:
	PUSH {lr}
	LDR r0, =msg_3
	ADD r0, r0, #4
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}

