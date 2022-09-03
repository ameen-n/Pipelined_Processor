	.data
n:
	10
	.text
main:
	load %x0, $n, %x7
	addi %x0, 1, %x8
	add %x0, %x0, %x4
	addi %x0, 65535, %x31
	store %x4, 0, %x31
	beq %x7, %x8, exit
	addi %x8, 1, %x8
	subi %x31, 1, %x31
	addi %x4, 1, %x5
	store %x5, 0, %x31
	beq %x7, %x8, exit
loop:
	beq %x8, %x7, exit
	add %x4, %x5, %x6
	subi %x31, 1, %x31
	store %x6, 0, %x31
	addi %x5, 0, %x4
	addi %x6, 0, %x5
	addi %x8, 1, %x8
	jmp loop
exit:
	end
