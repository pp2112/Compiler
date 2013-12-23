.data

msg_0:
	.word 8
	.ascii	"Change n"
msg_1:
	.word 36
	.ascii	"Should print \"Change n\" once before."
msg_2:
	.word 5
	.ascii	"%.*s\0"
msg_3:
	.word 1
	.ascii	"\0"

.text

.global main
main:
	PUSH {lr}
	SUB sp, sp, #4
	LDR r0, =0
	STR r0, [sp]
L0:
	LDR r0, [sp]
	PUSH {r0}
	LDR r0, =1
	MOV r1, r0
	POP {r0}
	CMP r0, r1
	MOVNE r0, #1
	MOVEQ r0, #0
	CMP r0, #0
	BEQ L1
	LDR r0, =1
	STR r0, [sp]
	LDR r0, =msg_0
	BL p_print_string
	BL p_print_ln
	B L0
L1:
	LDR r0, =msg_1
	BL p_print_string
	BL p_print_ln
	ADD sp, sp, #4
	MOV r0, #0
	POP {pc}

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

p_print_ln:
	PUSH {lr}
	LDR r0, =msg_3
	ADD r0, r0, #4
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}

