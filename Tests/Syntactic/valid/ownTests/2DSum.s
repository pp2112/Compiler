.data

msg_0:
	.word 44
	.ascii	"ArrayIndexOutOfBoundsError: nevative index\n\0"
msg_1:
	.word 45
	.ascii	"ArrayIndexOutOfBoundsError: index too large\n\0"
msg_2:
	.word 82
	.ascii	"OverflowError: the result is too small/large to store in a 4-byte signed-integer.\n"
msg_3:
	.word 5
	.ascii	"%.*s\0"
msg_4:
	.word 3
	.ascii	"%d\0"
msg_5:
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

f_func_intarrarr:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, =0
	STR r0, [sp]
	SUB sp, sp, #4
	LDR r0, =0
	STR r0, [sp]
L12:
	LDR r0, [sp]
	PUSH {r0}
	LDR r0, =3
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVLT r0, #1
	MOVGE r0, #0
	CMP r0, #1
	BEQ L14
	BNE L15
L13:
	LDR r0, [sp]
	PUSH {r0}
	LDR r0, =1
	MOV r1, r0
	POP {r0}
	ADDS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp]
	B L12
L14:
	SUB sp, sp, #4
	LDR r0, =0
	STR r0, [sp]
L16:
	LDR r0, [sp]
	PUSH {r0}
	LDR r0, =2
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVLT r0, #1
	MOVGE r0, #0
	CMP r0, #1
	BEQ L18
	BNE L19
L17:
	LDR r0, [sp]
	PUSH {r0}
	LDR r0, =1
	MOV r1, r0
	POP {r0}
	ADDS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp]
	B L16
L18:
	LDR r0, [sp, #8]
	PUSH {r0}
	LDR r0, [sp, #20]
	PUSH {r4}
	MOV r4, r0
	LDR r0, [sp, #8]
	BL p_check_array_bounds
	ADD r4, r4, #4
	ADD r4, r4, r0, LSL #2
	LDR r4, [r4]
	MOV r0, r4
	POP {r4}
	PUSH {r4}
	MOV r4, r0
	LDR r0, [sp, #12]
	BL p_check_array_bounds
	ADD r4, r4, #4
	ADD r4, r4, r0, LSL #2
	LDR r4, [r4]
	MOV r0, r4
	POP {r4}
	MOV r1, r0
	POP {r0}
	ADDS r0, r0, r1
	BLVS p_throw_overflow_error
	STR r0, [sp, #8]
	B L17
L19:
	ADD sp, sp, #4
	B L13
L15:
	ADD sp, sp, #4
	LDR r0, [sp]
	ADD sp, sp, #4
	POP {pc}
	.ltorg

main:
	PUSH {lr}
	SUB sp, sp, #16
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
	STR r0, [sp, #28]
	SUB sp, sp, #16
	LDR r0, =4
	STR r0, [sp, #4]
	LDR r0, =5
	STR r0, [sp, #8]
	LDR r0, =6
	STR r0, [sp, #12]
	MOV r0, #3
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #40]
	SUB sp, sp, #12
	LDR r0, [sp, #56]
	STR r0, [sp, #4]
	LDR r0, [sp, #52]
	STR r0, [sp, #8]
	MOV r0, #2
	STR r0, [sp]
	MOV r0, sp
	STR r0, [sp, #48]
	LDR r0, [sp, #48]
	STR r0, [sp, #-4]!
	BL f_func_intarrarr
	ADD sp, sp, #4
	STR r0, [sp, #44]
	LDR r0, [sp, #44]
	BL p_print_int
	BL p_print_ln
	ADD sp, sp, #60
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

p_throw_overflow_error:
	LDR r0, =msg_2
	BL p_throw_runtime_error

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

