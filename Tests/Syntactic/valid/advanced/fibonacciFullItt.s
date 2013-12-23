.data

msg_0:
	.word 62
	.ascii	"This program calculates the nth fibonacci number itteratively."
msg_1:
	.word 42
	.ascii	"Please enter n (should not be too large): "
msg_2:
	.word 15
	.ascii	"The input n is "
msg_3:
	.word 28
	.ascii	"The nth fibonacci number is "
msg_4:
	.word 82
	.ascii	"OverflowError: the result is too small/large to store in a 4-byte signed-integer.\n"
msg_5:
	.word 3
	.ascii	"%d\0"
msg_6:
	.word 5
	.ascii	"%.*s\0"
msg_7:
	.word 3
	.ascii	"%d\0"
msg_8:
	.word 1
	.ascii	"\0"

.text

.global main
main:
	PUSH {lr}
	SUB sp, sp, #16
	LDR r0, =msg_0
	BL p_print_string
	BL p_print_ln
	LDR r0, =msg_1
	BL p_print_string
	LDR r0, =0
	STR r0, [sp, #12]
	ADD r0, sp, #12
	BL p_read_int
	LDR r0, =msg_2
	BL p_print_string
	LDR r0, [sp, #12]
	BL p_print_int
	BL p_print_ln
	LDR r0, =msg_3
	BL p_print_string
	LDR r0, =0
	STR r0, [sp, #8]
	LDR r0, =1
	STR r0, [sp, #4]
	LDR r0, =0
	STR r0, [sp]
L0:
	LDR r0, [sp, #12]
	PUSH {r0}
	LDR r0, =0
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVGT r0, #1
	MOVLE r0, #0
	CMP r0, #0
	BEQ L1
	LDR r0, [sp, #8]
	STR r0, [sp]
	LDR r0, [sp, #4]
	STR r0, [sp, #8]
	LDR r0, [sp]
	PUSH {r0}
	LDR r0, [sp, #8]
	MOV r1, r0
	POP {r0}
	ADDS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp, #4]
	LDR r0, [sp, #12]
	PUSH {r0}
	LDR r0, =1
	MOV r1, r0
	POP {r0}
	SUBS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp, #12]
	B L0
L1:
	LDR r0, [sp, #8]
	BL p_print_int
	BL p_print_ln
	ADD sp, sp, #16
	MOV r0, #0
	POP {pc}

p_throw_overflow_error:
	LDR r0, =msg_4
	BL p_throw_runtime_error

p_throw_runtime_error:
	BL p_print_string
	MOV r0, #-1
	BL exit

p_read_int:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_5
	ADD r0, r0, #4
	BL scanf
	POP {pc}

p_print_string:
	PUSH {lr}
	LDR r1, [r0]
	ADD r2, r0, #4
	LDR r0, =msg_6
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_int:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_7
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_ln:
	PUSH {lr}
	LDR r0, =msg_8
	ADD r0, r0, #4
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}

