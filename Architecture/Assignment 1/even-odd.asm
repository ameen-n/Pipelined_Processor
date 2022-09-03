	.data
a:
	10
	.text
main:
	add %x0, %x0, %x10
	load %x0, $a, %x4
	divi %x4, 2, %x5
	beq %x31, 1, odd
	addi %x10, 1, %x10
	end
odd:
	subi %x10, 1, %x10
	end
