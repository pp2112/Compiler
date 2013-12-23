.data

msg_0:
	.word 82
	.ascii	"OverflowError: the result is too small/large to store in a 4-byte signed-integer.\n"
msg_1:
	.word 5
	.ascii	"%.*s\0"
msg_2:
	.word 3
	.ascii	"%d\0"
msg_3:
	.word 1
	.ascii	"\0"

.text

.global main
f_stdlib_abs_int:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, =1
	STR r0, [sp]
	LDR r0, [sp, #8]
	PUSH {r0}
	LDR r0, =0
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVGE r0, #1
	MOVLT r0, #0
	CMP r0, #0
	BEQ L0
	LDR r0, [sp, #8]
	STR r0, [sp]
	B L1
L0:
	LDR r0, =0
	PUSH {r0}
	LDR r0, [sp, #12]
	MOV r1, r0
	POP {r0}
	SUBS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp]
L1:
	LDR r0, [sp]
	ADD sp, sp, #4
	POP {pc}
	.ltorg

f_stdlib_pow_int_int:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, =1
	STR r0, [sp]
	LDR r0, [sp, #12]
	PUSH {r0}
	LDR r0, =0
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVEQ r0, #1
	MOVNE r0, #0
	CMP r0, #0
	BEQ L2
	LDR r0, =1
	STR r0, [sp]
	B L3
L2:
	LDR r0, [sp, #12]
	PUSH {r0}
	LDR r0, =0
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVLT r0, #1
	MOVGE r0, #0
	CMP r0, #0
	BEQ L4
	B L5
L4:
L6:
	LDR r0, [sp, #12]
	PUSH {r0}
	LDR r0, =0
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVNE r0, #1
	MOVEQ r0, #0
	CMP r0, #0
	BEQ L7
	LDR r0, [sp]
	PUSH {r0}
	LDR r0, [sp, #12]
	MOV r1, r0
	POP {r0}
	SMULL r0, r1, r0, r1
	CMP r1, r0, ASR #31
	BLNE p_throw_overflow_error
	STR r0, [sp]
	LDR r0, [sp, #12]
	PUSH {r0}
	LDR r0, =1
	MOV r1, r0
	POP {r0}
	SUBS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp, #12]
	B L6
L7:
L5:
L3:
	LDR r0, [sp]
	ADD sp, sp, #4
	POP {pc}
	.ltorg

f_stdlib_max_int_int:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, [sp, #8]
	STR r0, [sp]
	LDR r0, [sp, #8]
	PUSH {r0}
	LDR r0, [sp, #16]
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVGT r0, #1
	MOVLE r0, #0
	CMP r0, #0
	BEQ L8
	B L9
L8:
	LDR r0, [sp, #12]
	STR r0, [sp]
L9:
	LDR r0, [sp]
	ADD sp, sp, #4
	POP {pc}
	.ltorg

f_stdlib_min_int_int:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, [sp, #8]
	STR r0, [sp]
	LDR r0, [sp, #8]
	PUSH {r0}
	LDR r0, [sp, #16]
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVLT r0, #1
	MOVGE r0, #0
	CMP r0, #0
	BEQ L10
	B L11
L10:
	LDR r0, [sp, #12]
	STR r0, [sp]
L11:
	LDR r0, [sp]
	ADD sp, sp, #4
	POP {pc}
	.ltorg

f_func_int:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, =0
	STR r0, [sp]
	LDR r0, [sp, #8]
	PUSH {r0}
	LDR r0, =0
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVEQ r0, #1
	MOVNE r0, #0
	CMP r0, #0
	BEQ L12
	LDR r0, =0
	ADD sp, sp, #4
	POP {pc}
	B L13
L12:
	LDR r0, [sp, #8]
	PUSH {r0}
	LDR r0, =1
	MOV r1, r0
	POP {r0}
	SUBS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp, #-4]!
	BL f_func_int
	ADD sp, sp, #4
	STR r0, [sp]
	LDR r0, [sp]
	PUSH {r0}
	LDR r0, =2
	STR r0, [sp, #-4]!
	LDR r0, [sp, #16]
	STR r0, [sp, #-4]!
	BL f_stdlib_pow_int_int
	ADD sp, sp, #8
	MOV r1, r0
	POP {r0}
	ADDS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp]
	LDR r0, [sp]
	ADD sp, sp, #4
	POP {pc}
L13:
	LDR r0, =0
	ADD sp, sp, #4
	POP {pc}
	.ltorg

main:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, =100
	STR r0, [sp, #-4]!
	BL f_func_int
	ADD sp, sp, #4
	STR r0, [sp]
	LDR r0, [sp]
	BL p_print_int
	BL p_print_ln
	ADD sp, sp, #4
	MOV r0, #0
	POP {pc}

p_throw_overflow_error:
	LDR r0, =msg_0
	BL p_throw_runtime_error

p_throw_runtime_error:
	BL p_print_string
	MOV r0, #-1
	BL exit

p_print_string:
	PUSH {lr}
	LDR r1, [r0]
	ADD r2, r0, #4
	LDR r0, =msg_1
	ADD r0, r0, #4
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}

p_print_int:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_2
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

