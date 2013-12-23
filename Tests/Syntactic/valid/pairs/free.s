.text

.global main
main:
	PUSH {lr}
	SUB sp, sp, #4
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
	STR r0, [sp]
	LDR r0, [sp]
	BL p_free_pair
	ADD sp, sp, #4
	MOV r0, #0
	POP {pc}

p_free_pair:
	PUSH {lr}
	CMP r0, #0
	POPEQ {pc}
	PUSH {r0}
	LDR r0, [r0]
	BL free
	LDR r0, [sp]
	LDR r0, [r0, #4]
	BL free
	POP {r0}
	BL free
	POP {pc}

