.data

msg_0:
	.word 22
	.ascii	"An example integer is "
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
main:
	PUSH {lr}
	LDR r0, =msg_0
	BL p_print_string
	LDR r0, =189
	BL p_print_int
	BL p_print_ln
	MOV r0, #0
	POP {pc}

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

