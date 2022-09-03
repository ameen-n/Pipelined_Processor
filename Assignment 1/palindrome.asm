	.data
a:
	101
	.text
main:
	add %x0, %x0, %x10
	add %x0, %x0, %x11
	add %x0, %x0, %x12
	load %x0, $a, %x4
	add %x0, %x4, %x9
loop:
	divi %x4, 10, %x4
	muli %x11, 10, %x11
	add %x11, %x31, %x11
	bgt %x4, %x12, loop
	beq %x9, %x11, palindrome
	subi %x0, 1, %x10
	end
palindrome:
	addi %x0, 1, %x10
	end
