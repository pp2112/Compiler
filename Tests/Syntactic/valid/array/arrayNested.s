.data

msg_0:
	.word 44
	.ascii	"ArrayIndexOutOfBoundsError: nevative index\n\0"
msg_1:
	.word 45
	.ascii	"ArrayIndexOutOfBoundsError: index too large\n\0"
msg_2:
	.word 5
	.ascii	"%.*s\0"
msg_3:
	.word 3
	.ascii	"%d\0"
msg_4:
	.word 1
	.ascii	"\0"

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
	SUB sp, sp, #12
	LDR r0, =3
	STR r0, [sp, #4]
	LDR r0, =4
	STR r0, [sp, #8]
	MOV r0, #2
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #32]
	SUB sp, sp, #12
	LDR r0, [sp, #48]
	STR r0, [sp, #4]
	LDR r0, [sp, #44]
	STR r0, [sp, #8]
	MOV r0, #2
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #40]
	LDR r0, [sp, #40]
	PUSH {r4}
	MOV r4, r0
	LDR r0, =0
	BL p_check_array_bounds
	ADD r4, r4, #4
	ADD r4, r4, r0, LSL #2
	LDR r4, [r4]
	MOV r0, r4
	POP {r4}
	PUSH {r4}
	MOV r4, r0
	LDR r0, =2
	BL p_check_array_bounds
	ADD r4, r4, #4
	ADD r4, r4, r0, LSL #2
	LDR r4, [r4]
	MOV r0, r4
	POP {r4}
	BL p_print_int
	BL p_print_ln
	LDR r0, [sp, #40]
	PUSH {r4}
	MOV r4, r0
	LDR r0, =1
	BL p_check_array_bounds
	ADD r4, r4, #4
	ADD r4, r4, r0, LSL #2
	LDR r4, [r4]
	MOV r0, r4
	POP {r4}
	PUSH {r4}
	MOV r4, r0
	LDR r0, =0
	BL p_check_array_bounds
	ADD r4, r4, #4
	ADD r4, r4, r0, LSL #2
	LDR r4, [r4]
	MOV r0, r4
	POP {r4}
	BL p_print_int
	BL p_print_ln
	ADD sp, sp, #52
	MOV r0, #0
	POP {pc}

p_check_array_bounds:
	PUSH {lr}
	CMP r0, #0
	LDRLT r0, =msg_0
	BLLT p_throw_runtime_error
	LDR r1, [r4]
	CMP r0, r1
	LDRCS r0, =msg_1
	BLCS p_throw_runtime_error
	POP {pc}

p_throw_runtime_error:
	BL p_print_string
	MOV r0, #-1
	BL exit

p_print_string:
	PUSH {lr}
	LDR r1, [r0]
	ADD r2, r0, #4
	LDR r0, =msg_2
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_int:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_3
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_ln:
	PUSH {lr}
	LDR r0, =msg_4
	ADD r0, r0, #4
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}

