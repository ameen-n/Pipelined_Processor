	.data
a:
	10
	.text
main:
	addi %x0, 2, %x5
	addi %x0, 1, %x7
	add %x0, %x0, %x10
	load %x0, $a, %x4
	beq %x4, %x7, prime
loop:
	div %x4, %x5, %x6
	beq %x31, 0, prime
	addi %x5, 1, %x5
	bgt %x4, %x5, loop
	addi %x0, 1, %x10
	end
prime:
	subi %x0, 1, %x10
	end
