.data

msg_0:
	.word 4
	.ascii	" = ("
msg_1:
	.word 2
	.ascii	", "
msg_2:
	.word 50
	.ascii	"NullReferenceError: dereference a null reference\n\0"
msg_3:
	.word 5
	.ascii	"%.*s\0"
msg_4:
	.word 3
	.ascii	"%d\0"
msg_5:
	.word 1
	.ascii	"\0"
msg_6:
	.word 3
	.ascii	"%p\0"

.text

.global main
main:
	PUSH {lr}
	SUB sp, sp, #9
	LDR r0, =10
	PUSH {r0}
	MOV r0, #4
	BL malloc
	POP {r1}
	STR r1, [r0]
	PUSH {r0}
	MOV r0, #'a'
	PUSH {r0}
	MOV r0, #1
	BL malloc
	POP {r1}
	STRB r1, [r0]
	PUSH {r0}
	MOV r0, #8
	BL malloc
	POP {r1, r2}
	STR r2, [r0]
	STR r1, [r0, #4]
	STR r0, [sp, #5]
	LDR r0, [sp, #5]
	BL p_print_reference
	LDR r0, =msg_0
	BL p_print_string
	LDR r0, [sp, #5]
	BL p_check_null_pointer
	LDR r0, [r0]
	LDR r0, [r0]
	STR r0, [sp, #1]
	LDR r0, [sp, #1]
	BL p_print_int
	LDR r0, =msg_1
	BL p_print_string
	LDR r0, [sp, #5]
	BL p_check_null_pointer
	LDR r0, [r0, #4]
	LDRSB r0, [r0]
	STRB r0, [sp]
	LDRSB r0, [sp]
	BL putchar
	MOV r0, #')'
	BL putchar
	BL p_print_ln
	ADD sp, sp, #9
	MOV r0, #0
	POP {pc}

p_check_null_pointer:
	PUSH {lr}
	CMP r0, #0
	LDREQ r0, =msg_2
	BLEQ p_throw_runtime_error
	POP {pc}

p_throw_runtime_error:
	BL p_print_string
	MOV r0, #-1
	BL exit

p_print_string:
	PUSH {lr}
	LDR r1, [r0]
	ADD r2, r0, #4
	LDR r0, =msg_3
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_int:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_4
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_ln:
	PUSH {lr}
	LDR r0, =msg_5
	ADD r0, r0, #4
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_reference:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_6
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

